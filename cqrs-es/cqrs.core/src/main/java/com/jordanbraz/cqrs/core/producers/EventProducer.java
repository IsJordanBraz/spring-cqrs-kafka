package com.jordanbraz.cqrs.core.producers;

import com.jordanbraz.cqrs.core.events.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
