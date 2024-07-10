package com.jordanbraz.account.query.infra;

import com.jordanbraz.cqrs.core.domain.BaseEntity;
import com.jordanbraz.cqrs.core.infra.QueryDispatcher;
import com.jordanbraz.cqrs.core.queries.BaseQuery;
import com.jordanbraz.cqrs.core.queries.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {
    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, c -> new ArrayList<>());
        handlers.add(handler);
    }

    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) {
        var handlers = routes.get(query.getClass());
        if (handlers == null || handlers.size() <= 0) {
            throw new RuntimeException("No query handler");
        }
        if (handlers.size() > 1) {
            throw new RuntimeException("Cannot send query");
        }
        return handlers.get(0).handle(query);
    }
}
