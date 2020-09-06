package ru.flystar.travelrk.domain.persistents;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.flystar.travelrk.domain.serialize.PanoScanSerialize;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 30.10.2017.
 */
@Entity
@Table(name = "panoScan")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = PanoScanSerialize.class)
public class PanoScan extends BaseId {
    @Column(name = "path")
    private String path;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private Region region;

    @Column(name = "size", nullable = true, length = 20)
    private String size;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "dateOfDownload", nullable = true)
    private Date dateOfDownload;

    @OneToMany(mappedBy = "panoramaScan", fetch = FetchType.EAGER)
    private Set<Panorama> panoramas = new HashSet<>();

    public PanoScan(String path) {
        this.path = path;
        this.dateOfDownload = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PanoScan panoScan = (PanoScan) o;

        if (getId() != panoScan.getId()) return false;
        if (path != null ? !path.equals(panoScan.path) : panoScan.path != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }
}
