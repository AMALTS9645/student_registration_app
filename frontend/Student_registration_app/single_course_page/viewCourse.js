const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get("id");
const addBtn = document.querySelector("#add");
const input = document.querySelector("#cardItem");

const registerButton = document.createElement("button");
registerButton.type = "submit";
registerButton.id = "register";
registerButton.classList.add("btn", "btn-primary", "btn-block");
registerButton.textContent = "Submit Comment";

const fetchApiData = async () => {
  let response = await fetch(`http://localhost:8888/api/course/${id}`);

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

window.addEventListener("load", () => {
  fetchApiData()
    .then((data) => {
      populateTable(data);
    })
    .catch((error) => {
      console.error(error);
    });
});

console.log(id);

function populateTable(data) {
  const card = document.getElementById("card");

  const cardBody = document.createElement("div");
  cardBody.classList.add("card-body");

  const header = document.createElement("h5");
  header.textContent = "Course Details";

  const para1 = document.createElement("p");
  para1.classList.add("card-text");

  const strong = document.createElement("strong");
  strong.textContent = "Course Name: ";

  para1.appendChild(strong);
  para1.append(data.courseName);

  const para2 = document.createElement("p");
  para1.classList.add("card-text");

  const strong2 = document.createElement("strong");
  strong2.textContent = "Course Duration: ";

  para2.appendChild(strong2);
  para2.append(data.couseDuration);

  const para3 = document.createElement("p");
  para3.classList.add("card-text");

  const strong3 = document.createElement("strong");
  strong3.textContent = "Author: ";

  para3.appendChild(strong3);
  para3.append(data.author);

  cardBody.appendChild(header);
  cardBody.appendChild(para1);
  cardBody.appendChild(para2);
  cardBody.appendChild(para3);

  card.appendChild(cardBody);

  const list = document.getElementById("list-group");
  data.comments.map((item) => {
    const component = document.createElement("div");
    component.classList.add("m-2", "bg-dark", "text-white", "p-1", "rounded");

    const head = document.createElement("h6");
    head.textContent = item.studentName;

    const listItem = document.createElement("li");
    listItem.classList.add(
      "list-group-item",
      "d-flex",
      "justify-content-between"
    );
    listItem.textContent = item.text;

    const cellActions = document.createElement("div");

    const deleteButton = document.createElement("button");
    deleteButton.innerHTML = `<i class="bi bi-trash"></i>`;
    deleteButton.classList.add("btn", "btn-danger", "m1");
    deleteButton.addEventListener("click", () => {
      //   deleteItem(item.id);
    });

    const updateButton = document.createElement("button");
    updateButton.innerHTML = `<i class="bi bi-pencil-square"></i>`;
    updateButton.classList.add("btn", "btn-warning", "m-1");
    updateButton.addEventListener("click", (e) => {
      //   editStudent(e);
    });

    cellActions.appendChild(updateButton);
    cellActions.appendChild(deleteButton);

    listItem.appendChild(cellActions);
    component.appendChild(head);
    component.appendChild(listItem);
    list.appendChild(component);
  });
}

function addComment() {
  const commentInput = document.createElement("textarea");
  commentInput.id = "text";
  commentInput.name = "text";
  commentInput.placeholder = "Write your comment....";
  commentInput.classList.add("form-control", "my-3", "mb-2");
  //   commentInput.setAttribute("rows"=2);

  const card = document.createElement("div");
  card.classList.add("card-body", "cardItem");

  const removeButton = createRemoveButton();
  removeButton.addEventListener("click", () => {
    input.removeChild(card);
  });

  card.appendChild(commentInput);
  card.appendChild(removeButton);
  card.appendChild(registerButton);

  input.appendChild(card);
}

function createRemoveButton() {
  const removeButton = document.createElement("button");
  removeButton.type = "button";
  removeButton.classList.add("btn", "btn-danger", "btn-block");
  removeButton.textContent = "Remove";
  return removeButton;
}

addBtn.addEventListener("click", () => addComment());

async function register(data) {
  try {
    let response = await fetch(
      `http://localhost:8888/api/course/comment/${id}`,
      {
        method: "PUT",
        body: JSON.stringify(data[0]),
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
      }
    );

    if (response.status === 202) {
      let responseData = await response.json();
    //   console.log(responseData);
      alert("Comment Added");
      window.location.reload();
      return Promise.resolve(data);
    } else {
      console.error(response);
    }
  } catch (error) {
    console.error(error);
  }
}

async function registerAll(data) {
  try {
    let response = await fetch(
      `http://localhost:8888/api/course/comment/all/${id}`,
      {
        method: "PUT",
        body: JSON.stringify(data),
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
      }
    );

    if (response.ok) {
      let responseData = await response.json();
    //   console.log(responseData);
      alert("Comments Added");
      window.location.reload();
      return Promise.resolve(data);
    } else {
      console.error(response);
    }
  } catch (error) {
    console.error(error);
  }
}

function displayInputData(event) {
  event.preventDefault();
  const cards = document.querySelectorAll(".cardItem");
  const inputData = [];
  cards.forEach((card) => {
    const commentData = {};
    const input = card.querySelector("textarea");

    // console.log(input)
    commentData[input.id] = input.value;
    commentData["studentId"] = 1;

    inputData.push({ userId: "Admin", details: commentData });
  });
//   console.log(inputData);
  if (inputData.length === 1) {
    register(inputData);
  } else if (inputData.length > 1) {
    registerAll(inputData);
  }
}

registerButton.addEventListener("click", displayInputData);
