package com.stackroute.gipherrecommendersystem.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface RecomendationMessageProcessor {
    String INCREMENT_INPUT = "GipherRecomendationsStreamIncrement";//This is @Input Annotated method Name
    String DECREMENT_INPUT = "GipherRecomendationsStreamDecrement";//This is @Input Annotated method Name
    @Input("GipherRecomendationsStreamIncrement") //Note this name is Same as Name in Output Channel in Gipher Manager 
    SubscribableChannel gipherRecomendationsIncrementInput();
    @Input("GipherRecomendationsStreamDecrement") //Note this name is Same as Name in Output Channel in Gipher Manager 
    SubscribableChannel gipherRecomendationsDecrementInput();
    //No Output
}