package ru.flystar.travelrk.domain.nopersist;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.flystar.travelrk.domain.persistents.BaseId;
import ru.flystar.travelrk.domain.persistents.Panorama;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 30.10.2017.
 */
@Setter
@Getter
@NoArgsConstructor
public class Hotspot {
    private int id;
    private double ath;
    private double atv;
    private int distance;
    private Panorama panorama;

    public Hotspot(Panorama panorama) {
        this.id = panorama.getId();
        this.panorama = panorama;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hotspot hotspot = (Hotspot) o;

        return panorama.equals(hotspot.panorama);
    }

    @Override
    public int hashCode() {
        return panorama.hashCode();
    }
}
