package ru.flystar.travelrk.service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml3;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.flystar.travelrk.domain.nopersist.Hotspot;
import ru.flystar.travelrk.domain.nopersist.HotspotScene;
import ru.flystar.travelrk.domain.persistents.CustomerInfo;
import ru.flystar.travelrk.domain.persistents.ExclusiveTour;
import ru.flystar.travelrk.domain.persistents.PanoTourRenta;
import ru.flystar.travelrk.domain.persistents.PanoTourScene;
import ru.flystar.travelrk.domain.persistents.Panorama;
import ru.flystar.travelrk.domain.persistents.Region;
import ru.flystar.travelrk.domain.persistents.RentaTour;
import ru.flystar.travelrk.domain.persistents.Scene;
import ru.flystar.travelrk.repositories.ExclusiveTourRepository;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 11.12.2017.
 */
@SuppressWarnings("Duplicates")
@Log4j
@Service
public class XmlParserService {
  @Value("${pos.defvangle}")
  private double defVangle;
  @Value("${pos.defposvangle}")
  private double defPosVangle;
  @Value("${pos.dcoef}")
  private double dCoef;
  @Value("${pos.defheight}")
  private double defHeight;
  @Value("${pos.minhorangle}")
  private double minHorAngle;

  @Value("${path.exclusivetour}")
  private String PATH_EXCLUSIVE_TOUR;
  @Value("${url.exclusivetour}")
  private String URL_EXCLUSIVE_TOUR;
  @Value("${path.pano}")
  private String PATH_PANO;
  @Value("${path.logo}")
  private String PATH_LOGO;
  @Value("${path.customerlogos}")
  private String PATH_CUSTOMER_LOGO;

  private final ExclusiveTourRepository exclusiveTourRepository;

  private static DocumentBuilderFactory dbFactory;
  private static DocumentBuilder dBuilder;
  private static final Pattern startScenePattern = Pattern.compile("[^,]\\s+set\\(startscene, ([^\\(\\)]*)\\);");
  ;

