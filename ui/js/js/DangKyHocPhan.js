var dkhpAPI = 'http://localhost:8081/dangkyhocphan';
var studentAPI = 'http://localhost:8081/students';

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
                alert('Bạn đã nhấn vào dòng ' + (index + 1));
            });
        });
    })
    .catch(function (error) {
        console.error('Error fetching data:', error);
    });
}

dangKyHocPhan();

var choiceSemester = document.getElementById("semester");
choiceSemester.addEventListener("change", function() {
    var selectedValue = choiceSemester.value.slice(2, 3);
    dangKyHocPhan();
});