package dev.learning.dispatch.service;

import dev.learning.dispatch.message.OrderCreated;
import dev.learning.dispatch.message.OrderDispatched;
import dev.learning.dispatch.util.TestEventData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class DispatchServiceTest {

    private KafkaTemplate kafkaProducerMock;
    private DispatchService dispatchService;
    @BeforeEach
    void setUp() {
        kafkaProducerMock= Mockito.mock(KafkaTemplate.class);
        dispatchService = new DispatchService(kafkaProducerMock);
    }
    @Test
    void process() throws ExecutionException, InterruptedException {
        Mockito.when(kafkaProducerMock.send(ArgumentMatchers.anyString(), ArgumentMatchers.any(OrderDispatched.class))).thenReturn(Mockito.mock(CompletableFuture.class));
        OrderCreated orderCreated = TestEventData.buildOrderCreatedEvent(UUID.randomUUID(),UUID.randomUUID().toString());
        dispatchService.process(orderCreated);
    }
}