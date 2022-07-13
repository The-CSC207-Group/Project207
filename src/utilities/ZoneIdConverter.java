/*
 * NOTE TO TA:
 * The Gson library has trouble deserializing immutable objects, hence why TypeAdapters are needed.
 * Unfortunately, the only library I found that contains TypeAdapters for java.time.* is incomplete as it is missing the
 * java.time.ZoneId TypeAdapter.
 * Therefore, I have gotten this code from a push request on that same library as can be seen here:
 * https://github.com/gkopff/gson-javatime-serialisers/pull/24/commits
 */

package utilities;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.ZoneId;

/**
 * GSON serializer/deserializer for converting {@link ZoneId} objects.
 */
public class ZoneIdConverter implements JsonSerializer<ZoneId>, JsonDeserializer<ZoneId> {

    /**
     * Gson invokes this call-back method during serialization when it encounters a field of the
     * specified type. <p>
     *
     * In the implementation of this call-back method, you should consider invoking
     * {@link JsonSerializationContext#serialize(Object, Type)} method to create JsonElements for any
     * non-trivial field of the {@code src} object. However, you should never invoke it on the
     * {@code src} object itself since that will cause an infinite loop (Gson will call your
     * call-back method again).
     *
     * @param src the object that needs to be converted to Json.
     * @param typeOfSrc the actual type (fully generalized version) of the source object.
     * @return a JsonElement corresponding to the specified object.
     */
    @Override
    public JsonElement serialize(final ZoneId src, final Type typeOfSrc, final JsonSerializationContext context) {
        if (src == null) {
            return null;
        }
        return new JsonPrimitive(src.getId());
    }

    /**
     * Gson invokes this call-back method during deserialization when it encounters a field of the
     * specified type. <p>
     *
     * In the implementation of this call-back method, you should consider invoking
     * {@link JsonDeserializationContext#deserialize(JsonElement, Type)} method to create objects
     * for any non-trivial field of the returned object. However, you should never invoke it on the     *  same type passing {@code json} since that will cause an infinite loop (Gson will call your
     * call-back method again).
     *
     * @param json The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @return a deserialized object of the specified type typeOfT which is a subclass of {@code T}
     * @throws JsonParseException if json is not in the expected format of {@code typeOfT}
     */
    @Override
    public ZoneId deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws
            JsonParseException {
        if (json == null) {
            return null;
        }
        if (json.isJsonNull()) {
            return null;
        }
        final String zoneIdentifier = json.getAsString();
        if (zoneIdentifier == null || zoneIdentifier.isBlank())
        {
            return null;
        }
        return ZoneId.of(zoneIdentifier);
    }
}
