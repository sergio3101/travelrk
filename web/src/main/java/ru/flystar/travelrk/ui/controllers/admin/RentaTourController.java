package ru.flystar.travelrk.ui.controllers.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.flystar.travelrk.domain.persistents.Panorama;
import ru.flystar.travelrk.domain.persistents.RentaTour;
import ru.flystar.travelrk.domain.persistents.User;
import ru.flystar.travelrk.service.CustomerInfoService;
import ru.flystar.travelrk.service.PanoramaService;
import ru.flystar.travelrk.service.RentaTourService;
import ru.flystar.travelrk.service.UserService;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 19.03.2018.
 */
@Controller
@RequestMapping(value = "/admin")
@Secured("ROLE_ADMIN")
@Log4j
public class RentaTourController {
  private RentaTourService rentaTourService;
  private PanoramaService panoramaService;
  private CustomerInfoService customerInfoService;
  private UserService userService;

  @Autowired
  public RentaTourController(RentaTourService rentaTourService, PanoramaService panoramaService, CustomerInfoService customerInfoService, UserService userService) {
    this.rentaTourService = rentaTourService;
    this.panoramaService = panoramaService;
    this.customerInfoService = customerInfoService;
    this.userService = userService;
  }

  @RequestMapping(value = "/rentatour", method = RequestMethod.GET)
  public ModelAndView rentaTourList() {
    ModelAndView modelAndView = new ModelAndView("admin/rentatour");
    List<RentaTour> list = rentaTourService.getAllRentaTours();
    modelAndView.addObject("rentaTourList", list);
    return modelAndView;
  }

  @RequestMapping(value = "/rentaTourEdit-{id}", method = RequestMethod.GET)
  public ModelAndView rentaTourEdit(@PathVariable("id") int id) {
    RentaTour rentaTour;
    if (id > 0) {
      rentaTour = rentaTourService.getRentaTourById(id);
    } else {
      rentaTour = new RentaTour();
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String login = authentication.getName();
      User user = userService.getUserByLogin(login);
      rentaTour.setUser(user);
    }
    List<Panorama> panoramaList = panoramaService.getPanoramaList();
    Map<String, List<Panorama>> groupsOfPano = new HashMap<>();
    for (Panorama p : panoramaList) {
      if (!groupsOfPano.containsKey(p.getRegion().getViewName()))
        groupsOfPano.put(p.getRegion().getViewName(), new ArrayList<>());
      groupsOfPano.get(p.getRegion().getViewName()).add(p);
    }
    ModelAndView mov = new ModelAndView("admin/rentaTourEdit");
    mov.addObject("customers", customerInfoService.getCustomerInfoList());
    mov.addObject("rentaTour", rentaTour);
    mov.addObject("groupsOfPano", groupsOfPano);
    return mov;
  }

  @RequestMapping(value = "/rentaTourEdit-*", method = RequestMethod.POST)
  public ModelAndView saveRentaTourEdit(@ModelAttribute("rentaTour") RentaTour t) {
    Set<Panorama> panoramas = new HashSet<>();
    Set<Panorama> hsPano = t.getHsForRenta();
    t.setHsForRenta(panoramas);
    RentaTour tour = rentaTourService.saveRentaTour(t);
    for (Panorama p : hsPano) {
      panoramas.add(panoramaService.getPanoramaByPanoPath(p.getPanoPath()));
    }
    tour.setHsForRenta(panoramas);
    rentaTourService.saveRentaTour(tour);
    return new ModelAndView("redirect:rentatour");
  }

  @RequestMapping(value = "/ajaxRemoveRentaTour", method = RequestMethod.POST)
  @ResponseBody
  public String ajaxRemovePanoScan(@RequestParam("id") int id) {
    String msg = "ERROR";
    if (rentaTourService.removeRentaTourById(id)) {
      msg = "SUCCESS";
    }
    return msg;
  }
}
