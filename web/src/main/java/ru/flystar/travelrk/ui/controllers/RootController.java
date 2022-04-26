package ru.flystar.travelrk.ui.controllers;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.flystar.travelrk.domain.persistents.CategoryOfContent;
import ru.flystar.travelrk.domain.persistents.ExclusiveTour;
import ru.flystar.travelrk.domain.persistents.PanoTourRenta;
import ru.flystar.travelrk.domain.persistents.Panorama;
import ru.flystar.travelrk.domain.persistents.RentaTour;
import ru.flystar.travelrk.domain.persistents.Video;
import ru.flystar.travelrk.service.CategoryOfContentService;
import ru.flystar.travelrk.service.ExclusiveTourService;
import ru.flystar.travelrk.service.HtmlParserService;
import ru.flystar.travelrk.service.PanoTourRentaService;
import ru.flystar.travelrk.service.PanoramaService;
import ru.flystar.travelrk.service.RentaTourService;
import ru.flystar.travelrk.service.XmlParserService;
import ru.flystar.travelrk.service.YoutubeService;
import ru.flystar.travelrk.tools.StringTool;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 20.06.2017.
 */
@Log4j
@RestController
public class RootController {
  @Value("${path.exclusivetour}")
  private String PATH_EXCLUSIVE_TOUR;
  @Value("${path.panoshot}")
  private String PATH_PANOSHOT;
  @Value("${path.logo}")
  private String PATH_LOGO;
  @Value("${path.social}")
  private String PATH_SOCIAL;
  private YoutubeService videoList;
  private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
  private CategoryOfContentService categoryOfContentService;
  private ExclusiveTourService exclusiveTourService;
  private PanoramaService panoramaService;
  private RentaTourService rentaTourService;
  private PanoTourRentaService panoTourRentaService;
  private XmlParserService xmlParser;
  private HtmlParserService htmlParser;

  @Autowired
  public RootController(YoutubeService videoList, CategoryOfContentService categoryOfContentService, ExclusiveTourService exclusiveTourService, PanoramaService panoramaService, RentaTourService rentaTourService, PanoTourRentaService panoTourRentaService, XmlParserService xmlParser, HtmlParserService htmlParser) {
    this.videoList = videoList;
    this.categoryOfContentService = categoryOfContentService;
    this.exclusiveTourService = exclusiveTourService;
    this.panoramaService = panoramaService;
    this.rentaTourService = rentaTourService;
    this.panoTourRentaService = panoTourRentaService;
    this.xmlParser = xmlParser;
    this.htmlParser = htmlParser;
  }

  @RequestMapping(value = "/video.html", method = RequestMethod.GET)
  public ModelAndView videoPage() {
    ModelAndView modelAndView = new ModelAndView("video");
    List<CategoryOfContent> categoryOfContentList = categoryOfContentService.getCategoryList();
    List<Video> vList = videoList.getVideoList();
    modelAndView.addObject("categoryList", categoryOfContentList);
    modelAndView.addObject("videoList", vList);
    return modelAndView;
  }

  @RequestMapping(value = "/3Dtours.html", method = RequestMethod.GET)
  public ModelAndView toursPage() {
    ModelAndView modelAndView = new ModelAndView("tours3d");
    List<RentaTour> tList = rentaTourService.getRentaToursByDomain("travelrk.ru");
    modelAndView.addObject("tourList", tList);
    return modelAndView;
  }

  @RequestMapping(value = "/exclusivetour/{tourName}/{tourPage}.html", method = RequestMethod.GET)
  public ModelAndView getExclusiveTourHtml(@PathVariable String tourName,
                                           @PathVariable String tourPage,
                                           @RequestParam(name = "startscene", required = false) String startscene,
                                           @RequestParam(name = "startfov", required = false) String startfov,
                                           @RequestParam(name = "starthlookat", required = false) String starthlookat,
                                           @RequestParam(name = "startvlookat", required = false) String startvlookat) {
    ModelAndView modelAndView = new ModelAndView("fileParserView");
    ExclusiveTour exclusiveTour = exclusiveTourService.getExclusiveTourByPage(tourName + "/" + tourPage + ".html");
    String fileNameHtmlExclusiveTour = PATH_EXCLUSIVE_TOUR + exclusiveTour.getPath();
    String parameters = "";
    if (startscene != null) {
      parameters += "?startscene=" + startscene;
      parameters += startfov != null ? "&startfov=" + startfov : "";
      parameters += starthlookat != null ? "&starthlookat=" + starthlookat : "";
      parameters += startvlookat != null ? "&startvlookat=" + startvlookat : "";
    }
    String xmlString = htmlParser.parseExclusiveTourHtml(startscene, fileNameHtmlExclusiveTour, exclusiveTour, parameters);
    modelAndView.addObject("xmlString", xmlString);
    return modelAndView;
  }

