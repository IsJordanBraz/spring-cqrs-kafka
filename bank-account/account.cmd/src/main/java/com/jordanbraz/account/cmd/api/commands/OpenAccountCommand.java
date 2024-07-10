package com.jordanbraz.account.cmd.api.commands;

import com.jordanbraz.account.common.dto.AccountType;
import com.jordanbraz.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType accountType;
        private double openingBalance;
}
