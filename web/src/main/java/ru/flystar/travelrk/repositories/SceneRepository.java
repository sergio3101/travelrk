package ru.flystar.travelrk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.flystar.travelrk.domain.persistents.Scene;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 29.01.2018.
 */
public interface SceneRepository extends JpaRepository<Scene, Integer> {
    Scene saveAndFlush(Scene scene);

    Scene findByNameAndExclusiveTour_id(String name, int exclusiveTour_id);
}
