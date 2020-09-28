package ru.flystar.travelrk.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.flystar.travelrk.domain.persistents.ExclusiveTour;
import ru.flystar.travelrk.domain.persistents.Panorama;
import ru.flystar.travelrk.repositories.ExclusiveTourRepository;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 03.11.2017.
 */
@Service
@Log4j
public class ExclusiveTourService {
  @Value("${path.exclusivetour}")
  private String PATH_EXCLUSIVE_TOUR;
  private final ExclusiveTourRepository exclusiveTourRepository;

  @Autowired
  public ExclusiveTourService(ExclusiveTourRepository exclusiveTourRepository) {
    this.exclusiveTourRepository = exclusiveTourRepository;
  }

  public List<ExclusiveTour> getAllexclusivetour() {
    return exclusiveTourRepository.findAll(new Sort(Sort.Direction.ASC, "dateOfDownload"));
  }

  public ExclusiveTour getExclusiveTourById(int id) {
    return exclusiveTourRepository.findOne(id);
  }

  public ExclusiveTour getExclusiveTourByPage(String path) {
    return exclusiveTourRepository.findByPathEndingWith(path);
  }

  public ExclusiveTour getExclusiveTourByName(String name) {
    return exclusiveTourRepository.findByPathStartingWith(name);
  }

  public List<ExclusiveTour> getExclToursByHsForRenta(Panorama panorama) {
    return exclusiveTourRepository.findByHsForRenta(panorama);
  }

  @Transactional
  public ExclusiveTour addexclusivetour(ExclusiveTour exclusiveTour) {
    return exclusiveTourRepository.saveAndFlush(exclusiveTour);
  }

  public void saveExclusiveTour(ExclusiveTour tour) {
    exclusiveTourRepository.saveAndFlush(tour);
  }

  @Transactional
  public void removeexclusivetour(Integer id) {
    ExclusiveTour exclusivetourForDel = exclusiveTourRepository.getOne(id);
    String path = exclusivetourForDel.getPath().substring(0, exclusivetourForDel.getPath().indexOf("/"));
    try {
      String dirExclTour = PATH_EXCLUSIVE_TOUR + path;
      if (dirExclTour.length() > (PATH_EXCLUSIVE_TOUR.length())) {
        FileUtils.deleteDirectory(new File(dirExclTour));
        exclusiveTourRepository.delete(id);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
