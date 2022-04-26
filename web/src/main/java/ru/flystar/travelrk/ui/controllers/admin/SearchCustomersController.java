package ru.flystar.travelrk.ui.controllers.admin;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.flystar.travelrk.service.CustomerInfoService;
import ru.flystar.travelrk.service.RegionService;

@Controller
@RequestMapping(value = "/admin")
@Secured("ROLE_ADMIN")
@Log4j
public class SearchCustomersController {

  private final CustomerInfoService customerInfoService;
  private final RegionService regionService;

  @Autowired
  public SearchCustomersController(CustomerInfoService customerInfoService, RegionService regionService) {
    this.customerInfoService = customerInfoService;
    this.regionService = regionService;
  }

  @RequestMapping(value = "/searchcustomers", method = RequestMethod.GET)
  public ModelAndView customers() {
    ModelAndView modelAndView = new ModelAndView("admin/searchcustomers");
    modelAndView.addObject("regions", regionService.getRegionList());
    return modelAndView;
  }
}
