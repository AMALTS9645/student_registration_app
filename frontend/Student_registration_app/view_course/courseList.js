// API calls________________________________________
const fetchApiData = async () => {
  let response = await fetch("http://localhost:8888/api/course");

  if (response.status === 302) {
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
  let response = await fetch(`http://localhost:8888/api/course/edit/${id}`, {
    method: "PUT",
    body: JSON.stringify(body),
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  });

  if (response.status === 202) {
    alert("Course updated");
    window.location.reload();
    return Promise.resolve();
  } else {
    return Promise.reject({
      message: `Error ${response.status}`,
    });
  }
};

const updateDetailsBulk = async (body) => {
  let response = await fetch(`http://localhost:8888/api/course/editall`, {
    method: "PUT",
    body: JSON.stringify(body),
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  });

  if (response.ok) {
    alert("Courses updated");
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
      `http://localhost:8888/api/course/delete/${id}`,
      {
        method: "DELETE",
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
      }
    );

    if (response.status === 204) {
      alert("Course deleted");
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
  if (confirm("Are you sure you want to delete Selected Courses?")) {
    let response = await fetch(`http://localhost:8000/api/course/deleteall`, {
      method: "DELETE",
      body: JSON.stringify(dataIds),
      headers: {
        "Content-type": "application/json; charset=UTF-8",
      },
    });

    if (response.ok) {
      alert("Courses deleted");
      window.location.reload();
      return Promise.resolve();
    } else {
      return Promise.reject({
        message: `Error ${response.status}`,
      });
    }
  }
};

// Listing____________________________________________________

window.addEventListener("load", () => {
  fetchApiData()
    .then((data) => {
      // console.log(data)
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
    row.classList.add(".rowItem");

    const cell1 = document.createElement("td");
    cell1.textContent = item.id;

    const cell2 = document.createElement("td");
    cell2.textContent = item.courseName;

    const cellCourse = document.createElement("td");
    const text1 = document.createElement("input");
    text1.type = "text";
    text1.value = item.courseName;
    cellCourse.classList.add("d-none");
    cellCourse.appendChild(text1);

    const cell3 = document.createElement("td");
    cell3.textContent = item.couseDuration;

    const cellDuration = document.createElement("td");
    const text2 = document.createElement("input");
    text2.type = "text";
    text2.value = item.couseDuration;
    cellDuration.classList.add("d-none");
    cellDuration.appendChild(text2);

    const cell4 = document.createElement("td");
    cell4.textContent = item.author;

    const cellAuthor = document.createElement("td");
    const text3 = document.createElement("input");
    text3.type = "text";
    text3.value = item.author;
    cellAuthor.classList.add("d-none");
    cellAuthor.appendChild(text3);

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
      window.location.href = `../single_course_page/viewCourse.html?id=${item.id}`;
    });

    const updateButton = document.createElement("button");
    updateButton.textContent = "Edit";
    updateButton.classList.add("btn", "btn-warning", "m-1");
    updateButton.addEventListener("click", (e) => {
      editCourse(e);
    });

    const saveButton = document.createElement("button");
    saveButton.textContent = "Save";
    saveButton.classList.add("btn", "btn-success", "m-1", "d-none");
    saveButton.addEventListener("click", (e) => saveData(e));

    const checkboxCell = document.createElement("td");
    const checkbox = document.createElement("input");
    checkbox.classList.add("btn", "check-box");
    checkbox.type = "checkbox";
    // checkbox.addEventListener("click", (e) => ischecked(e));

    checkboxCell.appendChild(checkbox);

    cellActions.appendChild(viewButton);
    cellActions.appendChild(updateButton);
    cellActions.appendChild(saveButton);
    cellActions.appendChild(deleteButton);

    row.appendChild(cell1);
    row.appendChild(cell2);
    row.appendChild(cellCourse);
    row.appendChild(cell3);
    row.appendChild(cellDuration);
    row.appendChild(cell4);
    row.appendChild(cellAuthor);
    row.appendChild(cellActions);
    row.appendChild(checkboxCell);

    tableBody.appendChild(row);
  });
}

// update course__________________________________________________________

const editCourse = (e) => {
  console.log(e.target.parentNode.parentNode.childNodes);
  console.log(e.target);
  e.target.classList.add("d-none");
  e.target.parentNode.parentNode.childNodes[1].classList.add("d-none");
  e.target.parentNode.parentNode.childNodes[2].classList.toggle("d-none");
  e.target.parentNode.parentNode.childNodes[3].classList.add("d-none");
  e.target.parentNode.parentNode.childNodes[4].classList.toggle("d-none");
  e.target.parentNode.parentNode.childNodes[5].classList.add("d-none");
  e.target.parentNode.parentNode.childNodes[6].classList.toggle("d-none");

  e.target.parentNode.parentNode.childNodes[7].childNodes[2].classList.toggle(
    "d-none"
  );
};

const saveData = (e) => {
  const updatedData = {};
  console.log(e.target.parentNode.parentNode);
  const id = e.target.parentNode.parentNode.childNodes[0].textContent;
  updatedData["courseName"] =
    e.target.parentNode.parentNode.childNodes[2].childNodes[0].value;
  updatedData["couseDuration"] =
    e.target.parentNode.parentNode.childNodes[4].childNodes[0].value;
  updatedData["author"] =
    e.target.parentNode.parentNode.childNodes[6].childNodes[0].value;

  e.target.previousElementSibling.classList.toggle("d-none");
  e.target.classList.toggle("d-none");

  e.target.parentNode.parentNode.childNodes[1].classList.toggle("d-none");
  e.target.parentNode.parentNode.childNodes[2].classList.toggle("d-none");

  e.target.parentNode.parentNode.childNodes[3].classList.toggle("d-none");
  e.target.parentNode.parentNode.childNodes[4].classList.toggle("d-none");

  e.target.parentNode.parentNode.childNodes[5].classList.toggle("d-none");
  e.target.parentNode.parentNode.childNodes[6].classList.toggle("d-none");

  const body = {
    userId: "Admin",
    details: updatedData,
  };
  updateDetails(id, body);
  console.log(updatedData);
};

// updateBulk____________________________________________________

const updateBulkStart = (e) => {
  console.log(
    e.target.parentNode.parentNode.childNodes[3].childNodes[3].childNodes
  );

  e.target.classList.toggle("d-none");
  e.target.nextElementSibling.classList.toggle("d-none");
  e.target.parentNode.parentNode.childNodes[3].childNodes[3].childNodes.forEach(
    (item) => {
      if (item.childNodes[8].childNodes[0].checked) {
        console.log(item);
        item.childNodes[1].classList.toggle("d-none");
        item.childNodes[2].classList.toggle("d-none");
        item.childNodes[3].classList.toggle("d-none");
        item.childNodes[4].classList.toggle("d-none");
        item.childNodes[5].classList.toggle("d-none");
        item.childNodes[6].classList.toggle("d-none");
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
      if (item.childNodes[8].childNodes[0].checked) {
        const details = {};
        details["id"] = item.childNodes[0].textContent;
        details["courseName"] = item.childNodes[2].childNodes[0].value;
        details["couseDuration"] = item.childNodes[4].childNodes[0].value;
        details["author"] = item.childNodes[6].childNodes[0].value;

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
      }
    }
  );
  updateDetailsBulk(data);
};

const deleteBulkStart = (e) => {
  console.log(
    e.target.parentNode.parentNode.childNodes[3].childNodes[3].childNodes
  );

  const dataIds = [];
  e.target.parentNode.parentNode.childNodes[3].childNodes[3].childNodes.forEach(
    (item) => {
      if (item.childNodes[8].childNodes[0].checked) {
        console.log(item.childNodes[0].textContent);
        dataIds.push(item.childNodes[0].textContent);
      }
    }
  );

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
