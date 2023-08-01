package com.marco.sharelist.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.marco.sharelist.commons.CryptoUtils;
import com.marco.sharelist.commons.Utils;
import com.marco.sharelist.entity.ShareList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ListService {

    private static final Logger logger = LoggerFactory.getLogger(ListService.class);
    private FirebaseDatabase database;

    public void saveList(ShareList shareList){

        logger.info("Enter saveList method");

        DatabaseReference mainRef = database.getReference("sharelists");

        final DatabaseReference currentListRef = mainRef.child(Utils.generateListName());

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    saveList(shareList);
                } else {
                    logger.info("Saving new list...");
                    currentListRef.setValueAsync(shareList);
                    currentListRef.removeEventListener(this); // to avoid listener loop
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        currentListRef.addValueEventListener(valueEventListener);

        logger.info("Exit saveList method");

    }


    @EventListener(ApplicationReadyEvent.class)
    public void firebaseInit() throws Throwable {

        logger.info("Inizializzazione firebase");



        FileInputStream serviceAccount =
                new FileInputStream("src/main/resources/static/sharelist-2fe3c-firebase-adminsdk-zmm61-9bda6d75d3.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://sharelist-2fe3c-default-rtdb.europe-west1.firebasedatabase.app")
                .build();

        FirebaseApp.initializeApp(options);

        database = FirebaseDatabase.getInstance();
    }


    public void updateListData(String id, ShareList shareList) throws Exception {
        logger.info("Enter updateListData method with id " + id);

        DatabaseReference mainRef = database.getReference("sharelists");

        final DatabaseReference currentListRef = mainRef.child(id);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.getValue() != null) {
                    Map<String, Object> shareListUpdate = new HashMap<>();
                    shareListUpdate.put("title", shareList.getTitle());
                    shareListUpdate.put("description", shareList.getDescription());

                    currentListRef.updateChildrenAsync(shareListUpdate);
                    currentListRef.removeEventListener(this);
                } else {
                    currentListRef.removeEventListener(this); // to avoid listener loop
                    throw new RuntimeException("List not exists");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        currentListRef.addValueEventListener(valueEventListener);

        logger.info("Exit updateListData method with id " + id);
    }

    public void deleteList(String id) {

        logger.info("Enter deleteList method with id " + id);

        DatabaseReference mainRef = database.getReference("sharelists");

        mainRef.child(id).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

            }
        });

        logger.info("Exit deleteList method with id " + id);

    }
}
