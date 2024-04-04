package de.dennisguse.opentracks.data.interfaces;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import de.dennisguse.opentracks.data.models.CRUDConstants;

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
    default JsonObject toJSON() {
        // Create a GsonBuilder and configure it to serialize special floating point values
        Gson gson = new GsonBuilder()
                .serializeSpecialFloatingPointValues()
                .create();

        // Convert the object to JsonObject using Gson
        String jsonString = gson.toJson(this);
        return gson.fromJson(jsonString, JsonObject.class);
    }

    /**
     * Loads an object from a JSON string.
     * @param json Serialized JSON string representation of object
     * @param type Reference to expected class return type
     * @return A new instance of T populated with data from json
     * @param <T> Reference to expected class return type
     */
    static <T> T fromJSON(final JsonObject json, final Class<T> type) {
        if (json == null) {
            // Handle the case where the JSON object is null
            Log.e(CRUDConstants.CRUD_OPERATION, "JSON object is null");
            return null;
        }

        final Gson gson = new Gson();
        T t = null;
        try {
            t = gson.fromJson(json, type);
            Log.d(CRUDConstants.CRUD_OPERATION, "Deserialization successful");
        } catch (JsonSyntaxException e) {
            // Handle JSON syntax exception
            Log.e(CRUDConstants.CRUD_OPERATION, "JSON syntax exception: " + e.getMessage());
        } catch (JsonParseException e) {
            // Handle JSON parse exception
            Log.e(CRUDConstants.CRUD_OPERATION, "JSON parse exception: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            Log.e(CRUDConstants.CRUD_OPERATION, "Error during deserialization: " + e.getMessage());
        }
        return t;
    }
}
