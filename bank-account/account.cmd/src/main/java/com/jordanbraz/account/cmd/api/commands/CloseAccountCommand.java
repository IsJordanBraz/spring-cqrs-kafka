package com.jordanbraz.account.cmd.api.commands;

import com.jordanbraz.cqrs.core.commands.BaseCommand;

public class CloseAccountCommand extends BaseCommand {
    public CloseAccountCommand(String id) {
        super(id);
    }
}
