package ru.flystar.travelrk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.flystar.travelrk.domain.persistents.CustomerInfo;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 01.12.2017.
 */
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Integer> {
  CustomerInfo saveAndFlush(CustomerInfo panorama);

  CustomerInfo findCustomerInfoByYaid(String yaId);
}
