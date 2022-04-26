package ru.flystar.travelrk.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.flystar.travelrk.domain.persistents.LogRow;
import ru.flystar.travelrk.repositories.LogRowRepo;

@Service
public class LogRowService {
  private final LogRowRepo logRowRepo;

  @Autowired
  public LogRowService(LogRowRepo logRowRepo) {
    this.logRowRepo = logRowRepo;
  }

  public List<LogRow> getAllLogRow() {
    return logRowRepo.findAll();
  }

  @Transactional
  public LogRow saveLogRow(LogRow logRow) {
    return logRowRepo.saveAndFlush(logRow);
  }
}
