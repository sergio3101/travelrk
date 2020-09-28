package ru.flystar.travelrk.ui.controllers.admin;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.flystar.travelrk.domain.persistents.Region;
import ru.flystar.travelrk.service.RegionService;
import ru.flystar.travelrk.tools.StringTool;
import ru.flystar.travelrk.ui.dto.CustomerInfoForm;

import java.util.List;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 09.03.2020.
 */
@Controller
@RequestMapping(value = "/admin")
@Secured("ROLE_ADMIN")
@Log4j
public class RegionController {
  private RegionService regionService;

  @Autowired
  public RegionController(RegionService regionService) {
    this.regionService = regionService;
  }

  @RequestMapping(value = "/regions", method = RequestMethod.GET)
  public ModelAndView regions() {
    ModelAndView modelAndView = new ModelAndView("admin/regions");
    List<Region> regionList = regionService.getRegionList();
    modelAndView.addObject("regionList", regionList);
    return modelAndView;
  }

  @RequestMapping(value = "/regionEdit-{id}", method = RequestMethod.GET)
  public ModelAndView editRegion(@PathVariable("id") int id) {
    ModelAndView mov = new ModelAndView("admin/regionEdit");
    Region region;
    if (id == 0) {
      region = new Region();
      region.setRegion_id(1);
      region.setName(StringTool.genRandomLowerStr(8));
    } else {
      region = regionService.getRegionById(id);
    }
    mov.addObject("region", region);
    return mov;
  }

  @RequestMapping(value = "/regionEdit-*", method = RequestMethod.POST)
  public ModelAndView saveRegion(@ModelAttribute("region") Region region) {
    ModelAndView mov = new ModelAndView("redirect:regions");
    regionService.addRegion(region);
    return mov;
  }

  //    @PreAuthorize("hasRole('ROLE_ADMIN')")
  @RequestMapping(value = "/ajaxRemoveRegion", method = RequestMethod.POST)
  @ResponseBody
  public String ajaxRemoveRegion(@RequestParam("id") int id) {
    String msg = "ERROR";
    try {
      if (regionService.removeRegionById(id)) {
        msg = "SUCCESS";
      }
    } catch (DataIntegrityViolationException e) {
      msg = "CONSTRAIGHTS";
    }
    return msg;
  }
}
