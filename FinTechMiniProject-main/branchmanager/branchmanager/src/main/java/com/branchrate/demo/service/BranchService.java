package com.branchrate.demo.service;

import com.branchrate.demo.Account;
import com.branchrate.demo.Branch;
import com.branchrate.demo.repository.AccountRepository;
import com.branchrate.demo.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepo;

    @Autowired
    private AccountRepository accountRepo;

    // ✅ Update rate for a branch, its sub-branches and accounts
    public void updateRate(int branchId, double newRate) {
        Branch branch = branchRepo.findById(branchId)
                .orElseThrow(() -> new NoSuchElementException("Branch with ID " + branchId + " not found"));
        updateRateRecursive(branch, newRate);
    }

    private void updateRateRecursive(Branch branch, double rate) {
        branch.setRate(rate);
        branchRepo.save(branch);

        // ✅ Update rate for all accounts under this branch
        List<Account> accounts = accountRepo.findByBranch_Id(branch.getId());
        for (Account acc : accounts) {
            acc.setRate(rate);
        }
        accountRepo.saveAll(accounts);

        // ✅ Recursively update rate for child branches
        List<Branch> children = branchRepo.findByParent_Id(branch.getId());
        for (Branch child : children) {
            updateRateRecursive(child, rate);
        }
    }

    // ✅ Return all branches for display
    public List<Branch> getAllBranches() {
        return branchRepo.findAll();
    }
}
