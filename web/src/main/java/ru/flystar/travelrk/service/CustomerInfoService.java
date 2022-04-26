package ru.flystar.travelrk.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.flystar.travelrk.domain.persistents.CustomerInfo;
import ru.flystar.travelrk.repositories.CustomerInfoRepository;


/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 01.12.2017.
 */
@Service
public class CustomerInfoService {
  @Value("${path.customerlogos}")
  private String pathCustomerLogos;
  @Value("${ya.apikey}")
  private String yaApiKey;
  private static final String YA_QUERY = "гостиница %s республика крым";
  @Value("${ya.srchurl}")
  private String yaSrchUrl;

  private final CustomerInfoRepository customerInfoRepository;

  @Autowired
  public CustomerInfoService(CustomerInfoRepository customerInfoRepository) {
    this.customerInfoRepository = customerInfoRepository;
  }

  public List<CustomerInfo> getCustomerInfoList() {
    return customerInfoRepository.findByOrderByCompanyName();
  }

  public CustomerInfo getCustomerInfoById(int id) {
    return customerInfoRepository.findOne(id);
  }

  public CustomerInfo findByYaid(String yaId) {
    return customerInfoRepository.findCustomerInfoByYaid(yaId);
  }

  public List<CustomerInfo> getListCIByYaSearch(String srchQuery, Boolean isSiteExist) {
    ArrayList<CustomerInfo> searchResult = new ArrayList<>();
    try {
      ObjectMapper om = new ObjectMapper();
      String query = String.format(YA_QUERY, URLDecoder.decode(srchQuery, String.valueOf(StandardCharsets.UTF_8)));
      JsonNode root = null;
      String srchUrl = String.format(yaSrchUrl,
          URLEncoder.encode(query, String.valueOf(StandardCharsets.UTF_8)),
          yaApiKey);
      root = om.readTree(new URL(srchUrl));
      ArrayNode features = (ArrayNode) root.get("features");
      features.forEach(e -> {
        CustomerInfo ci = convertFeatureToCustomerInfo(e, isSiteExist);
        if (ci != null) {
          searchResult.add(ci);
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
    return searchResult;
  }

  private CustomerInfo convertFeatureToCustomerInfo(JsonNode e, Boolean isSiteExist) {
    CustomerInfo info = null;
    String url = e.get("properties").get("CompanyMetaData").path("url").asText();
    if (!isSiteExist || (url != null && !url.isEmpty())) {
      info = new CustomerInfo();
      info.setSite(url);
      info.setOfficeLng(e.get("geometry").get("coordinates").get(0).asText());
      info.setOfficeLat(e.get("geometry").get("coordinates").get(1).asText());
      info.setCompanyName(e.get("properties").get("name").asText());
      info.setDescription(e.get("properties").get("description").asText());
      info.setYaid(e.get("properties").get("CompanyMetaData").path("id").asText());
      info.setAddress(e.get("properties").get("CompanyMetaData").path("address").asText());
      List<String> phones = new ArrayList<>();
      e.get("properties").get("CompanyMetaData").path("Phones").forEach(a -> {
        phones.add(a.get("formatted").asText());
      });
      info.setPhone(String.join(", ", phones));
    }
    return info;
  }

  @Transactional
  public CustomerInfo addCustomerInfo(CustomerInfo customerInfo) {
    return customerInfoRepository.saveAndFlush(customerInfo);
  }

  @Transactional
  public void saveAll(List<CustomerInfo> customerInfoList) {
    customerInfoRepository.save(customerInfoList);
    customerInfoRepository.flush();
  }

  @Transactional
  public boolean removeCustomerInfoById(int id) throws DataIntegrityViolationException {
    File file = new File(pathCustomerLogos + customerInfoRepository.findOne(id).getLogoPath());
    customerInfoRepository.delete(id);
    if (file.exists()) file.delete();
    return !customerInfoRepository.exists(id);
  }

}
