package ru.flystar.travelrk.service;

import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.flystar.travelrk.domain.persistents.ExclusiveTour;
import ru.flystar.travelrk.domain.persistents.Panorama;
import ru.flystar.travelrk.domain.persistents.RentaTour;
import ru.flystar.travelrk.repositories.PanoScanRepository;
import ru.flystar.travelrk.repositories.PanoramaRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 24.11.2017.
 */
@Log4j
@Service
public class PanoramaService {
    @Value("${path.panoscan}")
    private String PATH_PANOSCAN;
    @Value("${path.pano}")
    private String PATH_PANO;
    @Value("${path.krpanocfg}")
    private String PATH_KRPANO_CFG;
    @Value("${path.krpanotools}")
    private String PATH_KRPANO_TOOL;
    @Value("${path.krpanotmp}")
    private String PATH_KRPANO_TMP;
    private final PanoramaRepository panoramaRepository;
    private final XmlParserService xmlParser;
    private final ExclusiveTourService exclusiveTourService;
    private final RentaTourService rentaTourService;
    private final PanoScanRepository panoScanRepository;

    public PanoramaService(PanoramaRepository panoramaRepository, XmlParserService xmlParser, ExclusiveTourService exclusiveTourService, RentaTourService rentaTourService, PanoScanRepository panoScanRepository) {
        this.panoramaRepository = panoramaRepository;
        this.xmlParser = xmlParser;
        this.exclusiveTourService = exclusiveTourService;
        this.rentaTourService = rentaTourService;
        this.panoScanRepository = panoScanRepository;
    }

    public List<Panorama> getPanoramaList() {
        return panoramaRepository.findAll();
    }

    public Panorama getPanoramaById(int id) {
        return panoramaRepository.findOne(id);
    }

    @Transactional
    public Panorama addPanorama(Panorama panorama, String krpanoConfigPath, String scan) {
        Panorama result = null;
        String panoDir = panorama.getPanoPath();
        try {
            FileUtils.deleteDirectory(new File(PATH_PANO + panoDir));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProcessBuilder builder = new ProcessBuilder(PATH_KRPANO_TOOL, "makepano", PATH_PANOSCAN + scan, "-config=" + PATH_KRPANO_CFG + krpanoConfigPath);
        builder.directory(new File(PATH_KRPANO_TMP));
        builder.redirectErrorStream(true);
        final Process process;
        try {
            process = builder.start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("gps: ")) {
                    String lat = line.substring(line.indexOf("lat=") + 4, line.indexOf(" lng="));
                    String lng = line.substring(line.indexOf("lng=") + 4, line.indexOf(" heading="));
                    panorama.setLatitude(lat);
                    panorama.setLongitude(lng);
                }
                if (line.contains("multires: ")) {
                    String multires = line.substring(line.indexOf("multires: ") + 10);
                    panorama.setInfo(multires);
                }
            }
            br.close();
            FileUtils.moveDirectory(FileUtils.getFile(PATH_PANO + scan.replace(".jpg", "")), FileUtils.getFile(PATH_PANO + panoDir));
            String tumbName = PATH_PANOSCAN + scan.replace(".jpg", "") + "_tumb.jpg";
            String bigTumbName = PATH_PANOSCAN + scan.replace(".jpg", "") + "_bt.jpg";
            FileUtils.copyFile(FileUtils.getFile(tumbName), FileUtils.getFile(PATH_PANO + panoDir + "/thumb.jpg"));
            FileUtils.copyFile(FileUtils.getFile(bigTumbName), FileUtils.getFile(PATH_PANO + panoDir + "/bigthumb.jpg"));
            panorama.setDateOfCreate(new Date());
            panorama.setSceneXml(xmlParser.getPanoXmlFromFile(panorama.getPanoPath()));
            panorama.setPanoramaScan(panoScanRepository.findByPath(scan));
            xmlParser.updatePanoXml(panorama);
            result = panoramaRepository.saveAndFlush(panorama);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return result;
    }

    @Transactional
    public void removePano3DById(int id) throws DataIntegrityViolationException {
        try {
            String panoPath = panoramaRepository.findOne(id).getPanoPath();
            panoramaRepository.delete(id);
            FileUtils.deleteDirectory(new File(PATH_PANO + panoPath));
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    public void savePanorama(Panorama panorama) {
        xmlParser.updatePanoXml(panorama);
        panoramaRepository.saveAndFlush(panorama);
    }

    public Panorama getPanoramaByPanoPath(String panoPath) {
        return panoramaRepository.findByPanoPath(panoPath);
    }

    public Panorama getPanoramaByPanoScan(String panoScan) {
        return panoramaRepository.findByPanoScan(panoScan);
    }

    public List<RentaTour> getTourNamesByPanoId(int id) {
        return rentaTourService.getRentaToursByHsForRenta(panoramaRepository.findOne(id));
    }

    public List<ExclusiveTour> getExclTourNamesByPanoId(int id) {
        return exclusiveTourService.getExclToursByHsForRenta(panoramaRepository.findOne(id));
    }
}
