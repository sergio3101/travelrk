package ru.flystar.travelrk.domain.persistents;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "testPanoScan")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TestPanoScan extends BaseId {
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "testPanoScan", fetch = FetchType.EAGER)
    private Set<TestPano> panoramas = new HashSet<>();
}
