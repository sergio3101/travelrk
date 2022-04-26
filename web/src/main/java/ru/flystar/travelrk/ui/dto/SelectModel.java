package ru.flystar.travelrk.ui.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SelectModel {
  private String region;
  private String rentaTourId;
  private List<CustomerModel> selected;
}
