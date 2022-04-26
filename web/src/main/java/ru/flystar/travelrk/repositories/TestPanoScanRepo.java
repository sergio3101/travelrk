package ru.flystar.travelrk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.flystar.travelrk.domain.persistents.TestPanoScan;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 20.09.2018.
 */
public interface TestPanoScanRepo extends JpaRepository<TestPanoScan, Integer> {

}
