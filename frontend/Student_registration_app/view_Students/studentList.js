const fetchApiData = async () => {
  let response = await fetch("http://localhost:8000/api/student");

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
    cell1.textContent = index + 1;

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
    deleteButton.classList.add("btn", "btn-danger", "m-1");
    deleteButton.addEventListener("click", () => {
      console.log("Delete clicked for item with ID: " + item.id);
    });
    const viewButton = document.createElement("button");
    viewButton.textContent = "View";
    viewButton.classList.add("btn", "btn-primary", "m-1");
    viewButton.addEventListener("click", () => {
      console.log("Delete clicked for item with ID: " + item.id);
    });

    const updateButton = document.createElement("button");
    updateButton.textContent = "Update";
    updateButton.classList.add("btn", "btn-warning", "m-1");
    updateButton.addEventListener("click", () => {
      console.log("update clicked for item with ID: " + item.id);
    });

    cellActions.appendChild(viewButton);
    cellActions.appendChild(updateButton);
    cellActions.appendChild(deleteButton);

    row.appendChild(cell1);
    row.appendChild(cell2);
    row.appendChild(cell3);
    row.appendChild(cell4);
    row.appendChild(cell5);
    row.appendChild(cellActions);

    tableBody.appendChild(row);
  });
}

// ________________________update____________________
const mainCheckbox = document.getElementById('customCheck1');
mainCheckbox.addEventListener('change', function () {
    const tableBody = document.getElementById('table-body');
    const rows = tableBody.querySelectorAll('tr');

    const addCheckboxes = mainCheckbox.checked;

    rows.forEach((row, index) => {
        if (addCheckboxes) {
            const checkbox = document.createElement('input');
            checkbox.type = 'checkbox';
            checkbox.classList.add('custom-control-input');

            const cell = document.createElement('td');
            cell.appendChild(checkbox);

            // Insert the checkbox cell after the "Department" column
            const departmentCell = row.querySelector('td:nth-child(5)'); // Assuming "Department" is the fifth column (1-based index)
            if (departmentCell) {
                row.insertBefore(cell, departmentCell.nextSibling);
            }
        } else {
            // Remove any existing checkboxes if the main checkbox is unchecked
            const existingCheckboxes = row.querySelectorAll('.custom-control-input');
            existingCheckboxes.forEach((cb) => {
                row.removeChild(cb.parentElement);
            });
        }
    });
});

