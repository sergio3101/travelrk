package ru.flystar.travelrk.ui.controllers.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.flystar.travelrk.domain.persistents.CustomerInfo;
import ru.flystar.travelrk.domain.persistents.PanoScan;
import ru.flystar.travelrk.domain.persistents.PanoTourRenta;
import ru.flystar.travelrk.domain.persistents.PanoTourScene;
import ru.flystar.travelrk.domain.persistents.PanoTourSrc;
import ru.flystar.travelrk.domain.persistents.Panorama;
import ru.flystar.travelrk.domain.persistents.Region;
import ru.flystar.travelrk.domain.persistents.RentaTour;
import ru.flystar.travelrk.domain.persistents.Scene;
import ru.flystar.travelrk.domain.persistents.User;
import ru.flystar.travelrk.domain.persistents.Video;
import ru.flystar.travelrk.service.CategoryOfContentService;
import ru.flystar.travelrk.service.CustomerInfoService;
import ru.flystar.travelrk.service.ExclusiveTourService;
import ru.flystar.travelrk.service.LogRowService;
import ru.flystar.travelrk.service.PanoScanService;
import ru.flystar.travelrk.service.PanoTourRentaService;
import ru.flystar.travelrk.service.PanoTourSceneService;
import ru.flystar.travelrk.service.PanoTourSrcService;
import ru.flystar.travelrk.service.PanoramaService;
import ru.flystar.travelrk.service.RegionService;
import ru.flystar.travelrk.service.RentaTourService;
import ru.flystar.travelrk.service.SceneService;
import ru.flystar.travelrk.service.UserService;
import ru.flystar.travelrk.service.YoutubeService;
import ru.flystar.travelrk.tools.StringTool;
import ru.flystar.travelrk.ui.dto.CustomerModel;
import ru.flystar.travelrk.ui.dto.SelectModel;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 21.07.2017.
 */
@Log4j
@RestController
@RequestMapping(value = "/admin")
public class AjaxController {
  private final YoutubeService youtubeService;
  private final PanoScanService panoScanService;
  private final ExclusiveTourService exclusiveTourService;
  private final PanoTourSrcService panoTourSrcService;
  private final PanoTourRentaService panoTourRentaService;
  private final SceneService sceneService;
  private final PanoTourSceneService panoTourSceneService;
  private final PanoramaService panoramaService;
  private final CategoryOfContentService categoryOfContentService;
  private final CustomerInfoService customerInfoService;
  private final RentaTourService rentaTourService;
  private final RegionService regionService;
  private final UserService userService;
  private final LogRowService logRowService;

  @Autowired
  public AjaxController(YoutubeService youtubeService, PanoScanService panoScanService, ExclusiveTourService exclusiveTourService, PanoTourSrcService panoTourSrcService, PanoTourRentaService panoTourRentaService, SceneService sceneService, PanoTourSceneService panoTourSceneService, PanoramaService panoramaService, CategoryOfContentService categoryOfContentService, CustomerInfoService customerInfoService, RentaTourService rentaTourService, RegionService regionService, UserService userService, LogRowService logRowService) {
    this.youtubeService = youtubeService;
    this.panoScanService = panoScanService;
    this.exclusiveTourService = exclusiveTourService;
    this.panoTourSrcService = panoTourSrcService;
    this.panoTourRentaService = panoTourRentaService;
    this.sceneService = sceneService;
    this.panoTourSceneService = panoTourSceneService;
    this.panoramaService = panoramaService;
    this.categoryOfContentService = categoryOfContentService;
    this.customerInfoService = customerInfoService;
    this.rentaTourService = rentaTourService;
    this.regionService = regionService;
    this.userService = userService;
    this.logRowService = logRowService;
  }

  @RequestMapping(value = "/ajaxRemove", method = RequestMethod.POST)
  @ResponseBody
  public String ajaxRemove(@RequestParam("idytb") String idytb) {
    String msg = "ERROR";
    if (youtubeService.removeById(idytb) > 0) {
      msg = "SUCCESS";
    }
    return msg;
  }

