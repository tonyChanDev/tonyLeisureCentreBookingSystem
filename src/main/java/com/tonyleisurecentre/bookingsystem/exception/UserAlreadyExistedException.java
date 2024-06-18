package com.tonyleisurecentre.bookingsystem.exception;

import com.tonyleisurecentre.bookingsystem.response.ErrMsg;
import lombok.Data;

import java.util.List;

@Data
public class UserAlreadyExistedException extends BaseException {
    public UserAlreadyExistedException(String desc) {
        ErrMsg errMsg = new ErrMsg();
        errMsg.setDescription(desc);
        errMsg.setCode("60409");
        this.setErrorMessages(List.of(errMsg));
    }
}
