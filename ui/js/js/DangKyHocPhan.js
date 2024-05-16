var dkhpAPI = 'http://localhost:8081/dangkyhocphan';
var studentAPI = 'http://localhost:8081/students';
var lopHPAPI = 'http://localhost:8081/dangkyhocphan/lophocphan';
var detailAPI = 'http://localhost:8081/dangkyhocphan/lophocphan/chitietlophocphan';
var lopHPDaDangKyAPI = 'http://localhost:8081/dangkyhocphan/lophocphan/sinhvien';
var registerAPI = 'http://localhost:8081/dangkyhocphan/lophocphan/sinhvien/dangky';

// lấy thông tin sinh viên từ localStorage
var studentID = localStorage.getItem("studentID");
document.getElementById('sv-mssv').textContent = studentID;

// load thông tin sinh viên
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

// load danh sách môn học
function loadListOfCourse() {
    studentID = document.getElementById('sv-mssv').textContent;
    year = new Date().getFullYear();
    semester = document.getElementById('semester').value.slice(2, 3);
    console.log(dkhpAPI + '?studentID=' + studentID + '&semester=' + semester + '&year=' + year);
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

// chọn môn học
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

// lấy danh sách môn học theo học kỳ
var choiceSemester = document.getElementById("semester");
choiceSemester.addEventListener("change", function() {
    loadListOfCourse();
    loadListOfRegisteredClass();
});

var thucHanh = [];
// chọn lớp học phần
function choiceClass(enrollmentID) {
    fetch(detailAPI + '?enrollmentID=' + enrollmentID)
    .then(function(response) {
        return response.json();
    })
    .then(function(detail) {
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
            row.classList.add('active');
        }

        for (var i = 0; i < detail.enrollmentPs.length; i++) {
            thucHanh.push(detail.enrollmentPs[i]);
            const row = tbody.insertRow();
            row.insertCell(0).textContent = i + 1 + detail.scheduleStudy.length;
            row.insertCell(1).textContent = 'TH - Thứ ' + detail.enrollmentPs[i].scheduleStudy.dayOfWeek + ' (T' + detail.enrollmentPs[i].scheduleStudy.classesStart + ' - T' + detail.enrollmentPs[i].scheduleStudy.classesEnd + ')';
            row.insertCell(2).textContent = detail.enrollmentPs[i].name;
            row.insertCell(3).textContent = detail.enrollmentPs[i].room;
            row.insertCell(4).textContent = detail.enrollmentPs[i].nameInstructor;
            row.insertCell(5).textContent = detail.dateApplyStart + ' - ' + detail.dateApplyEnd;
        }
    })
}

var nhomTH = "";
// chọn nhóm thực hành
document.getElementById('thuchanh').addEventListener('change', function() {
    var choiceNhom = this.value;
    console.log(choiceNhom);

    var tableRows = document.querySelectorAll('#tb-detail tbody tr');
    tableRows.forEach(function(row) {
        var nhomTHValue = row.cells[2].textContent.trim().slice(5);
        if (nhomTHValue === '' || nhomTHValue === choiceNhom) {
            row.classList.add('active');
            thucHanh.forEach(function(item) {
                if (choiceNhom === item.name.slice(5)) {
                    nhomTH = item.enrollmentPID;
                }
            });
        } else {
            row.classList.remove('active');
        }
    });
});

// xử lý đăng ký học phần
function register() {
    var activeCourse = document.querySelector('#tb-course tr.active');
    var activeClass = document.querySelector('#tb-class tr.active');
    var activeDetail = document.querySelector('#tb-detail tr.active');
    var tableDetail = document.querySelector('#tb-detail'); 
    
    if (!activeCourse) {
        alert('Vui lòng chọn môn học');
        return;
    }

    if (!activeClass) {
        alert('Vui lòng chọn lớp học phần');
        return;
    }

    var activeRowCount = 0;
    if (tableDetail.rows.length > 1) {
        for (var i = 0; i < tableDetail.rows.length; i++) {
            if (tableDetail.rows[i].classList.contains("active")) {
                activeRowCount++;
            }
        }
        if (activeRowCount === 1) {
            alert('Vui lòng chọn nhóm thực hành');
            return;
        }
    }

    var codePractive = nhomTH;

    if(new Date().getMonth() < 10) {
        var dateApply = new Date().getFullYear() + '-0' + new Date().getMonth() + '-' + new Date().getDate();
    }
    else if (new Date().getDate() < 10) {
        var dateApply = new Date().getFullYear() + '-' + new Date().getMonth() + '-0' + new Date().getDate();
    } else {
        var dateApply = new Date().getFullYear() + '-' + new Date().getMonth() + '-' + new Date().getDate();
    }

    var enrollment = {
        "studentID": studentID,
        "dateApply": dateApply,
        "codePractive": codePractive,
        "enrollmentID": activeClass.cells[1].textContent,
    }

    console.log(enrollment);

    fetch(registerAPI, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(enrollment)
    })
        .then((res) => {
            return res.text();
        })
        .then((response) => {
            // window.location.reload();
            loadListOfRegisteredClass();
        })
}

// load danh sách lớp học phần đã đăng ký
function loadListOfRegisteredClass() {
    studentID = document.getElementById('sv-mssv').textContent;
    year = new Date().getFullYear();
    semester = document.getElementById('semester').value.slice(2, 3);
    console.log(lopHPDaDangKyAPI + '?studentID=' + studentID + '&semester=' + semester + '&year=' + year);
    fetch(lopHPDaDangKyAPI + '?studentID=' + studentID + '&semester=' + semester + '&year=' + year)
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
            row.insertCell(3).textContent = register.nameCourse;
            row.insertCell(4).textContent = register.name;
            row.insertCell(5).textContent = register.credit;
            if (register.codePractice === null) {
                row.insertCell(6).textContent = "";
            } else {
                row.insertCell(6).textContent = register.codePractice.slice(4);
            }
            row.insertCell(7).textContent = register.fee;
            row.insertCell(8).textContent = register.paymentDeadline;
            row.insertCell(9).textContent = register.paymentStatus;
            row.insertCell(10).textContent = register.dateApply;
            row.insertCell(11).textContent = register.status;
        });
    })
}

loadListOfRegisteredClass();
