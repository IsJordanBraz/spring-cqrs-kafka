package com.jordanbraz.account.query.api.queries;

import com.jordanbraz.account.query.api.dto.EqualityType;
import com.jordanbraz.account.query.domain.AccountRepository;
import com.jordanbraz.account.query.domain.BankAccount;
import com.jordanbraz.cqrs.core.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountQueryHandler implements QueryHandler {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<BaseEntity> handle(FindAllAccountsQuery query) {
        Iterable<BankAccount> bankAccounts = accountRepository.findAll();
        List<BaseEntity> baseEntityList = new ArrayList<>();
        bankAccounts.forEach(baseEntityList::add);
        return baseEntityList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByIdQuery query) {
        var bankAccount = accountRepository.findById(query.getId());
        if (bankAccount.isEmpty()) {
            return null;
        }
        List<BaseEntity> baseEntityList = new ArrayList<>();
        baseEntityList.add(bankAccount.get());
        return baseEntityList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByHolderQuery query) {
        var bankAccount = accountRepository.findByAccountHolder(query.getAccountHolder());
        if (bankAccount.isEmpty()) {
            return null;
        }
        List<BaseEntity> baseEntityList = new ArrayList<>();
        baseEntityList.add(bankAccount.get());
        return baseEntityList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountWithBalanceQuery query) {
        return query.getEqualityType() == EqualityType.GREATER_THAN
                ? accountRepository.findByBalanceGreaterThan(query.getBalance())
                : accountRepository.findByBalanceLessThan(query.getBalance());
    }
}
