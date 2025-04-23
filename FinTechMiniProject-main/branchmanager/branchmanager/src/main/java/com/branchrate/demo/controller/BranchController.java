package com.branchrate.demo.controller;

import com.branchrate.demo.Branch;
import com.branchrate.demo.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "http://127.0.0.1:5500") // Enable frontend access
@RestController
@RequestMapping("/branch")
public class BranchController {

    @Autowired
    private BranchService branchService;

    // ✅ Fetch all branches
    @GetMapping("/all")
    public ResponseEntity<List<Branch>> getAllBranches() {
        try {
            List<Branch> branches = branchService.getAllBranches();
            return ResponseEntity.ok(branches);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // ✅ Update rate of a branch (and its children + accounts)
    @PutMapping("/update-rate/{id}")
    public ResponseEntity<String> updateRate(@PathVariable int id, @RequestParam double rate) {
        try {
            branchService.updateRate(id, rate);
            return ResponseEntity.ok("Rate updated successfully for branch " + id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("Branch not found: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // ✅ Test API
    @GetMapping("/test")
    public String testApi() {
        return "API is working!";
    }
}
