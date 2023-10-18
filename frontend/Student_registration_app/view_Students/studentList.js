const fetchApiData = async () => {
  let response = await fetch("http://localhost:8888/api/student");

  if (response.ok) {
    let data = await response.json();

    console.log(data);

    return Promise.resolve(data);
  } else {
    console.log(response);

    return Promise.reject({
      message: `Error ${response.status}`,
    });
  }
};

const deleteItem = async (id) => {
  let response = await fetch(`http://localhost:8888/api/student/delete/${id}`, {
    method: "DELETE",
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  });

  if (response.status === 204) {
    alert("Student deleted");
    window.location.reload();
    return Promise.resolve();
  } else {
    return Promise.reject({
      message: `Error ${response.status}`,
    });
  }
};

window.addEventListener("load", () => {
  fetchApiData()
    .then((data) => {
      populateTable(data);
    })
    .catch((error) => {
      console.error(error);
    });
});

function populateTable(data) {
  const tableBody = document.getElementById("table-body");

  data.forEach((item, index) => {
    const row = document.createElement("tr");

    const cell1 = document.createElement("td");
    cell1.textContent = item.id;

    const cell2 = document.createElement("td");
    cell2.textContent = item.firstName;

    const cell3 = document.createElement("td");
    cell3.textContent = item.lastName;

    const cell4 = document.createElement("td");
    cell4.textContent = item.age;

    const cell5 = document.createElement("td");
    cell5.textContent = item.departmentName;

    const cellActions = document.createElement("td");

    const deleteButton = document.createElement("button");
    deleteButton.textContent = "Delete";
    deleteButton.classList.add("btn", "btn-danger", "m1");
    deleteButton.addEventListener("click", () => {
      deleteItem(item.id);
    });

    const viewButton = document.createElement("button");
    viewButton.textContent = "View";
    viewButton.classList.add("btn", "btn-primary", "m-1");
    viewButton.addEventListener("click", () => {
      console.log("view");
    });

    const updateButton = document.createElement("button");
    updateButton.textContent = "Edit";
    updateButton.classList.add("btn", "btn-warning", "m-1");
    updateButton.addEventListener("click", () => {
      console.log("update clicked for item with ID: " + item.id);
    });

    cellActions.appendChild(viewButton);
    cellActions.appendChild(updateButton);
    cellActions.appendChild(deleteButton);

    const checkboxCell = document.createElement("td");
    const checkbox = document.createElement("input");
    checkbox.classList.add("btn");
    checkbox.type = "checkbox";

    checkboxCell.appendChild(checkbox);

    row.appendChild(cell1);
    row.appendChild(cell2);
    row.appendChild(cell3);
    row.appendChild(cell4);
    row.appendChild(cell5);
    row.appendChild(cellActions);
    row.appendChild(checkboxCell);

    tableBody.appendChild(row);
  });
}

// ________________________update____________________
