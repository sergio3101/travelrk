package ru.flystar.travelrk.service;

import java.util.List;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.flystar.travelrk.domain.persistents.PanoTourRenta;
import ru.flystar.travelrk.repositories.PanoTourRentaRepository;

@Service
@Log4j
public class PanoTourRentaService {
  private final PanoTourRentaRepository panoTourRentaRepository;

  @Autowired
  public PanoTourRentaService(PanoTourRentaRepository panoTourRentaRepository) {
    this.panoTourRentaRepository = panoTourRentaRepository;
  }

  public List<PanoTourRenta> getAllPanoTourRentas() {
    return panoTourRentaRepository.findAll();
  }

  public PanoTourRenta getPanoTourRentaById(int id) {
    return panoTourRentaRepository.findOne(id);
  }

  public PanoTourRenta getPanoTourRentaByPath(String path) {
    return panoTourRentaRepository.findByPath(path);
  }

  public List<PanoTourRenta> getPanoTourRentasByOwner(String login) {
    return panoTourRentaRepository.findByUser_Login(login);
  }

  @Transactional
  public PanoTourRenta savePanoTourRenta(PanoTourRenta panoTourRenta) {
    return panoTourRentaRepository.saveAndFlush(panoTourRenta);
  }

  @Transactional
  public boolean removePanoTourRentaById(Integer id) {
    panoTourRentaRepository.delete(id);
    return true;
  }

  public List<PanoTourRenta> getPanoTourRentasByDomain(String domain) {
    return panoTourRentaRepository.findByDomain(domain);
  }

  @Transactional
  public void incrementCountById(Integer id) {
    panoTourRentaRepository.incrementCounterById(id);
  }
}
