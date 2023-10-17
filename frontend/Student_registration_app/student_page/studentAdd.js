const addBtn = document.querySelector("#add");
const input = document.querySelector(".card");
const registerButton = document.createElement("button");
registerButton.type = "submit";
registerButton.id = "register";
registerButton.classList.add("btn", "btn-primary", "btn-block");
registerButton.textContent = "Register";

function addInput() {
  const newForm = document.createElement("form");
  const firstNameLabel = document.createElement("label");
  const firstName = document.createElement("input");
  const lastNameLabel = document.createElement("label");
  const lastName = document.createElement("input");
  const ageLabel = document.createElement("label");
  const age = document.createElement("input");
  const departmentNameLabel = document.createElement("label");
  const departmentName = document.createElement("input");
  const card = document.createElement("div");
  const separator = document.createElement("hr");

  const removeButton = document.createElement("button");
  removeButton.type = "button";
  removeButton.classList.add("btn", "btn-danger", "btn-block");
  removeButton.textContent = "Remove";
  removeButton.addEventListener("click", () => {
    input.removeChild(card);
  });

  newForm.id = "registration-form";
  newForm.classList.add("form-group");
  firstNameLabel.for = "firstName";
  firstNameLabel.textContent = "First Name:";
  firstName.type = "text";
  firstName.placeholder = "Enter first name";
  firstName.id = "firstName";
  firstName.classList.add("form-control", "my-3");

  lastNameLabel.for = "lastName";
  lastNameLabel.textContent = "Last Name:";
  lastName.type = "text";
  lastName.id = "lastName";
  lastName.placeholder = "Enter last name";
  lastName.classList.add("form-control", "my-3");

  ageLabel.for = "age";
  ageLabel.textContent = "Age:";
  age.type = "number";
  age.id = "age";
  age.placeholder = "Enter Age";
  age.classList.add("form-control", "my-3");

  departmentNameLabel.for = "departmentName";
  departmentNameLabel.textContent = "Department Name:";
  departmentName.type = "text";
  departmentName.id = "departmentName";
  departmentName.placeholder = "Enter departmentName";
  departmentName.classList.add("form-control", "my-3");

  card.classList.add("card-body");

  newForm.appendChild(firstNameLabel);
  newForm.appendChild(firstName);
  newForm.appendChild(lastNameLabel);
  newForm.appendChild(lastName);
  newForm.appendChild(ageLabel);
  newForm.appendChild(age);
  newForm.appendChild(departmentNameLabel);
  newForm.appendChild(departmentName);
  newForm.appendChild(removeButton);
  newForm.appendChild(separator);

  newForm.appendChild(registerButton);
  // ____________________________________________________
  card.appendChild(newForm);

  input.appendChild(card);
}

addBtn.addEventListener("click", addInput);

const register = async (data) => {
    let response = await fetch("http://localhost:8000/api/student/register", {
      method: "POST",

      body: JSON.stringify(data[0]),

      headers: {
        "Content-type": "application/json; charset=UTF-8",
      },
    });

    if (response.ok) {
      let data = await response.json();

      console.log(data);

      alert("Student Added");

      return Promise.resolve(data);
    } else {
      console.log(response);

      return Promise.reject({
        message: `Error ${response.status}`,
      });
    }
};

function displayInputData(event) {
  event.preventDefault();
  const forms = document.querySelectorAll("form");
  const inputData = [];
  forms.forEach((form) => {
    const studentData = {};
    const inputs = form.querySelectorAll("input");

    inputs.forEach((input) => {
      studentData[input.id] = input.value;
    });
    inputData.push({ userId: "Admin", details: studentData });
  });
  register(inputData);
}

registerButton.addEventListener("click", displayInputData);
