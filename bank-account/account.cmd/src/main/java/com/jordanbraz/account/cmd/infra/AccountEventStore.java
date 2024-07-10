package com.jordanbraz.account.cmd.infra;

import com.jordanbraz.account.cmd.domain.AccountAggregate;
import com.jordanbraz.account.cmd.domain.EventStoreRepository;
import com.jordanbraz.cqrs.core.events.BaseEvent;
import com.jordanbraz.cqrs.core.events.EventModel;
import com.jordanbraz.cqrs.core.exceptions.AggregateNotFoundException;
import com.jordanbraz.cqrs.core.exceptions.ConcurrencyException;
import com.jordanbraz.cqrs.core.infra.EventStore;
import com.jordanbraz.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountEventStore implements EventStore {
    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private EventStoreRepository eventStoreRepository;

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1 && eventStream.get(eventStream.size() -1).getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }
        var version = expectedVersion;
        for (var event: events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timeStamp(new Date())
                    .aggregateType(AccountAggregate.class.getName())
                    .aggregateIdentifier(aggregateId)
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();
            var persistedEvent = eventStoreRepository.save(eventModel);
            if (!persistedEvent.getId().isEmpty()) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventstream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (eventstream == null || eventstream.isEmpty()) {
            throw new AggregateNotFoundException("Incorrect Account ID provided");
        }
        return eventstream.stream().map(x -> x.getEventData()).collect(Collectors.toList());
    }

    @Override
    public List<String> getAggregateIds() {
        var eventStream = eventStoreRepository.findAll();
        if (eventStream == null || eventStream.isEmpty()) {
            throw new IllegalStateException("Could Not retrieve event");
        }
        return eventStream.stream().map(EventModel::getAggregateIdentifier).distinct().collect(Collectors.toList());

    }
}
