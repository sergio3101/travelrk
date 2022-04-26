package ru.flystar.travelrk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.flystar.travelrk.domain.persistents.PanoTourSrc;

public interface PanoTourSrcRepository extends JpaRepository<PanoTourSrc, Integer> {
  PanoTourSrc findByPath(String path);

  PanoTourSrc findByPathEndingWith(String path);

  PanoTourSrc findByPathStartingWith(String name);
}
