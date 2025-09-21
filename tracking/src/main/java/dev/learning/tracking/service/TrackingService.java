package dev.learning.tracking.service;

import dev.learning.dispatch.message.DispatchPreparing;
import dev.learning.dispatch.message.TrackingStatus;
import dev.learning.dispatch.message.TrackingStatusUpdated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrackingService {
    private static final String TRACKING_STATUS_TOPIC = "tracking.status";

    private final KafkaTemplate<String,Object> kafkaProducer;


    public void process(DispatchPreparing payload) throws ExecutionException, InterruptedException {
        TrackingStatusUpdated trackingStatusUpdated = TrackingStatusUpdated.builder().orderId(payload.getOrderId()).status(TrackingStatus.PREPARING).build();
        kafkaProducer.send(TRACKING_STATUS_TOPIC, trackingStatusUpdated).get();
    }
}
