package ru.flystar.travelrk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.flystar.travelrk.domain.persistents.Panorama;
import ru.flystar.travelrk.domain.persistents.RentaTour;

import java.util.List;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 19.03.2018.
 */
public interface RentaTourRepository extends JpaRepository<RentaTour, Integer> {
    RentaTour findByPath(String path);

    List<RentaTour> findByHsForRenta(Panorama panorama);
    List<RentaTour> findByDomain(String path);
    List<RentaTour> findByUser_Login(String login);
}
