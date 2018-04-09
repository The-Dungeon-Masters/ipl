package com.dungeon.master.ipl.util;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus()
public class PointsException extends RuntimeException{

    public PointsException(String exception) {
        super(exception);
    }
    
}
