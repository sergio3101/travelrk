package ru.flystar.travelrk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.flystar.travelrk.domain.persistents.LogRow;

public interface LogRowRepo extends JpaRepository<LogRow, Integer> {

}
