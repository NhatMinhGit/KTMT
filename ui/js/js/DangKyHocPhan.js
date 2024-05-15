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

function dangKyHocPhan() {
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

dangKyHocPhan();

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
    dangKyHocPhan();
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


function register() {
    var activeClass = document.querySelector('#tb-class tr.active');
    var activeCourse = document.querySelector('#tb-course tr.active');
    var activeDetail = document.querySelector('#tb-detail tr.active');
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

    var enrollmentID = activeClass.cells[1].textContent;
    console.log(enrollmentID);

    var tableRegister = document.querySelector('#tb-register tbody');
    var newRow = tableRegister.insertRow();

    // Thêm các ô cho hàng mới
    newRow.insertCell(0).textContent = '';
    newRow.insertCell(1).textContent = '';
    newRow.insertCell(2).textContent = enrollmentID;
    newRow.insertCell(3).textContent = activeCourse.cells[2].textContent;
    newRow.insertCell(4).textContent = activeClass.cells[3].textContent;
    newRow.insertCell(5).textContent = activeCourse.cells[2].textContent;
    newRow.insertCell(6).textContent = activeDetail.cells[2].textContent;
    newRow.insertCell(7).textContent = ''; // Học phí
    newRow.insertCell(8).textContent = ''; // Hạn nộp
    newRow.insertCell(9).textContent = ''; // Thu
    newRow.insertCell(10).textContent = ''; // Ngày ĐK
    newRow.insertCell(11).textContent = ''; // Trạng thái LHP

}

fetch(registerAPI + '?studentID=' + studentID)
    .then(function(response) {
        return response.json();
    })
    .then(function(classes) {
        var table = document.querySelector('#tb-register');
        var tbody = table.querySelector('tbody');

        tbody.innerHTML = '';

        classes.forEach((lop, index) => {
            const row = tbody.insertRow();
            row.insertCell(0).textContent = ""; 
            row.insertCell(1).textContent = index + 1; 
            row.insertCell(2).textContent = lop.enrollmentID;
            row.insertCell(3).textContent = lop.name;
            row.insertCell(4).textContent = lop.name;
            row.insertCell(5).textContent = lop.quantity;
            row.insertCell(6).textContent = "";
            row.insertCell(7).textContent = "";
            row.insertCell(8).textContent = "";
            row.insertCell(9).textContent = "";
            row.insertCell(10).textContent = "";
            row.insertCell(11).textContent = "";
        });
    })