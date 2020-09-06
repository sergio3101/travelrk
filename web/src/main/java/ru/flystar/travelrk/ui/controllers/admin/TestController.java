package ru.flystar.travelrk.ui.controllers.admin;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.flystar.travelrk.domain.persistents.TestPano;
import ru.flystar.travelrk.domain.persistents.TestPanoScan;
import ru.flystar.travelrk.repositories.TestPanoRepo;
import ru.flystar.travelrk.repositories.TestPanoScanRepo;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 20.09.2018.
 */
@Controller
@RequestMapping(value = "/test")
@Log4j
public class TestController {
    private TestPanoRepo testPanoRepo;
    private TestPanoScanRepo testPanoScanRepo;

    public TestController(TestPanoRepo testPanoRepo, TestPanoScanRepo testPanoScanRepo) {
        this.testPanoRepo = testPanoRepo;
        this.testPanoScanRepo = testPanoScanRepo;
    }

    @RequestMapping(value = "/1", method = RequestMethod.GET)
    public String getTest1() {
        TestPanoScan panoScan = testPanoScanRepo.findOne(1);
        TestPano pano = testPanoRepo.findOne(4);
//        testPanoScanRepo.delete(3);
        return "";
    }
}
