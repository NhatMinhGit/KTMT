var dkhpAPI = 'http://localhost:8081/dangkyhocphan';
var studentAPI = 'http://localhost:8081/students';
var lopHPAPI = 'http://localhost:8081/dangkyhocphan/lophocphan';
var detailAPI = 'http://localhost:8081/dangkyhocphan/lophocphan/chitietlophocphan';
var registerAPI = 'http://localhost:8081/dangkyhocphan/lophocphan/sinhvien';

var studentID = localStorage.getItem("studentID");
document.getElementById('sv-mssv').textContent = studentID;

fetch(studentAPI + '/' + studentID)
    .then(function(response) {
        return response.json();
    })
    .then(function(student) {
        document.getElementById('sv-hoten').textContent = student.name;
        const sexText = student.sex ? 'Nam' : 'Nữ';
        document.getElementById('avatar').src = student.img;
        document.getElementById('sv-gioitinh').textContent = sexText;
        switch (student.status) {
            case 0:
                document.getElementById('sv-status').textContent = "Đã nghỉ";
                break;
            case 1:
                document.getElementById('sv-status').textContent = "Đang học";
                break;
            case 2:
                document.getElementById('sv-status').textContent = "Bảo lưu";
                break;
            case 3:
                document.getElementById('sv-status').textContent = "Đã tốt nghiệp";
                break;
            default:
                document.getElementById('sv-status').textContent = "Không xác định";
                break;
        }
})

function loadListOfCourse() {
    studentID = document.getElementById('sv-mssv').textContent;
    year = new Date().getFullYear();
    semester = document.getElementById('semester').value.slice(2, 3);
    console.log(studentID, year, semester);
    
    fetch(dkhpAPI + '?studentID=' + studentID + '&semester=' + semester + '&year=' + year)
    .then(function (res) {
        return res.json();
    })
    .then(function (courses) {
        var table = document.querySelector('#tb-course');
        var tbody = table.querySelector('tbody');

        tbody.innerHTML = '';

        courses.forEach((course, index) => {
            const row = tbody.insertRow();
            row.insertCell(0).textContent = index + 1; 
            row.insertCell(1).textContent = course.courseID;
            row.insertCell(2).textContent = course.name;
            row.insertCell(3).textContent = course.credits;
            row.insertCell(4).textContent = course.type;
            row.insertCell(5).textContent = course.prerequisites;

            row.addEventListener('click', function() {
                var prevActiveRow = document.querySelector('tr.active');
                if (prevActiveRow) {
                    prevActiveRow.classList.remove('active');
                }
                this.classList.add('active');
                choiceCourse(course.courseID, course.name);
                var table = document.querySelector('#tb-detail');
                var tbody = table.querySelector('tbody');
                tbody.innerHTML = '';
            });
        });
    })
    .catch(function (error) {
        console.error('Error fetching data:', error);
    });
}

loadListOfCourse();

function choiceCourse(courseID, courseName) {
    fetch(lopHPAPI + '?courseID=' + courseID)
    .then(function(response) {
        return response.json();
    })
    .then(function(classes) {
        
        var table = document.querySelector('#tb-class');
        var tbody = table.querySelector('tbody');

        tbody.innerHTML = '';

        classes.forEach((lop, index) => {
            const row = tbody.insertRow();
            row.insertCell(0).textContent = index + 1; 
            row.insertCell(1).textContent = lop.enrollmentID;
            row.insertCell(2).textContent = courseName;
            row.insertCell(3).textContent = lop.name;
            row.insertCell(4).textContent = lop.quantity;
            row.insertCell(5).textContent = lop.quantityApply;
            row.insertCell(6).textContent = lop.status;

            row.addEventListener('click', function() {
                var prevActiveRow = table.querySelector('.active');
                if (prevActiveRow) {
                    prevActiveRow.classList.remove('active');
                }
                this.classList.add('active');
                choiceClass(lop.enrollmentID);
            });
        });
    })
}

