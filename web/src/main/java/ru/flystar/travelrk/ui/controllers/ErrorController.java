package ru.flystar.travelrk.ui.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 20.06.2017.
 */
@Controller
public class ErrorController {

  @RequestMapping(value = "errors", method = RequestMethod.GET)
  public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

    ModelAndView errorPage = new ModelAndView("errors/errorPage");
    String errorMsg = "";
    int httpErrorCode = getErrorCode(httpRequest);

    switch (httpErrorCode) {
      case 400: {
        errorMsg = "Http Error Code: 400. Ошибка в запросе!";
        break;
      }
      case 401: {
        errorMsg = "Http Error Code: 401. Неавторизированный запрос";
        break;
      }
      case 404: {
        errorMsg = "Http Error Code: 404. Страница не найдена";
        break;
      }
      case 500: {
        errorMsg = "Http Error Code: 500. Ошибка сервера. Попробуйте зайти позже.";
        break;
      }
    }
    errorPage.addObject("errorMsg", errorMsg);
    return errorPage;
  }

  private int getErrorCode(HttpServletRequest httpRequest) {
    return (Integer) httpRequest
        .getAttribute("javax.servlet.error.status_code");
  }
}