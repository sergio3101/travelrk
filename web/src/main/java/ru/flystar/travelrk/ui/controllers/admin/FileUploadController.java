package ru.flystar.travelrk.ui.controllers.admin;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.flystar.travelrk.domain.nopersist.Message;
import ru.flystar.travelrk.domain.nopersist.Status;
import ru.flystar.travelrk.domain.persistents.ExclusiveTour;
import ru.flystar.travelrk.domain.persistents.PanoScan;
import ru.flystar.travelrk.domain.persistents.Region;
import ru.flystar.travelrk.service.ExclusiveTourService;
import ru.flystar.travelrk.service.PanoScanService;
import ru.flystar.travelrk.service.RegionService;
import ru.flystar.travelrk.service.XmlParserService;
import ru.flystar.travelrk.tools.StringTool;
import ru.flystar.travelrk.ui.dto.ExclusiveTourDto;

@Log4j
@RestController
@RequestMapping(value = "/admin")
@Secured("ROLE_ADMIN")
public class FileUploadController {
  private final PanoScanService panoScanService;
  private final ExclusiveTourService exclusiveTourService;
  private final XmlParserService xmlParserService;
  private final RegionService regionService;

  @Value("${path.panoscan}")
  private String PATH_PANO_SCAN;
  @Value("${path.logo}")
  private String PATH_LOGO;
  @Value("${path.exclusivetour}")
  private String PATH_EXCLUSIVE_TOUR;
  private static final String PATH_UPLOAD_DIR = "/var/www/upload/";
  private DateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");

  @Autowired
  public FileUploadController(PanoScanService panoScanService, ExclusiveTourService exclusiveTourService, XmlParserService xmlParserService, RegionService regionService) {
    this.panoScanService = panoScanService;
    this.exclusiveTourService = exclusiveTourService;
    this.xmlParserService = xmlParserService;
    this.regionService = regionService;
  }

  @Setter
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @ToString
  static class UploadModel {
    String region;
  }

