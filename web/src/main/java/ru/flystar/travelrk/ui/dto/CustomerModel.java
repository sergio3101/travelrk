package ru.flystar.travelrk.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerModel {
  private String address;
  private String companyName;
  private String email;
  private String id;
  private String officeLat;
  private String officeLng;
  private String phone;
  private String site;
  private String yaBronUrl;
  private String yaid;
  private String logoPath;
  private RegionModel region;
}
