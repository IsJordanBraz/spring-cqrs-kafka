package com.jordanbraz.cqrs.core.infra;

import com.jordanbraz.cqrs.core.commands.BaseCommand;
import com.jordanbraz.cqrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
    void send(BaseCommand command);
}