function choiceClass(enrollmentID) {
    fetch(detailAPI + '?enrollmentID=' + enrollmentID)
    .then(function(response) {
        return response.json();
    })
    .then(function(detail) {
        console.log(detail);
        var table = document.querySelector('#tb-detail');
        var tbody = table.querySelector('tbody');

        tbody.innerHTML = '';

        for (var i = 0; i < detail.scheduleStudy.length; i++) {
            const row = tbody.insertRow();
            row.insertCell(0).textContent = i + 1;
            row.insertCell(1).textContent = 'LT - Thứ ' + detail.scheduleStudy[i].dayOfWeek + ' (T' + detail.scheduleStudy[i].classesStart + ' - T' + detail.scheduleStudy[i].classesEnd + ')';
            row.insertCell(2).textContent = "";
            row.insertCell(3).textContent = detail.roomName;
            row.insertCell(4).textContent = detail.nameInstuctor;
            row.insertCell(5).textContent = detail.dateApplyStart + ' - ' + detail.dateApplyEnd;
        }

        for (var i = 0; i < detail.enrollmentPs.length; i++) {
            const row = tbody.insertRow();
            row.insertCell(0).textContent = i + 1 + detail.scheduleStudy.length;
            detail.enrollmentPs[i].scheduleStudy.forEach((schedule, index) => {
                row.insertCell(index + 1).textContent = 'TH - Thứ ' + schedule.dayOfWeek + ' (T' + schedule.classesStart + ' - T' + schedule.classesEnd + ')';
            });

            row.insertCell(2).textContent = detail.enrollmentPs[i].name;
            row.insertCell(3).textContent = detail.enrollmentPs[i].roomName;
            row.insertCell(4).textContent = detail.enrollmentPs[i].nameInstuctor;
            row.insertCell(5).textContent = detail.dateApplyStart + ' - ' + detail.dateApplyEnd;
        }
    })
}

var choiceSemester = document.getElementById("semester");
choiceSemester.addEventListener("change", function() {
    loadListOfCourse();
    loadListOfRegisteredClass();
});

document.getElementById('thuchanh').addEventListener('change', function() {
    var choiceNhom = this.value;
    console.log(choiceNhom);
    markActiveRow(choiceNhom);
});

function markActiveRow(choiceNhom) {
    var tableRows = document.querySelectorAll('#tb-detail tbody tr');
    tableRows.forEach(function(row) {
        var nhomTHValue = row.cells[2].textContent.trim().slice(5);
        console.log(nhomTHValue);
        if (nhomTHValue === choiceNhom) {
            row.classList.add('active');
        } else {
            row.classList.remove('active');
        }
    });
}


// function addEnrollment() {
//     var activeClass = document.querySelector('#tb-class tr.active');
//     var activeCourse = document.querySelector('#tb-course tr.active');
//     var activeDetail = document.querySelector('#tb-detail tr.active');
//     if (!activeClass) {
//         alert('Vui lòng chọn lớp học phần');
//         return;
//     }

//     if (!activeCourse) {
//         alert('Vui lòng chọn môn học');
//         return;
//     }

//     if (!activeDetail) {
//         alert('Vui lòng chọn nhóm thực hành');
//         return; 
//     }

//     var enrollmentID = activeClass.cells[1].textContent;
//     console.log(enrollmentID);

//     var tableRegister = document.querySelector('#tb-register tbody');
//     var newRow = tableRegister.insertRow();

//     // Thêm các ô cho hàng mới
//     newRow.insertCell(0).textContent = '';
//     newRow.insertCell(1).textContent = '';
//     newRow.insertCell(2).textContent = enrollmentID;
//     newRow.insertCell(3).textContent = activeCourse.cells[2].textContent;
//     newRow.insertCell(4).textContent = activeClass.cells[3].textContent;
//     newRow.insertCell(5).textContent = activeCourse.cells[3].textContent;
//     newRow.insertCell(6).textContent = activeDetail.cells[2].textContent;
//     newRow.insertCell(7).textContent = ''; // Học phí
//     newRow.insertCell(8).textContent = ''; // Hạn nộp
//     newRow.insertCell(9).textContent = ''; // Thu
//     newRow.insertCell(10).textContent = ''; // Ngày ĐK
//     newRow.insertCell(11).textContent = ''; // Trạng thái LHP