  @PostConstruct
  private void setup() {
    try {
      dbFactory = DocumentBuilderFactory.newInstance();
      dbFactory.setValidating(true);
      dbFactory.setIgnoringComments(true);
      dBuilder = dbFactory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
  }

  @Autowired
  public XmlParserService(ExclusiveTourRepository exclusiveTourRepository) {
    this.exclusiveTourRepository = exclusiveTourRepository;
  }

  public String getPanoXmlFromFile(String panoPath) {
    return readFile(PATH_PANO + panoPath + "/panorama.xml", StandardCharsets.UTF_8);
  }

  public String getExclTourXmlFromFile(String tourPath) {
    return readFile(tourPath, StandardCharsets.UTF_8);
  }

  public void updatePanoXml(Panorama panorama) {
    Document doc = getDocumentFromString(panorama.getSceneXml());
    Element preview = (Element) doc.getElementsByTagName("preview").item(0);
    if (preview != null && !preview.getAttribute("url").contains(panorama.getPanoPath())) {
      String url = preview.getAttribute("url");
      preview.setAttribute("url", "/pano/" + panorama.getPanoPath() + "/" + url);
    }
    NodeList cubes = doc.getElementsByTagName("cube");
    if (cubes != null) {
      for (int c = 0; c < cubes.getLength(); c++) {
        Element cube = (Element) cubes.item(c);
        if (!cube.getAttribute("url").contains(panorama.getPanoPath())) {
          String url = cube.getAttribute("url");
          cube.setAttribute("url", "/pano/" + panorama.getPanoPath() + "/" + url);
        }
      }
    }
    Node childNode = doc.getDocumentElement();
    if (childNode != null && childNode.getNodeName().equals("scene")) {
      if (!panorama.getLatitude().isEmpty() && !panorama.getLongitude().isEmpty()) {
        ((Element) childNode).setAttribute("lat", panorama.getLatitude());
        ((Element) childNode).setAttribute("lng", panorama.getLongitude());
        ((Element) childNode).setAttribute("heading", "0.0");
      }
      ((Element) childNode).setAttribute("name", "scene_" + panorama.getPanoPath());
      if (panorama.getPanoScan() == null || panorama.getPanoScan().isEmpty())
        panorama.setPanoScan(((Element) childNode).getAttribute("title"));
      ((Element) childNode).setAttribute("title", panorama.getTitle());
      ((Element) childNode).setAttribute("thumburl", "/pano/" + panorama.getPanoPath() + "/bigthumb.jpg");
      NodeList sceneChilds = childNode.getChildNodes();
      for (int k = 0; k < sceneChilds.getLength(); k++) {
        Node sceneChild = sceneChilds.item(k);
        if (sceneChild.getNodeName().equals("image")) {
          if (!panorama.getNorth().isEmpty()) {
            ((Element) sceneChild).setAttribute("prealign", panorama.getVa() + "|" + panorama.getNorth() + "|" + panorama.getVc());
          }
        }
      }
      if (panorama.isACompassOn()) {
        Element compasPlugin = doc.createElement("include");
        compasPlugin.setAttribute("url", "/krengine/plugins/compass.xml");
        childNode.appendChild(compasPlugin);
      }
    }
    Element view;
    if (doc.getElementsByTagName("view").getLength() == 0) {
      view = doc.createElement("view");
      childNode.appendChild(view);
    } else {
      view = (Element) doc.getElementsByTagName("view").item(0);
    }
    if (!panorama.getHLookAt().isEmpty()) view.setAttribute("hlookat", panorama.getHLookAt());
    if (!panorama.getVLookAt().isEmpty()) view.setAttribute("vlookat", panorama.getVLookAt());
    if (!panorama.getFov().isEmpty()) view.setAttribute("fov", panorama.getFov());
    String strXml = transformXmlToString(doc);
    if (!panorama.isACompassOn())
      strXml = strXml.replaceAll("<include url=\"/krengine/plugins/compass.xml\"/>", "");
    panorama.setSceneXml(strXml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", ""));
  }

  public String parseExclusiveTourXml(String tourName, String tourPage, String tourXML, String scn, boolean tr) {
    String strXml;
    ExclusiveTour tour = exclusiveTourRepository.findByPathEndingWith(tourName + "/" + tourPage + ".html");
    Document doc = getDocumentFromString(tour.getKrpanoXml());
    Element krpanoTag = doc.getDocumentElement();
    Element travelrkSkin = doc.createElement("include");
    travelrkSkin.setAttribute("name", "travelrk_skin");
    travelrkSkin.setAttribute("url", "/krengine/skin/travelrk_skin_renta.xml");
    krpanoTag.insertBefore(travelrkSkin, doc.getElementsByTagName("action").item(0));
    if (!scn.isEmpty()) {
      krpanoTag.setAttribute("startscene", scn);
      NodeList nodeList = krpanoTag.getChildNodes();
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node childNode = nodeList.item(i);
        if (childNode instanceof Element && (childNode.getNodeName().equals("krpano") && !((Element) childNode).getAttribute("tour_firstlittleplanet").isEmpty())) {
          ((Element) childNode).setAttribute("tour_firstlittleplanet", "false");
          ((Element) childNode).setAttribute("tour_firststartactiondone", "true");
        }
        if (childNode instanceof Element && (childNode.getNodeName().equals("include") && ((Element) childNode).getAttribute("url").contains(tourXML + "_skin.xml"))) {
          childNode.getParentNode().removeChild(childNode);
        }
        if (childNode instanceof Element && childNode.getNodeName().equals("panoramagroup")) {
          childNode.getParentNode().removeChild(childNode);
        }
        if (childNode instanceof Element && childNode.getNodeName().equals("scene")) {
          if (!((Element) childNode).getAttribute("name").equals(scn)) {
            childNode.getParentNode().removeChild(childNode);
          } else {
            NodeList scnChild = childNode.getChildNodes();
            for (int k = 0; k < scnChild.getLength(); k++) {
              Node childScn = scnChild.item(k);
              if (childScn instanceof Element && (
                  childScn.getNodeName().equals("hotspot") || childScn.getNodeName().equals("action") || childScn.getNodeName().equals("events") || childScn.getNodeName().equals("lensflare")
              )) {
                childScn.getParentNode().removeChild(childScn);
              }
            }

          }
        }
      }
    }
    modifyXmlForRenta(tour, doc, krpanoTag);
    strXml = transformXmlToString(doc);
    return strXml;
  }

  private void modifyXmlForRenta(ExclusiveTour tour, Document doc, Element krpanoTag) {
    if (tour.isARenta() && tour.getHsForRenta().size() > 0) {
      String backgroundsound = "";
      String group = "";
      NodeList scenes = doc.getElementsByTagName("scene");
      List<Scene> listActiveScenes = new ArrayList<>();
      String scStyle = null;
      for (int sc = 0; sc < scenes.getLength(); sc++) {
        Node scene = scenes.item(sc);
        if (scene.getNodeType() == Node.ELEMENT_NODE) {
          Scene curScene = getSceneByName(tour.getScenes(), ((Element) scene).getAttribute("name"));
          if (curScene != null) {
            listActiveScenes.add(curScene);
            if (scStyle == null) {
              String hotSpNameForStyle = null;
              NodeList actsForStyle = krpanoTag.getElementsByTagName("action");
              for (int ai = 0; ai < actsForStyle.getLength(); ai++) {
                Node actForStyle = actsForStyle.item(ai);
                if (actForStyle.getNodeType() == Node.ELEMENT_NODE) {
                  if (actForStyle.getTextContent().contains("mainloadscene(" + curScene.getName() + ")")) {
                    hotSpNameForStyle = ((Element) actForStyle).getAttribute("name");
                  }
                }
              }
              if (hotSpNameForStyle != null) {
                NodeList hssForStyle = krpanoTag.getElementsByTagName("hotspot");
                for (int ai = 0; ai < hssForStyle.getLength(); ai++) {
                  Node hsForStyle = hssForStyle.item(ai);
                  if (hsForStyle.getNodeType() == Node.ELEMENT_NODE) {
                    if (((Element) hsForStyle).getAttribute("onclick").equals(hotSpNameForStyle)) {
                      scStyle = ((Element) hsForStyle).getAttribute("style");
                    }
                  }
                }
              }
            }
            backgroundsound = ((Element) scene).getAttribute("backgroundsound");
            group = ((Element) scene).getAttribute("group");
            String north = curScene.getNorth() == null || curScene.getNorth().isEmpty() ? "0" : curScene.getNorth();
            NodeList sceneChilds = scene.getChildNodes();
            for (int sn = 0; sn < sceneChilds.getLength(); sn++) {
              Node sceneChild = sceneChilds.item(sn);
              if (sceneChild.getNodeType() == Node.ELEMENT_NODE) {
                if (sceneChild.getNodeName().equals("image")) {
                  ((Element) sceneChild).setAttribute("prealign", "0|" + north + "|0");
                }
                if (sceneChild.getNodeName().equals("hotspot") && ((Element) sceneChild).hasAttribute("ath")) {
                  double curAth = Double.parseDouble(((Element) sceneChild).getAttribute("ath"));
                  double ath = Double.parseDouble(north) + curAth;
                  ((Element) sceneChild).setAttribute("ath", String.valueOf(ath));
                }
                if (sceneChild.getNodeName().equals("action")) {
                  if (((Element) sceneChild).getAttribute("name").equals("hidepanopointspots")) {
                    StringBuilder actionText = new StringBuilder(sceneChild.getTextContent());
                    for (Panorama p : tour.getHsForRenta()) {
                      actionText.append("\tset(hotspot[spot_scene_").append(p.getPanoPath()).append("].visible, false);\n");
                    }
                    sceneChild.setTextContent(actionText.toString());
                  }
                  if (((Element) sceneChild).getAttribute("name").equals("showpanopointspots")) {
                    StringBuilder actionText = new StringBuilder(sceneChild.getTextContent());
                    for (Panorama p : tour.getHsForRenta()) {
                      actionText.append("\tset(hotspot[spot_scene_").append(p.getPanoPath()).append("].visible, true);\n");
                    }
                    sceneChild.setTextContent(actionText.toString());
                  }
                }
              }
            }
            Node target = ((Element) scene).getElementsByTagName("hotspot").item(0);
            for (Hotspot h : getHotSpotsFromPanoramas(tour.getHsForRenta(), curScene.getLatitude(), curScene.getLongitude(), curScene.getHeight())) {
              Element hotspot = doc.createElement("hotspot");
              Element hsHtml5 = doc.createElement("hotspot");
              Element hsFlash = doc.createElement("hotspot");
              Element action = doc.createElement("action");
              String atvVal = String.valueOf(h.getAtv() == defVangle ? defPosVangle : h.getAtv());
              hotspot.setAttribute("name", "spot_scene_" + h.getPanorama().getPanoPath());
              hotspot.setAttribute("ath", String.valueOf(h.getAth()));
              hotspot.setAttribute("atv", atvVal);
              hotspot.setAttribute("distance", String.valueOf(h.getDistance()));
              hotspot.setAttribute("style", getStyleByHotspot(h, false));
              hotspot.setAttribute("tag", "point");
              hotspot.setAttribute("descriptionid", "");
              hotspot.setAttribute("onclick", "onclick_spot_scene_" + h.getPanorama().getPanoPath());
              hotspot.setAttribute("linkedscene", "scene_" + h.getPanorama().getPanoPath());
              action.setAttribute("name", "onclick_spot_scene_" + h.getPanorama().getPanoPath());
              action.setTextContent("hideTooltip();zoomto(get(panoview.fov),smooth(400,20,100));mainloadscene(scene_" + h.getPanorama().getPanoPath() + ");lookat(get(panoview.h), get(panoview.v), get(panoview.fov));");

              hsHtml5.setAttribute("devices", "html5");
              hsHtml5.setAttribute("zorder", "3");
              hsHtml5.setAttribute("zorder2", "1");
              hsHtml5.setAttribute("name", "spot_scene_" + h.getPanorama().getPanoPath());

              hsFlash.setAttribute("devices", "flash");
              hsFlash.setAttribute("zorder", "3");
              hsFlash.setAttribute("zorder2", "1");
              hsFlash.setAttribute("name", "spot_scene_" + h.getPanorama().getPanoPath());

              scene.insertBefore(hsFlash, target);
              scene.insertBefore(hsHtml5, target);
              scene.insertBefore(hotspot, target);
              scene.insertBefore(action, target);
            }
          }
        }
      }
      for (Panorama p : tour.getHsForRenta()) {
        Document docRenta = getDocumentFromString(p.getSceneXml());
        Element scnRenta = docRenta.getDocumentElement();
        scnRenta.setAttribute("backgroundsound", backgroundsound);
        scnRenta.setAttribute("group", group);
        Element actHidepanopointspots = docRenta.createElement("action");
        Element actShowpanopointspots = docRenta.createElement("action");
        actHidepanopointspots.setAttribute("name", "hidepanopointspots");
        actShowpanopointspots.setAttribute("name", "showpanopointspots");
        StringBuilder actHiTextSb = new StringBuilder();
        StringBuilder actShTextSb = new StringBuilder();
        for (Hotspot h : getHotSpotsFromPanoramas(tour.getHsForRenta(), p.getLatitude(), p.getLongitude(), p.getHeight())) {
          if (!h.getPanorama().getPanoPath().equals(p.getPanoPath())) {
            String atvVal = String.valueOf(h.getAtv() == defVangle ? defPosVangle : h.getAtv());
            Element hs = docRenta.createElement("hotspot");
            Element hsHtml5 = docRenta.createElement("hotspot");
            Element hsFlash = docRenta.createElement("hotspot");
            Element act = docRenta.createElement("action");
            hs.setAttribute("name", "spot_scene_" + h.getPanorama().getPanoPath());
            hs.setAttribute("ath", String.valueOf(h.getAth()));
            hs.setAttribute("atv", atvVal);
            hs.setAttribute("distance", String.valueOf(h.getDistance()));
            hs.setAttribute("style", getStyleByHotspot(h, p.isAAir()));
            hs.setAttribute("tag", "point");
            hs.setAttribute("descriptionid", "");
            hs.setAttribute("onclick", "onclick_spot_scene_" + h.getPanorama().getPanoPath());
            hs.setAttribute("linkedscene", "scene_" + h.getPanorama().getPanoPath());
            act.setAttribute("name", "onclick_spot_scene_" + h.getPanorama().getPanoPath());
            act.setTextContent("hideTooltip();zoomto(get(panoview.fov),smooth(400,20,100));mainloadscene(scene_" + h.getPanorama().getPanoPath() + ");lookat(get(panoview.h), get(panoview.v), get(panoview.fov));");
            actHiTextSb.append("\tset(hotspot[spot_scene_").append(h.getPanorama().getPanoPath()).append("].visible, false);\n");
            actShTextSb.append("\tset(hotspot[spot_scene_").append(h.getPanorama().getPanoPath()).append("].visible, true);\n");

            hsHtml5.setAttribute("devices", "html5");
            hsHtml5.setAttribute("zorder", "3");
            hsHtml5.setAttribute("zorder2", "1");
            hsHtml5.setAttribute("name", "spot_scene_" + h.getPanorama().getPanoPath());

            hsFlash.setAttribute("devices", "flash");
            hsFlash.setAttribute("zorder", "3");
            hsFlash.setAttribute("zorder2", "1");
            hsFlash.setAttribute("name", "spot_scene_" + h.getPanorama().getPanoPath());

            scnRenta.appendChild(hsFlash);
            scnRenta.appendChild(hsHtml5);

            scnRenta.appendChild(hs);
            scnRenta.appendChild(act);
          }
        }
        for (HotspotScene h : getHotSpotsFromScenes(listActiveScenes, p.getLatitude(), p.getLongitude(), p.getHeight())) {
          Element hs = docRenta.createElement("hotspot");
          Element act = docRenta.createElement("action");
          hs.setAttribute("name", "hs_tour_" + h.getScene().getName());
          hs.setAttribute("ath", String.valueOf(h.getAth()));
          hs.setAttribute("atv", String.valueOf(h.getAtv()));
          hs.setAttribute("distance", String.valueOf(h.getDistance()));
          hs.setAttribute("style", scStyle);
          hs.setAttribute("linkedscene", "scene_" + h.getScene().getName());
          hs.setAttribute("tag", "point");
          hs.setAttribute("tooltip", "project_title");
          hs.setAttribute("descriptionid", "");
          hs.setAttribute("onclick", "onclick_hs_tour_" + h.getScene().getName());
          act.setAttribute("name", "onclick_hs_tour_" + h.getScene().getName());
          act.setTextContent("hideTooltip();zoomto(get(panoview.fov),smooth(400,20,100));mainloadscene(" + h.getScene().getName() + ");lookat(get(panoview.h), get(panoview.v), get(panoview.fov));");
          actHiTextSb.append("\tset(hotspot[hs_tour_").append(h.getScene().getName()).append("].visible, false);\n");
          actShTextSb.append("\tset(hotspot[hs_tour_").append(h.getScene().getName()).append("].visible, true);\n");
          scnRenta.appendChild(hs);
          scnRenta.appendChild(act);
        }
        actHidepanopointspots.setTextContent(actHiTextSb.toString());
        actShowpanopointspots.setTextContent(actShTextSb.toString());
        scnRenta.appendChild(actHidepanopointspots);
        scnRenta.appendChild(actShowpanopointspots);
        krpanoTag.appendChild(doc.adoptNode(scnRenta.cloneNode(true)));
      }
    }
  }

  public String getPanoTourRentaXml(PanoTourRenta tour) {
    double bgFrameWidth = 0;
    if (tour.getCustomerInfo() != null) {
      bgFrameWidth = getBgFrameWidth(tour.getCustomerInfo().getLogoPath());
    }
    if (tour.getPanoTourSrc() != null) {
      String srcXml = tour.getPanoTourSrc().getSrcXml();
      Document scnsDoc = getDocumentFromString(srcXml);
      if (tour.getDefaultPano() != null && !tour.getDefaultPano().isEmpty()) {
        Node startupAct = scnsDoc.getElementsByTagName("action").item(0).getFirstChild();
        String startupVal = startupAct.getNodeValue();
        String startScene = "\n      set(startscene, " + tour.getDefaultPano() + ");\n";
        startupAct.setNodeValue(startupVal.replaceFirst("[^,]\\s+set\\(startscene,\\s(.*)\\);", startScene));
      }
      NodeList scns = scnsDoc.getElementsByTagName("scene");
      // removeHiddenScenas(scns, tour, scnsDoc);
      if (tour.getCustomerInfo() != null) {
        Element inc = scnsDoc.createElement("include");
        inc.setAttribute("url", "/krengine/skin/travelrk_skin_renta.xml");
        scnsDoc.getDocumentElement().appendChild(inc);
        if (StringUtils.isNotBlank(tour.getCustomerInfo().getYaBronUrl())) {
          Element bronlayer = scnsDoc.createElement("layer");
          bronlayer.setAttribute("name", "bronLogo");
          bronlayer.setAttribute("url", "/img/logo_bron.png");
          bronlayer.setAttribute("keep", "true");
          bronlayer.setAttribute("scale", "0.6");
          bronlayer.setAttribute("zorder", "0");
          bronlayer.setAttribute("align", "centerbottom");
          bronlayer.setAttribute("edge", "righttop");
          bronlayer.setAttribute("x", "80");
          bronlayer.setAttribute("y", "40");
          bronlayer.setAttribute("preload", "true");
          bronlayer.setAttribute("enabled", "true");
          bronlayer.setAttribute("capture", "false");
          bronlayer.setAttribute("hintru", "© travelrk.ru");
          bronlayer.setAttribute("hintruW", "auto");
          bronlayer.setAttribute("hinteng", "© travelrk.ru");
          bronlayer.setAttribute("hintengW", "auto");
          bronlayer.setAttribute("onclick", "openurl("+tour.getCustomerInfo().getYaBronUrl()+");");
          scnsDoc.getDocumentElement().appendChild(bronlayer);
        }
      }
      // добавляем счетчик просмотров
      Element counter = scnsDoc.createElement("include");
      counter.setAttribute("url", "/krengine/travelrk_counter.xml");
      scnsDoc.getDocumentElement().appendChild(counter);

      for (int temp = 0; temp < scns.getLength(); temp++) {
        Node node = scns.item(temp);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element scEl = (Element) node;
          String name = scEl.getAttribute("name");
          PanoTourScene p = getPanoTourScene(tour, name);
          if (tour.getCustomerInfo() != null && p != null) {
            Boolean showCustomerInfo = p.getShowCustomerInfo();
            if (showCustomerInfo == null || !showCustomerInfo) continue;
            String north = p.getNorth() == null || p.getNorth().isEmpty()
                ? "0"
                : p.getNorth();
            Element image = (Element) scEl.getElementsByTagName("image").item(0);
            image.setAttribute("prealign", "0|" + north + "|0");

            NodeList hotspots = scEl.getElementsByTagName("hotspot");
            for (int t = 0; t < hotspots.getLength(); t++) {
              Node nodeHotspot = hotspots.item(t);
              if (nodeHotspot.getNodeType() == Node.ELEMENT_NODE) {
                Element hotspotEl = (Element) nodeHotspot;
                if (hotspotEl.hasAttribute("ath")) {
                  double curAth = Double.parseDouble(hotspotEl.getAttribute("ath"));
                  double athD = Double.parseDouble(north) + curAth;
                  hotspotEl.setAttribute("ath", String.valueOf(athD));
                }
                NodeList points = hotspotEl.getElementsByTagName("point");
                for (int l = 0; l < points.getLength(); l++) {
                  Node nodePoint = points.item(l);
                  if (nodePoint.getNodeType() == Node.ELEMENT_NODE) {
                    Element pointEl = (Element) nodePoint;
                    if (pointEl.hasAttribute("ath")) {
                      double curAth = Double.parseDouble(pointEl.getAttribute("ath"));
                      double athD = Double.parseDouble(north) + curAth;
                      pointEl.setAttribute("ath", String.valueOf(athD));
                    }
                  }
                }
              }
            }

            Hotspot custHot = getHotSpotFromCustomerInfo(tour.getCustomerInfo(), p.getLatitude(), p.getLongitude(), p.getHeight());
            if (tour.getDefaultPano() != null && !tour.getDefaultPano().isEmpty() && tour.getDefaultPano().equals(name)) {
              Element panoview = (Element) scEl.getElementsByTagName("panoview").item(0);
              panoview.setAttribute("h", String.valueOf(custHot.getAth()));
              panoview.setAttribute("v", String.valueOf(custHot.getAtv()));
            }
            Element ciHs = scnsDoc.createElement("hotspot");
            ciHs.setAttribute("name", "customerInfo");
            ciHs.setAttribute("url", "/images/customerlogos/" + tour.getCustomerInfo().getLogoPath());
            ciHs.setAttribute("ath", String.valueOf(custHot.getAth()));
            ciHs.setAttribute("atv", String.valueOf(custHot.getAtv()));
            ciHs.setAttribute("style", "customerStyle");
            ciHs.setAttribute("onclick", "openurl('" + tour.getCustomerInfo().getExcltour() + "',_blank);");
            ciHs.setAttribute("tag", "point");
            ciHs.setAttribute("visible", "true");
            scEl.appendChild(ciHs);

            Element bgFrame = scnsDoc.createElement("hotspot");
            bgFrame.setAttribute("name", "customerBgFrame");
            bgFrame.setAttribute("url", "/images/1wpx.png");
            bgFrame.setAttribute("ath", String.valueOf(custHot.getAth()));
            bgFrame.setAttribute("atv", String.valueOf(custHot.getAtv()));
            bgFrame.setAttribute("height", "60");
            bgFrame.setAttribute("width", String.valueOf(bgFrameWidth));
            bgFrame.setAttribute("style", "customerBgFrameStyle");
            bgFrame.setAttribute("tag", "point");
            bgFrame.setAttribute("visible", "true");
            scEl.appendChild(bgFrame);

            Element ci1Hs = scnsDoc.createElement("hotspot");
            ci1Hs.setAttribute("name", "customerInfoLine");
            ci1Hs.setAttribute("ath", String.valueOf(custHot.getAth()));
            ci1Hs.setAttribute("atv", String.valueOf(custHot.getAtv()));
            ci1Hs.setAttribute("style", "customerStyleLine");
            ci1Hs.setAttribute("visible", "true");
            scEl.appendChild(ci1Hs);

            Element ci2Hs = scnsDoc.createElement("hotspot");
            ci2Hs.setAttribute("name", "customerInfoTitle");
            ci2Hs.setAttribute("ath", String.valueOf(custHot.getAth()));
            ci2Hs.setAttribute("atv", String.valueOf(custHot.getAtv()));
            ci2Hs.setAttribute("type", "text");
            ci2Hs.setAttribute("html", tour.getCustomerInfo().getCompanyName() + " - " + custHot.getDistance() + "м");
            ci2Hs.setAttribute("style", "customerStyleTitle");
            ci2Hs.setAttribute("visible", "true");
            scEl.appendChild(ci2Hs);

            Element customerLayer = scnsDoc.createElement("layer");
            customerLayer.setAttribute("name", "customerLayer");
            customerLayer.setAttribute("url", "/images/customerlogos/" + tour.getCustomerInfo().getLogoPath());
            customerLayer.setAttribute("keep", "true");
            customerLayer.setAttribute("zorder", "0");
            customerLayer.setAttribute("align", "righttop");
            customerLayer.setAttribute("edge", "righttop");
            customerLayer.setAttribute("x", "10");
            customerLayer.setAttribute("y", "10");
            customerLayer.setAttribute("height", "150");
            customerLayer.setAttribute("width", "prop");
            customerLayer.setAttribute("scale", "0.5");
            customerLayer.setAttribute("preload", "true");
            customerLayer.setAttribute("enabled", "true");
            customerLayer.setAttribute("capture", "false");
            customerLayer.setAttribute("onclick", "openurl('" + tour.getCustomerInfo().getSite() + "',_blank);");
            scEl.appendChild(customerLayer);
          }
        }
      }
      scnsDoc.normalize();
      return transformXmlToString(scnsDoc);
    }
    return "";
  }