  @RequestMapping(value = "/ajaxRemovePanoscan", method = RequestMethod.POST)
  @ResponseBody
  public String ajaxRemovePanoScan(@RequestParam String path) {
    String msg = "ERROR";
    if (panoScanService.removePanoscanByPath(path) > 0) {
      msg = "SUCCESS";
    }
    return msg;
  }


  @RequestMapping(value = "/ajaxRemoveexclusivetour", method = RequestMethod.POST)
  @ResponseBody
  public String ajaxRemoveexclusivetour(@RequestParam("id") Integer id) {
    exclusiveTourService.removeexclusivetour(id);
    return "SUCCESS";
  }

  @RequestMapping(value = "/ajaxRemovePanoTourSrc", method = RequestMethod.POST)
  @ResponseBody
  public String ajaxRemovPanoTourSrc(@RequestParam("id") Integer id) {
    panoTourSrcService.removePanoTourSrc(id);
    return "SUCCESS";
  }

  @RequestMapping(value = "/ajaxGetVideoById", produces = "application/json", method = RequestMethod.POST)
  @ResponseBody
  public Video ajaxEditById(@RequestParam("idForEdit") String idForEdit) {
    return youtubeService.getVideoById(idForEdit);
  }

  @RequestMapping(value = "/ajaxSaveVideo", method = RequestMethod.POST)
  @ResponseBody
  public String ajaxSaveVideo(@ModelAttribute("video") Video video) {
    String result = "ERROR";
    if (!youtubeService.saveVideo(video).getYoutubeId().isEmpty()) result = "SUCCESS";
    return result;
  }

  @RequestMapping(value = "/ajaxSaveScena", method = RequestMethod.POST)
  @ResponseBody
  public String ajaxSaveScena(@ModelAttribute("scena") Scene scene) {
    String result = "ERROR";
    if (sceneService.saveScene(scene) != null) result = "SUCCESS";
    return result;
  }

  @RequestMapping(value = "/ajaxReloadPanoTourSrc", method = RequestMethod.POST)
  @ResponseBody
  public String ajaxReloadPanoTourSrc(@RequestParam("id") int id) {
    String result;
    try {
      panoTourSrcService.reloadPanoTourSrc(id);
      result = "SUCCESS";
    } catch (Throwable e) {
      e.printStackTrace();
      result = "ERROR";
    }
    return result;
  }

  @RequestMapping(value = "/ajaxSavePanoTourScene", method = RequestMethod.POST)
  @ResponseBody
  public String ajaxSavePanoTourScene(@ModelAttribute("scena") PanoTourScene scene) {
    String result = "ERROR";
    int panoTourSrcId = scene.getPanoTourSrc().getId();
    PanoTourSrc panoTourSrc = panoTourSrcService.getPanoTourSrcById(panoTourSrcId);
    if (panoTourSrc != null) {
      scene.setPanoTourSrc(panoTourSrc);
    }
    if (panoTourSceneService.savePanoTourScene(scene) != null) result = "SUCCESS";
    return result;
  }

  @RequestMapping(value = "/ajaxSavePanoscan", method = RequestMethod.POST)
  @ResponseBody
  public String ajaxSaveVideo(@ModelAttribute("panoscan") PanoScan panoscan) {
    String result = "ERROR";
    if (panoScanService.editPanoscan(panoscan) != null) result = "SUCCESS";
    return result;
  }

