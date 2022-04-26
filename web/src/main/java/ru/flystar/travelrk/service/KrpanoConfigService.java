package ru.flystar.travelrk.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.flystar.travelrk.domain.persistents.KrpanoConfig;
import ru.flystar.travelrk.repositories.KrpanoConfigRepository;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 21.11.2017.
 */
@Service
@Log4j
public class KrpanoConfigService {
  @Value("${path.krpanocfg}")
  private String PATH_KRPANO_CFG;
  @Value("${path.pano}")
  private String PATH_PANO;
  private final KrpanoConfigRepository krpanoConfigRepository;

  public KrpanoConfigService(KrpanoConfigRepository krpanoConfigRepository) {
    this.krpanoConfigRepository = krpanoConfigRepository;
  }

  public List<KrpanoConfig> getKrpanoConfigList() {
    return krpanoConfigRepository.findAll();
  }

  public KrpanoConfig getKrpanoConfigById(int id) {
    return krpanoConfigRepository.findOne(id);
  }

  @Transactional
  public KrpanoConfig addKrpanoConfig(KrpanoConfig krCfg) {
    String path = krCfg.getPath();
    if (krCfg.getId() == 0) {
      KrpanoConfig duplicat = krpanoConfigRepository.findByPath(path);
      if (duplicat != null) krCfg.setId(duplicat.getId());
    }
    File file = new File(PATH_KRPANO_CFG + path);
    try {
      file.createNewFile();
    } catch (IOException e) {
      log.info("Create file: " + e.getMessage());
    }
    try (FileWriter writer = new FileWriter(PATH_KRPANO_CFG + path, false)) {
      String n = "\n";
      writer.write("include default/def.config" + n);
      writer.write("panotype=" + krCfg.getPanotype().name().toLowerCase() + n);
      writer.write("hfov=" + krCfg.getHfov() + n);
      String vfov = krCfg.getVfov() == 0 ? "auto" : String.valueOf(krCfg.getVfov());
      writer.write("vfov=" + vfov + n);
      writer.write("voffset=" + krCfg.getVoffset() + n);
      writer.write("stereosupport=" + krCfg.isStereosupport() + n);
      writer.write("converttocube=" + krCfg.isConverttocube() + n);
      writer.write("converttocubelimit=360x" + krCfg.getConverttocubelimit360x() + n);
      writer.write("converttocubeformat=" + krCfg.getConverttocubeformat().name().toLowerCase() + n);
      writer.write("multires=" + krCfg.isMultires() + n);
      String tileSize = krCfg.getTilesize() == 0 ? "auto" : String.valueOf(krCfg.getTilesize());
      writer.write("tilesize=" + tileSize + n);
      String levels = krCfg.getLevels() == 0 ? "auto" : String.valueOf(krCfg.getLevels());
      writer.write("levels=" + levels + n);
      writer.write("levelstep=" + krCfg.getLevelstep() + n);
      String minsize = krCfg.getMinsize() == 0 ? "auto" : String.valueOf(krCfg.getMinsize());
      writer.write("minsize=" + minsize + n);
      String maxsize = krCfg.getMaxsize() == 0 ? "auto" : String.valueOf(krCfg.getMaxsize());
      writer.write("maxsize=" + maxsize + n);
      String maxcubesize = krCfg.getMaxcubesize() == 0 ? "auto" : String.valueOf(krCfg.getMaxcubesize());
      writer.write("maxcubesize=" + maxcubesize + n);
      writer.write("tilepath=" + PATH_PANO + "%BASENAME%/tiles/" + krCfg.getTilepath() + n);
      writer.write("preview=" + krCfg.isPreview() + n);
      writer.write("graypreview=" + krCfg.isGraypreview() + n);
      writer.write("cspreview=" + krCfg.isCspreview() + n);
      writer.write("previewsmooth=" + krCfg.getPreviewsmooth() + n);
      writer.write("previewspsize=" + krCfg.getPreviewspsize() + n);
      writer.write("previewcssize=" + krCfg.getPreviewcssize() + n);
      writer.write("previewpath=" + PATH_PANO + "%BASENAME%/" + krCfg.getPreviewpath() + n);
      if (krCfg.isMobileVersion())
        writer.write("include default/mobile.config" + n);
      writer.write("makethumb=" + krCfg.isMakethumb() + n);
      writer.write("thumbsize=" + krCfg.getThumbsize() + n);
      writer.write("jpegquality=" + krCfg.getJpegquality() + n);
      writer.write("jpegsubsamp=" + krCfg.getJpegsubsamp().getValue() + n);
      writer.write("jpegoptimize=" + krCfg.isJpegoptimize() + n);
      writer.write("jpegprogressive=" + krCfg.isJpegprogressive() + n);
      writer.write("filter=" + krCfg.getFilter().name() + n);
      writer.write("parsegps=" + krCfg.isParsegps() + n);
//            writer.write("flash=" + krCfg.isFlash() + n);
//            writer.write("html5=" + krCfg.isHtml5() + n);
      writer.write("ignorelayers=" + krCfg.isIgnorelayers() + n);
      writer.write("sortinput=" + krCfg.isSortinput() + n);
      writer.write("autolevel=" + krCfg.getAutolevel().name().toLowerCase() + n);
      if (krCfg.isProtect()) {
        String krprotect = getKrProtect(krCfg);
        if (!krprotect.isEmpty()) writer.write("kprotectclparameters=" + krprotect + n);
      }

    } catch (IOException e) {
      log.info("Open for write file: " + e.getMessage());
    }
    return krpanoConfigRepository.saveAndFlush(krCfg);
  }

  private String getKrProtect(KrpanoConfig krCfg) {
    StringBuilder result = new StringBuilder();
    if (krCfg.isNoep()) result.append("-noep").append(" ");
    if (krCfg.isNoex()) result.append("-noex").append(" ");
    if (krCfg.isNojs()) result.append("-nojs").append(" ");
    if (krCfg.isNolu()) result.append("-nolu").append(" ");
    if (krCfg.isPxml()) result.append("-pxml").append(" ");
    if (!krCfg.getDomain().isEmpty()) result.append("-domain=").append(krCfg.getDomain()).append(" ");
    if (!krCfg.getExpire().isEmpty()) result.append("-expire=").append(krCfg.getExpire()).append(" ");
    String r = result.toString();
    if (!r.isEmpty()) r = r.substring(0, r.length() - 1);
    return r;
  }

  @Transactional
  public boolean removeKrpanoConfigById(int id) {
    File file = new File(PATH_KRPANO_CFG + krpanoConfigRepository.findOne(id).getPath());
    krpanoConfigRepository.delete(id);
    return file.delete();
  }
}
