package ru.flystar.travelrk.domain.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import ru.flystar.travelrk.domain.persistents.PanoScan;
import ru.flystar.travelrk.domain.persistents.Panorama;
import ru.flystar.travelrk.service.PanoScanService;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 17.09.2018.
 */
public class PanoScanSerialize extends StdSerializer<PanoScan> {
  @Autowired
  private PanoScanService panoScanService;

  public PanoScanSerialize() {
    this(null);
  }

  public PanoScanSerialize(Class<PanoScan> t) {
    super(t);
  }

  @Override
  public void serialize(PanoScan p, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
    jgen.writeStartObject();
    jgen.writeNumberField("id", p.getId());
    jgen.writeStringField("dateOfDownload", p.getDateOfDownload().toString());
    jgen.writeStringField("name", p.getName());
    jgen.writeStringField("path", p.getPath());
    jgen.writeObjectField("region", p.getRegion());
    jgen.writeStringField("size", p.getSize());
    if (p.getPanoramas() != null && !p.getPanoramas().isEmpty()) {
      jgen.writeFieldName("panoramas");
      jgen.writeStartArray();
      Iterator<Panorama> itPano = p.getPanoramas().iterator();
      while (itPano.hasNext()) {
        Panorama pano = itPano.next();
        jgen.writeStartObject();
        jgen.writeNumberField("id", pano.getId());
        jgen.writeStringField("name", pano.getTitle());
        jgen.writeStringField("panoPath", pano.getPanoPath());
        jgen.writeEndObject();
      }
      jgen.writeEndArray();
    }
    jgen.writeEndObject();
  }
}
