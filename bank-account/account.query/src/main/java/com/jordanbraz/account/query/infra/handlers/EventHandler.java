package com.jordanbraz.account.query.infra.handlers;

import com.jordanbraz.account.common.events.AccountClosedEvent;
import com.jordanbraz.account.common.events.AccountOpenedEvent;
import com.jordanbraz.account.common.events.FundsDepositedEvent;
import com.jordanbraz.account.common.events.FundsWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent event);
}
