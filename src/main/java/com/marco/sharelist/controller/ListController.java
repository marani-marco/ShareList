package com.marco.sharelist.controller;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.marco.sharelist.commons.CryptoUtils;
import com.marco.sharelist.entity.ShareList;
import com.marco.sharelist.services.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import java.io.*;

@RestController
public class ListController {

    @Autowired
    ListService listService;

    @GetMapping(value="/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ShareList getSharedList(@PathVariable("id") String id) throws Throwable {



        /*final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("sharelists");

        DatabaseReference currentListRef = ref.child("nop-qrs");

        ShareList list = new ShareList();
        list.setTitle("Seconda prova");

        currentListRef.setValueAsync(list);*/

        return new ShareList();
    }

    @PostMapping(value="/list", consumes = MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity newSharedList(@RequestBody ShareList shareList){

        try{
            listService.saveList(shareList);
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Success");
    }

    @PutMapping(value="/list/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateSharedListData(@PathVariable String id, @RequestBody ShareList shareList){

        try{
            listService.updateListData(id, shareList);
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Success");
    }

    @DeleteMapping(value="/list/{id}")
    public ResponseEntity deleteSharedList(@PathVariable String id){

        try{
            listService.deleteList(id);
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Success");
    }


}
