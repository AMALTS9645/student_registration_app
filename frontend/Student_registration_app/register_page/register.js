const fetchApiCourse = async () => {
  let response = await fetch("http://localhost:8000/api/course");

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

const fetchApiStudent = async () => {
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

function populateStudent(data) {
  const dropdown = document.getElementById("studentName");
  data.forEach((item) => {
    // console.log(item)
    const option = document.createElement("option");
    option.value = item.id;
    option.text = item.firstName + item.lastName;
    dropdown.add(option);
  });
}

function populateCourse(data) {
  const dropdown = document.getElementById("courseName");
  data.forEach((item) => {
    const option = document.createElement("option");
    option.value = item.id;
    option.text = item.courseName;
    dropdown.add(option);
  });
}

window.addEventListener("load", () => {
  fetchApiStudent()
    .then((data) => {
      populateStudent(data);
    })
    .catch((error) => {
      console.error(error);
    });
});
window.addEventListener("load", () => {
  fetchApiCourse()
    .then((data) => {
      populateCourse(data);
    })
    .catch((error) => {
      console.error(error);
    });
});

const registerCourseApi = async (cId, sId) => {
  let response = await fetch(
    `http://localhost:8000/api/student/${sId}/course/${cId}`,
    {
      method: "PUT",
    }
  );

  if (response.ok) {
    let data = await response.json();

    console.log(data);
    alert("Course Assigned");

    return Promise.resolve(data);
  } else {
    console.log(response);

    return Promise.reject({
      message: `Error ${response.status}`,
    });
  }
};
// register Student________________________________
let selectedCourseId;
let selectedStudentId;

document.getElementById("courseName").addEventListener("change", function () {
  selectedCourseId = this.value;
});

document.getElementById("studentName").addEventListener("change", function () {
  selectedStudentId = this.value;
});

document.getElementById("register").addEventListener("click", (event) => {
  event.preventDefault();

  registerCourseApi(selectedCourseId, selectedStudentId);
});
