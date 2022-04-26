package ru.flystar.travelrk.ui.controllers.admin;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.flystar.travelrk.domain.persistents.CategoryOfContent;
import ru.flystar.travelrk.domain.persistents.PanoScan;
import ru.flystar.travelrk.domain.persistents.Panorama;
import ru.flystar.travelrk.domain.persistents.Region;
import ru.flystar.travelrk.service.CategoryOfContentService;
import ru.flystar.travelrk.service.KrpanoConfigService;
import ru.flystar.travelrk.service.PanoScanService;
import ru.flystar.travelrk.service.RegionService;
import ru.flystar.travelrk.service.YoutubeService;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 20.06.2017.
 */
@Controller
@RequestMapping(value = "/admin")
@Secured("ROLE_ADMIN")
@Log4j
public class AdminController {
  private final YoutubeService youtubeService;
  private final PanoScanService panoScanService;
  private final RegionService regionService;
  private final CategoryOfContentService categoryOfContentService;
  private final KrpanoConfigService krpanoConfigService;

  /**
   * Конструктор Admin контроллера.
   *
   * @param youtubeService           - сервис по работе с youtube
   * @param panoScanService          - сервис по работе с развертками
   * @param regionService            - сервис по работе со справочником регионов
   * @param categoryOfContentService - сервис по работе со справочником категорий контента
   * @param krpanoConfigService      - сервис по работе с конфигами KrPano
   */
  @Autowired
  public AdminController(YoutubeService youtubeService,
                         PanoScanService panoScanService,
                         RegionService regionService,
                         CategoryOfContentService categoryOfContentService,
                         KrpanoConfigService krpanoConfigService) {
    this.youtubeService = youtubeService;
    this.panoScanService = panoScanService;
    this.regionService = regionService;
    this.categoryOfContentService = categoryOfContentService;
    this.krpanoConfigService = krpanoConfigService;
  }

  /**
   * Контроллер главной страница админки.
   *
   * @return - регидрект на страницу youtube галлереи
   */
  @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
  public String adminRootPage() {
    return "redirect:/admin/youtubegallery";
  }

  /**
   * Контроллер youtube галлереи.
   *
   * @return - модель вью youtube галлереи
   */
  @RequestMapping(value = "/youtubegallery", method = RequestMethod.GET)
  public ModelAndView youtubeGallery() {
    ModelAndView modelAndView = new ModelAndView("admin/youtubegallery");
    modelAndView.addObject("videoList", youtubeService.getVideoList());
    modelAndView.addObject("categoryList", categoryOfContentService.getCategoryList());
    modelAndView.addObject("regionList", regionService.getRegionList());
    return modelAndView;
  }

  /**
   * Контроллер страницы галлереи разверток.
   *
   * @return - модель вью страницы галлереи разверток
   */
  @RequestMapping(value = "/panoscans", method = RequestMethod.GET)
  public ModelAndView panoScans() {
    ModelAndView modelAndView = new ModelAndView("admin/panoscans");
    List<PanoScan> list = new ArrayList<>(panoScanService.getPanoScanList());
    modelAndView.addObject("scanList", list);
    modelAndView.addObject("regions", regionService.getRegionList());
    modelAndView.addObject("krpanoConfigList", krpanoConfigService.getKrpanoConfigList());
    return modelAndView;
  }

  /**
   * Контроллер ajax запроса генерации 3D-панорамы.
   *
   * @param panorama - панорама полученная из формы запроса
   * @return - возвращаем
   */
  @RequestMapping(value = "/ajaxGenPano3D", method = RequestMethod.POST)
  @ResponseBody
  public String ajaxGenPano3D(@ModelAttribute("panoForm") Panorama panorama) {
    // PanoScan psc = panoScanService.getPanoScanByPath(panorama.getPanoScan().getPath());
    Region r = regionService.getRegionByName(panorama.getRegion().getName());
    CategoryOfContent c = categoryOfContentService.getCategoryOfContentByName(
        panorama.getCategoryOfContent().getName()
    );
    // panorama.setPanoScan(psc);
    panorama.setRegion(r);
    panorama.setCategoryOfContent(c);
    return panorama.toString();
  }

  /**
   * Тестовый контроллер.
   *
   * @return - модель вью
   */
  @RequestMapping(value = "/testHtml", method = RequestMethod.GET)
  public ModelAndView testHtml() {
    ModelAndView modelAndView = new ModelAndView("admin/testHtml");
    List<PanoScan> list = new ArrayList<>(panoScanService.getPanoScanList());
    modelAndView.addObject("scanList", list);
    modelAndView.addObject("regions", regionService.getRegionList());
    modelAndView.addObject("krpanoConfigList", krpanoConfigService.getKrpanoConfigList());
    return modelAndView;
  }
}
