package ru.flystar.travelrk.ui.controllers.admin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.flystar.travelrk.domain.persistents.ExclusiveTour;
import ru.flystar.travelrk.domain.persistents.KrpanoConfig;
import ru.flystar.travelrk.domain.persistents.PanoScan;
import ru.flystar.travelrk.domain.persistents.Panorama;
import ru.flystar.travelrk.domain.persistents.RentaTour;
import ru.flystar.travelrk.service.CategoryOfContentService;
import ru.flystar.travelrk.service.ExclusiveTourService;
import ru.flystar.travelrk.service.KrpanoConfigService;
import ru.flystar.travelrk.service.PanoScanService;
import ru.flystar.travelrk.service.PanoramaService;
import ru.flystar.travelrk.service.RegionService;
import ru.flystar.travelrk.service.RentaTourService;
import ru.flystar.travelrk.service.XmlParserService;
import ru.flystar.travelrk.ui.dto.ToursConstraight;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 29.11.2017.
 */
@Controller
@RequestMapping(value = "/admin")
@Secured("ROLE_ADMIN")
@Log4j
public class Pano3DController {
  private KrpanoConfigService krpanoConfigService;
  private PanoramaService panoramaService;
  private RentaTourService rentaTourService;
  private PanoScanService panoScanService;
  private RegionService regionService;
  private CategoryOfContentService categoryOfContentService;
  private XmlParserService xmlParser;
  private ExclusiveTourService exclusiveTourService;

  @Autowired
  public Pano3DController(KrpanoConfigService krpanoConfigService, PanoramaService panoramaService, RentaTourService rentaTourService, PanoScanService panoScanService, RegionService regionService, CategoryOfContentService categoryOfContentService, XmlParserService xmlParser, ExclusiveTourService exclusiveTourService) {
    this.krpanoConfigService = krpanoConfigService;
    this.panoramaService = panoramaService;
    this.rentaTourService = rentaTourService;
    this.panoScanService = panoScanService;
    this.regionService = regionService;
    this.categoryOfContentService = categoryOfContentService;
    this.xmlParser = xmlParser;
    this.exclusiveTourService = exclusiveTourService;
  }

  @RequestMapping(value = "/pano3D", method = RequestMethod.GET)
  public ModelAndView pano3D() {
    ModelAndView modelAndView = new ModelAndView("admin/pano3D");
    List<Panorama> panoramalist = panoramaService.getPanoramaList();
    List<PanoScan> panoScanList = panoScanService.getPanoScanList();
    List<KrpanoConfig> krpanoConfigList = krpanoConfigService.getKrpanoConfigList();
    modelAndView.addObject("panoramalist", panoramalist);
    modelAndView.addObject("panoScanList", panoScanList);
    modelAndView.addObject("krpanoConfigList", krpanoConfigList);
    return modelAndView;
  }

  @RequestMapping(value = "/pano3D", method = RequestMethod.POST)
  public ModelAndView makePano3D(@RequestParam("panoScanPath") String panoScanPath, @RequestParam("krpanoConfigPath") String krpanoConfigPath) {
    ModelAndView mov = new ModelAndView("redirect:pano3D");
    Panorama panorama = new Panorama();
    if (!panoScanPath.isEmpty() && !krpanoConfigPath.isEmpty()) {
      panorama.setPanoPath(panoScanPath.substring(0, panoScanPath.lastIndexOf('.')));
      panorama.setRegion(regionService.getRegionByName("crimea"));
      panorama.setCategoryOfContent(categoryOfContentService.getCategoryOfContentByName("natural"));
//            panoramaService.addPanorama(panorama, krpanoConfigPath);
    }
    return mov;
  }

  @RequestMapping(value = "/pano3Dedit-{id}", method = RequestMethod.GET)
  public ModelAndView editPano3D(@PathVariable("id") int id) {
    Panorama panorama = panoramaService.getPanoramaById(id);
    ModelAndView mov = new ModelAndView("admin/pano3Dedit");
    mov.addObject("regionList", regionService.getRegionList());
    mov.addObject("categoryList", categoryOfContentService.getCategoryList());
    mov.addObject("panorama", panorama);
    mov.addObject("krpanoConfigList", krpanoConfigService.getKrpanoConfigList());
    return mov;
  }

  @RequestMapping(value = "/pano3Dedit-*", method = RequestMethod.POST)
  public ModelAndView savePano3D(@ModelAttribute("panorama") Panorama panorama) {
    ModelAndView mov = new ModelAndView("redirect:pano3D");
    if (panorama != null) {
      panoramaService.savePanorama(panorama);
    }
    return mov;
  }

  @RequestMapping(value = "/ajaxRemovePano3D", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
  @ResponseBody
  public List<ToursConstraight> ajaxRemovePano3D(@RequestParam("id") int id) {
    try {
      panoramaService.removePano3DById(id);
    } catch (DataIntegrityViolationException e) {
      List<RentaTour> rentaTours = panoramaService.getTourNamesByPanoId(id);
      if (!rentaTours.isEmpty()) {
        Panorama panorama = panoramaService.getPanoramaById(id);
        rentaTours.forEach(t -> {
          t.getHsForRenta().remove(panorama);
          String defPano = t.getDefaultPano();
          String panoPath = panorama.getPanoPath();
          if (panoPath.equals(defPano)) {
            t.setDefaultPano(
                getHighestPano(t.getHsForRenta())
                    .getPanoPath());
          }
          rentaTourService.saveRentaTour(t);
        });
        panoramaService.removePano3DById(id);
      }
    }
    return new ArrayList<>();
  }

  private Panorama getHighestPano(Set<Panorama> hsForRenta) {
    return hsForRenta
        .stream()
        .filter(Panorama::isAAir)
        .max(Comparator.comparing(p -> Integer.parseInt(p.getHeight())))
        .get();
  }
}
