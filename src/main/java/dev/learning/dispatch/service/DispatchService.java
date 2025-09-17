package dev.learning.dispatch.service;


import dev.learning.dispatch.message.DispatchPreparing;
import dev.learning.dispatch.message.OrderCreated;
import dev.learning.dispatch.message.OrderDispatched;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class DispatchService {
    private static final String DISPATCH_TRACKING_TOPIC = "dispatch.tracking";

    private static  final String ORDER_DISPATCHED_TOPIC = "order.dispatched";


    private final KafkaTemplate<String,Object> kafkaProducer;


    public void process(OrderCreated payload) throws ExecutionException, InterruptedException {
        DispatchPreparing dispatchPreparing = DispatchPreparing.builder().orderId(payload.getOrderId()).build();
        kafkaProducer.send(DISPATCH_TRACKING_TOPIC,dispatchPreparing).get();
        OrderDispatched orderDispatched = OrderDispatched.builder().orderId(payload.getOrderId()).build();
        kafkaProducer.send(ORDER_DISPATCHED_TOPIC, orderDispatched).get();
    }
}
