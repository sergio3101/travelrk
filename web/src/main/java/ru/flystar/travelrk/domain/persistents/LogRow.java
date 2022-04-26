package ru.flystar.travelrk.domain.persistents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLInsert;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "logrow")
@Setter
@Getter
@NoArgsConstructor
@IdClass(LogRowPK.class)
@SQLInsert(sql="INSERT INTO logrow(domain, dateOfCreate, panoTourRenta_id, counter) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE domain=?, dateOfCreate=? counter = counter + 1")
public class LogRow implements Serializable {
  @Column(name = "counter")
  private Integer counter;
  @Id
  @Column(name = "domain")
  private String domain;
  @Id
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Column(name = "dateOfCreate")
  private Date dateOfCreate;
  @Id
  @ManyToOne
  @JoinColumn(name = "panoTourRenta_id")
  private PanoTourRenta panoTourRenta;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    LogRow logRow = (LogRow) o;

    if (domain != null ? !domain.equals(logRow.domain) : logRow.domain != null) return false;
    if (!dateOfCreate.equals(logRow.dateOfCreate)) return false;
    return panoTourRenta.equals(logRow.panoTourRenta);
  }

  @Override
  public int hashCode() {
    int result = domain != null ? domain.hashCode() : 0;
    result = 31 * result + dateOfCreate.hashCode();
    result = 31 * result + panoTourRenta.hashCode();
    return result;
  }
}