// }
function addEnrollment() {
    var activeClass = document.querySelector('#tb-class tr.active');
    var activeCourse = document.querySelector('#tb-course tr.active');
    var activeDetail = document.querySelector('#tb-detail tr.active');
  
    // Validate selections
    if (!activeClass) {
      alert('Vui lòng chọn lớp học phần');
      return;
    }
  
    if (!activeCourse) {
      alert('Vui lòng chọn môn học');
      return;
    }
  
    if (!activeDetail) {
      alert('Vui lòng chọn nhóm thực hành');
      return;
    }
  
    // Extract enrollment ID
    var enrollmentID = activeClass.cells[1].textContent;
  
    // Create a modal element (or use an existing one)
    var modal = document.getElementById('enrollmentModal'); // Assuming an existing modal
    if (!modal) {
      modal = createEnrollmentModal(); // Create modal if it doesn't exist
    }
  
    // Populate modal content with retrieved data and input fields
    modal.querySelector('#enrollmentId').textContent = enrollmentID; // Pre-fill enrollment ID
    modal.querySelector('#courseName').textContent = activeCourse.cells[2].textContent;
    modal.querySelector('#className').textContent = activeClass.cells[3].textContent;
    modal.querySelector('#practiceGroup').textContent = activeDetail.cells[2].textContent;
  
    // Add input fields for missing data (e.g., học phí, hạn nộp, thu)
    var feeInput = modal.querySelector('#feeInput');
    if (!feeInput) { // Create input only if it doesn't exist
      feeInput = document.createElement('input');
      feeInput.id = 'feeInput';
      feeInput.type = 'number';
      feeInput.placeholder = 'Nhập học phí';
      modal.querySelector('#feeCell').appendChild(feeInput);
    }
  
    // Similar logic for other missing data input fields (e.g., deadline, payment period)
  
    // Clear any previously entered values
    modal.querySelectorAll('input[type="text"], input[type="number"]').forEach(function(input) {
      input.value = '';
    });
  
    // Show the modal
    modal.style.display = 'block';
  
    // Optionally (if modal doesn't have a submit button):
    var submitButton = modal.querySelector('#submitButton');
    if (submitButton) {
      submitButton.addEventListener('click', function() {
        // Validate user-entered data
        // ... (implement data validation logic)
        if (dataIsValid) {
          // Add a new row to the registration table using the retrieved and user-entered data
          var tableRegister = document.querySelector('#tb-register tbody');
          var newRow = tableRegister.insertRow();
  
          // Add cells with retrieved and user-entered data
          newRow.insertCell(0).textContent = ''; // To be filled later
          newRow.insertCell(1).textContent = enrollmentID;
          newRow.insertCell(2).textContent = activeCourse.cells[2].textContent;
          newRow.insertCell(3).textContent = activeClass.cells[3].textContent;
          newRow.insertCell(4).textContent = activeDetail.cells[2].textContent;
          newRow.insertCell(5).textContent = activeCourse.cells[3].textContent; // Placeholder
          newRow.insertCell(6).textContent = activeDetail.cells[2].textContent; // Placeholder
          newRow.insertCell(7).textContent = modal.querySelector('#feeInput').value;
          // Similar logic to populate other cells with user-entered data (e.g., deadline, payment period)
          newRow.insertCell(8).textContent = ''; // To be filled later (e.g., submission deadline)
          newRow.insertCell(9).textContent = ''; // To be filled later (e.g., payment period)
          newRow.insertCell(10).textContent = ''; // To be filled later (e.g., registration date)
          newRow.insertCell(11).textContent = ''; // To be filled later (e.g., enrollment status)
  
          // Hide the modal
          modal.style.display = 'none';
        } else {
          alert('Vui lòng nhập đủ thông tin chính xác.');
        }
      });
    }
  }
  
  // Function to create the enrollment modal (if it doesn't exist)

// function addCourse() {
//     var activeClass = document.querySelector('#tb-class tr.active');
//     var activeCourse = document.querySelector('#tb-course tr.active');
//     var activeDetail = document.querySelector('#tb-detail tr.active');
//     if (!activeClass) {
//         alert('Vui lòng chọn lớp học phần');
//         return;
//     }

//     if (!activeCourse) {
//         alert('Vui lòng chọn môn học');
//         return;
//     }

//     if (!activeDetail) {
//         alert('Vui lòng chọn nhóm thực hành');
//         return; 
//     }

//     var enrollmentID = activeClass.cells[1].textContent;
//     console.log(enrollmentID);

//     var tableRegister = document.querySelector('#tb-register tbody');
//     var newRow = tableRegister.insertRow();

