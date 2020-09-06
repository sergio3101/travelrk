package ru.flystar.travelrk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.flystar.travelrk.domain.persistents.Panorama;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 24.11.2017.
 */
public interface PanoramaRepository extends JpaRepository<Panorama, Integer> {
    Panorama findByPanoPath(String panoPath);

    Panorama saveAndFlush(Panorama panorama);

    Panorama findByPanoScan(String panoScan);
}
