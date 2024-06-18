package com.tonyleisurecentre.bookingsystem.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GenericResponse<T> {

    public GenericResponse(T content) {
        this.content = content;
    }

    private Integer status = 200;
    private String errorMsg;
    private List<ErrMsg> errorMessages;

    private T content;
}
