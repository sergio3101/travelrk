package ru.flystar.travelrk.ui.controllers.admin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.flystar.travelrk.domain.persistents.PanoTourRenta;
import ru.flystar.travelrk.domain.persistents.PanoTourScene;
import ru.flystar.travelrk.domain.persistents.User;
import ru.flystar.travelrk.service.CustomerInfoService;
import ru.flystar.travelrk.service.PanoTourRentaService;
import ru.flystar.travelrk.service.PanoTourSceneService;
import ru.flystar.travelrk.service.PanoTourSrcService;
import ru.flystar.travelrk.service.PanoramaService;
import ru.flystar.travelrk.service.UserService;
import ru.flystar.travelrk.ui.controllers.editors.ShowScenaEditor;

@Controller
@RequestMapping(value = "/admin")
@Secured("ROLE_ADMIN")
@Log4j
public class PanoTourRentaController {
  private PanoTourRentaService panoTourRentaService;
  private PanoTourSrcService panoTourSrcService;
  private PanoramaService panoramaService;
  private CustomerInfoService customerInfoService;
  private UserService userService;
  private PanoTourSceneService panoTourSceneService;

  @Autowired
  public PanoTourRentaController(PanoTourRentaService panoTourRentaService, PanoTourSrcService panoTourSrcService, PanoramaService panoramaService, CustomerInfoService customerInfoService, UserService userService, PanoTourSceneService panoTourSceneService) {
    this.panoTourRentaService = panoTourRentaService;
    this.panoTourSrcService = panoTourSrcService;
    this.panoramaService = panoramaService;
    this.customerInfoService = customerInfoService;
    this.userService = userService;
    this.panoTourSceneService = panoTourSceneService;
  }

  @InitBinder("panoTourRenta")
  protected void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(
        List.class,
        "scenasFowShow",
        new ShowScenaEditor(panoTourSceneService));
  }

  @RequestMapping(value = "/panotourrenta", method = RequestMethod.GET)
  public ModelAndView panoTourRentaList() {
    ModelAndView modelAndView = new ModelAndView("admin/panotourrenta");
    // List<PanoTourRenta> list = panoTourRentaService.getAllPanoTourRentas();
    // modelAndView.addObject("panoTourRentaList", list);
    return modelAndView;
  }

  @RequestMapping(value = "/panoTourRentaEdit-{id}", method = RequestMethod.GET)
  public ModelAndView panoTourRentaEdit(@PathVariable("id") int id) {
    PanoTourRenta panoTourRenta;
    List<PanoTourScene> rentaTourScenas = null;
    if (id > 0) {
      panoTourRenta = panoTourRentaService.getPanoTourRentaById(id);
      rentaTourScenas = panoTourRenta.getPanoTourSrc().getScenes();
      if (panoTourRenta.getScenasFowShow() == null || panoTourRenta.getScenasFowShow().isEmpty()) {
        panoTourRenta.setScenasFowShow(rentaTourScenas);
      }
    } else {
      panoTourRenta = new PanoTourRenta();
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String login = authentication.getName();
      User user = userService.getUserByLogin(login);
      panoTourRenta.setUser(user);
    }
    ModelAndView mov = new ModelAndView("admin/panoTourRentaEdit");
    mov.addObject("rentaTourScenas", rentaTourScenas);
    mov.addObject("customers", customerInfoService.getCustomerInfoList());
    mov.addObject("panoTourSrcs", panoTourSrcService.getAllPanoTourSrc(true));
    mov.addObject("panoTourRenta", panoTourRenta);
    return mov;
  }

  @RequestMapping(value = "/panoTourRentaEdit-*", method = RequestMethod.POST)
  public ModelAndView savePanoTourRentaEdit(@ModelAttribute("panoTourRenta") PanoTourRenta t, BindingResult result, Model model) {
    if (result.hasErrors()) {
      log.info(model);
      List<ObjectError> list = result.getAllErrors();
      log.info("Error binding!!!!!");
      for (ObjectError err : list) {
        log.info(err.getObjectName());
        log.info(err.getDefaultMessage());
        log.info(err.toString());
      }
    }
    PanoTourRenta tour = panoTourRentaService.savePanoTourRenta(t);
    panoTourRentaService.savePanoTourRenta(tour);
    return new ModelAndView("redirect:panotourrenta");
  }

  @RequestMapping(value = "/ajaxRemovePanoTourRenta", method = RequestMethod.POST)
  @ResponseBody
  public String ajaxRemovePanoTourRenta(@RequestParam("id") int id) {
    String msg = "ERROR";
    if (panoTourRentaService.removePanoTourRentaById(id)) {
      msg = "SUCCESS";
    }
    return msg;
  }
}
