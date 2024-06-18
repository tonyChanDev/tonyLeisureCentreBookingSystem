package com.tonyleisurecentre.bookingsystem.exception;

import com.tonyleisurecentre.bookingsystem.response.ErrMsg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseException extends Exception{
    private List<ErrMsg> errorMessages;
}
