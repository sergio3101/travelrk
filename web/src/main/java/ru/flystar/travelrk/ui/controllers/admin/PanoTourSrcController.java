package ru.flystar.travelrk.ui.controllers.admin;

import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.flystar.travelrk.domain.persistents.PanoTourScene;
import ru.flystar.travelrk.domain.persistents.PanoTourSrc;
import ru.flystar.travelrk.service.PanoTourSceneService;
import ru.flystar.travelrk.service.PanoTourSrcService;
import ru.flystar.travelrk.service.PanoramaService;
import ru.flystar.travelrk.service.XmlParserService;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
@Secured("ROLE_ADMIN")
@Log4j
public class PanoTourSrcController {
  private PanoTourSrcService panoTourSrcService;
  private PanoramaService panoramaService;
  private XmlParserService xmlParserService;
  private PanoTourSceneService panoTourSceneService;

  @Autowired
  public PanoTourSrcController(PanoTourSrcService panoTourSrcService, PanoramaService panoramaService, XmlParserService xmlParserService, PanoTourSceneService panoTourSceneService) {
    this.panoTourSrcService = panoTourSrcService;
    this.panoramaService = panoramaService;
    this.xmlParserService = xmlParserService;
    this.panoTourSceneService = panoTourSceneService;
  }

  @RequestMapping(value = "/panotoursrc", method = RequestMethod.GET)
  public ModelAndView panotoursrc() {
    ModelAndView modelAndView = new ModelAndView("admin/panotoursrc");
    List<PanoTourSrc> list = panoTourSrcService.getAllPanoTourSrc(false);
    modelAndView.addObject("panoTourSrcList", list);
    return modelAndView;
  }

  @RequestMapping(value = "/panotoursrcedit-{id}", method = RequestMethod.GET)
  public ModelAndView panoTourSrcEdit(
          @PathVariable("id") int id,
          @RequestParam(required = false) String panotoururl) {
    ModelAndView mov = new ModelAndView("admin/panoTourSrcEdit");
    PanoTourSrc panoTourSrc = null;
    if (id == 0 && panotoururl != null && !panotoururl.isEmpty()) {
      panoTourSrc = panoTourSrcService.createPanoTourSrc(panotoururl);
    } else if (id != 0) {
      panoTourSrc = panoTourSrcService.getPanoTourSrcById(id);
    }
    if (panoTourSrc != null) {
      String panoTourSrcHtml = panoTourSrc.getPath().substring(0, panoTourSrc.getPath().lastIndexOf("/"));
      String nameTour = panoTourSrcHtml.substring(panoTourSrcHtml.lastIndexOf("/") + 1);
      mov.addObject("panoTourSrcHtml", panoTourSrcHtml + "/" + StringUtils.capitalize(nameTour) + ".html");
      mov.addObject("panoTourSrc", panoTourSrc);
    }
    return mov;
  }

  @PostMapping("/panoTourSrcEdit")
  public ModelAndView savePanoTourSrcEdit(@ModelAttribute("panoTourSrc") PanoTourSrc t) {
    PanoTourSrc tour = panoTourSrcService.getPanoTourSrcById(t.getId());
    tour.setId(t.getId());
    tour.setName(t.getName());
    tour.setDescription(t.getDescription());
    tour.setSrcXml(tour.getSrcXml().replaceAll("(?m)^[ \t]*\r?\n", ""));
    panoTourSrcService.updatePanoTourSrc(tour);
    return new ModelAndView("redirect:panotoursrc");
  }

  @RequestMapping(value = "/getPanoTourScene.xml", method = RequestMethod.GET)
  public ModelAndView getPanoTourXmlById(@RequestParam("scenaId") int scenaId) {
    ModelAndView modelAndView = new ModelAndView("admin/xml/setNorthXml");
    PanoTourScene scena = panoTourSceneService.getPanoTourSceneById(scenaId);
    String scenaXml = panoTourSrcService.getScenaXml(scena);
    modelAndView.addObject("scena", scena);
    modelAndView.addObject("scenaXml", scenaXml);
    return modelAndView;
  }
}
