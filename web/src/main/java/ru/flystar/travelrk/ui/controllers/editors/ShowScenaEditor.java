package ru.flystar.travelrk.ui.controllers.editors;

import java.beans.PropertyEditorSupport;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ru.flystar.travelrk.domain.persistents.PanoTourScene;
import ru.flystar.travelrk.service.PanoTourSceneService;

public class ShowScenaEditor extends PropertyEditorSupport {
  private final PanoTourSceneService service;

  public ShowScenaEditor(PanoTourSceneService service) {
    this.service = service;
  }

  @Override
  public void setAsText(String text) throws IllegalArgumentException {
    List<Integer> ids = Stream.of(text.split(",")).map(Integer::parseInt).collect(Collectors.toList());
    List<PanoTourScene> scenas = service.getPanoTourScenesByIdList(ids);
    setValue(scenas);
  }
}
