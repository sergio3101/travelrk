package ru.flystar.travelrk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.flystar.travelrk.domain.persistents.CategoryOfContent;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 01.11.2017.
 */
public interface CategoryOfContentRepository extends JpaRepository<CategoryOfContent, Long> {

  //    @Query("select c from CategoryOfContent c where name = :name")
  CategoryOfContent findByName(String name);
}
