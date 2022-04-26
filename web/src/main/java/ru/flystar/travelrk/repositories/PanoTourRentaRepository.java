package ru.flystar.travelrk.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.flystar.travelrk.domain.persistents.PanoTourRenta;

public interface PanoTourRentaRepository extends JpaRepository<PanoTourRenta, Integer> {
  @Transactional
  @EntityGraph(value = "PanoTourRenta.detail", type = EntityGraph.EntityGraphType.LOAD)
  PanoTourRenta findByPath(String path);

  @Override
  @Transactional
  @EntityGraph(value = "PanoTourRenta.detail", type = EntityGraph.EntityGraphType.LOAD)
  List<PanoTourRenta> findAll();

  @Override
  @Transactional
  @EntityGraph(value = "PanoTourRenta.detail", type = EntityGraph.EntityGraphType.LOAD)
  PanoTourRenta findOne(Integer integer);

  @Override
  @Transactional
  @EntityGraph(value = "PanoTourRenta.detail", type = EntityGraph.EntityGraphType.LOAD)
  <S extends PanoTourRenta> S saveAndFlush(S s);

  @Transactional
  @EntityGraph(value = "PanoTourRenta.detail", type = EntityGraph.EntityGraphType.LOAD)
  List<PanoTourRenta> findByDomain(String path);

  @Transactional
  @EntityGraph(value = "PanoTourRenta.detail", type = EntityGraph.EntityGraphType.LOAD)
  List<PanoTourRenta> findByUser_Login(String login);

  @Modifying
  @Query("UPDATE PanoTourRenta p set p.counter = p.counter + 1 WHERE p.id = :id")
  void incrementCounterById(@Param("id") Integer id);
}
