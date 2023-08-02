package com.marco.sharelist.services;

import com.google.api.core.SettableApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.marco.sharelist.commons.CryptoUtils;
import com.marco.sharelist.commons.Utils;
import com.marco.sharelist.entity.ShareItem;
import com.marco.sharelist.entity.ShareList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class ListService {

    private static final Logger logger = LoggerFactory.getLogger(ListService.class);
    private FirebaseDatabase database;

    public void saveList(ShareList shareList){

        database = FirebaseDatabase.getInstance();

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

    public void updateListData(String id, ShareList shareList) throws Exception {
        logger.info("Enter updateListData method with id " + id);

        database = FirebaseDatabase.getInstance();

        DatabaseReference mainRef = database.getReference("sharelists");

        final DatabaseReference currentListRef = mainRef.child(id);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.getValue() != null) {
                    Map<String, Object> shareListUpdate = new HashMap<>();
                    shareListUpdate.put("title", shareList.getTitle());
                    shareListUpdate.put("description", shareList.getDescription());
                    shareListUpdate.put("type", shareList.getType());

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

        database = FirebaseDatabase.getInstance();

        DatabaseReference mainRef = database.getReference("sharelists");

        mainRef.child(id).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

            }
        });

        logger.info("Exit deleteList method with id " + id);

    }

    public ShareList getList(String id) {

        logger.info("Enter getList method");

        database = FirebaseDatabase.getInstance();

        final SettableApiFuture<DataSnapshot> future = SettableApiFuture.create();

        DatabaseReference mainRef = database.getReference("sharelists");
        mainRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                future.set(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.setException(databaseError.toException());
            }
        });
        try {

            DataSnapshot snapshot = future.get();

            ShareList shareList = new ShareList();

            snapshot.getChildren().forEach(l -> {
                switch (l.getKey()){

                    case "title":
                        shareList.setTitle((String)l.getValue());
                        break;

                    case "description":
                        shareList.setDescription((String)l.getValue());
                        break;

                    case "type":
                        shareList.setType((String)l.getValue());
                        break;

                    case "userId":
                        shareList.setUserId((String)l.getValue());
                        break;
                }
            });

            List<ShareItem> items = new ArrayList<>();

            snapshot.child("items").getChildren().forEach(i -> {

                ShareItem item = i.getValue(ShareItem.class);
                item.setItemId(i.getKey());
                items.add(item);
            });

            shareList.setItems(items);

            return shareList;
        } catch(InterruptedException | ExecutionException e) {
            logger.error(e.toString());
            return null;
        }

        /*DatabaseReference mainRef = database.getReference("sharelists");

        final DatabaseReference currentListRef = mainRef.child(id);

        ShareList shareList = new ShareList();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                shareList = snapshot.getValue(ShareList.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        currentListRef.addValueEventListener(valueEventListener);

        logger.info("Exit getList method");

        return shareList;*/



    }

}
