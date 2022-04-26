package ru.flystar.travelrk.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.flystar.travelrk.domain.persistents.PanoTourScene;
import ru.flystar.travelrk.domain.persistents.Panorama;

public interface PanoTourSceneRepository extends JpaRepository<PanoTourScene, Integer> {
  PanoTourScene saveAndFlush(PanoTourScene scene);

  PanoTourScene findById(String id);

  PanoTourScene findByNameAndPanoTourSrc_Id(String name, int panoTourSrc_id);

  List<PanoTourScene> findByPanoTourSrc_Id(int panoTourSrc_id);

  List<PanoTourScene> findByIdIn(List<Integer> ids);
}
