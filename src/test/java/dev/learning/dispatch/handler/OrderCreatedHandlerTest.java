package dev.learning.dispatch.handler;

import dev.learning.dispatch.message.OrderCreated;
import dev.learning.dispatch.service.DispatchService;
import dev.learning.dispatch.util.TestEventData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class OrderCreatedHandlerTest {

    private OrderCreatedHandler orderCreatedHandler;
    private DispatchService dispatchServiceMock;
    @BeforeEach
    void setUp() {
        dispatchServiceMock = Mockito.mock(DispatchService.class);
        orderCreatedHandler = new OrderCreatedHandler(dispatchServiceMock);
    }

    @Test
    void listen() throws ExecutionException, InterruptedException {
        OrderCreated orderCreated= TestEventData.buildOrderCreatedEvent(UUID.randomUUID(),UUID.randomUUID().toString());
        orderCreatedHandler.listen(orderCreated);
        Mockito.verify(dispatchServiceMock,Mockito.times(1)).process(orderCreated);
    }
}