  @RequestMapping(value = "/exclusivetour/{tourName}/{tourPage}data/{tourXML}.xml", method = RequestMethod.GET)
  public ModelAndView getExclusiveTourXml(@PathVariable String tourName, @PathVariable String tourPage, @PathVariable String tourXML, HttpServletRequest req) {
    ModelAndView modelAndView = new ModelAndView("fileParserView");
    String referer = req.getHeader("referer");
    String scn = "";
    boolean tr = false;
    if (referer != null && referer.contains("?")) {
      String params = referer.substring(referer.indexOf("?") + 1, referer.length());
      if (params.contains("scn")) {
        String[] paramsArr = params.split("&");
        for (int i = 0; i < paramsArr.length; i++) {
          String[] pair = paramsArr[i].split("=");
          if (pair[0].equals("scn")) scn = pair[1];
          if (pair[0].equals("tr")) tr = Boolean.parseBoolean(pair[1]);
        }
      }
    }
    String xmlString = xmlParser.parseExclusiveTourXml(tourName, tourPage, tourXML, scn, tr);
    modelAndView.addObject("xmlString", xmlString);
    return modelAndView;
  }

  @RequestMapping(value = "/pano/{panoName}/index.html", method = RequestMethod.GET)
  public ModelAndView getPanoHtml(@PathVariable String panoName, @RequestParam(defaultValue = "none") String snapshoturl) {
    ModelAndView modelAndView = new ModelAndView("pano/panoHtml");
    Panorama panorama = panoramaService.getPanoramaByPanoPath(panoName);
    if (snapshoturl.equals("none")) {
      snapshoturl = "https://travelrk.ru/pano/" + panorama.getPanoPath() + "/bigthumb.jpg";
    } else {
      snapshoturl = "https://travelrk.ru" + snapshoturl;
    }
    modelAndView.addObject("panorama", panorama);
    modelAndView.addObject("snapshoturl", snapshoturl);
    return modelAndView;
  }

  @RequestMapping(value = "/pano/{panoPath}/panorama.xml", method = RequestMethod.GET)
  public ModelAndView getPanoXml(@PathVariable String panoPath) {
    ModelAndView modelAndView = new ModelAndView("panoXmlView");
    Panorama pano = panoramaService.getPanoramaByPanoPath(panoPath);
    modelAndView.addObject("pano", pano);
    return modelAndView;
  }

  @RequestMapping(value = {"/rentatours/{path}/", "/rentatours/{path}/index.html"}, method = RequestMethod.GET)
  public ModelAndView getRentaTourHtml(@PathVariable String path, @RequestParam(defaultValue = "none") String snapshoturl, HttpServletResponse response, HttpServletRequest request) {
    ModelAndView modelAndView = new ModelAndView("rentatoursError");
    RentaTour tour = rentaTourService.getRentaTourByPath(path);
    if (snapshoturl.equals("none")) {
      snapshoturl = "https://travelrk.ru/pano/" + tour.getDefaultPano() + "/bigthumb.jpg";
    } else {
      snapshoturl = "https://travelrk.ru" + snapshoturl;
    }
    Date now = new Date();
    StringBuilder message = new StringBuilder();
    if (tour != null) {
      modelAndView.setViewName("rentatoursHtml");
      if (now.after(tour.getRentaExpired())) {
        message.append("Подписка на тур закончилась " + dateFormat.format(tour.getRentaExpired()));
        message.append("<br/>Для продления работы тура обращайтесь по телефону: +7(978) 810-33-95");
        modelAndView.setViewName("rentatoursError");
        modelAndView.addObject("message", message);
      }
      if (request.getHeader("referer") != null && !isDomainValidate(request.getHeader("referer"), tour.getDomain())) {
        message.append("<br/>Подписка оформлена на домен " + tour.getDomain());
        message.append("<br/>Для оформления подписки на тур для вашего домена обращайтесь по телефону: +7(978) 810-33-95");
        modelAndView.setViewName("rentatoursError");
        modelAndView.addObject("message", message);
      } else {
        response.addHeader("X-Frame-Options", "ALLOW-FROM " + tour.getDomain());
//                response.addHeader("Content-Security-Policy", "frame-ancestors " + tour.getDomain());
      }
      modelAndView.addObject("snapshoturl", snapshoturl);
      modelAndView.addObject("tour", tour);
    }
    return modelAndView;
  }

