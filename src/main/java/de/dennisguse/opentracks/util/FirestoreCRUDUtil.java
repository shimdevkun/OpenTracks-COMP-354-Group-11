package de.dennisguse.opentracks.util;

import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Map;

import de.dennisguse.opentracks.data.models.CRUDConstants;

/**
 * Util class the performs CRUD operations on a given collection and handles
 * onSuccess and onError callbacks.
 */
public class FirestoreCRUDUtil {
    private FirebaseFirestore db;
    private static FirestoreCRUDUtil instance;

    private FirestoreCRUDUtil() {
        db = FirebaseFirestore.getInstance(); // TODO: Singleton pattern will be implemented later, use it
    }

    public static FirestoreCRUDUtil getInstance() {
        if (instance == null) {
            instance = new FirestoreCRUDUtil();
        }
        return instance;
    }

    /**
     * Creates a new entry in db
     * @param collection The collection in which to create the entry ("users" or "runs")
     * @param data The data to be added as the new entry
     */
    public void createEntry(final String collection, final Map<String, Object> data) {
        String id = data.get("id").toString();
        db.collection(collection)
                .document(id).set(data)
                .addOnSuccessListener(documentReference-> {
                    Log.d(CRUDConstants.TAG_CREATED, CRUDConstants.SUCCESS_CREATING_DOCUMENT + id);
                })
                .addOnFailureListener(e-> {
                    Log.e(CRUDConstants.TAG_ERROR, CRUDConstants.ERROR_CREATING_DOCUMENT + e.getMessage());
                });
    }

    /**
     * Updates an existing entry in db
     * @param collection The collection containing the entry to be updated
     * @param data The new data to be updated in the entry
     * TODO: discuss with the team, as I currently expect uniqueKey to be part of the data itself
     * TODO: make each table to have a  unique ID
     */
    public void updateEntry(final String collection, final Map<String, Object> data) {
        String id = data.get("id").toString();
        db.collection(collection).document(id)
                .update(data)
                .addOnSuccessListener(documentReference-> {
                    Log.d(CRUDConstants.TAG_UPDATED, CRUDConstants.SUCCESS_UPDATING_DOCUMENT + id);
                })
                .addOnFailureListener(e-> {
                    Log.e(CRUDConstants.TAG_ERROR, CRUDConstants.ERROR_UPDATING_DOCUMENT + e.getMessage());
                });
    }

    /**
     * Deletes an existing entry in db
     * @param collection The collection containing the entry to be updated
     * @param data The new data to be updated in the entry
     * TODO: discuss with the team, as I currently expect uniqueKey to be part of the data itself
     * TODO: make each table to have a  unique ID
     */
    public void deleteEntry(final String collection, final Map<String, Object> data) {
        String id = data.get("id").toString();
        db.collection(collection).document(id)
                .delete()
                .addOnSuccessListener(documentReference -> {
                    Log.d(CRUDConstants.TAG_DELETED, CRUDConstants.SUCCESS_DELETING_DOCUMENT + id);
                })
                .addOnFailureListener(e -> {
                    Log.e(CRUDConstants.TAG_ERROR, CRUDConstants.ERROR_DELETING_DOCUMENT + e.getMessage());
                });
    }

    /**
     * Gets an existing entry in db
     * @param collection The collection containing the entry to be updated
     * @param data The new data to be updated in the entry
     * TODO: discuss with the team, as I currently expect uniqueKey to be part of the data itself
     * TODO: make each table to have a  unique ID
     */
    public void getEntry(final String collection, final Map<String, Object> data) {
        String id = data.get("id").toString();
        db.collection(collection).document(id)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    Log.d(CRUDConstants.TAG_GET, CRUDConstants.SUCCESS_RETRIEVING_ENTRY + id);
                })
                .addOnFailureListener(e -> {
                    Log.e(CRUDConstants.TAG_ERROR, CRUDConstants.ERROR_RETRIEVING_ENTRY + e.getMessage());
                });
    }

    /**
     *
     * @param data The user data to be added as a new entry
     */
    public void createUser(Map<String, Object> data) {
        db.collection(CRUDConstants.USERS_TABLE)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Use the document ID from the DocumentReference in the log message
                        Log.d(CRUDConstants.TAG_CREATED, CRUDConstants.SUCCESS_CREATING_DOCUMENT + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.e(CRUDConstants.TAG_ERROR, CRUDConstants.ERROR_CREATING_DOCUMENT + e.getMessage());
                    }
                });
    }


    /**
     * Retrieves a user entry from the database based on the user ID
     * @param userId The ID of the user to retrieve
     */
    public void getUser(String userId) {
        db.collection(CRUDConstants.USERS_TABLE).document(userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            // Use the userId in the success log
                            Log.d(CRUDConstants.TAG_GET, CRUDConstants.SUCCESS_RETRIEVING_ENTRY + userId);
                            // Additional handling of the user data as needed
                        } else {
                            Log.d(CRUDConstants.TAG_GET, "No such user exists: " + userId);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.e(CRUDConstants.TAG_ERROR, CRUDConstants.ERROR_RETRIEVING_ENTRY + e.getMessage());
                    }
                });
    }

    /**
     * Updates a user's data in the database.
     * @param userId The ID of the user to update.
     * @param data The new data for the user.
     */
    public void updateUser(String userId, Map<String, Object> data) {
        db.collection(CRUDConstants.USERS_TABLE).document(userId)
                .update(data)
                .addOnSuccessListener(aVoid -> Log.d(CRUDConstants.TAG_UPDATED, CRUDConstants.SUCCESS_UPDATING_DOCUMENT + userId))
                .addOnFailureListener(e -> Log.e(CRUDConstants.TAG_ERROR, CRUDConstants.ERROR_UPDATING_DOCUMENT + e.getMessage()));
    }

    /**
     * Deletes a user from the database.
     * @param userId The ID of the user to delete.
     */
    //public void deleteUser(String userId) {
    public void deleteUser(String userId) {

            db.collection(CRUDConstants.USERS_TABLE).document(userId)
                .delete()
                .addOnSuccessListener(aVoid -> Log.d(CRUDConstants.TAG_DELETED, CRUDConstants.SUCCESS_DELETING_DOCUMENT + userId))
                .addOnFailureListener(e -> Log.e(CRUDConstants.TAG_ERROR, CRUDConstants.ERROR_DELETING_DOCUMENT + e.getMessage()));
    }



}

