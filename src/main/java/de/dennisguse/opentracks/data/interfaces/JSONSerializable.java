package de.dennisguse.opentracks.data.interfaces;

import com.google.gson.Gson;

/**
 * Adds JSON serialization to a class using the Google gson library.
 * Allows nesting and exposing individual attributes with the @Expose tag.
 * @param <T> Reference to implementing class type.
 */
public interface JSONSerializable<T> {

    /**
     * Serialize current object to a JSON string.
     * NOTE - Only exposed attributes will be included (see @Expose).
     * @return JSON String representation of object.
     */
    default String toJSON() {
        final Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * Loads an object from a JSON string.
     * @param json Serialized JSON string representation of object
     * @param type Reference to expected class return type
     * @return A new instance of T populated with data from json
     * @param <T> Reference to expected class return type
     */
    static <T> T fromJSON(final String json, final Class<T> type) {
        final Gson gson = new Gson();
        return gson.fromJson(json, type);
    }
}
