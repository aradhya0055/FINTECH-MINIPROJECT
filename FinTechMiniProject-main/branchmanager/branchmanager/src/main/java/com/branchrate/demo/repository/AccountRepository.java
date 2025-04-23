package com.branchrate.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.branchrate.demo.Account;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findByBranch_Id(int branchId);
}
