package com.jordanbraz.account.cmd.api.controllers;

import com.jordanbraz.account.cmd.api.commands.CloseAccountCommand;
import com.jordanbraz.account.cmd.api.commands.DepositFundsCommand;
import com.jordanbraz.account.common.dto.BaseResponse;
import com.jordanbraz.cqrs.core.exceptions.AggregateNotFoundException;
import com.jordanbraz.cqrs.core.infra.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/depositFunds")
public class DepositFundsController {
    private final Logger logger = Logger.getLogger(DepositFundsController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> depositFunds(
            @PathVariable(value = "id") String id,
            @RequestBody DepositFundsCommand command) {
        try {
            commandDispatcher.send(new CloseAccountCommand(id));
            return new ResponseEntity<>(new BaseResponse("Closed Completed"), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request - ${0}", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format("Error while processing request id - ${0}", id);
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
