package ru.flystar.travelrk.domain.persistents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 01.12.2017.
 */
@Entity
@Table(name = "customerInfo")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, of = "companyName")
@JsonIgnoreProperties(value = {
    "rentaTours",
    "excltour",
    "logoPath",
    "height",
    "description"
})
public class CustomerInfo extends BaseId {
  @Column(name = "companyName")
  private String companyName = "";
  @Column(name = "phone")
  private String phone = "";
  @Column(name = "address")
  private String address = "";
  @Column(name = "site")
  private String site = "";
  @Column(name = "excltour")
  private String excltour = "";
  @Column(name = "email")
  private String email = "";
  @Column(name = "logoPath")
  private String logoPath = "";
  @Column(name = "officeLat")
  private String officeLat = "";
  @Column(name = "officeLng")
  private String officeLng = "";
  @Column(name = "height")
  private String height = "0";
  @Column(name = "description")
  private String description = "";
  @Column(name = "yaid")
  private String yaid = "";
  @OneToMany(mappedBy = "customerInfo")
  private Set<RentaTour> rentaTours = new HashSet<>();
  @OneToOne
  @JoinColumn(name = "region_id", referencedColumnName = "id", nullable = false)
  private Region region;

  public CustomerInfo(String companyName) {
    this.companyName = companyName;
  }
}
