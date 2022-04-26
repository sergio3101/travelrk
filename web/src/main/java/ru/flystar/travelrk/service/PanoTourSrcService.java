package ru.flystar.travelrk.service;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.flystar.travelrk.domain.persistents.PanoTourScene;
import ru.flystar.travelrk.domain.persistents.PanoTourSrc;
import ru.flystar.travelrk.repositories.PanoTourSceneRepository;
import ru.flystar.travelrk.repositories.PanoTourSrcRepository;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml3;

@Service
@Log4j
public class PanoTourSrcService {
    private final PanoTourSrcRepository panoTourSrcRepository;
    private final PanoTourSceneRepository panoTourSceneRepository;

    private static DocumentBuilderFactory dbFactory;
    private static DocumentBuilder dBuilder;

    @PostConstruct
    private void setup() {
        try {
            dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setValidating(true);
            dbFactory.setIgnoringComments(true);
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.error(e);
        }
    }

    @Autowired
    public PanoTourSrcService(PanoTourSrcRepository panoTourSrcRepository, PanoTourSceneRepository panoTourSceneRepository) {
        this.panoTourSrcRepository = panoTourSrcRepository;
        this.panoTourSceneRepository = panoTourSceneRepository;
    }

    public List<PanoTourSrc> getAllPanoTourSrc(boolean sortByName) {
        return sortByName?
                panoTourSrcRepository.findAll(new Sort(Sort.Direction.ASC, "name")):
                panoTourSrcRepository.findAll(new Sort(Sort.Direction.ASC, "dateOfCreate"));
    }

    public PanoTourSrc getPanoTourSrcById(int id) {
        return panoTourSrcRepository.findOne(id);
    }

    public PanoTourSrc getPanoTourSrcByPage(String path) {
        return panoTourSrcRepository.findByPathEndingWith(path);
    }

    public PanoTourSrc getPanoTourSrcByName(String name) {
        return panoTourSrcRepository.findByPathStartingWith(name);
    }

    @Transactional
    public void updatePanoTourSrc(PanoTourSrc tour) {
        panoTourSrcRepository.saveAndFlush(tour);
    }

    @Transactional
    public void removePanoTourSrc(Integer id) {
        panoTourSrcRepository.delete(id);
    }

    public PanoTourSrc createPanoTourSrc(String panotoururl) {
        PanoTourSrc panoTourSrc = new PanoTourSrc();
        getResources(panoTourSrc, panotoururl);
        genScensByTour(panoTourSrc);
        return panoTourSrcRepository.saveAndFlush(panoTourSrc);
    }

    @Transactional
    public PanoTourSrc reloadPanoTourSrc(Integer id) {
        PanoTourSrc panoTourSrc = getPanoTourSrcById(id);
        String path = panoTourSrc.getPath();
        String panotoururl = path.substring(0, path.lastIndexOf("/"));
        getResources(panoTourSrc, panotoururl);
        updateScensByTour(panoTourSrc);
        return panoTourSrcRepository.saveAndFlush(panoTourSrc);
    }

    private void getResources(PanoTourSrc panoTourSrc, String panotoururl) {
        try {
            String name = panotoururl.substring(panotoururl.lastIndexOf("/") + 1);
            String srcXmlPath = panotoururl + "/" + StringUtils.capitalize(name) + "data/" + StringUtils.capitalize(name) + ".xml";
            String msgXmlPath = panotoururl + "/" + StringUtils.capitalize(name) + "data/" + StringUtils.capitalize(name) + "_messages_ru.xml";
            String srcXml = Resources.toString(new URL(srcXmlPath), Charsets.UTF_8);
            String msgXml = Resources.toString(new URL(msgXmlPath), Charsets.UTF_8);
            srcXml = srcXml.replaceAll("(?m)^[ \t]*\r?\n", "");
            srcXml = srcXml.replaceAll("<front url=\"", "<front url=\"%FIRSTXML%/");
            srcXml = srcXml.replaceAll("<right url=\"", "<right url=\"%FIRSTXML%/");
            srcXml = srcXml.replaceAll("<back  url=\"", "<back url=\"%FIRSTXML%/");
            srcXml = srcXml.replaceAll("<left  url=\"", "<left url=\"%FIRSTXML%/");
            srcXml = srcXml.replaceAll("<up    url=\"", "<up url=\"%FIRSTXML%/");
            srcXml = srcXml.replaceAll("<down  url=\"", "<down url=\"%FIRSTXML%/");
            if (!srcXml.isEmpty()) {
                panoTourSrc.setPath(panotoururl + "/" + StringUtils.capitalize(name) + "data");
                panoTourSrc.setSrcXml(srcXml);
                panoTourSrc.setMsgXml(msgXml);
                panoTourSrc.setDateOfCreate(new Date());
            }
        } catch (IOException e) {
            log.error(e);
        }
    }

