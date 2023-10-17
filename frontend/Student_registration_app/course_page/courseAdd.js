const addBtn = document.querySelector("#add");
const input = document.querySelector(".card");
const registerButton = document.createElement("button");
registerButton.type = "submit";
registerButton.id = "register";
registerButton.classList.add("btn", "btn-primary", "btn-block");
registerButton.textContent = "Register";

function addCourse() {
  const newForm = document.createElement("form");

  const courseNameLabel = createLabel("Course Name:");
  const courseNameInput = createInput(
    "text",
    "courseName",
    "Enter course name"
  );

  const courseDurationLabel = createLabel("Course Duration:");
  const courseDurationInput = createInput(
    "text",
    "courseDuration",
    "Enter Course Duration"
  );

  const authorLabel = createLabel("Author:");
  const authorInput = createInput("text", "author", "Enter author");

  const card = document.createElement("div");
  card.classList.add("card-body");

  const removeButton = createRemoveButton();
  removeButton.addEventListener("click", () => {
    input.removeChild(card);
  });

  newForm.id = "registration-form";
  newForm.classList.add("form-group");

  newForm.appendChild(courseNameLabel);
  newForm.appendChild(courseNameInput);
  newForm.appendChild(courseDurationLabel);
  newForm.appendChild(courseDurationInput);
  newForm.appendChild(authorLabel);
  newForm.appendChild(authorInput);

  newForm.appendChild(removeButton);

  newForm.appendChild(registerButton);

  card.appendChild(newForm);
  input.appendChild(card);
}

addBtn.addEventListener("click", addCourse);

async function register(data) {
  try {
    let response = await fetch("http://localhost:8000/api/course/add", {
      method: "POST",
      body: JSON.stringify(data[0]),
      headers: {
        "Content-type": "application/json; charset=UTF-8",
      },
    });

    if (response.ok) {
      let responseData = await response.json();
      console.log(responseData);
      alert("Course Added");
    } else {
      console.error(response);
    }
  } catch (error) {
    console.error(error);
  }
}

function displayInputData(event) {
  event.preventDefault();
  const forms = document.querySelectorAll("form");
  const inputData = [];
  forms.forEach((form) => {
    const courseData = {};
    const inputs = form.querySelectorAll("input");
    inputs.forEach((input) => {
      courseData[input.id] = input.value;
    });
    inputData.push({ userId: "Admin", details: courseData });
  });
  console.log(inputData);
  register(inputData);
}

registerButton.addEventListener("click", displayInputData);

function createLabel(text) {
  const label = document.createElement("label");
  label.for = text.toLowerCase();
  label.textContent = text;
  return label;
}

function createInput(type, id, placeholder) {
  const input = document.createElement("input");
  input.type = type;
  input.id = id;
  input.name = id;
  input.placeholder = placeholder;
  input.classList.add("form-control", "my-3");
  return input;
}

function createRemoveButton() {
  const removeButton = document.createElement("button");
  removeButton.type = "button";
  removeButton.classList.add("btn", "btn-danger", "btn-block");
  removeButton.textContent = "Remove";
  return removeButton;
}
