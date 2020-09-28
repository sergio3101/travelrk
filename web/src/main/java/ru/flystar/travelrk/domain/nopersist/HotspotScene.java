package ru.flystar.travelrk.domain.nopersist;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.flystar.travelrk.domain.persistents.Scene;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 01.02.2018.
 */
@Setter
@Getter
@NoArgsConstructor
public class HotspotScene {
  private int id;
  private double ath;
  private double atv;
  private int distance;
  private Scene scene;

  public HotspotScene(Scene scene) {
    this.id = scene.getId();
    this.scene = scene;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Hotspot hotspot = (Hotspot) o;
    if (getId() != hotspot.getId()) return false;
    return true;
  }

  @Override
  public int hashCode() {
    return getId();
  }
}
