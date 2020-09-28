package ru.flystar.travelrk.ui.controllers.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 18.07.2017.
 */
@Controller
public class LoginController {
  @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
  public ModelAndView login(
      @RequestParam(value = "error", required = false) String error,
      @RequestParam(value = "access", required = false) String access,
      @RequestParam(value = "logout", required = false) String logout) {
    ModelAndView model = new ModelAndView();
    if (error != null) {
      model.addObject("info", "Неправильное имя или пароль!");
    }
    if (access != null) {
      model.addObject("info", "Для доступа к этой странице необходима авторизация!");
    }
    if (logout != null) {
      model.addObject("info", "Вы разлогинились!");
    }
    model.setViewName("admin/login");
    return model;
  }

  @RequestMapping(value = "/admin/logout", method = RequestMethod.GET)
  public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    return "redirect:/admin/login?logout";
  }
}
