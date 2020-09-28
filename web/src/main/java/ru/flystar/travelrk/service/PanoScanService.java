package ru.flystar.travelrk.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.flystar.travelrk.domain.persistents.PanoScan;
import ru.flystar.travelrk.domain.persistents.Panorama;
import ru.flystar.travelrk.domain.persistents.Region;
import ru.flystar.travelrk.repositories.PanoScanRepository;
import ru.flystar.travelrk.repositories.PanoramaRepository;
import ru.flystar.travelrk.tools.StringTool;

/**
 * Created by sergey on 18.09.2017.
 */
@Service
public class PanoScanService {
  @Value("${path.panoscan}")
  private String PATH_PANOSCAN;
  private final PanoScanRepository panoScanRepository;
  private final PanoramaRepository panoramaRepository;

  public PanoScanService(PanoScanRepository panoScanRepository, PanoramaRepository panoramaRepository) {
    this.panoScanRepository = panoScanRepository;
    this.panoramaRepository = panoramaRepository;
  }

  public List<PanoScan> getPanoScanList() {
    return panoScanRepository.findAll();
  }

  public PanoScan getPanoScanByPath(String pathForEdit) {
    return panoScanRepository.findByPath(pathForEdit);
  }

  public PanoScan getPanoScanById(Integer id) {
    return panoScanRepository.getOne(id);
  }

  public String getPanoPath(String panoScan) {
    String result = "";
    Panorama p = panoramaRepository.findByPanoScan(panoScan);
    if (p != null) result = p.getPanoPath();
    return result;
  }

  @Transactional
  public PanoScan addPanoScan(String filename, long size, Region region, String name) {
    PanoScan panoScan = panoScanRepository.findByPath(filename);
    if (panoScan == null) {
      panoScan = new PanoScan(filename);
      panoScan.setSize(StringTool.redeableSize(size));
      panoScan.setRegion(region);
      panoScan.setName(name);
    } else {
      panoScan.setDateOfDownload(new Date());
      panoScan.setSize(StringTool.redeableSize(size));
    }
    return panoScanRepository.saveAndFlush(panoScan);
  }

  @Transactional
  public PanoScan editPanoscan(PanoScan panoscan) {
    return panoScanRepository.saveAndFlush(panoscan);
  }

  @Transactional
  public Integer removePanoscanByPath(String path) {
    try {
      String tumbName = path.replace(".jpg", "") + "_tumb.jpg";
      String bigTumbName = path.replace(".jpg", "") + "_bt.jpg";
      FileUtils.touch(new File(PATH_PANOSCAN + path));
      FileUtils.forceDelete(FileUtils.getFile(PATH_PANOSCAN + path));
      FileUtils.touch(new File(PATH_PANOSCAN + tumbName));
      FileUtils.forceDelete(FileUtils.getFile(PATH_PANOSCAN + tumbName));
      FileUtils.touch(new File(PATH_PANOSCAN + bigTumbName));
      FileUtils.forceDelete(FileUtils.getFile(PATH_PANOSCAN + bigTumbName));
      return panoScanRepository.deleteByPath(path);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }
}
