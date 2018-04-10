package com.dungeon.master.ipl.util;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus()
public class ContestsException extends RuntimeException{

    public ContestsException(String exception) {
        super(exception);
    }
    
}
