package com.marco.sharelist.controller;

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
    public ResponseEntity newItem(@PathVariable String listId, @RequestBody
    ShareItem item){
        try{
            itemService.saveItem(listId, item);
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Success");
    }

    @PutMapping(value = "/list/{listId}/item/{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateItem(@PathVariable String listId, @PathVariable String itemId, @RequestBody
    ShareItem item){
        try{
            itemService.updateItem(listId, itemId, item);
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Success");
    }

    @DeleteMapping(value = "/list/{listId}/item/{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteItem(@PathVariable String listId, @PathVariable String itemId){
        try{
            itemService.deleteItem(listId, itemId);
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Success");
    }

}
