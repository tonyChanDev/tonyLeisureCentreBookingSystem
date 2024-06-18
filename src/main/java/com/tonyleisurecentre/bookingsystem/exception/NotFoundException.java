package com.tonyleisurecentre.bookingsystem.exception;

import com.tonyleisurecentre.bookingsystem.response.ErrMsg;
import lombok.Data;

import java.util.List;

@Data
public class NotFoundException extends BaseException {
    public NotFoundException(String errMsgStr) {
        ErrMsg errMsg = new ErrMsg();
        errMsg.setDescription(errMsgStr);
        errMsg.setCode("60400");
        super.setErrorMessages(List.of(errMsg));
    }
}
