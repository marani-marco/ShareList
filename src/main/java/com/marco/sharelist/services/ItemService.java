package com.marco.sharelist.services;

import com.google.api.core.SettableApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.marco.sharelist.commons.CryptoUtils;
import com.marco.sharelist.commons.Utils;
import com.marco.sharelist.entity.ShareItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);
    private FirebaseDatabase database;

    public void saveItem(String listId, ShareItem item) {

        logger.info("Enter saveItem method");

        database = FirebaseDatabase.getInstance();

        final SettableApiFuture<DataSnapshot> future = SettableApiFuture.create();

        DatabaseReference mainRef = database.getReference("sharelists/" + listId + "/items").child(Utils.generateListName());
        mainRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null)
                    saveItem(listId, item);
                else {
                    logger.info("Saving new item");
                    mainRef.setValueAsync(item);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.setException(databaseError.toException());
            }
        });

        logger.info("Exit saveItem method");
    }

    public void updateItem(String listId, String itemId, ShareItem item) {

        logger.info("Enter saveItem method");

        database = FirebaseDatabase.getInstance();

        final SettableApiFuture<DataSnapshot> future = SettableApiFuture.create();

        DatabaseReference mainRef = database.getReference("sharelists/" + listId + "/items").child(itemId);
        mainRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    Map<String, Object> shareItemUpdate = new HashMap<>();
                    shareItemUpdate.put("title", item.getTitle());
                    shareItemUpdate.put("description", item.getDescription());
                    shareItemUpdate.put("userAssigned", item.getUserAssigned());
                    shareItemUpdate.put("completed", item.getCompleted());
                    shareItemUpdate.put("confirmComplete", item.getConfirmComplete());

                    mainRef.updateChildrenAsync(shareItemUpdate);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.setException(databaseError.toException());
            }
        });

        logger.info("Exit saveItem method");

        /*logger.info("Enter updateItem method");

        DatabaseReference mainRef = database.getReference("sharelists/" + listId);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {

                    DatabaseReference itemRef = mainRef.child("lists/" + itemId);

                    Map<String, Object> shareItemUpdate = new HashMap<>();
                    shareItemUpdate.put("title", item.getTitle());
                    shareItemUpdate.put("description", item.getDescription());
                    shareItemUpdate.put("userAssigned", item.getUserAssigned());
                    shareItemUpdate.put("completed", item.getCompleted());
                    shareItemUpdate.put("confirmComplete", item.getConfirmComplete());

                    itemRef.updateChildrenAsync(shareItemUpdate);

                    mainRef.removeEventListener(this);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mainRef.addValueEventListener(valueEventListener);

        logger.info("Exit updateItem method");*/
    }

    public void deleteItem(String listId, String itemId) {

        logger.info("Enter deleteItem method");

        DatabaseReference mainRef = database.getReference("sharelists/" + listId + "/lists");

        mainRef.child(itemId).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

            }
        });

        logger.info("Exit deleteItem method");

    }

    @EventListener(ApplicationReadyEvent.class)
    public void firebaseInit() throws Throwable {

        logger.info("Inizializzazione firebase");
        CryptoUtils.decrypt(
                getClass().getResourceAsStream("/firebase.json"),
                new FileOutputStream(getClass().getResource("/firebase_decrypt.json").getFile()));

        InputStream serviceAccount =
               getClass().getResourceAsStream("/firebase_decrypt.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://sharelist-2fe3c-default-rtdb.europe-west1.firebasedatabase.app")
                .build();

        FirebaseApp.initializeApp(options);

        database = FirebaseDatabase.getInstance();

    }


}
