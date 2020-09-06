package ru.flystar.travelrk.ui.controllers.admin;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.flystar.travelrk.domain.persistents.*;
import ru.flystar.travelrk.service.*;
import ru.flystar.travelrk.ui.dto.ToursConstraight;

import java.util.ArrayList;
import java.util.List;

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
    private PanoScanService panoScanService;
    private RegionService regionService;
    private CategoryOfContentService categoryOfContentService;
    private XmlParserService xmlParser;
    private ExclusiveTourService exclusiveTourService;

    @Autowired
    public Pano3DController(KrpanoConfigService krpanoConfigService, PanoramaService panoramaService, PanoScanService panoScanService, RegionService regionService, CategoryOfContentService categoryOfContentService, XmlParserService xmlParser, ExclusiveTourService exclusiveTourService) {
        this.krpanoConfigService = krpanoConfigService;
        this.panoramaService = panoramaService;
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
        List<ToursConstraight> toursConstraights = new ArrayList<>();
        try {
            panoramaService.removePano3DById(id);
        } catch (DataIntegrityViolationException e) {
            List<RentaTour> rentaTours = panoramaService.getTourNamesByPanoId(id);
            for (RentaTour s:rentaTours) {
                toursConstraights.add(new ToursConstraight(s.getName(), "/admin/rentaTourEdit-" + s.getId()));
            }
            List<ExclusiveTour> exclTours = panoramaService.getExclTourNamesByPanoId(id);
            for (ExclusiveTour s:exclTours) {
                toursConstraights.add(new ToursConstraight(s.getName(), "/admin/exclTourEdit-" + s.getId()));
            }
        }
        return toursConstraights;
    }
}
