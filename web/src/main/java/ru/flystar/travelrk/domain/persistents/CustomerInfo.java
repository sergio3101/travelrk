package ru.flystar.travelrk.domain.persistents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
@EqualsAndHashCode(callSuper = true,of = "companyName")
@JsonIgnoreProperties(value = {
        "rentaTours",
        "address",
        "excltour",
        "logoPath",
        "officeLat",
        "officeLng",
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

    @OneToMany(mappedBy = "customerInfo")
    private Set<RentaTour> rentaTours = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "region_id", referencedColumnName = "id", nullable = false)
    private Region region;

    public CustomerInfo(String companyName) {
        this.companyName = companyName;
    }
}
