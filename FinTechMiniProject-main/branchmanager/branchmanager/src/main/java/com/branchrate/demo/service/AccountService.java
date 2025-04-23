package com.branchrate.demo.service;

import com.branchrate.demo.Account;
import com.branchrate.demo.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepo;

    public Account createAccount(Account account) {
        return accountRepo.save(account);
    }

    public Account getAccountById(int id) {
        return accountRepo.findById(id).orElse(null);
    }

    public List<Account> getAccountsByBranchId(int branchId) {
        return accountRepo.findByBranch_Id(branchId);
    }

    public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }
}
