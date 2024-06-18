package com.tonyleisurecentre.bookingsystem.exception;

import com.tonyleisurecentre.bookingsystem.response.ErrMsg;

import java.util.List;

public class SaveException extends BaseException {
    public SaveException(String errMsgStr) {
        ErrMsg errMsg = new ErrMsg();
        errMsg.setDescription(errMsgStr);
        errMsg.setCode("60100");
        super.setErrorMessages(List.of(errMsg));
    }
}
