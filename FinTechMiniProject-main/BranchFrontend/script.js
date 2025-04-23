async function fetchBranches() {
    try {
      const response = await fetch("http://localhost:8080/branch/all");
      const branches = await response.json();
  
      const branchMap = new Map();
      branches.forEach(branch => {
        branchMap.set(branch.id, { ...branch, children: [] });
      });
  
      let roots = [];
      branches.forEach(branch => {
        if (branch.parent && branchMap.has(branch.parent.id)) {
          branchMap.get(branch.parent.id).children.push(branchMap.get(branch.id));
        } else {
          roots.push(branchMap.get(branch.id));
        }
      });
  
      const container = document.getElementById("branchTree");
      container.innerHTML = "";
  
      roots.forEach(root => {
        container.appendChild(createTreeNode(root, true)); // head is open
      });
  
    } catch (err) {
      console.error("Branch Fetch Error:", err);
      document.getElementById("branchTree").innerHTML = "Failed to load branches.";
    }
  }
  
  function createTreeNode(branch, initiallyVisible = false) {
    const li = document.createElement("li");
  
    const toggle = document.createElement("span");
    toggle.className = "toggle";
    toggle.textContent = branch.children.length > 0 ? "▶ " : "• ";
    
    const label = document.createElement("span");
    label.innerHTML = `<strong>${branch.name}</strong> (ID: ${branch.id}) - Rate: ${branch.rate}`;
  
    const childContainer = document.createElement("ul");
    childContainer.className = "tree-view hidden";
  
    if (branch.children.length > 0) {
      toggle.addEventListener("click", () => {
        const expanded = toggle.textContent === "▼ ";
        toggle.textContent = expanded ? "▶ " : "▼ ";
        childContainer.classList.toggle("hidden");
      });
    }
  
    branch.children.forEach(child => {
      childContainer.appendChild(createTreeNode(child));
    });
  
    li.appendChild(toggle);
    li.appendChild(label);
    li.appendChild(childContainer);
  
    if (initiallyVisible) {
      toggle.textContent = "▼ ";
      childContainer.classList.remove("hidden");
    }
  
    return li;
  }
  
  async function fetchAccounts() {
    try {
      const response = await fetch("http://localhost:8080/account/all");
      const data = await response.json();
  
      const accountList = document.getElementById("accountList");
      accountList.innerHTML = "<h3>All Accounts</h3>";
  
      const grouped = {};
      data.forEach(acc => {
        const city = acc.branch.name;
        if (!grouped[city]) grouped[city] = [];
        grouped[city].push(acc);
      });
  
      for (const [city, accounts] of Object.entries(grouped)) {
        const cityBlock = document.createElement("div");
        cityBlock.innerHTML = `<h4>${city}</h4>`;
        accounts.forEach(acc => {
          const p = document.createElement("p");
          p.textContent = `${acc.name} (ID: ${acc.id}) - Rate: ${acc.rate}`;
          cityBlock.appendChild(p);
        });
        accountList.appendChild(cityBlock);
      }
    } catch (err) {
      console.error("Account Fetch Error:", err);
      document.getElementById("accountList").innerHTML = "Failed to load accounts.";
    }
  }
  
  async function updateRate(branchId, newRate) {
    try {
      const response = await fetch(`http://localhost:8080/branch/update-rate/${branchId}?rate=${newRate}`, {
        method: "PUT",
      });
  
      if (!response.ok) throw new Error("Rate update failed");
  
      alert("Rate updated successfully!");
      fetchBranches();
      fetchAccounts();
    } catch (err) {
      alert("Error: " + err.message);
    }
  }
  
  window.addEventListener("DOMContentLoaded", () => {
    fetchBranches();
    fetchAccounts();
  
    document.getElementById("updateForm").addEventListener("submit", (e) => {
      e.preventDefault();
      const branchId = document.getElementById("branchId").value;
      const newRate = document.getElementById("newRate").value;
      if (!branchId || !newRate) {
        alert("Fill all fields.");
        return;
      }
      updateRate(branchId, newRate);
    });
  
    document.querySelector("#accounts button").addEventListener("click", async () => {
      const branchId = document.getElementById("accountBranchId").value;
      if (!branchId) {
        alert("Enter a Branch ID");
        return;
      }
  
      try {
        const response = await fetch("http://localhost:8080/account/all");
        const data = await response.json();
        const filtered = data.filter(acc => acc.branch.id == branchId);
  
        const accountList = document.getElementById("accountList");
        accountList.innerHTML = `<h3>Accounts in Branch ${branchId}</h3>`;
        if (filtered.length === 0) {
          accountList.innerHTML += "<p>No accounts found.</p>";
        } else {
          filtered.forEach(acc => {
            accountList.innerHTML += `<p>${acc.name} (ID: ${acc.id}) - Rate: ${acc.rate}</p>`;
          });
        }
      } catch (err) {
        console.error("Error filtering accounts:", err);
        alert("Failed to fetch filtered accounts.");
      }
    });
  });
  