  @RequestMapping(value = "/updateFromUploadDir", method = {RequestMethod.POST}, produces = "application/json;charset=utf8")
  @ResponseBody
  public ResponseEntity<String> updateFromUploadDir(@RequestBody UploadModel uploadModel) {
    String result = "SUCCESS";
    Set<String> listFileNames = listFilesUsingJavaIO(PATH_UPLOAD_DIR);
    if (listFileNames.isEmpty()) {
      result = "EMPTY";
    }
    listFileNames.forEach(f -> {
      File file = new File(PATH_UPLOAD_DIR + f);
      PanoScan pano = new PanoScan();
      String panoScanFileName = "scan_" + StringTool.genRandomLowerStr(10).toLowerCase() + ".jpg";
      long fileSize = FileUtils.sizeOf(file);
      Region region = regionService.getRegionByName(uploadModel.getRegion());
      pano = panoScanService.addPanoScan(panoScanFileName, fileSize, region, f);
      if (pano.getId() != 0) {
        InputStream targetStream;
        try {
          targetStream = new FileInputStream(file);
          savePanoScan(targetStream, panoScanFileName);
          FileUtils.forceDelete(file);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
    return new ResponseEntity<>("{\"status\":\"" + result + "\"}", HttpStatus.OK);
  }

  public Set<String> listFilesUsingJavaIO(String dir) {
    return Stream.of(Objects.requireNonNull(new File(dir).listFiles()))
        .filter(file -> !file.isDirectory())
        .map(File::getName)
        .collect(Collectors.toSet());
  }

  @RequestMapping(value = "/uploadpanoscan", method = {RequestMethod.POST}, produces = "application/json;charset=utf8")
  @ResponseBody
  public ResponseEntity<PanoScan> uploadPanoScan(@RequestParam("file") MultipartFile file, @RequestParam String region, @RequestParam String name, @RequestParam String rescan) {
    PanoScan pano = new PanoScan();
    Region regionSet = regionService.getRegionByName(region);
    if (file.isEmpty()) return new ResponseEntity<>(pano, HttpStatus.NO_CONTENT);
    String panoScanFileName;
    if (!rescan.isEmpty()) {
      panoScanFileName = rescan;
    } else {
      panoScanFileName = "scan_" + StringTool.genRandomLowerStr(10).toLowerCase() + ".jpg";
    }
    pano = panoScanService.addPanoScan(panoScanFileName, file.getSize(), regionSet, name);
    if (pano.getId() == 0) return new ResponseEntity<>(pano, HttpStatus.NO_CONTENT);
    try {
      savePanoScan(file.getInputStream(), panoScanFileName);
    } catch (IOException e) {
      return new ResponseEntity<>(pano, HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(pano, HttpStatus.OK);
  }

  private void savePanoScan(InputStream inputStream, String panoScanFileName) throws IOException {
    Path path = Paths.get(PATH_PANO_SCAN + panoScanFileName);
    String tumbName = PATH_PANO_SCAN + panoScanFileName.replace(".jpg", "") + "_tumb.jpg";
    String bigTumbName = PATH_PANO_SCAN + panoScanFileName.replace(".jpg", "") + "_bt.jpg";
    BufferedImage inbi = ImageIO.read(inputStream);
    Thumbnails.of(inbi)
        .size(425, 213)
        .outputQuality(0.85)
        .toFile(new File(tumbName));
    BufferedImage biWaterMark = Thumbnails.of(ImageIO.read(new File(PATH_LOGO))).scale(0.8).asBufferedImage();
    int origW = inbi.getWidth();
    int origH = inbi.getHeight();
    Thumbnails.of(inbi)
        .sourceRegion(Positions.CENTER, origW / 3, origH / 3)
        .forceSize(600, 314)
        .watermark(Positions.TOP_RIGHT, biWaterMark, 0.5f)
        .outputQuality(0.85)
        .toFile(new File(bigTumbName));
    inbi.flush();
    inbi = null;
    biWaterMark.flush();
    bigTumbName = null;
    addTextWatermark("Created by TravelRK.ru", inputStream, path.toFile());
  }

  private void addTextWatermark(String text, InputStream sourceInputStream, File destImageFile) {
    try {
      ImageReader reader = ImageIO.getImageReadersBySuffix("jpg").next();
      reader.setInput(ImageIO.createImageInputStream(sourceInputStream));
      IIOMetadata metadata = reader.getImageMetadata(0);
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      ImageOutputStream ios = ImageIO.createImageOutputStream(os);

      BufferedImage bi = reader.read(0);
      reader.dispose();
      Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
      ImageWriter writer = iter.next();
      writer.setOutput(ios);

      Graphics2D g2d = (Graphics2D) bi.getGraphics();

      AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
      g2d.setComposite(alphaChannel);
      g2d.setColor(Color.WHITE);
      g2d.setFont(new Font("Arial", Font.BOLD, 48));
      FontMetrics fontMetrics = g2d.getFontMetrics();
      Rectangle2D rect = fontMetrics.getStringBounds(text, g2d);

      int centerX = (bi.getWidth() - (int) rect.getWidth()) / 2;
      int centerY = bi.getHeight() / 2;

      g2d.drawString(text, centerX, centerY);

      JPEGImageWriteParam iwParam = (JPEGImageWriteParam) writer.getDefaultWriteParam();
      iwParam.setOptimizeHuffmanTables(true);
      writer.write(null, new IIOImage(bi, null, metadata), iwParam);
      writer.dispose();
      ImageIO.write(bi, "jpg", ios);
      FileOutputStream fos = new FileOutputStream(destImageFile);
      bi.flush();
      bi = null;
      os.writeTo(fos);
      g2d.dispose();
      fos.close();
      os.close();
      ios.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @RequestMapping(value = "/uploadexclusivetour", method = {RequestMethod.POST}, produces = "application/json;charset=utf8")
  @ResponseBody
  public Message uploadexclusivetour(@RequestParam("file") MultipartFile file) {
    Message msg = new Message();
    msg.setStatus(Status.SUCCESS);
    msg.setStatusMsg("Файл " + file.getOriginalFilename() + " успешно сохранен!");
    ExclusiveTour exclusivetour;
    if (file.isEmpty()) {
      msg.setStatus(Status.ERROR);
      msg.setError("Файл " + file.getOriginalFilename() + " пустой!");
      return msg;
    }
    File f = new File(PATH_EXCLUSIVE_TOUR + destinationDirectory(file.getOriginalFilename()));
    if (f.exists()) {
      ExclusiveTour tourForRemove = exclusiveTourService.getExclusiveTourByName(destinationDirectory(file.getOriginalFilename()));
      exclusiveTourService.removeexclusivetour(tourForRemove.getId());
      removeExclusiveTour(PATH_EXCLUSIVE_TOUR + destinationDirectory(file.getOriginalFilename()));
      msg.setStatus(Status.UPDATED);
      msg.setStatusMsg("Файл " + file.getOriginalFilename() + " обновлен!");
    }
    try {
      InputStream zipInStr = file.getInputStream();
      String mainPage = unZipp(zipInStr, file.getOriginalFilename());
      if (mainPage.isEmpty()) {
        removeExclusiveTour(PATH_EXCLUSIVE_TOUR + destinationDirectory(file.getOriginalFilename()));
        msg.setStatus(Status.ERROR);
        msg.setError("Не удалось распаковать файл " + file.getOriginalFilename());
        return msg;
      }
      exclusivetour = buildExclusiveTour(destinationDirectory(file.getOriginalFilename()), mainPage);
      exclusiveTourService.addexclusivetour(exclusivetour);
      xmlParserService.genThumbnailsByPath(exclusivetour.getPath());
    } catch (IOException e) {
      e.printStackTrace();
      msg.setStatus(Status.ERROR);
      msg.setError("Файл " + file.getName() + " не был сохранен!");
      return msg;
    }
    msg.setMsgObj(getDtoByExclusiveTour(exclusivetour));
    return msg;
  }

  private String unZipp(InputStream isZip, String zipFileName) throws IOException {
    String mainPage = "";
    String dstDirectory = destinationDirectory(PATH_EXCLUSIVE_TOUR + zipFileName);
    File dstDir = new File(dstDirectory);
    ZipArchiveInputStream in = new ZipArchiveInputStream(isZip);
    ZipArchiveEntry entry = in.getNextZipEntry();
    while (entry != null) {
      if (entry.isDirectory()) {
        String dirName = entry.getName();
        if (dirName.endsWith("data/")) {
          mainPage = dirName.substring(0, dirName.lastIndexOf("data/"));
        }
        entry = in.getNextZipEntry();
        continue;
      }
      File curfile = new File(dstDir, entry.getName());
      File parent = curfile.getParentFile();
      if (!parent.exists()) {
        parent.mkdirs();
      }
      OutputStream out = new FileOutputStream(curfile);
      IOUtils.copy(in, out);
      out.close();
      entry = in.getNextZipEntry();
    }
    in.close();
    return mainPage;
  }

  private ExclusiveTour buildExclusiveTour(String dstDir, String mainPage) throws IOException {
    ExclusiveTour tour = new ExclusiveTour();
    String indexHtml = mainPage + ".html";
    String title = "";
    String logo = "";
    String latitude = "";
    String longitude = "";
    File indexFile = new File(PATH_EXCLUSIVE_TOUR + dstDir + File.separator + indexHtml);
    File skinXMLFile = new File(PATH_EXCLUSIVE_TOUR + dstDir + File.separator + mainPage + "data" + File.separator + mainPage + "_skin.xml");
    File xmlFile = new File(PATH_EXCLUSIVE_TOUR + dstDir + File.separator + mainPage + "data" + File.separator + mainPage + ".xml");

    if (indexFile.exists()) {
      Document doc = Jsoup.parse(indexFile, "utf-8").normalise();
      title = doc.title();
    }
    if (skinXMLFile.exists()) {
      InputStream is = new FileInputStream(skinXMLFile);
      Document doc = Jsoup.parse(is, "utf-8", "", Parser.xmlParser());
      Elements elements = doc.getElementsByAttributeValueMatching("name", "^logo[0-9]*$");
      if (!elements.isEmpty()) {
        String url = elements.eachAttr("url").get(0);
        logo = url.substring(url.indexOf("/"), url.length());
      }
      is.close();
    }
    if (xmlFile.exists()) {
      InputStream is = new FileInputStream(xmlFile);
      Document doc = Jsoup.parse(is, "utf-8", "", Parser.xmlParser());
      Elements elLat = doc.getElementsByAttribute("latitude");
      if (!elLat.isEmpty()) {
        latitude = elLat.eachAttr("latitude").get(0);
        longitude = elLat.eachAttr("longitude").get(0);
      }
      is.close();
    }

    String exclusivetourPath = dstDir + "/" + indexHtml;
    if (!logo.isEmpty())
      logo = dstDir + "/" + indexHtml.substring(0, indexHtml.lastIndexOf(".")) + "data" + logo;
    String exclusivetourName = title.substring(0, title.indexOf("|"));
    long size = FileUtils.sizeOfDirectory(new File(PATH_EXCLUSIVE_TOUR + dstDir));
    Date curDate = new Date();
    tour.setSize(StringTool.redeableSize(size));
    tour.setDateOfDownload(curDate);
    tour.setLogo(getShortStr(logo, 254));
    tour.setName(getShortStr(exclusivetourName, 254));
    tour.setPath(getShortStr(exclusivetourPath, 1000));
    tour.setLatitude(getShortStr(latitude, 20));
    tour.setLongitude(getShortStr(longitude, 20));
    tour.setKrpanoXml(xmlParserService.getExclTourXmlFromFile(xmlFile.getPath()).replaceAll("(?m)^[ \t]*\r?\n", ""));
    return tour;
  }

  private ExclusiveTourDto getDtoByExclusiveTour(ExclusiveTour exclusivetour) {
    return new ExclusiveTourDto(
        exclusivetour.getId(),
        exclusivetour.getPath(),
        exclusivetour.getName(),
        exclusivetour.getSize(),
        exclusivetour.getLogo(),
        exclusivetour.getLatitude(),
        exclusivetour.getLongitude(),
        DATEFORMAT.format(exclusivetour.getDateOfDownload()));
  }


  private String getShortStr(String exclusivetourPath, int size) {
    return exclusivetourPath.substring(0, Math.min(size, exclusivetourPath.length()));
  }

  private String destinationDirectory(final String srcZip) {
    return srcZip.substring(0, srcZip.lastIndexOf("."));
  }

  private void removeExclusiveTour(String dir) {
    try {
      if (dir.length() > (PATH_EXCLUSIVE_TOUR.length())) FileUtils.deleteDirectory(new File(dir));
    } catch (IOException e) {
      log.info(e.getMessage());
    }
  }
}
