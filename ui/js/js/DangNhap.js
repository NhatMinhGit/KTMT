var accountNormalAPI = 'http://localhost:8081/accountNormal';


function login(){
    console.log("login");
    // event.preventDefault();
    var uname = document.getElementById("UserName").value;
    var pw = document.getElementById("Password").value; 
    
    if(uname == "" || pw == ""){
        return;
    } else {
        fetch(accountNormalAPI + '/' + uname)
        .then(function(response){
            return response.json();
        })
        .then(function(account){
            console.log(account);
            if(account == null){
                alert("Tài khoản không tồn tại")
            } else if(uname == account.username && pw == account.password){
                localStorage.setItem('studentID',uname);
                window.location.href="dashboard.html"
            } else if(uname != account.username || pw != account.password){
                alert("Tên hoặc mật khẩu không chính xác")
            }
        });
    }
}