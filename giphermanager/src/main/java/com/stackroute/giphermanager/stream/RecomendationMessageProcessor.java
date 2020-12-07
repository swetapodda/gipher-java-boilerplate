package com.stackroute.giphermanager.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface RecomendationMessageProcessor {
    String OUTPUT_INCREMENT = "GipherRecomendationsStream";//This is @Input Annotated method Name
    String OUTPUT_DECREMENT = "GipherRecomendationsStream";//This is @Input Annotated method Name
    
    @Output("GipherRecomendationsStreamIncrement") //Note this name is will be used in Gipher Recomender Ssytem
    MessageChannel  gipherRecomendationsIncrementOutput();
    
    @Output("GipherRecomendationsStreamDecrement") //Note this name is will be used in Gipher Recomender Ssytem
    MessageChannel  gipherRecomendationsDecrementOutput();
   
    //No Input
}