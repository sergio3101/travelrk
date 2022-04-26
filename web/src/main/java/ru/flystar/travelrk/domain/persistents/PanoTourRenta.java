package ru.flystar.travelrk.domain.persistents;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import ru.flystar.travelrk.tools.StringTool;

@Entity
@Table(name = "panotourrenta")
@Setter
@Getter
@JsonIgnoreProperties(value = {
    "defaultPano",
    "description",
    "scenasFowShow"
})
@NamedEntityGraph(name = "PanoTourRenta.detail",
    attributeNodes = @NamedAttributeNode("scenasFowShow"))
public class PanoTourRenta extends BaseId {
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

  @Column(name = "counter")
  private Integer counter = 0;

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

  @ManyToOne
  @JoinColumn(name = "customerInfo_id")
  private CustomerInfo customerInfo;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "panotourrenta_scenas",
      joinColumns = @JoinColumn(name = "tour_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "scena_id", referencedColumnName = "id"))
  private List<PanoTourScene> scenasFowShow = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "panoTourSrc_id")
  private PanoTourSrc panoTourSrc;

  public PanoTourRenta() {
    this.path = StringTool.genPath();
    this.dateOfCreate = new Date();
  }
/*
  public Integer[] getScenasFowShow(){
    return scenasFowShow.stream().map(e -> e.getId()).toArray(Integer[]::new);
  }*/

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
    PanoTourRenta panoTourRenta = (PanoTourRenta) o;
    return path.equals(panoTourRenta.path);
  }

  @Override
  public int hashCode() {
    return path.hashCode();
  }
}
