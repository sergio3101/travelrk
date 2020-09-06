package ru.flystar.travelrk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.flystar.travelrk.domain.persistents.PanoScan;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 03.11.2017.
 */
public interface PanoScanRepository extends JpaRepository<PanoScan,Integer> {

    PanoScan findByPath(String path);

    PanoScan saveAndFlush(PanoScan panoScan);

    Integer deleteByPath(String path);
}