    public void genScensByTour(PanoTourSrc tour) {
        Map<String, String> titles = getTitlesMapAndSetDescription(tour);

        List<PanoTourScene> scenes = getPanoTourScenes(tour, titles);

        if (tour.getScenes() == null || tour.getScenes().size() == 0) {
            tour.setScenes(scenes);
        }
    }

    public void updateScensByTour(PanoTourSrc tour) {
        Map<String, String> titles = getTitlesMapAndSetDescription(tour);
        List<PanoTourScene> scenes = getPanoTourScenes(tour, titles);
        Map<String, PanoTourScene> xmlScenasMap = scenes.stream()
                .collect(Collectors.toMap(PanoTourScene::getName, s -> s));
        Iterator<PanoTourScene> si = tour.getScenes().iterator();
        while (si.hasNext()) {
            PanoTourScene cur = si.next();
            if (xmlScenasMap.containsKey(cur.getName())) {
                PanoTourScene xmlScena = xmlScenasMap.get(cur.getName());
                cur.setDir(xmlScena.getDir());
                cur.setTitle(xmlScena.getTitle());
                xmlScenasMap.remove(cur.getName());
            } else {
                panoTourSceneRepository.delete(cur.getId());
                si.remove();
            }
        }
        if (xmlScenasMap.values().size() > 0) {
            tour.getScenes().addAll(xmlScenasMap.values());
        }
    }

    private Map<String, String> getTitlesMapAndSetDescription(PanoTourSrc tour) {
        Map<String, String> titles = new HashMap<>();
        Document messages = getDocumentFromString(tour.getMsgXml());
        Element rootMessTag = messages.getDocumentElement();
        NodeList messNodeList = rootMessTag.getChildNodes();
        for (int m = 0; m < messNodeList.getLength(); m++) {
            Node messNode = messNodeList.item(m);
            if (messNode instanceof Element && messNode.getNodeName().equals("data")) {
                String dataName = ((Element) messNode).getAttribute("name");
                String text;
                if (dataName.contains("title")) {
                    text = messNode.getFirstChild().getNodeValue();
                    titles.put(dataName, text);
                }
                if (dataName.equals("ru_project_description")) {
                    tour.setDescription(getShortStr(messNode.getFirstChild().getNodeValue(), 256));
                }
            }
        }
        return titles;
    }

