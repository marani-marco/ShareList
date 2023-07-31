package com.marco.sharelist.commons;

import com.marco.sharelist.services.ListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(ListService.class);

    public static String generateListName(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String listName = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        logger.debug("new listName " + listName);

        return listName;

    }

}