  private void removeHiddenScenas(NodeList list, PanoTourRenta tour, Document root) {
    List<String> names = new ArrayList<>();
    for (int temp = 0; temp < list.getLength(); temp++) {
      Node node = list.item(temp);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) node;
        String name = element.getAttribute("name");
        PanoTourScene p = getPanoTourScene(tour, name);
        if (p == null) {
          element.setAttribute("hidden","true");
          // root.getDocumentElement().removeChild(list.item(temp));
          // temp--;
        } else {
          names.add(p.getName());
        }
      }
    }
    names.forEach(e -> {
      removeByAttr(list, root, "hotspot","linktarget", e);
    });
  }

  private void removeByAttr(NodeList scnList, Document root, String tag, String attr, String name) {
    for (int y = 0; y < scnList.getLength(); y++) {
      Node scn = scnList.item(y);
      if (scn.getNodeType() == Node.ELEMENT_NODE) {
        Element elScn = (Element) scn;
        NodeList list = elScn.getElementsByTagName(tag);
        for (int i = 0; i < list.getLength(); i++) {
          Node node = list.item(i);
          if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String attrVal = element.getAttribute(attr);
            if (attrVal != null && !attrVal.isEmpty() && !attr.equals(name)) {
              elScn.removeChild(list.item(i));
              // element.setAttribute("hidden","true");
            }
          }
        }
      }
    }
  }

  private PanoTourScene getPanoTourScene(PanoTourRenta tour, String name) {
    PanoTourScene p;
    if (tour.getScenasFowShow() != null && !tour.getScenasFowShow().isEmpty()) {
      p = tour.getScenasFowShow().stream()
          .filter(s -> s.getName().equals(name)).findFirst().orElse(null);
    } else {
      p = tour.getPanoTourSrc().getScenes().stream()
          .filter(s -> s.getName().equals(name)).findFirst().orElse(null);
    }
    return p;
  }

  private double getBgFrameWidth(String logoPath) {
    BufferedImage bimg;
    double logoWidth;
    double logoHeight;
    try {
      bimg = ImageIO.read(new File(PATH_CUSTOMER_LOGO + logoPath));
      logoWidth = bimg.getWidth();
      logoHeight = bimg.getHeight();
      return (60 / logoHeight) * logoWidth;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }

  public String getRentaTourXml(RentaTour tour) {
    double bgFrameWidth = 0;
    if (tour.getCustomerInfo() != null) {
      bgFrameWidth = getBgFrameWidth(tour.getCustomerInfo().getLogoPath());
    }
    String result = "";
    StringBuilder sbXml = new StringBuilder();
    sbXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    sbXml.append("<scenes>");
    Map<Region, Panorama> topPanoOfRegion = new HashMap<>();
    for (Panorama p : tour.getHsForRenta()) {
      if (!p.haveGeoMetaData()) continue;
      sbXml.append(p.getSceneXml());
      if (!topPanoOfRegion.containsKey(p.getRegion())) {
        topPanoOfRegion.put(p.getRegion(), p);
      } else {
        Panorama pp = topPanoOfRegion.get(p.getRegion());
        double h = Double.valueOf(pp.getHeight());
        double hc = Double.valueOf(p.getHeight());
        if (hc > h) topPanoOfRegion.replace(p.getRegion(), p);
      }
    }
    Set<Panorama> regionsHs = new HashSet<>();
    for (Panorama p : topPanoOfRegion.values()) {
      regionsHs.add(p);
    }

    sbXml.append("</scenes>");
    String strXml = sbXml.toString().replaceAll("(?m)^[ \t]*\r?\n", "");
    Document scnsDoc = getDocumentFromString(strXml);
    NodeList scenes = scnsDoc.getElementsByTagName("scene");
    for (int sc = 0; sc < scenes.getLength(); sc++) {
      Node scene = scenes.item(sc);
      if (scene.getNodeType() == Node.ELEMENT_NODE) {
        Element scEl = (Element) scene;
        String name = scEl.getAttribute("name");
        name = name.replace("scene_", "");
        Panorama p = getPanoramaByName(tour.getHsForRenta(), name);
        List<Hotspot> listHs = getHotSpotsFromPanoramas(tour.getHsForRenta(), p.getLatitude(), p.getLongitude(), p.getHeight());
        if (!p.isAAir()) removeGroupHs(listHs);
        for (Hotspot h : listHs) {
          if (!h.getPanorama().getPanoPath().equals(p.getPanoPath())
              && p.getRegion().getName().equals(h.getPanorama().getRegion().getName())) {
//                        String atvVal = String.valueOf(h.getAtv() == defVangle && !p.isAAir() ? defPosVangle : h.getAtv());
            String atvVal = String.valueOf(h.getAtv());
            Element hs = scnsDoc.createElement("hotspot");
            String stHsp = getStyleByHotspot(h, p.isAAir());
            if (stHsp.equals("hsDirection")) atvVal = String.valueOf(defPosVangle);
            hs.setAttribute("name", "spot_scene_" + h.getPanorama().getPanoPath());
            hs.setAttribute("distance", String.valueOf(h.getDistance()));
            hs.setAttribute("style", h.getPanorama().getPanoPath().equals("pano_NEOOUSXIKS") ? "hsVideo360" : stHsp);
            hs.setAttribute("ath", String.valueOf(h.getAth()));
            hs.setAttribute("atv", atvVal);
            hs.setAttribute("tag", "point");
            hs.setAttribute("descriptionid", "");
            hs.setAttribute("visible", "true");
            hs.setAttribute("onclick", "mainloadscene(get(linkedscene),get(ath),0,get(name));");
            hs.setAttribute("linkedscene", "scene_" + h.getPanorama().getPanoPath());
            scEl.appendChild(hs);
          }
        }

        for (Hotspot h : getHotSpotsFromPanoramas(regionsHs, p.getLatitude(), p.getLongitude(), p.getHeight())) {
          if (!p.getRegion().getName().equals(h.getPanorama().getRegion().getName()) && p.isAAir()) {
            Element hs = scnsDoc.createElement("hotspot");
            hs.setAttribute("name", "spot_scene_" + h.getPanorama().getPanoPath());
            hs.setAttribute("ath", String.valueOf(h.getAth()));
            hs.setAttribute("atv", "0");
            hs.setAttribute("distance", String.valueOf(h.getDistance()));
            hs.setAttribute("style", "arrow_style");
            hs.setAttribute("tag", "point");
            hs.setAttribute("region", h.getPanorama().getRegion().getViewName());
            hs.setAttribute("descriptionid", "");
            hs.setAttribute("visible", "true");
            hs.setAttribute("onclick", "mainloadscene(get(linkedscene),get(ath),0,get(name));");
            hs.setAttribute("linkedscene", "scene_" + h.getPanorama().getPanoPath());
            scEl.appendChild(hs);
          }
        }
        if (Integer.valueOf(p.getHeight()) == 0) {
          Element hsNadir = scnsDoc.createElement("hotspot");
          hsNadir.setAttribute("name", "nadirLogo");
          hsNadir.setAttribute("url", "/images/nadir_logo.png");
          hsNadir.setAttribute("ath", "0");
          hsNadir.setAttribute("atv", "90");
          hsNadir.setAttribute("distorted", "true");
          hsNadir.setAttribute("scale", "0.7");
          hsNadir.setAttribute("rotate", "0.0");
          hsNadir.setAttribute("rotatewithview", "false");
          hsNadir.setAttribute("onclick", "openurl('https://travelrk.ru',_blank);");
          scEl.appendChild(hsNadir);
        }
        if (tour.getCustomerInfo() != null && p.isAAir()) {
          Hotspot custHot = getHotSpotFromCustomerInfo(tour.getCustomerInfo(), p.getLatitude(), p.getLongitude(), p.getHeight());
          Element ciHs = scnsDoc.createElement("hotspot");
          ciHs.setAttribute("name", "customerInfo");
          ciHs.setAttribute("url", "/images/customerlogos/" + tour.getCustomerInfo().getLogoPath());
          ciHs.setAttribute("ath", String.valueOf(custHot.getAth()));
          ciHs.setAttribute("atv", String.valueOf(custHot.getAtv()));
          ciHs.setAttribute("style", "customerStyle");
          ciHs.setAttribute("onclick", "openurl('" + tour.getCustomerInfo().getExcltour() + "',_blank);");
          ciHs.setAttribute("tag", "point");
          ciHs.setAttribute("visible", "true");
          scEl.appendChild(ciHs);

          Element bgFrame = scnsDoc.createElement("hotspot");
          bgFrame.setAttribute("name", "customerBgFrame");
          bgFrame.setAttribute("url", "/images/1wpx.png");
          bgFrame.setAttribute("ath", String.valueOf(custHot.getAth()));
          bgFrame.setAttribute("atv", String.valueOf(custHot.getAtv()));
          bgFrame.setAttribute("height", "60");
          bgFrame.setAttribute("width", String.valueOf(bgFrameWidth));
          bgFrame.setAttribute("style", "customerBgFrameStyle");
          bgFrame.setAttribute("tag", "point");
          bgFrame.setAttribute("visible", "true");
          scEl.appendChild(bgFrame);

          Element ci1Hs = scnsDoc.createElement("hotspot");
          ci1Hs.setAttribute("name", "customerInfoLine");
          ci1Hs.setAttribute("ath", String.valueOf(custHot.getAth()));
          ci1Hs.setAttribute("atv", String.valueOf(custHot.getAtv()));
          ci1Hs.setAttribute("style", "customerStyleLine");
          ci1Hs.setAttribute("visible", "true");
          scEl.appendChild(ci1Hs);

          Element ci2Hs = scnsDoc.createElement("hotspot");
          ci2Hs.setAttribute("name", "customerInfoTitle");
          ci2Hs.setAttribute("ath", String.valueOf(custHot.getAth()));
          ci2Hs.setAttribute("atv", String.valueOf(custHot.getAtv()));
          ci2Hs.setAttribute("type", "text");
          ci2Hs.setAttribute("html", tour.getCustomerInfo().getCompanyName() + " - " + custHot.getDistance() + "м");
          ci2Hs.setAttribute("style", "customerStyleTitle");
          ci2Hs.setAttribute("visible", "true");
          scEl.appendChild(ci2Hs);

          Element customerLayer = scnsDoc.createElement("layer");
          customerLayer.setAttribute("name", "customerLayer");
          customerLayer.setAttribute("url", "/images/customerlogos/" + tour.getCustomerInfo().getLogoPath());
          customerLayer.setAttribute("keep", "true");
          customerLayer.setAttribute("zorder", "0");
          customerLayer.setAttribute("align", "righttop");
          customerLayer.setAttribute("edge", "righttop");
          customerLayer.setAttribute("x", "10");
          customerLayer.setAttribute("y", "10");
          customerLayer.setAttribute("height", "150");
          customerLayer.setAttribute("width", "prop");
          customerLayer.setAttribute("scale", "0.5");
          customerLayer.setAttribute("preload", "true");
          customerLayer.setAttribute("enabled", "true");
          customerLayer.setAttribute("capture", "false");
          customerLayer.setAttribute("onclick", "openurl('" + tour.getCustomerInfo().getSite() + "',_blank);");
          scEl.appendChild(customerLayer);
        }
      }
    }
    result = transformXmlToString(scnsDoc);
    result = result.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "");
    result = result.replace("<scenes>", "");
    result = result.replace("</scenes>", "");
    return result;
  }

  private void removeGroupHs(List<Hotspot> listHs) {
    if (listHs == null || listHs.size() == 0) return;
    List<Hotspot> rs = new ArrayList<>(listHs);
    rs.sort((lhs, rhs) -> lhs.getAth() < rhs.getAth() ? -1 : (lhs.getAth() > rhs.getAth()) ? 0 : 1);
    listHs.sort((lhs, rhs) -> lhs.getAth() < rhs.getAth() ? -1 : (lhs.getAth() > rhs.getAth()) ? 0 : 1);
    Iterator<Hotspot> hsIter = rs.iterator();
    while (hsIter.hasNext()) {
      Hotspot hotspot = hsIter.next();
      if (hotspot.getDistance() == 0) {
        hsIter.remove();
        continue;
      }
      if (hotspot.getPanorama().isAAir()) {
        hsIter.remove();
        continue;
      }
    }
    if (rs.size() == 0) return;
    hsIter = rs.iterator();
    Hotspot fstHs = hsIter.next();
    Hotspot strHs = null;
    while (hsIter.hasNext()) {
      Hotspot lstHs = hsIter.next();
      double curAngle = getRaznica(fstHs.getAth(), lstHs.getAth());
      if (curAngle < minHorAngle) {
        if (fstHs.getDistance() > lstHs.getDistance()) {
          listHs.remove(fstHs);
          if (strHs == null) strHs = lstHs;
        } else {
          listHs.remove(lstHs);
          if (strHs == null) strHs = fstHs;
          lstHs = fstHs;
        }
      } else {
        if (strHs == null) strHs = fstHs;
      }
      fstHs = lstHs;
    }
    if (strHs != null) {
      double curAngle = getRaznica(fstHs.getAth(), strHs.getAth());
      if (curAngle < minHorAngle) {
        if (fstHs.getDistance() > strHs.getDistance())
          listHs.remove(fstHs);
        else
          listHs.remove(strHs);
      }
    }
  }

  private double getRaznica(double v, double v1) {
    double result = Math.abs(v - v1);
    if (result > 180) result = Math.abs(result - 360);
    return result;
  }

  private Panorama getPanoramaByName(Set<Panorama> hsForRenta, String name) {
    Panorama pano = null;
    for (Panorama p : hsForRenta) {
      if (p.getPanoPath().equals(name))
        pano = p;
    }
    return pano;
  }

  private String getStyleByHotspot(Hotspot h, boolean isCurSceneIsAir) {
//        String style = "skin_hotspotstyle_navigate";
    String style = "hsDirection";
    if (isCurSceneIsAir && !h.getPanorama().isAAir()) {
      style = h.getPanorama().getCategoryOfContent().getName() + "Style";
    }
    if (h.getPanorama().isAAir())
      style = "hsSmHeli";
    return style;
  }

  private Scene getSceneByName(List<Scene> tour, String name) {
    for (Scene s : tour)
      if (s.getName().equals(name))
        if (s.haveGeoMetaData()) return s;
    return null;
  }

  public String getScenaXml(Scene scena) {
    ExclusiveTour tour = exclusiveTourRepository.findOne(scena.getExclusiveTour().getId());
    String stringXml;
    String tourPath = URL_EXCLUSIVE_TOUR + "/" + tour.getPath();
    String tourData = tourPath.replace(".html", "data/");
    Document doc = getDocumentFromString(tour.getKrpanoXml());
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
      url = url.replace("%FIRSTXML%/", tourData);
      preview.setAttribute("url", url);

      String north = scena.getNorth() == null || scena.getNorth().isEmpty() ? "0" : scena.getNorth();
      Element image = (Element) root.getElementsByTagName("image").item(0);
      image.setAttribute("prealign", "0|" + north + "|0");

      Element hotspot = scnDoc.createElement("hotspot");
      hotspot.setAttribute("name", "spotNorth");
      hotspot.setAttribute("url", "/images/red-north-arrow2.png");
      hotspot.setAttribute("ath", "0");
      hotspot.setAttribute("atv", "40");
      hotspot.setAttribute("ondown", "draghotspot();");
      root.appendChild(hotspot);

    }

    stringXml = transformXmlToString(scnDoc);
    stringXml = stringXml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "");
    stringXml = stringXml.replaceAll("(?m)^[ \t]*\r?\n", "");
    stringXml = stringXml.replaceAll("<front url=\"", "<front url=\"" + tourData);
    stringXml = stringXml.replaceAll("<right url=\"", "<right url=\"" + tourData);
    stringXml = stringXml.replaceAll("<back url=\"", "<back url=\"" + tourData);
    stringXml = stringXml.replaceAll("<left url=\"", "<left url=\"" + tourData);
    stringXml = stringXml.replaceAll("<up url=\"", "<up url=\"" + tourData);
    stringXml = stringXml.replaceAll("<down url=\"", "<down url=\"" + tourData);

    return stringXml;
  }

  private List<Hotspot> getHotSpotsFromPanoramas(Set<Panorama> panoramas, String startLatitude, String startLongitude, String height) {
    List<Hotspot> hotspots = new ArrayList<>();
    if ((startLatitude != null && startLongitude != null && height != null) &&
        (!startLatitude.isEmpty() && !startLongitude.isEmpty() && !height.isEmpty())) {
      double STARTLAT = Double.parseDouble(startLatitude);
      double STARTLNG = Double.parseDouble(startLongitude);
      double HEIGHT = Double.parseDouble(height);
      for (Panorama p : panoramas) {
        if (!p.haveGeoMetaData()) continue;
        Hotspot h = new Hotspot(p);
        double dstLng = Double.parseDouble(p.getLongitude());
        double dstLat = Double.parseDouble(p.getLatitude());
        double dist = getDistance(STARTLAT, STARTLNG, dstLat, dstLng);
        double scHght = Double.parseDouble(p.getHeight());
        int realmeters = (int) dist;
        double vangl = 90 - toDegrees(Math.atan(dist * dCoef / HEIGHT));
        double scVangl = 90 - toDegrees(Math.atan(dist * dCoef / scHght));
        vangl = vangl - scVangl;
/*
                if (scHght > 0) {
                    double scVangl = 90 - toDegrees(Math.atan(dist * dCoef / scHght));
                    vangl = vangl - scVangl;
                } else {
                    vangl = Math.max(vangl, defVangle);
                }
*/
        double gangl = getHotSpotAzimuth(STARTLAT, STARTLNG, dstLat, dstLng);
        h.setDistance(realmeters);
        h.setAth(gangl);
        h.setAtv(vangl);
        hotspots.add(h);
      }
    }
    return hotspots;
  }

  private Hotspot getHotSpotFromCustomerInfo(CustomerInfo c, String startLatitude, String startLongitude, String height) {
    Hotspot h = new Hotspot();
    if ((startLatitude != null && startLongitude != null && height != null) &&
        (!startLatitude.isEmpty() && !startLongitude.isEmpty() && !height.isEmpty())) {
      double STARTLAT = Double.parseDouble(startLatitude);
      double STARTLNG = Double.parseDouble(startLongitude);
      double HEIGHT = Double.parseDouble(height);
      double dstLng = Double.parseDouble(c.getOfficeLng());
      double dstLat = Double.parseDouble(c.getOfficeLat());
      double dist = getDistance(STARTLAT, STARTLNG, dstLat, dstLng);
      double scHght = Double.parseDouble(c.getHeight());
      int realmeters = (int) dist;
      double vangl = 90 - toDegrees(Math.atan(dist * dCoef / HEIGHT));
      double scVangl = 90 - toDegrees(Math.atan(dist * dCoef / scHght));
      vangl = vangl - scVangl;
/*
            if (scHght > 0) {
                double scVangl = 90 - toDegrees(Math.atan(dist * dCoef / scHght));
                vangl = vangl - scVangl;
            } else {
                vangl = Math.max(vangl, defVangle);
            }
*/
      double gangl = getHotSpotAzimuth(STARTLAT, STARTLNG, dstLat, dstLng);
      h.setDistance(realmeters);
      h.setAth(gangl);
      h.setAtv(vangl);
    }
    return h;
  }

  private List<HotspotScene> getHotSpotsFromScenes(List<Scene> scenes, String startLatitude, String startLongitude, String height) {
    List<HotspotScene> hotspots = new ArrayList<>();
    double STARTLAT = Double.parseDouble(startLatitude);
    double STARTLNG = Double.parseDouble(startLongitude);
    double HEIGHT = Double.parseDouble(height);
    for (Scene s : scenes) {
      if (!s.haveGeoMetaData()) continue;
      HotspotScene h = new HotspotScene(s);
      double dstLng = Double.parseDouble(s.getLongitude());
      double dstLat = Double.parseDouble(s.getLatitude());
      double dist = getDistance(STARTLAT, STARTLNG, dstLat, dstLng);
      double scHght = Double.parseDouble(s.getHeight());
      int realmeters = (int) dist;
      double vangl = 90 - toDegrees(Math.atan(dist * dCoef / HEIGHT));
      double scVangl = 90 - toDegrees(Math.atan(dist * dCoef / scHght));
      vangl = vangl - scVangl;
/*
            if (scHght > 0) {
                double scVangl = 90 - toDegrees(Math.atan(dist * dCoef / scHght));
                vangl = vangl - scVangl;
            } else {
                vangl = Math.max(vangl, defVangle);
            }
*/
      double gangl = getHotSpotAzimuth(STARTLAT, STARTLNG, dstLat, dstLng);
      h.setDistance(realmeters);
      h.setAth(gangl);
      h.setAtv(vangl);
      hotspots.add(h);
    }
    return hotspots;
  }

  private double getHotSpotAzimuth(double lat, double lng, double latHs, double lngHs) {
    double result = toDegrees(atan2(
        cos(toRadians(latHs)) * sin(toRadians(lngHs - lng)),
        cos(toRadians(lat)) * sin(toRadians(latHs)) - sin(toRadians(lat)) * cos(toRadians(latHs)) * cos(toRadians(lngHs - lng))

    ));
    return result;
  }

  private double getDistance(double lat1, double lon1, double lat2, double lon2) {
    double R = 6371000;
    double dLat = Math.toRadians(lat2 - lat1);
    double dLon = Math.toRadians(lon2 - lon1);
    double a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c;
  }

  public void genThumbnailsByPath(String path) {
    List<Scene> scenes = new ArrayList<>();
    ExclusiveTour tour = exclusiveTourRepository.findByPath(path);
    String logo = tour.getLogo();
    if (logo.isEmpty()) {
      logo = PATH_LOGO;
    } else {
      logo = PATH_EXCLUSIVE_TOUR + logo;
    }
    String dirTour = tour.getPath();
    dirTour = dirTour.replace(".html", "");
    String tourName = dirTour.split("/")[1];
    String xmlFileName = PATH_EXCLUSIVE_TOUR + dirTour + "data/" + tourName + ".xml";
    String messagesXmlFileName = PATH_EXCLUSIVE_TOUR + dirTour + "data/" + tourName + "_messages_ru.xml";
    Document messages = getDocumentFromFileName(messagesXmlFileName);
    Map<String, String> titles = new HashMap<>();
    Document doc = getDocumentFromFileName(xmlFileName);
    Element rootMessTag = messages.getDocumentElement();
    NodeList messNodeList = rootMessTag.getChildNodes();
    String description = "";
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
          description = messNode.getFirstChild().getNodeValue();
        }
      }
    }

    Element krpanoTag = doc.getDocumentElement();
    NodeList nodeList = krpanoTag.getChildNodes();
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node childNode = nodeList.item(i);
      if (childNode instanceof Element && childNode.getNodeName().equals("scene")) {
        Scene scene = new Scene();
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
        scene.setExclusiveTour(tour);
        scene.setTitle(titles.get("ru_" + scene.getName() + "_title"));
        scenes.add(scene);
      }
    }
    for (Scene scn : scenes) {
      String origImgName = PATH_EXCLUSIVE_TOUR + dirTour + "data/" + scn.getDir() + "/tablet/0.jpg";
      String bigTumbName = PATH_EXCLUSIVE_TOUR + dirTour + "data/" + scn.getDir() + "/thumbnail_bt.jpg";
      BufferedImage inbi;
      try {
        inbi = ImageIO.read(new File(origImgName));
        BufferedImage biWaterMark = Thumbnails.of(ImageIO.read(new File(logo))).size(100, 100).asBufferedImage();
        int origW = inbi.getWidth();
        int origH = inbi.getHeight();
        Thumbnails.of(inbi)
            .sourceRegion(Positions.CENTER, origW, (int) (origH / 1.7f))
            .forceSize(600, 314)
            .watermark(Positions.TOP_RIGHT, biWaterMark, 0.7f)
            .outputQuality(0.85)
            .toFile(new File(bigTumbName));
      } catch (IOException e) {
        log.info(e.getMessage());
      }
    }
    tour.setDescription(getShortStr(description, 256));
    if (tour.getScenes() == null || tour.getScenes().size() == 0) {
      tour.setScenes(scenes);
      exclusiveTourRepository.saveAndFlush(tour);
    }
  }

  private String getShortStr(String str, int size) {
    String result = Jsoup.parse(str).text();
    result = escapeHtml3(Jsoup.parse(result).text());
    result = result.substring(0, Math.min(size, result.length()));
    return result;
  }

  private Document getDocumentFromFileName(String inputFileName) {
    Document doc = null;
    try {
      File inputFile = new File(inputFileName);
      doc = dBuilder.parse(inputFile);
    } catch (SAXException | IOException e) {
      log.info(e.getMessage());
    }
    return doc;
  }

  private Document getNewDocument() {
    return dBuilder.newDocument();
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

  private String readFile(String path, Charset encoding) {
    byte[] encoded = new byte[0];
    try {
      encoded = Files.readAllBytes(Paths.get(path));
    } catch (IOException e) {
      log.info(e.getMessage());
    }
    return new String(encoded, encoding);
  }
}
