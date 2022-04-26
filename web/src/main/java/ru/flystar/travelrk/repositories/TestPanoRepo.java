package ru.flystar.travelrk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.flystar.travelrk.domain.persistents.TestPano;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 20.09.2018.
 */
public interface TestPanoRepo extends JpaRepository<TestPano, Integer> {
}
