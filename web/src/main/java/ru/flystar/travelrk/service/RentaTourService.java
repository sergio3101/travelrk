package ru.flystar.travelrk.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.flystar.travelrk.domain.persistents.Panorama;
import ru.flystar.travelrk.domain.persistents.RentaTour;
import ru.flystar.travelrk.repositories.RentaTourRepository;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 19.03.2018.
 */
@Service
public class RentaTourService {
  private final RentaTourRepository rentaTourRepository;

  @Autowired
  public RentaTourService(RentaTourRepository rentaTourRepository) {
    this.rentaTourRepository = rentaTourRepository;
  }

  public List<RentaTour> getAllRentaTours() {
    return rentaTourRepository.findAll(new Sort(Sort.Direction.ASC, "dateOfCreate"));
  }

  public RentaTour getRentaTourById(int id) {
    return rentaTourRepository.findOne(id);
  }

  public RentaTour getRentaTourByPath(String path) {
    return rentaTourRepository.findByPath(path);
  }

  public List<RentaTour> getRentaToursByOwner(String login) {
    return rentaTourRepository.findByUser_Login(login);
  }

  public List<RentaTour> getRentaToursByHsForRenta(Panorama panorama) {
    return rentaTourRepository.findByHsForRenta(panorama);
  }

  @Transactional
  public RentaTour saveRentaTour(RentaTour rentaTour) {
    return rentaTourRepository.saveAndFlush(rentaTour);
  }

  @Transactional
  public boolean removeRentaTourById(Integer id) {
    rentaTourRepository.delete(id);
    return true;
  }

  public List<RentaTour> getRentaToursByDomain(String domain) {
    return rentaTourRepository.findByDomain(domain);
  }
}
