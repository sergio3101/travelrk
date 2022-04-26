package ru.flystar.travelrk.ui.controllers.admin;

import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.flystar.travelrk.domain.nopersist.AutoLevel;
import ru.flystar.travelrk.domain.nopersist.CubeFormat;
import ru.flystar.travelrk.domain.nopersist.ImageFilter;
import ru.flystar.travelrk.domain.nopersist.JpegSubSamp;
import ru.flystar.travelrk.domain.nopersist.PanoType;
import ru.flystar.travelrk.domain.persistents.KrpanoConfig;
import ru.flystar.travelrk.service.KrpanoConfigService;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 21.11.2017.
 */
@Controller
@RequestMapping(value = "/admin")
@Secured("ROLE_ADMIN")
@Log4j
public class KrpanoConfigController {
  private final KrpanoConfigService krpanoConfigService;

  @Autowired
  public KrpanoConfigController(KrpanoConfigService krpanoConfigService) {
    this.krpanoConfigService = krpanoConfigService;
  }

  @RequestMapping(value = "/krpanoconfigs", method = RequestMethod.GET)
  public ModelAndView exclusivetour() {
    ModelAndView modelAndView = new ModelAndView("admin/krpanoconfigs");
    List<KrpanoConfig> list = krpanoConfigService.getKrpanoConfigList();
    modelAndView.addObject("krpanoConfigList", list);
    return modelAndView;
  }

  @RequestMapping(value = "/krpanoconfigedit-{id}", method = RequestMethod.GET)
  public ModelAndView editKrpanoConfig(@PathVariable("id") int id) {
    ModelAndView mov = new ModelAndView("admin/krpanoconfigedit");
    KrpanoConfig def = new KrpanoConfig();
    KrpanoConfig krpanoConfig;
    if (id > 0) {
      krpanoConfig = krpanoConfigService.getKrpanoConfigById(id);
    } else {
      krpanoConfig = new KrpanoConfig();
    }
    PanoType[] panotypes = PanoType.values();
    CubeFormat[] cubeFormats = CubeFormat.values();
    AutoLevel[] autoLevels = AutoLevel.values();
    ImageFilter[] imageFilters = ImageFilter.values();
    JpegSubSamp[] jpegSubSamps = JpegSubSamp.values();
    mov.addObject("krpanoConfig", krpanoConfig);
    mov.addObject("panoTypes", panotypes);
    mov.addObject("cubeFormats", cubeFormats);
    mov.addObject("autoLevels", autoLevels);
    mov.addObject("imageFilters", imageFilters);
    mov.addObject("jpegSubSamps", jpegSubSamps);
    ObjectMapper mapper = new ObjectMapper();
    try {
      mov.addObject("def", mapper.writeValueAsString(def));
    } catch (JsonProcessingException e) {
      log.info(e.getMessage());
    }
    return mov;
  }

  @RequestMapping(value = "/krpanoconfigedit-*", method = RequestMethod.POST)
  public ModelAndView saveKrpanoConfig(@ModelAttribute("krpanoConfig") KrpanoConfig krpanoConfig) {
    ModelAndView mov = new ModelAndView("redirect:krpanoconfigs");
    if (krpanoConfig != null) {
      krpanoConfigService.addKrpanoConfig(krpanoConfig);
    }
    return mov;
  }

  @RequestMapping(value = "/ajaxRemoveKrpanoconfig", method = RequestMethod.POST)
  @ResponseBody
  public String ajaxRemovePanoScan(@RequestParam("id") int id) {
    String msg = "ERROR";
    if (krpanoConfigService.removeKrpanoConfigById(id)) {
      msg = "SUCCESS";
    }
    return msg;
  }
}
