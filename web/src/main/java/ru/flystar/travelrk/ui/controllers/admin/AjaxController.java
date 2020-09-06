package ru.flystar.travelrk.ui.controllers.admin;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.flystar.travelrk.domain.persistents.*;
import ru.flystar.travelrk.service.*;
import ru.flystar.travelrk.tools.StringTool;

import java.util.List;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 21.07.2017.
 */
@Log4j
@RestController
@RequestMapping(value = "/admin")
public class AjaxController {
    private YoutubeService youtubeService;
    private PanoScanService panoScanService;
    private ExclusiveTourService exclusiveTourService;
    private SceneService sceneService;
    private PanoramaService panoramaService;
    private CategoryOfContentService categoryOfContentService;
    private CustomerInfoService customerInfoService;
    private RentaTourService rentaTourService;

    @Autowired
    public AjaxController(YoutubeService youtubeService, PanoScanService panoScanService, ExclusiveTourService exclusiveTourService, SceneService sceneService, PanoramaService panoramaService, CategoryOfContentService categoryOfContentService, CustomerInfoService customerInfoService, RentaTourService rentaTourService) {
        this.youtubeService = youtubeService;
        this.panoScanService = panoScanService;
        this.exclusiveTourService = exclusiveTourService;
        this.sceneService = sceneService;
        this.panoramaService = panoramaService;
        this.categoryOfContentService = categoryOfContentService;
        this.customerInfoService = customerInfoService;
        this.rentaTourService = rentaTourService;
    }

    @RequestMapping(value = "/ajaxRemove", method = RequestMethod.POST)
    @ResponseBody
    public String ajaxRemove(@RequestParam("idytb") String idytb) {
        String msg = "ERROR";
        if (youtubeService.removeById(idytb) > 0) {
            msg = "SUCCESS";
        }
        return msg;
    }

    @RequestMapping(value = "/ajaxRemovePanoscan", method = RequestMethod.POST)
    @ResponseBody
    public String ajaxRemovePanoScan(@RequestParam("path") String path) {
        String msg = "ERROR";
        if (panoScanService.removePanoscanByPath(path) > 0) {
            msg = "SUCCESS";
        }
        return msg;
    }


    @RequestMapping(value = "/ajaxRemoveexclusivetour", method = RequestMethod.POST)
    @ResponseBody
    public String ajaxRemoveexclusivetour(@RequestParam("id") Integer id) {
        exclusiveTourService.removeexclusivetour(id);
        return "SUCCESS";
    }

    @RequestMapping(value = "/ajaxGetVideoById", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public Video ajaxEditById(@RequestParam("idForEdit") String idForEdit) {
        return youtubeService.getVideoById(idForEdit);
    }

    @RequestMapping(value = "/ajaxSaveVideo", method = RequestMethod.POST)
    @ResponseBody
    public String ajaxSaveVideo(@ModelAttribute("video") Video video) {
        String result = "ERROR";
        if (!youtubeService.saveVideo(video).getYoutubeId().isEmpty()) result = "SUCCESS";
        return result;
    }

    @RequestMapping(value = "/ajaxSaveScena", method = RequestMethod.POST)
    @ResponseBody
    public String ajaxSaveScena(@ModelAttribute("scena") Scene scene) {
        String result = "ERROR";
        if (sceneService.saveScene(scene) != null) result = "SUCCESS";
        return result;
    }

    @RequestMapping(value = "/ajaxSavePanoscan", method = RequestMethod.POST)
    @ResponseBody
    public String ajaxSaveVideo(@ModelAttribute("panoscan") PanoScan panoscan) {
        String result = "ERROR";
        if (panoScanService.editPanoscan(panoscan) != null) result = "SUCCESS";
        return result;
    }

    @RequestMapping(value = "/getScansJson", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<PanoScan>> ajaxGetScansJson() {
        ResponseEntity<List<PanoScan>> re = new ResponseEntity<>(panoScanService.getPanoScanList(), HttpStatus.OK);
        return re;
    }

    @RequestMapping(value = "/getPanoramasJson", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Panorama>> ajaxGetPanoramasJson() {
        return new ResponseEntity<>(panoramaService.getPanoramaList(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getCustomersJson", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<CustomerInfo>> ajaxGetCustomersJson() {
        return new ResponseEntity<>(customerInfoService.getCustomerInfoList(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getRentaTourJson", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<RentaTour>> ajaxGetRentaTourJson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<RentaTour> list = null;
        if (authentication != null &&
                authentication
                        .getAuthorities()
                        .stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"))) {
            String login = authentication.getName();
            list = rentaTourService.getRentaToursByOwner(login);
        } else {
            list = rentaTourService.getAllRentaTours();
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/ajaxGenPanorama", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Panorama> ajaxGenPanorama(@RequestParam("path") String path, @RequestParam("krpanoConfigPath") String krpanoConfigPath) {
        PanoScan panoScan = panoScanService.getPanoScanByPath(path);
        Panorama panorama = new Panorama();
        if (!path.isEmpty() && !krpanoConfigPath.isEmpty()) {
            panorama.setPanoPath("pano_" + StringTool.genRandomLowerStr(10));
            panorama.setTitle(panoScan.getName());
            panorama.setRegion(panoScan.getRegion());
            panorama.setCategoryOfContent(categoryOfContentService.getCategoryOfContentByName("natural"));
            panoramaService.addPanorama(panorama, krpanoConfigPath,path);
        }
        return new ResponseEntity<>(panorama, HttpStatus.OK);
    }

    @RequestMapping(value = "/ajaxReGenPano3D", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Panorama> ajaxReGenPano3D(@RequestParam("panoPath") String panoPath, @RequestParam("krpanoConfigPath") String krpanoConfigPath) {
        Panorama panorama = panoramaService.getPanoramaByPanoPath(panoPath);
        panoramaService.addPanorama(panorama, krpanoConfigPath, panorama.getPanoScan() + ".jpg");
        return new ResponseEntity<>(panorama, HttpStatus.OK);
    }

    @RequestMapping(value = "/ajaxAddVideo", method = RequestMethod.POST)
    @ResponseBody
    public String AddSaveVideo(@RequestParam("idForAdd") String idForAdd) {
        return youtubeService.addVideo(idForAdd);
    }

    @RequestMapping(value = "/ajaxSavePreAlign", method = RequestMethod.POST)
    @ResponseBody
    public String savePreAlign(@RequestParam("name") String name, @RequestParam("prealign") String prealign) {
        String result;
        String panoPath = name.replace("scene_", "");
        String[] v = prealign.split("\\|");
        Panorama p = panoramaService.getPanoramaByPanoPath(panoPath);
        p.setNorth(v[1]);
        p.setVa(v[0]);
        p.setVc(v[2]);
        panoramaService.savePanorama(p);
        result = "OK";
        return result;
    }
}
