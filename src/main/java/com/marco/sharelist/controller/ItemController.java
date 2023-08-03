package com.marco.sharelist.controller;

import com.marco.sharelist.dto.Response;
import com.marco.sharelist.entity.ShareItem;
import com.marco.sharelist.services.ItemService;
import com.marco.sharelist.services.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
public class ItemController {

    @Autowired
    ItemService itemService;

    @PostMapping(value = "/list/{listId}/item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response newItem(@PathVariable String listId, @RequestBody
    ShareItem item){
        try{
            itemService.saveItem(listId, item);
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

    @PutMapping(value = "/list/{listId}/item/{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updateItem(@PathVariable String listId, @PathVariable String itemId, @RequestBody
    ShareItem item){
        try{
            itemService.updateItem(listId, itemId, item);
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

    @DeleteMapping(value = "/list/{listId}/item/{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response deleteItem(@PathVariable String listId, @PathVariable String itemId){
        try{
            itemService.deleteItem(listId, itemId);
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
