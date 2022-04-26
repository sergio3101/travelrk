package ru.flystar.travelrk.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.flystar.travelrk.domain.persistents.CategoryOfContent;
import ru.flystar.travelrk.repositories.CategoryOfContentRepository;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 01.11.2017.
 */
@Service
public class CategoryOfContentService {
  private final CategoryOfContentRepository categoryOfContentRepository;

  @Autowired
  public CategoryOfContentService(CategoryOfContentRepository categoryOfContentRepository) {
    this.categoryOfContentRepository = categoryOfContentRepository;
  }

  public CategoryOfContent getCategoryOfContentByName(String name) {
    return categoryOfContentRepository.findByName(name);
  }

  public List<CategoryOfContent> getCategoryList() {
    return categoryOfContentRepository.findAll();
  }
}
