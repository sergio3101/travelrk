package ru.flystar.travelrk.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.flystar.travelrk.domain.persistents.Region;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 01.11.2017.
 */
public interface RegionRepository extends JpaRepository<Region, Integer> {

  //    @Query("select r from Region r where name = :name")
  Region findByName(String name);

  List<Region> findByOrderByViewNameAsc();

}
