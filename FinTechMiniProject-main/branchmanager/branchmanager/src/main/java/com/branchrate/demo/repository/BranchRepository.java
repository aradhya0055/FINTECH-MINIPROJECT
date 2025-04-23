package com.branchrate.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.branchrate.demo.Branch;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
    List<Branch> findByParent_Id(int parentId);
}