//     // Thêm các ô cho hàng mới
//     newRow.insertCell(0).textContent = '';
//     newRow.insertCell(1).textContent = '';
//     newRow.insertCell(2).textContent = enrollmentID;
//     newRow.insertCell(3).textContent = activeCourse.cells[2].textContent;
//     newRow.insertCell(4).textContent = activeClass.cells[3].textContent;
//     newRow.insertCell(5).textContent = activeCourse.cells[3].textContent;
//     newRow.insertCell(6).textContent = activeDetail.cells[2].textContent;
//     newRow.insertCell(7).textContent = ''; // Học phí
//     newRow.insertCell(8).textContent = ''; // Hạn nộp
//     newRow.insertCell(9).textContent = ''; // Thu
//     newRow.insertCell(10).textContent = ''; // Ngày ĐK
//     newRow.insertCell(11).textContent = ''; // Trạng thái LHP

// }
let stt = 1;
function addCourse() {
  // 1. Retrieve Modal Element (Error Handling)
      var courseID = document.querySelector("courseID").value;       
      var name = document.querySelector("courseName").value;       
      var credits = document.querySelector("credits").value;
       
      
      const tableBody = document.querySelector('#tb-course tbody'); // Replace with your selector
      const newRow = tableBody.insertRow();
      
        // Thêm các ô cho hàng mới
        newRow.insertCell(0).textContent = '';
        newRow.insertCell(1).textContent = courseID;
        newRow.insertCell(2).textContent = name;
        newRow.insertCell(3).textContent = credits;

    return true;
  
}
function loadListOfAddedClass() {
  courseID = document.getElementById('courseID').textContent;
  courseID = document.getElementById('courseName').textContent;
  courseID = document.getElementById('credits').textContent;
  console.log(registerAPI + '?studentID=' + studentID + '&semester=' + semester + '&year=' + year);
  fetch(registerAPI + '?studentID=' + studentID + '&semester=' + semester + '&year=' + year)
  .then(function(response) {
      return response.json();
  })
  .then(function(registers) {
      var table = document.querySelector('#tb-register');
      var tbody = table.querySelector('tbody');

      tbody.innerHTML = '';

      registers.forEach((register, index) => {
          const row = tbody.insertRow();
          row.insertCell(0).textContent = "";
          row.insertCell(1).textContent = index + 1;
          row.insertCell(2).textContent = register.enrollmentID;
          row.insertCell(3).textContent = "";
          row.insertCell(4).textContent = register.name;
          row.insertCell(5).textContent = register.nameClass;
          row.insertCell(6).textContent = register.nameCourse;
          row.insertCell(7).textContent = register.fee;
          row.insertCell(8).textContent = register.paymentDeadline;
          row.insertCell(9).textContent = register.paymentStatus;
          row.insertCell(10).textContent = register.dateApply;
          row.insertCell(11).textContent = register.status;
      });
  })
}

loadListOfRegisteredClass();


let intervalId = null; // To store the interval ID

function loadListOfPrerequisiteRelationship() {
  // Add event listeners for checkbox clicks (optional)
  const checkboxHocTruoc = document.getElementById('hocTruoc');
  const checkboxTienQuyet = document.getElementById('tienQuyet');
  const checkboxSongHanh = document.getElementById('songHanh');

  const hocTruocInputDiv = document.getElementById('hocTruocInputDiv');
  const tienQuyetInputDiv = document.getElementById('tienQuyetInputDiv');
  const songHanhInputDiv = document.getElementById('songHanhInputDiv');

  // Check checkbox states and toggle input visibility every 100 milliseconds
  intervalId = setInterval(() => {
    // Function to show input when checkbox is checked
    function showInput(checkbox, inputDiv) {
      if (checkbox.checked) {
        inputDiv.style.display = 'flex';
      } else {
        inputDiv.style.display = 'none';
      }
    }

    // Apply showInput function to each checkbox-input pair
    showInput(checkboxHocTruoc, hocTruocInputDiv);
    showInput(checkboxTienQuyet, tienQuyetInputDiv);
    showInput(checkboxSongHanh, songHanhInputDiv);
  }, 100);
}

// Start the continuous checking
loadListOfPrerequisiteRelationship();

// Function to stop the continuous checking (if needed)
function stopContinuousChecking() {
  clearInterval(intervalId);
  intervalId = null;
}