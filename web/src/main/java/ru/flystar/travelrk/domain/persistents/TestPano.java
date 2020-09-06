package ru.flystar.travelrk.domain.persistents;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "testPano")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TestPano extends BaseId {
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "scan_id", nullable = false)
    TestPanoScan testPanoScan;

}
