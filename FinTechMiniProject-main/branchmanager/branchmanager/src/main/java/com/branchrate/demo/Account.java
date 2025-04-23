package com.branchrate.demo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
public class Account {

    @Id
    private int id;

    private String name;
    private Double rate;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    @JsonManagedReference("branch-accounts") // Include branch info in account
    private Branch branch;

    public Double getRate() { return rate; }
    public void setRate(Double rate) { this.rate = rate; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Branch getBranch() { return branch; }
    public void setBranch(Branch branch) { this.branch = branch; }
}