    private List<PanoTourScene> getPanoTourScenes(PanoTourSrc tour, Map<String, String> titles) {
        List<PanoTourScene> scenes = new ArrayList<>();
        Document doc = getDocumentFromString(tour.getSrcXml());
        Element krpanoTag = doc.getDocumentElement();
        NodeList nodeList = krpanoTag.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNode = nodeList.item(i);
            if (childNode instanceof Element && childNode.getNodeName().equals("scene")) {
                PanoTourScene scene = new PanoTourScene();
                scene.setName(((Element) childNode).getAttribute("name"));
                Node previewEl = ((Element) childNode).getElementsByTagName("preview").item(0);
                String url = ((Element) previewEl).getAttribute("url");
                if (((Element) childNode).hasAttribute("latitude") && ((Element) childNode).hasAttribute("longitude")) {
                    scene.setLatitude(((Element) childNode).getAttribute("latitude"));
                    scene.setLongitude(((Element) childNode).getAttribute("longitude"));
                }
                url = url.replace("%FIRSTXML%/", "");
                url = url.replace("/preview.jpg", "");
                scene.setDir(url);
                scene.setPanoTourSrc(tour);
                scene.setTitle(titles.get("ru_" + scene.getName() + "_title"));
                scenes.add(scene);
            }
        }
        return scenes;
    }

    public String getScenaXml(PanoTourScene scena) {
        PanoTourSrc tour = panoTourSrcRepository.findOne(scena.getPanoTourSrc().getId());
        String stringXml;
        String tourPath = tour.getPath();
        String tourData = tourPath.replace(".html", "data/");
        Document doc = getDocumentFromString(tour.getSrcXml());
        Document scnDoc = getNewDocument();
        NodeList scenes = doc.getElementsByTagName("scene");
        Element scene = null;
        for (int s = 0; s < scenes.getLength(); s++) {
            Element sc = (Element) scenes.item(s);
            if (sc.getAttribute("name").equals(scena.getName())) {
                scene = sc;
                break;
            }
        }
        if (scene != null) {
            scnDoc.appendChild(scnDoc.adoptNode(scene.cloneNode(true)));
            Element root = scnDoc.getDocumentElement();
            NamedNodeMap attrs = root.getAttributes();
            Attr name = (Attr) attrs.getNamedItem("name");
            while (attrs.getLength() > 0)
                attrs.removeNamedItem(attrs.item(0).getNodeName());
            root.setAttribute("name", name.getValue());
            NodeList childs = root.getChildNodes();
            for (int i = 0; i < childs.getLength(); i++) {
                Node ch = childs.item(i);
                if (ch.getNodeType() == Node.ELEMENT_NODE) {
                    if (!ch.getNodeName().equals("preview")
                            && !ch.getNodeName().equals("image")) {
                        root.removeChild(childs.item(i));
                    }
                }
            }
            Element preview = (Element) root.getElementsByTagName("preview").item(0);
            String url = preview.getAttribute("url");
            url = url.replace("%FIRSTXML%", tourData);
            preview.setAttribute("url", url);

            String north = scena.getNorth() == null || scena.getNorth().isEmpty() ? "0" : scena.getNorth();
            Element image = (Element) root.getElementsByTagName("image").item(0);
            image.setAttribute("prealign", "0|" + north + "|0");

            Element hotspot = scnDoc.createElement("hotspot");
            hotspot.setAttribute("name", "spotNorth");
            hotspot.setAttribute("url", "/images/red-north-arrow2.png");
            hotspot.setAttribute("ath", "0");
            hotspot.setAttribute("atv", "20");
            hotspot.setAttribute("ondown", "draghotspot();");
            root.appendChild(hotspot);
        }

        stringXml = transformXmlToString(scnDoc);
        stringXml = stringXml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "");
        stringXml = stringXml.replaceAll("(?m)^[ \t]*\r?\n", "");
        stringXml = stringXml.replaceAll("<front url=\"%FIRSTXML%", "<front url=\"" + tourData);
        stringXml = stringXml.replaceAll("<right url=\"%FIRSTXML%", "<right url=\"" + tourData);
        stringXml = stringXml.replaceAll("<back url=\"%FIRSTXML%", "<back url=\"" + tourData);
        stringXml = stringXml.replaceAll("<left url=\"%FIRSTXML%", "<left url=\"" + tourData);
        stringXml = stringXml.replaceAll("<up url=\"%FIRSTXML%", "<up url=\"" + tourData);
        stringXml = stringXml.replaceAll("<down url=\"%FIRSTXML%", "<down url=\"" + tourData);

        return stringXml;
    }

    private Document getNewDocument() {
        return dBuilder.newDocument();
    }

    private String transformXmlToString(Document doc) {
        String strXml = "";
        try {
            Writer out;
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            out = new StringWriter();
            tf.transform(new DOMSource(doc), new StreamResult(out));
            strXml = out.toString();
        } catch (TransformerFactoryConfigurationError | IllegalArgumentException | TransformerException e) {
            log.info(e.getMessage());
        }
        return strXml;
    }

    private String getShortStr(String str, int size) {
        String result = Jsoup.parse(str).text();
        result = escapeHtml3(Jsoup.parse(result).text());
        result = result.substring(0, Math.min(size, result.length()));
        return result;
    }

    private Document getDocumentFromString(String stringXml) {
        Document doc = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(new ByteArrayInputStream(stringXml.getBytes()));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            log.info(e.getMessage());
        }
        return doc;
    }
}
