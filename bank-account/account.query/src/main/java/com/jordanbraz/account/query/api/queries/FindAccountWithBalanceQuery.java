package com.jordanbraz.account.query.api.queries;

import com.jordanbraz.account.query.api.dto.EqualityType;
import com.jordanbraz.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FinidAccountWithBalanceQuery extends BaseQuery {
    private EqualityType equalityType;
    private double balance;
}
