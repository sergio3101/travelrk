package ru.flystar.travelrk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.flystar.travelrk.domain.persistents.KrpanoConfig;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 21.11.2017.
 */
public interface KrpanoConfigRepository extends JpaRepository<KrpanoConfig, Integer> {
  KrpanoConfig findByPath(String path);

  KrpanoConfig saveAndFlush(KrpanoConfig panoScan);

}
