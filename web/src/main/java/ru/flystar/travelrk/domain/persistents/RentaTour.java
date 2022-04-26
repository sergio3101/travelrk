package ru.flystar.travelrk.domain.persistents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.flystar.travelrk.tools.StringTool;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 19.03.2018.
 */
@Entity
@Table(name = "rentaTour")
@Setter
@Getter
@JsonIgnoreProperties(value = {
    "hsForRenta",
    "defaultPano",
    "description"
})
public class RentaTour extends BaseId {
  @Column(name = "path", nullable = true, length = 1024)
  private String path;

  @Column(name = "name", nullable = true, length = 1024)
  private String name;

  @Column(name = "description", nullable = true, length = 1024)
  private String description;

  @Column(name = "sum")
  private BigDecimal sum;

  @Column(name = "monthCount")
  private Integer monthCount;

  @Column(name = "isFuturePayment")
  private Boolean isFuturePayment;

  @Column(name = "defaultPano", nullable = true, length = 1024)
  private String defaultPano;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Column(name = "dateOfCreate", nullable = true)
  private Date dateOfCreate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Column(name = "rentaExpired", nullable = true)
  private Date rentaExpired;

  @Column(name = "domain", length = 1024)
  private String domain = "";

  @OneToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "rentatour_panoramas",
      joinColumns = @JoinColumn(name = "tour_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "pano_id", referencedColumnName = "id"))
  private Set<Panorama> hsForRenta = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "customerInfo_id")
  CustomerInfo customerInfo;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User user;


  public RentaTour() {
    this.path = StringTool.genPath();
    this.dateOfCreate = new Date();
  }

  @JsonProperty("dayProgress")
  public BigDecimal getDayProgress() {
    Long rentaDays = StringTool.diffDays(dateOfCreate, rentaExpired);
    Long pregressDays = StringTool.diffDays(dateOfCreate, new Date());
    BigDecimal percent = new BigDecimal(100);
    if (rentaDays.compareTo(pregressDays) >= 0)
      percent = BigDecimal.valueOf(((double) pregressDays / rentaDays) * 100).setScale(2, BigDecimal.ROUND_DOWN);
    return percent;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RentaTour rentaTour = (RentaTour) o;
    return path.equals(rentaTour.path);
  }

  @Override
  public int hashCode() {
    return path.hashCode();
  }
}
