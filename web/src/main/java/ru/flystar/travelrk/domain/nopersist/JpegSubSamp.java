package ru.flystar.travelrk.domain.nopersist;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 22.11.2017.
 */
public enum JpegSubSamp {
  NO_CHROMA(444), HOR12(422), HOR12VERT12(420), HOR12VERT12BEST(411);

  private int value = 422;

  JpegSubSamp(int qlty) {
    value = qlty;
  }

  public int getValue() {
    return value;
  }
}
