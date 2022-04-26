package ru.flystar.travelrk.ui.controllers.admin;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.flystar.travelrk.domain.persistents.CustomerInfo;
import ru.flystar.travelrk.service.CustomerInfoService;
import ru.flystar.travelrk.service.RegionService;
import ru.flystar.travelrk.ui.dto.CustomerInfoForm;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 01.12.2017.
 */
@Controller
@RequestMapping(value = "/admin")
@Secured("ROLE_ADMIN")
@Log4j
public class CustomerInfoController {
  @Value("${path.customerlogos}")
  private String PATH_CUSTOMER_LOGOS;
  @Value("${url.customerlogos}")
  private String URL_CUSTOMER_LOGOS;

  private final CustomerInfoService customerInfoService;
  private final RegionService regionService;

  @Autowired
  public CustomerInfoController(CustomerInfoService customerInfoService, RegionService regionService) {
    this.customerInfoService = customerInfoService;
    this.regionService = regionService;
  }

  @RequestMapping(value = "/customers", method = RequestMethod.GET)
  public ModelAndView customers() {
    ModelAndView modelAndView = new ModelAndView("admin/customers");
    List<CustomerInfo> customerInfolist = customerInfoService.getCustomerInfoList();
    modelAndView.addObject("customerInfolist", customerInfolist);
    return modelAndView;
  }


  @RequestMapping(value = "/customerEdit-{id}", method = RequestMethod.GET)
  public ModelAndView editCustomerInfo(@PathVariable("id") int id) {
    ModelAndView mov = new ModelAndView("admin/customerEdit");
    CustomerInfoForm customerInfoForm;
    if (id > 0) {
      customerInfoForm = getDtoFromInfo(customerInfoService.getCustomerInfoById(id));
    } else {
      customerInfoForm = new CustomerInfoForm();
    }
    String defaultLogo;
    if (customerInfoForm.getLogo().isEmpty()) {
      defaultLogo = "/images/nologo.png";
    } else {
      defaultLogo = URL_CUSTOMER_LOGOS + customerInfoForm.getLogo();
    }
    mov.addObject("regionList", regionService.getRegionList());
    mov.addObject("customerInfoForm", customerInfoForm);
    mov.addObject("defaultLogo", defaultLogo);
    return mov;
  }

  @RequestMapping(value = "/customerEdit-*", method = RequestMethod.POST)
  public ModelAndView saveCustomerInfo(@ModelAttribute("customerInfoForm") CustomerInfoForm customerInfoForm) {
    ModelAndView mov = new ModelAndView("redirect:customers");
    try {
      CustomerInfo customerInfo = getInfoFromDto(customerInfoForm);
      if (customerInfo.getId() == 0) {
        int id = customerInfoService.addCustomerInfo(customerInfo).getId();
        customerInfoForm.setId(id);
      }
      String logoPath = saveLogo(customerInfoForm);
      if (!logoPath.isEmpty()) {
        customerInfo.setLogoPath(logoPath);
      }
      customerInfoService.addCustomerInfo(customerInfo);
    } catch (IOException e) {
      log.info(e.getMessage());
    }
    return mov;
  }

  @Secured("ROLE_ADMIN")
  @RequestMapping(value = "/ajaxRemoveCustomerInfo", method = RequestMethod.POST)
  @ResponseBody
  public String ajaxRemoveCustomerInfo(@RequestParam("id") int id) {
    String msg = "ERROR";
    try {
      if (customerInfoService.removeCustomerInfoById(id)) {
        msg = "SUCCESS";
      }
    } catch (DataIntegrityViolationException e) {
      msg = "CONSTRAIGHTS";
    }
    return msg;
  }

  private String saveLogo(CustomerInfoForm cif) throws IOException {
    String logoPath = "";
    MultipartFile file = cif.getLogoPath();
    if (file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()) {
      log.info(file.getOriginalFilename());
      String ffName = file.getOriginalFilename();
      String fileExt = ffName.substring(ffName.lastIndexOf("."), ffName.length());
      String fileName = ffName.substring(0, ffName.lastIndexOf("."));
      logoPath = fileName + "-" + cif.getId() + fileExt;
      Path path = Paths.get(PATH_CUSTOMER_LOGOS + logoPath);
      file.transferTo(path.toFile());
    }
    return logoPath;
  }

  private CustomerInfo getInfoFromDto(CustomerInfoForm cif) {
    CustomerInfo ci = new CustomerInfo();
    ci.setId(cif.getId());
    ci.setAddress(cif.getAddress());
    ci.setCompanyName(cif.getCompanyName());
    ci.setRegion(cif.getRegion());
    ci.setEmail(cif.getEmail());
    ci.setOfficeLat(cif.getOfficeLat());
    ci.setOfficeLng(cif.getOfficeLng());
    ci.setHeight(cif.getHeight());
    ci.setDescription(cif.getDescription());
    ci.setSite(cif.getSite());
    ci.setPhone(cif.getPhone());
    ci.setExcltour(cif.getExcltour());
    ci.setLogoPath(cif.getLogo());
    return ci;
  }

  private CustomerInfoForm getDtoFromInfo(CustomerInfo ci) {
    CustomerInfoForm cif = new CustomerInfoForm();
    cif.setId(ci.getId());
    cif.setAddress(ci.getAddress());
    cif.setCompanyName(ci.getCompanyName());
    cif.setRegion(ci.getRegion());
    cif.setEmail(ci.getEmail());
    cif.setOfficeLat(ci.getOfficeLat());
    cif.setOfficeLng(ci.getOfficeLng());
    cif.setHeight(ci.getHeight());
    cif.setDescription(ci.getDescription());
    cif.setSite(ci.getSite());
    cif.setPhone(ci.getPhone());
    cif.setExcltour(ci.getExcltour());
    cif.setLogo(ci.getLogoPath());
    return cif;
  }
}
