// API calls ____________________________________________
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

const updateDetails = async (id, body) => {
  let response = await fetch(`http://localhost:8888/api/student/edit/${id}`, {
    method: "PUT",
    body: JSON.stringify(body),
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  });

  if (response.status === 202) {
    alert("Student updated");
    window.location.reload();
    return Promise.resolve();
  } else {
    return Promise.reject({
      message: `Error ${response.status}`,
    });
  }
};
const updateDetailsBulk = async (body) => {
  let response = await fetch(`http://localhost:8888/api/student/editall`, {
    method: "PUT",
    body: JSON.stringify(body),
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  });

  if (response.ok) {
    alert("Students updated");
    window.location.reload();
    return Promise.resolve();
  } else {
    return Promise.reject({
      message: `Error ${response.status}`,
    });
  }
};

const deleteItem = async (id) => {
  if (confirm("Are you sure you want to delete?")) {
    let response = await fetch(
      `http://localhost:8888/api/student/delete/${id}`,
      {
        method: "DELETE",
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
      }
    );

    if (response.status === 204) {
      alert("Student deleted");
      window.location.reload();
      return Promise.resolve();
    } else {
      return Promise.reject({
        message: `Error ${response.status}`,
      });
    }
  }
};
const deleteItemBulk = async (dataIds) => {
  if (confirm("Are you sure you want to delete Selected Students?")) {
    let response = await fetch(
      `http://localhost:8888/api/student/deleteall`,
      {
        method: "DELETE",
        body: JSON.stringify(dataIds),
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
      }
    );

    if (response.ok) {
      alert("Students deleted");
      window.location.reload();
      return Promise.resolve();
    } else {
      return Promise.reject({
        message: `Error ${response.status}`,
      });
    }
  }
};

// Listing________________________________________________________
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

  data.map((item, index) => {
    const row = document.createElement("tr");

    const cell1 = document.createElement("td");
    cell1.textContent = item.id;

    const cell2 = document.createElement("td");
    cell2.textContent = item.firstName;

    const cellFirstName = document.createElement("td");
    const text1 = document.createElement("input");
    text1.type = "text";
    text1.value = item.firstName;
    cellFirstName.classList.add("d-none", "first-name-up");
    cellFirstName.appendChild(text1);

    const cell3 = document.createElement("td");
    cell3.textContent = item.lastName;

    const cellLastName = document.createElement("td");
    const text2 = document.createElement("input");
    text2.type = "text";
    text2.classList.add("l-name");
    text2.value = item.lastName;
    cellLastName.classList.add("d-none", "last-name-up");
    cellLastName.appendChild(text2);

    const cell4 = document.createElement("td");
    cell4.textContent = item.age;

    const cellAge = document.createElement("td");
    const text3 = document.createElement("input");
    text3.type = "number";
    text3.value = item.age;
    cellAge.classList.add("d-none", "age-up");
    cellAge.appendChild(text3);

    const cell5 = document.createElement("td");
    cell5.textContent = item.departmentName;

    const cellDepartment = document.createElement("td");
    const text4 = document.createElement("input");
    text4.type = "text";
    text4.value = item.departmentName;
    cellDepartment.classList.add("d-none", "department-up");
    cellDepartment.appendChild(text4);

    const cellActions = document.createElement("td");

    const deleteButton = document.createElement("button");
    deleteButton.textContent = "Delete";
    deleteButton.classList.add("btn", "btn-danger", "m1");
    deleteButton.addEventListener("click", () => {
      deleteItem(item.id);
    });

    const updateButton = document.createElement("button");
    updateButton.textContent = "Edit";
    updateButton.classList.add("btn", "btn-warning", "m-1");
    updateButton.addEventListener("click", (e) => {
      editStudent(e);
    });

    const saveButton = document.createElement("button");
    saveButton.textContent = "Save";
    saveButton.classList.add("btn", "btn-success", "m-1", "d-none");
    saveButton.addEventListener("click", (e) => saveData(e));

    cellActions.appendChild(updateButton);
    cellActions.appendChild(saveButton);
    cellActions.appendChild(deleteButton);

    const checkboxCell = document.createElement("td");
    const checkbox = document.createElement("input");
    checkbox.classList.add("btn");
    checkbox.type = "checkbox";

    checkboxCell.appendChild(checkbox);

    row.appendChild(cell1);
    row.appendChild(cell2);
    row.appendChild(cellFirstName);
    row.appendChild(cell3);
    row.appendChild(cellLastName);
    row.appendChild(cell4);
    row.appendChild(cellAge);
    row.appendChild(cell5);
    row.appendChild(cellDepartment);
    row.appendChild(cellActions);
    row.appendChild(checkboxCell);

    tableBody.appendChild(row);
  });
}

// ________________________update____________________

const editStudent = (e) => {
  console.log(e.target.parentNode.parentNode.childNodes);
  e.target.classList.add("d-none");
  e.target.parentNode.parentNode.childNodes[1].classList.add("d-none");
  e.target.parentNode.parentNode.childNodes[2].classList.toggle("d-none");
  e.target.parentNode.parentNode.childNodes[3].classList.add("d-none");
  e.target.parentNode.parentNode.childNodes[4].classList.toggle("d-none");
  e.target.parentNode.parentNode.childNodes[5].classList.add("d-none");
  e.target.parentNode.parentNode.childNodes[6].classList.toggle("d-none");
  e.target.parentNode.parentNode.childNodes[7].classList.add("d-none");
  e.target.parentNode.parentNode.childNodes[8].classList.toggle("d-none");

  e.target.parentNode.parentNode.childNodes[9].childNodes[1].classList.toggle(
    "d-none"
  );
};

