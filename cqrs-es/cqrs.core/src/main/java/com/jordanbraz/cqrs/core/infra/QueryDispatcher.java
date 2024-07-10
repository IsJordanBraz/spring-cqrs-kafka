package com.jordanbraz.cqrs.core.infra;

import com.jordanbraz.cqrs.core.domain.BaseEntity;
import com.jordanbraz.cqrs.core.queries.BaseQuery;
import com.jordanbraz.cqrs.core.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
    <U extends BaseEntity> List<U> send(BaseQuery query);
}
