package com.jordanbraz.account.cmd.api.commands;

import com.jordanbraz.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class DepositFundsCommand extends BaseCommand {
    private double amount;
}
