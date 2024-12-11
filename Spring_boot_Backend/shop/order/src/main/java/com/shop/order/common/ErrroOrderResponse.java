package com.shop.order.common;

import lombok.Getter;

@Getter
public class ErrroOrderResponse implements OrderResponse{

    private final String errorMessage;

    public ErrroOrderResponse(String errorMessage){
        this.errorMessage=errorMessage;
    }

}