  @RequestMapping(value = {"/panotour/{path}/", "/panotour/{path}/index.html"}, method = RequestMethod.GET)
  public ModelAndView getPanoTourRentaHtml(@PathVariable String path, @RequestParam(defaultValue = "none") String snapshoturl, HttpServletResponse response, HttpServletRequest request) {
    ModelAndView modelAndView = new ModelAndView("rentatoursError");
    PanoTourRenta tour = panoTourRentaService.getPanoTourRentaByPath(path);
    if (snapshoturl.equals("none")) {
      snapshoturl = "https://travelrk.ru/pano/" + tour.getDefaultPano() + "/bigthumb.jpg";
    } else {
      snapshoturl = "https://travelrk.ru" + snapshoturl;
    }
    Date now = new Date();
    StringBuilder message = new StringBuilder();
    if (tour != null) {
      modelAndView.setViewName("panotourrentaHtml");
      if (now.after(tour.getRentaExpired())) {
        message.append("Подписка на тур закончилась " + dateFormat.format(tour.getRentaExpired()));
        message.append("<br/>Для продления работы тура обращайтесь по телефону: +7(978) 810-33-95");
        modelAndView.setViewName("rentatoursError");
        modelAndView.addObject("message", message);
      }
      if (request.getHeader("referer") != null && !isDomainValidate(request.getHeader("referer"), tour.getDomain())) {
        message.append("<br/>Подписка оформлена на домен " + tour.getDomain());
        message.append("<br/>Для оформления подписки на тур для вашего домена обращайтесь по телефону: +7(978) 810-33-95");
        modelAndView.setViewName("rentatoursError");
        modelAndView.addObject("message", message);
      } else {
        response.addHeader("X-Frame-Options", "ALLOW-FROM " + tour.getDomain());
//                response.addHeader("Content-Security-Policy", "frame-ancestors " + tour.getDomain());
      }
      String firstXmlPath = tour.getPanoTourSrc().getPath() + "/First.xml";
      String nameTour = tour.getPanoTourSrc().getPath().substring(tour.getPanoTourSrc().getPath().lastIndexOf("/") + 1);
      panoTourRentaService.incrementCountById(tour.getId());
      nameTour = nameTour.replace("data", "");
      modelAndView.addObject("nameTour", nameTour);
      modelAndView.addObject("firstXmlPath", firstXmlPath);
      modelAndView.addObject("snapshoturl", snapshoturl);
      modelAndView.addObject("tour", tour);
      modelAndView.addObject("showAdv", tour.getIsFuturePayment());
    }
    return modelAndView;
  }

  @RequestMapping(value = "/panotour/{path}/index.xml", method = RequestMethod.GET)
  public ModelAndView getPanoTourRentaXml(@PathVariable String path) {
    ModelAndView modelAndView = new ModelAndView("panotourrentaXml");
    PanoTourRenta tour = panoTourRentaService.getPanoTourRentaByPath(path);
    String rentaTourXml = xmlParser.getPanoTourRentaXml(tour);
    modelAndView.addObject("rentaTourXml", rentaTourXml);
    return modelAndView;
  }

  @GetMapping(value = "/panotour/{path}/{name}data/graphics/**")
  public RedirectView getPanoTourRentaResources(@PathVariable String path, @PathVariable String name, HttpServletRequest request){
    String res = request.getServletPath().substring(request.getServletPath().indexOf("data/graphics/") + 14);
    RedirectView redirectView = new RedirectView("/krengine/resources/graphics/" + res);
    redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
    return redirectView;
  }

  private boolean isDomainValidate(String ref, String domain) {
    if (ref.contains(domain)) return true;
    for (String s : Arrays.asList(PATH_SOCIAL.split(","))) if (ref.contains(s)) return true;
    return false;
  }

  @RequestMapping(value = "/rentatours/savescreenshot", method = RequestMethod.POST)
  @ResponseBody
  public String getScreenshot(@RequestParam("imgBase64") String imgBase64, @RequestParam("watermark") boolean watermark) {
    byte[] decodedImg = Base64.getDecoder().decode(imgBase64.replace("data:image/jpeg;base64,", "").getBytes(StandardCharsets.UTF_8));
    String imgName = "shot_" + StringTool.genRandomLowerStr(10) + ".jpg";
    Path destinationFile = Paths.get(PATH_PANOSHOT + "/screenshots", imgName);
    try {
      if (watermark) {
        BufferedImage waterMark = Thumbnails.of(ImageIO.read(new File(PATH_LOGO))).scale(0.8).asBufferedImage();
        BufferedImage inbi = ImageIO.read(new ByteArrayInputStream(decodedImg));
        int origW = inbi.getWidth();
        int origH = inbi.getHeight();
        Thumbnails.of(inbi)
            .size(origW, origH)
            .watermark(Positions.CENTER, waterMark, 0.5f)
            .outputQuality(0.85)
            .toFile(destinationFile.toFile());
      } else {
        Files.write(destinationFile, decodedImg);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "screenshots/" + imgName;
  }

  @RequestMapping(value = "/rentatours/setmeta", method = RequestMethod.GET)
  public ModelAndView getSetmMta(@RequestParam String panourl,
                                 @RequestParam String sceneview,
                                 @RequestParam String vfw,
                                 @RequestParam String vfh,
                                 @RequestParam String snapshoturl) {
    ModelAndView mov = new ModelAndView("shareUrlView");
    return mov;
  }

  @RequestMapping(value = "/rentatours/{path}/index.xml", method = RequestMethod.GET)
  public ModelAndView getRentaTourXml(@PathVariable String path) {
    ModelAndView modelAndView = new ModelAndView("rentatoursXml");
    RentaTour tour = rentaTourService.getRentaTourByPath(path);
    String rentaTourXml = xmlParser.getRentaTourXml(tour);
    modelAndView.addObject("panoList", new ArrayList<>(tour.getHsForRenta()));
    modelAndView.addObject("tour", tour);
    modelAndView.addObject("rentaTourXml", rentaTourXml);
    return modelAndView;
  }
}
