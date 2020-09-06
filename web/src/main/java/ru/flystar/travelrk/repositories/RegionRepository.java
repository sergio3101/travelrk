package ru.flystar.travelrk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.flystar.travelrk.domain.persistents.Region;

import java.util.List;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 01.11.2017.
 */
public interface RegionRepository extends JpaRepository<Region, Integer> {

    //    @Query("select r from Region r where name = :name")
    Region findByName(String name);

    List<Region> findByOrderByViewNameAsc();

}
