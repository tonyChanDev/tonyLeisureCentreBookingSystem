package com.tonyleisurecentre.bookingsystem.exception;

import com.tonyleisurecentre.bookingsystem.response.ErrMsg;

import java.util.List;

public class SectionAlreadyExistedException extends BaseException{
    public SectionAlreadyExistedException() {
        ErrMsg errMsg = new ErrMsg();
        errMsg.setDescription("The sections for the date is already existed");
        errMsg.setCode("60409");
        this.setErrorMessages(List.of(errMsg));
    }
}
