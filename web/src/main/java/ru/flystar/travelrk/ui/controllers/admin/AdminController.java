package ru.flystar.travelrk.ui.controllers.admin;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.flystar.travelrk.domain.persistents.*;
import ru.flystar.travelrk.service.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 20.06.2017.
 */
@Controller
@RequestMapping(value = "/admin")
@Secured("ROLE_ADMIN")
@Log4j
public class AdminController {
    private YoutubeService youtubeService;
    private PanoScanService panoScanService;
    private RegionService regionService;
    private CategoryOfContentService categoryOfContentService;
    private KrpanoConfigService krpanoConfigService;
    private XmlParserService xmlParserService;


    @Autowired
    public AdminController(YoutubeService youtubeService, PanoScanService panoScanService, ExclusiveTourService exclusiveTourService, RegionService regionService, CategoryOfContentService categoryOfContentService, KrpanoConfigService krpanoConfigService, XmlParserService xmlParserService) {
        this.youtubeService = youtubeService;
        this.panoScanService = panoScanService;
        this.regionService = regionService;
        this.categoryOfContentService = categoryOfContentService;
        this.krpanoConfigService = krpanoConfigService;
        this.xmlParserService = xmlParserService;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String adminRootPage() {
        return "redirect:/admin/youtubegallery";
    }

    @RequestMapping(value = "/youtubegallery", method = RequestMethod.GET)
    public ModelAndView youtubeGallery() {
        ModelAndView modelAndView = new ModelAndView("admin/youtubegallery");
        modelAndView.addObject("videoList", youtubeService.getVideoList());
        modelAndView.addObject("categoryList", categoryOfContentService.getCategoryList());
        modelAndView.addObject("regionList", regionService.getRegionList());
        return modelAndView;
    }


    @RequestMapping(value = "/panoscans", method = RequestMethod.GET)
    public ModelAndView panoScans() {
        ModelAndView modelAndView = new ModelAndView("admin/panoscans");
        List<PanoScan> list = new ArrayList<>(panoScanService.getPanoScanList());
        modelAndView.addObject("scanList", list);
        modelAndView.addObject("regions", regionService.getRegionList());
        modelAndView.addObject("krpanoConfigList", krpanoConfigService.getKrpanoConfigList());
        return modelAndView;
    }

    @RequestMapping(value = "/ajaxGenPano3D", method = RequestMethod.POST)
    @ResponseBody
    public String ajaxGenPano3D(@ModelAttribute("panoForm") Panorama panorama) {
//        PanoScan psc = panoScanService.getPanoScanByPath(panorama.getPanoScan().getPath());
        Region r = regionService.getRegionByName(panorama.getRegion().getName());
        CategoryOfContent c = categoryOfContentService.getCategoryOfContentByName(panorama.getCategoryOfContent().getName());
//        panorama.setPanoScan(psc);
        panorama.setRegion(r);
        panorama.setCategoryOfContent(c);
        return panorama.toString();
    }

    @RequestMapping(value = "/testHtml", method = RequestMethod.GET)
    public ModelAndView testHtml() {
        ModelAndView modelAndView = new ModelAndView("admin/testHtml");
        List<PanoScan> list = new ArrayList<>(panoScanService.getPanoScanList());
        modelAndView.addObject("scanList", list);
        modelAndView.addObject("regions", regionService.getRegionList());
        modelAndView.addObject("krpanoConfigList", krpanoConfigService.getKrpanoConfigList());
        return modelAndView;
    }
}
