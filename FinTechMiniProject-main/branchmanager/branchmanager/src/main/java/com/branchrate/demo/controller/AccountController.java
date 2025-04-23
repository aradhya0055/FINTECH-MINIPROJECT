package com.branchrate.demo.controller;

import com.branchrate.demo.Account;
import com.branchrate.demo.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        return ResponseEntity.ok(accountService.createAccount(account));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable int id) {
        Account account = accountService.getAccountById(id);
        if (account == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(account);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<Account>> getAccountsByBranch(@PathVariable int branchId) {
        return ResponseEntity.ok(accountService.getAccountsByBranchId(branchId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
}
