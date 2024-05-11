var studentAPI = 'http://localhost:8081/students';

// Lấy thông tin sinh viên từ Local Storage
var studentID = localStorage.getItem("studentID");
document.getElementById('sv-mssv').textContent = studentID;

function redirectToDKHP() {
    var studentID = document.getElementById('sv-mssv').textContent;
    localStorage.setItem('studentID', studentID);
    window.location.href = 'DangKyHocPhan.html';
}

fetch(studentAPI + '/' + studentID)
    .then(function(response) {
        return response.json();
    })
    .then(function(student) {
        document.getElementById('sv-hoten').textContent = student.name;
        const sexText = student.sex ? 'Nam' : 'Nữ';
        document.getElementById('avatar').src = student.img;
        document.getElementById('sv-khoahọc').textContent = student.academicYear;
        document.getElementById('sv-gioitinh').textContent = sexText;
        document.getElementById('sv-nganh').textContent = student.nameMajor;
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

