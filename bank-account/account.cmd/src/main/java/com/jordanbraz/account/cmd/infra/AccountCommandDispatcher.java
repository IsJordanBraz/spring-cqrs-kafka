package com.jordanbraz.account.cmd.infra;

import com.jordanbraz.cqrs.core.commands.BaseCommand;
import com.jordanbraz.cqrs.core.commands.CommandHandlerMethod;
import com.jordanbraz.cqrs.core.infra.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {
    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routers = new HashMap<>();
    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        var handlers = routers.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public void send(BaseCommand command) {
        var handlers = routers.get(command.getClass());
        if (handlers == null || handlers.size() == 0) {
            throw new RuntimeException("No command handler was registered");
        }
        if (handlers.size() > 1) {
            throw new RuntimeException("Cannot send command to more than one Handler!");
        }
        handlers.get(0).handle(command);
    }
}
