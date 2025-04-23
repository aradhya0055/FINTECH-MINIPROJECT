package com.branchrate.demo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Branch {

    @Id
    private int id;
    private String name;
    private Double rate;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonBackReference("branch-parent") // For recursion prevention
    private Branch parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @JsonManagedReference("branch-parent") // Match name with @JsonBackReference
    private List<Branch> children;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    @JsonBackReference("branch-accounts") // Prevent recursion here
    private List<Account> accounts;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getRate() { return rate; }
    public void setRate(Double rate) { this.rate = rate; }

    public Branch getParent() { return parent; }
    public void setParent(Branch parent) { this.parent = parent; }

    public List<Branch> getChildren() { return children; }
    public void setChildren(List<Branch> children) { this.children = children; }

    public List<Account> getAccounts() { return accounts; }
    public void setAccounts(List<Account> accounts) { this.accounts = accounts; }
}
