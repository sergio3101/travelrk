package ru.flystar.travelrk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.flystar.travelrk.domain.persistents.ExclusiveTour;
import ru.flystar.travelrk.domain.persistents.Panorama;

import java.util.List;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 03.11.2017.
 */
public interface ExclusiveTourRepository extends JpaRepository<ExclusiveTour, Integer> {
    ExclusiveTour findByPath(String path);

    ExclusiveTour findByPathEndingWith(String path);

    ExclusiveTour findByPathStartingWith(String name);

    List<ExclusiveTour> findByHsForRenta(Panorama panorama);
}