  @RequestMapping(value = "/getScansJson", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<List<PanoScan>> ajaxGetScansJson() {
    ResponseEntity<List<PanoScan>> re = new ResponseEntity<>(panoScanService.getPanoScanList(), HttpStatus.OK);
    return re;
  }

  @RequestMapping(value = "/getPanoramasJson", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<List<Panorama>> ajaxGetPanoramasJson() {
    return new ResponseEntity<>(panoramaService.getPanoramaList(), HttpStatus.OK);
  }

  @RequestMapping(value = "/getCustomersJson", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<List<CustomerInfo>> ajaxGetCustomersJson() {
    return new ResponseEntity<>(customerInfoService.getCustomerInfoList(), HttpStatus.OK);
  }

  @RequestMapping(value = "/getSearchCustomersJson", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
  public @ResponseBody
  ResponseEntity<List<CustomerInfo>> ajaxGetSearchCustomersJson(
      @RequestParam("query") String query,
      @RequestParam("isSiteExist") Boolean isSiteExist, HttpServletRequest request, HttpServletResponse response) {
    return new ResponseEntity<>(customerInfoService.getListCIByYaSearch(query, isSiteExist), HttpStatus.OK);
  }

  @RequestMapping(value = "/setSearchCustomersJson", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
  @ResponseBody
  public ResponseEntity<String> ajaxSetSearchCustomersJson(
      @RequestBody SelectModel selectModel) {
    Region region = regionService.getRegionByName(selectModel.getRegion());
    List<CustomerInfo> customers = getCustommersByModel(selectModel.getSelected(), region);
    customerInfoService.saveAll(customers);
    String rentaTourId = selectModel.getRentaTourId();
    if (rentaTourId != null && !rentaTourId.isEmpty()) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String login = authentication.getName();
      User user = userService.getUserByLogin(login);
      RentaTour rtSrc = rentaTourService.getRentaTourByPath(rentaTourId);
      if (rtSrc != null) {
        Calendar rentaExpired = Calendar.getInstance();
        rentaExpired.add(Calendar.MONTH, 3);
        customers.forEach(c -> {
          Set<Panorama> panoramas = new HashSet<>();
          Set<Panorama> hsPano = rtSrc.getHsForRenta();
          RentaTour rt = new RentaTour();
          rt.setName(c.getCompanyName());
          rt.setUser(user);
          rt.setDescription(c.getAddress());
          rt.setDomain("travelrk.ru");
          rt.setSum(null);
          rt.setIsFuturePayment(false);
          rt.setMonthCount(null);
          rt.setRentaExpired(rentaExpired.getTime());
          rt.setHsForRenta(panoramas);
          rt.setDefaultPano(rtSrc.getDefaultPano());
          RentaTour rtDst = rentaTourService.saveRentaTour(rt);
          for (Panorama p : hsPano) {
            panoramas.add(panoramaService.getPanoramaByPanoPath(p.getPanoPath()));
          }
          rtDst.setCustomerInfo(c);
          rtDst.setHsForRenta(panoramas);
          rentaTourService.saveRentaTour(rtDst);
        });
      }
    }
    return new ResponseEntity<>("{\"status\":\"SUCCESS\"}", HttpStatus.OK);
  }

  @RequestMapping(value = "/setPanoTourCustomerJson", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
  @ResponseBody
  public ResponseEntity<String> ajaxSetPanoTourCustomerJson(
      @RequestBody SelectModel selectModel) {
    Region region = regionService.getRegionByName(selectModel.getRegion());
    List<CustomerInfo> customers = getCustommersByModel(selectModel.getSelected(), region);
    customerInfoService.saveAll(customers);
    String panoTourSrcId = selectModel.getRentaTourId();
    PanoTourSrc panoTourSrc = panoTourSrcService.getPanoTourSrcById(Integer.parseInt(panoTourSrcId));
    if (panoTourSrc != null) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String login = authentication.getName();
      User user = userService.getUserByLogin(login);
      Calendar rentaExpired = Calendar.getInstance();
      rentaExpired.add(Calendar.MONTH, 3);
      customers.forEach(c -> {
        PanoTourRenta rt = new PanoTourRenta();
        rt.setName(c.getCompanyName());
        rt.setUser(user);
        rt.setDescription(c.getAddress());
        rt.setDomain("test.travelrk.ru");
        rt.setSum(null);
        rt.setIsFuturePayment(false);
        rt.setMonthCount(null);
        rt.setRentaExpired(rentaExpired.getTime());
        PanoTourRenta rtDst = panoTourRentaService.savePanoTourRenta(rt);
        rtDst.setPanoTourSrc(panoTourSrc);
        rtDst.setCustomerInfo(c);
        panoTourRentaService.savePanoTourRenta(rtDst);
      });
    }
    return new ResponseEntity<>("{\"status\":\"SUCCESS\"}", HttpStatus.OK);
  }

  private List<CustomerInfo> getCustommersByModel(List<CustomerModel> selected, Region region) {
    List<CustomerInfo> result = new ArrayList<>();
    selected.forEach(e -> {
      if (customerInfoService.findByYaid(e.getId()) == null) {
        CustomerInfo info = new CustomerInfo();
        info.setCompanyName(e.getCompanyName());
        info.setAddress(e.getAddress());
        info.setPhone(e.getPhone());
        info.setSite(e.getSite());
        info.setYaid(e.getYaid());
        info.setOfficeLng(e.getOfficeLng());
        info.setOfficeLat(e.getOfficeLat());
        info.setLogoPath(e.getLogoPath());
        info.setRegion(region);
        info.setLogoPath("default.png");
        info.setHeight("0");
        result.add(info);
      }
    });
    return result;
  }

  @RequestMapping(value = "/getRentaTourJson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  @ResponseBody
  public ResponseEntity<List<RentaTour>> ajaxGetRentaTourJson() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    List<RentaTour> list = null;
    if (authentication != null &&
        authentication
            .getAuthorities()
            .stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"))) {
      String login = authentication.getName();
      list = rentaTourService.getRentaToursByOwner(login);
    } else {
      list = rentaTourService.getAllRentaTours();
    }
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @RequestMapping(value = "/getPanoTourRentaJson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
  @ResponseBody
  public ResponseEntity<List<PanoTourRenta>> ajaxGetPanoTourRentaJson() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    List<PanoTourRenta> list;
    if (authentication != null &&
        authentication
            .getAuthorities()
            .stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"))) {
      String login = authentication.getName();
      list = panoTourRentaService.getPanoTourRentasByOwner(login);
    } else {
      list = panoTourRentaService.getAllPanoTourRentas();
    }
    // LogRow logRow = new LogRow();
    // logRow.setDateOfCreate(new Date());
    // logRow.setDomain("travelrk.ru");
    // logRow.setPanoTourRenta(list.get(0));
    // logRowService.saveLogRow(logRow);
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @RequestMapping(value = "/ajaxGenPanorama", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<Panorama> ajaxGenPanorama(@RequestParam("path") String path, @RequestParam("krpanoConfigPath") String krpanoConfigPath) {
    PanoScan panoScan = panoScanService.getPanoScanByPath(path);
    Panorama panorama = new Panorama();
    if (!path.isEmpty() && !krpanoConfigPath.isEmpty()) {
      panorama.setPanoPath("pano_" + StringTool.genRandomLowerStr(10));
      panorama.setTitle(panoScan.getName());
      panorama.setRegion(panoScan.getRegion());
      panorama.setCategoryOfContent(categoryOfContentService.getCategoryOfContentByName("natural"));
      panoramaService.addPanorama(panorama, krpanoConfigPath, path);
    }
    return new ResponseEntity<>(panorama, HttpStatus.OK);
  }

  @RequestMapping(value = "/ajaxReGenPano3D", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<Panorama> ajaxReGenPano3D(@RequestParam("panoPath") String panoPath, @RequestParam("krpanoConfigPath") String krpanoConfigPath) {
    Panorama panorama = panoramaService.getPanoramaByPanoPath(panoPath);
    panoramaService.addPanorama(panorama, krpanoConfigPath, panorama.getPanoScan() + ".jpg");
    return new ResponseEntity<>(panorama, HttpStatus.OK);
  }

  @RequestMapping(value = "/ajaxAddVideo", method = RequestMethod.POST)
  @ResponseBody
  public String addSaveVideo(@RequestParam("idForAdd") String idForAdd) {
    return youtubeService.addVideo(idForAdd);
  }

  @RequestMapping(value = "/ajaxSavePreAlign", method = RequestMethod.POST)
  @ResponseBody
  public String savePreAlign(@RequestParam("name") String name, @RequestParam("prealign") String prealign) {
    String result;
    String panoPath = name.replace("scene_", "");
    String[] v = prealign.split("\\|");
    Panorama p = panoramaService.getPanoramaByPanoPath(panoPath);
    p.setNorth(v[1]);
    p.setVa(v[0]);
    p.setVc(v[2]);
    panoramaService.savePanorama(p);
    result = "OK";
    return result;
  }
}
