package ru.flystar.travelrk.domain.persistents;

import java.io.Serializable;
import java.util.Date;

public class LogRowPK implements Serializable {
  private String domain;
  private Date dateOfCreate;
  private Integer panoTourRenta;

  public LogRowPK() {
  }

  public LogRowPK(String domain, Date dateOfCreate, Integer panoTourRenta) {
    this.domain = domain;
    this.dateOfCreate = dateOfCreate;
    this.panoTourRenta = panoTourRenta;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    LogRowPK logRowPK = (LogRowPK) o;

    if (domain != null ? !domain.equals(logRowPK.domain) : logRowPK.domain != null) return false;
    if (!dateOfCreate.equals(logRowPK.dateOfCreate)) return false;
    return panoTourRenta.equals(logRowPK.panoTourRenta);
  }

  @Override
  public int hashCode() {
    int result = domain != null ? domain.hashCode() : 0;
    result = 31 * result + dateOfCreate.hashCode();
    result = 31 * result + panoTourRenta.hashCode();
    return result;
  }
}
