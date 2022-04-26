package ru.flystar.travelrk.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.flystar.travelrk.domain.persistents.PanoTourScene;
import ru.flystar.travelrk.domain.persistents.Panorama;
import ru.flystar.travelrk.domain.persistents.Scene;
import ru.flystar.travelrk.repositories.PanoTourSceneRepository;

@Service
public class PanoTourSceneService {
  private final PanoTourSceneRepository panoTourSceneRepository;

  @Autowired
  public PanoTourSceneService(PanoTourSceneRepository panoTourSceneRepository) {
    this.panoTourSceneRepository = panoTourSceneRepository;
  }

  public PanoTourScene getPanoTourSceneByNameAndTourId(String name, int tourId) {
    return panoTourSceneRepository.findByNameAndPanoTourSrc_Id(name, tourId);
  }

  public List<PanoTourScene> getPanoTourScenesByTourId(int tourId) {
    return panoTourSceneRepository.findByPanoTourSrc_Id(tourId);
  }

  public List<PanoTourScene> getPanoTourScenesByIdList(List<Integer> ids) {
    return panoTourSceneRepository.findByIdIn(ids);
  }

  public PanoTourScene getPanoTourSceneById(int id) {
    return panoTourSceneRepository.findOne(id);
  }

  @Transactional
  public PanoTourScene savePanoTourScene(PanoTourScene scene) {
    return panoTourSceneRepository.saveAndFlush(scene);
  }
}
