var studentAPI = 'http://localhost:8081/students';

// Lấy thông tin sinh viên từ Local Storage
var studentID = localStorage.getItem("studentID");
document.getElementById('sv-mssv').textContent = studentID;

fetch(studentAPI + '/' + studentID, {
    method: 'GET',
    mode: 'cors',
    headers: {
        'Content-Type': 'application/json',
    }
})
    .then(function(response) {
        return response.json();
    })
    .then(function(student) {
        console.log(student);
        document.getElementById('user-account-avatar').src = student.img;
        document.getElementById('user-account-name').textContent = student.name;
        document.getElementById('image-profile').src = student.img;
        document.getElementById('sv-lophoc').textContent = student.nameClazz;
        document.getElementById('sv-hoten').textContent = student.name;
        document.getElementById('sv-khoahoc').textContent = student.academicYear;
        const sexText = student.sex ? 'Nam' : 'Nữ';
        document.getElementById('sv-gioitinh').textContent = sexText;
        document.getElementById('sv-quoctich').textContent = student.nationality;
        document.getElementById('sv-noisinh').textContent = 'Tỉnh ' + student.contact.address;
        document.getElementById('sv-nganh').textContent = student.nameMajor;
})
