package ru.flystar.travelrk.ui.controllers.admin;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.flystar.travelrk.domain.persistents.PanoScan;
import ru.flystar.travelrk.domain.persistents.Scene;
import ru.flystar.travelrk.domain.persistents.Video;
import ru.flystar.travelrk.service.CategoryOfContentService;
import ru.flystar.travelrk.service.PanoScanService;
import ru.flystar.travelrk.service.PanoTourSceneService;
import ru.flystar.travelrk.service.RegionService;
import ru.flystar.travelrk.service.SceneService;
import ru.flystar.travelrk.service.YoutubeService;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 22.08.2017.
 */
@Log4j
@Controller
@RequestMapping(value = "/admin")
public class AjaxFormController {
  private YoutubeService youtubeService;
  private RegionService regionService;
  private CategoryOfContentService categoryOfContentService;
  private PanoScanService panoScanService;
  private SceneService sceneService;
  private PanoTourSceneService panoTourSceneService;

  @Autowired
  public AjaxFormController(YoutubeService youtubeService, RegionService regionService, CategoryOfContentService categoryOfContentService, PanoScanService panoScanService, SceneService sceneService, PanoTourSceneService panoTourSceneService) {
    this.youtubeService = youtubeService;
    this.regionService = regionService;
    this.categoryOfContentService = categoryOfContentService;
    this.panoScanService = panoScanService;
    this.sceneService = sceneService;
    this.panoTourSceneService = panoTourSceneService;
  }

  @RequestMapping(value = "/ajaxForm/ajaxGetVideoFormById", method = RequestMethod.POST)
  public ModelAndView ajaxGetVideoFormById(@RequestParam("idForEdit") String idForEdit, @ModelAttribute("videoForm") Video videoForm, BindingResult result) {
    ModelAndView mov = new ModelAndView("admin/ajaxForm/editVideo");
    Video videoById = youtubeService.getVideoById(idForEdit);
    mov.addObject("videoForm", videoById);
    mov.addObject("regionList", regionService.getRegionList());
    mov.addObject("categoryList", categoryOfContentService.getCategoryList());
        /*if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            log.info("Error binding!!!!!");
            for (ObjectError err:
                 list) {
                log.info(err.getObjectName());
                log.info(err.getDefaultMessage());
                log.info(err.toString());
            }
        }*/
    log.info(videoById.getYoutubeId());
    log.info(videoById.getCategoryOfContent().getName());
    log.info(videoById.getRegion().getName());
    return mov;
  }

  @RequestMapping(value = "/ajaxForm/ajaxGetPanoscanFormById", method = RequestMethod.POST)
  public ModelAndView ajaxGetPanoscanFormById(@RequestParam("idForEdit") String idForEdit, @ModelAttribute("panoscanForm") PanoScan panoScan) {
    ModelAndView mov = new ModelAndView("admin/ajaxForm/editPanoscan");
    mov.addObject("panoscanForm", panoScanService.getPanoScanByPath(idForEdit));
    return mov;
  }

  @RequestMapping(value = "/ajaxForm/ajaxGetScenaFormByName", method = RequestMethod.POST)
  public ModelAndView ajaxGetScenaFormByName(@RequestParam("name") String name, @RequestParam("tourid") int tourId, @ModelAttribute("panoscanForm") Scene scene) {
    ModelAndView mov = new ModelAndView("admin/ajaxForm/editScena");
    mov.addObject("scenaForm", sceneService.getSceneByNameAndTourId(name, tourId));
    return mov;
  }

  @RequestMapping(value = "/ajaxForm/ajaxGetScenaFormByNameAndTourId", method = RequestMethod.POST)
  public ModelAndView ajaxGetScenaFormByNameAndTourId(@RequestParam("name") String name, @RequestParam("tourid") int tourId, @ModelAttribute("panoscanForm") Scene scene) {
    ModelAndView mov = new ModelAndView("admin/ajaxForm/editPanoTourScene");
    mov.addObject("scenaForm", panoTourSceneService.getPanoTourSceneByNameAndTourId(name, tourId));
    return mov;
  }
}
