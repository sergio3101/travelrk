package ru.flystar.travelrk.ui.controllers.admin;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.flystar.travelrk.domain.persistents.ExclusiveTour;
import ru.flystar.travelrk.domain.persistents.Panorama;
import ru.flystar.travelrk.domain.persistents.Scene;
import ru.flystar.travelrk.service.ExclusiveTourService;
import ru.flystar.travelrk.service.PanoramaService;
import ru.flystar.travelrk.service.SceneService;
import ru.flystar.travelrk.service.XmlParserService;

import java.util.*;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 23.01.2018.
 */
@Controller
@RequestMapping(value = "/admin")
@Secured("ROLE_ADMIN")
@Log4j
public class ExclTourController {
    private ExclusiveTourService exclusiveTourService;
    private PanoramaService panoramaService;
    private XmlParserService xmlParserService;
    private SceneService sceneService;

    @Autowired
    public ExclTourController(ExclusiveTourService exclusiveTourService, PanoramaService panoramaService, XmlParserService xmlParserService, SceneService sceneService) {
        this.exclusiveTourService = exclusiveTourService;
        this.panoramaService = panoramaService;
        this.xmlParserService = xmlParserService;
        this.sceneService = sceneService;
    }

    @RequestMapping(value = "/exclusivetour", method = RequestMethod.GET)
    public ModelAndView exclusivetour() {
        ModelAndView modelAndView = new ModelAndView("admin/exclusivetour");
        List<ExclusiveTour> list = exclusiveTourService.getAllexclusivetour();
        modelAndView.addObject("exclusivetourList", list);
        return modelAndView;
    }

    @RequestMapping(value = "/exclTourEdit-{id}", method = RequestMethod.GET)
    public ModelAndView exclTourEdit(@PathVariable("id") int id) {
        ExclusiveTour exclusiveTour = exclusiveTourService.getExclusiveTourById(id);
        List<Panorama> panoramaList = panoramaService.getPanoramaList();
        Map<String, List<Panorama>> groupsOfPano = new HashMap<>();
        for (Panorama p:panoramaList) {
            if (!groupsOfPano.containsKey(p.getRegion().getViewName()))
                groupsOfPano.put(p.getRegion().getViewName(), new ArrayList<>());
            groupsOfPano.get(p.getRegion().getViewName()).add(p);
        }
        ModelAndView mov = new ModelAndView("admin/exclTourEdit");
        mov.addObject("exclusiveTour", exclusiveTour);
        mov.addObject("groupsOfPano", groupsOfPano);
        return mov;
    }

    @RequestMapping(value = "/exclTourEdit-*", method = RequestMethod.POST)
    public ModelAndView saveExclTourEdit(@ModelAttribute("exclusiveTour") ExclusiveTour t) {
        Set<Panorama> panoramas = new HashSet<>();
        ExclusiveTour tour = exclusiveTourService.getExclusiveTourById(t.getId());
        for (Panorama p:t.getHsForRenta()) {
            panoramas.add(panoramaService.getPanoramaByPanoPath(p.getPanoPath()));
        }
        tour.setId(t.getId());
        tour.setName(t.getName());
        tour.setARenta(t.isARenta());
        tour.setLatitude(t.getLatitude());
        tour.setLongitude(t.getLongitude());
        tour.setDescription(t.getDescription());
        tour.setHeight(t.getHeight());
        tour.setNorth(t.getNorth());
        tour.setHsForRenta(panoramas);
        tour.setKrpanoXml(tour.getKrpanoXml().replaceAll("(?m)^[ \t]*\r?\n", ""));
        exclusiveTourService.saveExclusiveTour(tour);
        return new ModelAndView("redirect:exclusivetour");
    }

    @RequestMapping(value = "/getSceneXml.xml", method = RequestMethod.GET)
    public ModelAndView getSceneXmlById(@RequestParam("scenaId") int scenaId) {
        ModelAndView modelAndView = new ModelAndView("admin/xml/setNorthXml");
        Scene scena = sceneService.getSceneById(scenaId);
        String scenaXml = xmlParserService.getScenaXml(scena);
        modelAndView.addObject("scena", scena);
        modelAndView.addObject("scenaXml", scenaXml);
        return modelAndView;
    }
}
