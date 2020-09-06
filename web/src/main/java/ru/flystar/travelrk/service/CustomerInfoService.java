package ru.flystar.travelrk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.flystar.travelrk.domain.persistents.CustomerInfo;
import ru.flystar.travelrk.repositories.CustomerInfoRepository;

import java.io.File;
import java.util.List;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 01.12.2017.
 */
@Service
public class CustomerInfoService {

    @Value("${path.customerlogos}")
    private String PATH_CUSTOMER_LOGOS;
    private final CustomerInfoRepository customerInfoRepository;

    @Autowired
    public CustomerInfoService(CustomerInfoRepository customerInfoRepository) {
        this.customerInfoRepository = customerInfoRepository;
    }

    public List<CustomerInfo> getCustomerInfoList() {
        return customerInfoRepository.findAll();
    }

    public CustomerInfo getCustomerInfoById(int id) {
        return customerInfoRepository.findOne(id);
    }

    @Transactional
    public CustomerInfo addCustomerInfo(CustomerInfo customerInfo) {
        return customerInfoRepository.saveAndFlush(customerInfo);
    }

    @Transactional
    public boolean removeCustomerInfoById(int id) throws DataIntegrityViolationException {
        File file = new File(PATH_CUSTOMER_LOGOS + customerInfoRepository.findOne(id).getLogoPath());
        customerInfoRepository.delete(id);
        if (file.exists()) file.delete();
        return !customerInfoRepository.exists(id);
    }

}
