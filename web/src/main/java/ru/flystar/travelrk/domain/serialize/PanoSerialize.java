package ru.flystar.travelrk.domain.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.flystar.travelrk.domain.persistents.Panorama;

import java.io.IOException;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 21.09.2018.
 */
public class PanoSerialize extends StdSerializer<Panorama> {
    protected PanoSerialize(Class<Panorama> t) {
        super(t);
    }

    @Override
    public void serialize(Panorama panorama, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

    }
}
