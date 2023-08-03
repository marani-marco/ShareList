package com.marco.sharelist.controller;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.marco.sharelist.commons.CryptoUtils;
import com.marco.sharelist.dto.Response;
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
    public Response getSharedList(@PathVariable String id) throws Throwable {

        ShareList shareList;

        try{
            shareList = listService.getList(id);
        } catch (Exception e){
            Response response = new Response();
            response.setResult("KO");
            response.setErrorDescription(e.getMessage());

            return response;
        }

        Response response = new Response();
        response.setResult("OK");
        response.setShareList(shareList);

        return response;
    }

    @PostMapping(value="/list", consumes = MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
    public Response newSharedList(@RequestBody ShareList shareList){

        try{
            listService.saveList(shareList);
        } catch (Exception e){
            Response response = new Response();
            response.setResult("KO");
            response.setErrorDescription(e.getMessage());

            return response;
        }

        Response response = new Response();
        response.setResult("OK");

        return response;
    }

    @PutMapping(value="/list/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
    public Response updateSharedListData(@PathVariable String id, @RequestBody ShareList shareList){

        try{
            listService.updateListData(id, shareList);
        } catch (Exception e){
            Response response = new Response();
            response.setResult("KO");
            response.setErrorDescription(e.getMessage());

            return response;
        }

        Response response = new Response();
        response.setResult("OK");

        return response;
    }

    @DeleteMapping(value="/list/{id}")
    public Response deleteSharedList(@PathVariable String id){

        try{
            listService.deleteList(id);
        } catch (Exception e){
            Response response = new Response();
            response.setResult("KO");
            response.setErrorDescription(e.getMessage());

            return response;
        }

        Response response = new Response();
        response.setResult("OK");

        return response;
    }


}
