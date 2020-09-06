package ru.flystar.travelrk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.flystar.travelrk.domain.persistents.Scene;
import ru.flystar.travelrk.repositories.SceneRepository;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 29.01.2018.
 */
@Service
public class SceneService {
    private final SceneRepository sceneRepository;

    @Autowired
    public SceneService(SceneRepository sceneRepository) {
        this.sceneRepository = sceneRepository;
    }

    public Scene getSceneByNameAndTourId(String name, int tourId) {
        return sceneRepository.findByNameAndExclusiveTour_id(name, tourId);
    }

    public Scene getSceneById(int id) {
        return sceneRepository.findOne(id);
    }

    @Transactional
    public Scene saveScene(Scene scene) {
        return sceneRepository.saveAndFlush(scene);
    }
}