const saveData = (e) => {
  const updatedData = {};
  console.log(e.target.parentNode.parentNode);
  const id = e.target.parentNode.parentNode.childNodes[0].textContent;
  updatedData["firstName"] =
    e.target.parentNode.parentNode.childNodes[2].childNodes[0].value;
  updatedData["lastName"] =
    e.target.parentNode.parentNode.childNodes[4].childNodes[0].value;
  updatedData["age"] =
    e.target.parentNode.parentNode.childNodes[6].childNodes[0].value;
  updatedData["departmentName"] =
    e.target.parentNode.parentNode.childNodes[8].childNodes[0].value;

  e.target.previousElementSibling.classList.toggle("d-none");
  e.target.classList.toggle("d-none");

  e.target.parentNode.parentNode.childNodes[1].classList.toggle("d-none");
  e.target.parentNode.parentNode.childNodes[2].classList.toggle("d-none");

  e.target.parentNode.parentNode.childNodes[3].classList.toggle("d-none");
  e.target.parentNode.parentNode.childNodes[4].classList.toggle("d-none");

  e.target.parentNode.parentNode.childNodes[5].classList.toggle("d-none");
  e.target.parentNode.parentNode.childNodes[6].classList.toggle("d-none");

  e.target.parentNode.parentNode.childNodes[7].classList.toggle("d-none");
  e.target.parentNode.parentNode.childNodes[8].classList.toggle("d-none");

  const body = {
    userId: "Admin",
    details: updatedData,
  };
  updateDetails(id, body);
};

// bulkUpdate__________________________________________________
const updateBulkStart = (e) => {
  console.log(
    e.target.parentNode.parentNode.childNodes[3].childNodes[3].childNodes
  );

  e.target.classList.toggle("d-none");
  e.target.nextElementSibling.classList.toggle("d-none");
  e.target.parentNode.parentNode.childNodes[3].childNodes[3].childNodes.forEach(
    (item) => {
      if (item.childNodes[10].childNodes[0].checked) {
        console.log(item);
        item.childNodes[1].classList.toggle("d-none");
        item.childNodes[2].classList.toggle("d-none");
        item.childNodes[3].classList.toggle("d-none");
        item.childNodes[4].classList.toggle("d-none");
        item.childNodes[5].classList.toggle("d-none");
        item.childNodes[6].classList.toggle("d-none");
        item.childNodes[7].classList.toggle("d-none");
        item.childNodes[8].classList.toggle("d-none");
      }
    }
  );
};

const saveBulkStart = (e) => {
  console.log(
    e.target.parentNode.parentNode.childNodes[3].childNodes[3].childNodes
  );
  const data = [];
  e.target.classList.toggle("d-none");
  e.target.previousElementSibling.classList.toggle("d-none");
  e.target.parentNode.parentNode.childNodes[3].childNodes[3].childNodes.forEach(
    (item) => {
      if (item.childNodes[10].childNodes[0].checked) {
        const details = {};
        details["id"] = item.childNodes[0].textContent;
        details["firstName"] = item.childNodes[2].childNodes[0].value;
        details["lastName"] = item.childNodes[4].childNodes[0].value;
        details["age"] = item.childNodes[6].childNodes[0].value;
        details["departmentName"] = item.childNodes[8].childNodes[0].value;

        const body = {
          userId: "Admin",
          details: details,
        };
        data.push(body);
        // console.log(item.childNodes[0]);
        item.childNodes[1].classList.toggle("d-none");
        item.childNodes[2].classList.toggle("d-none");
        item.childNodes[3].classList.toggle("d-none");
        item.childNodes[4].classList.toggle("d-none");
        item.childNodes[5].classList.toggle("d-none");
        item.childNodes[6].classList.toggle("d-none");
        item.childNodes[7].classList.toggle("d-none");
        item.childNodes[8].classList.toggle("d-none");
      }
    }
  );
  // console.log(data)
  updateDetailsBulk(data);
};

const deleteBulkStart = (e) => {
  console.log(
    e.target.parentNode.parentNode.childNodes[3].childNodes[3].childNodes
  );

  const dataIds = [];
  e.target.parentNode.parentNode.childNodes[3].childNodes[3].childNodes.forEach(
    (item) => {
      if (item.childNodes[10].childNodes[0].checked) {
        console.log(item.childNodes[0].textContent);
        dataIds.push(item.childNodes[0].textContent);
      }
    }
  );
  // console.log(dataIds);
  deleteItemBulk(dataIds);
};

document
  .getElementById("update-all")
  .addEventListener("click", (e) => updateBulkStart(e));

document
  .getElementById("save-all")
  .addEventListener("click", (e) => saveBulkStart(e));

document
  .getElementById("delete-all")
  .addEventListener("click", (e) => deleteBulkStart(e));
