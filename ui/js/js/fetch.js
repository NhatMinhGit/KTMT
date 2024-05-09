var postApi = 'http://localhost:8081/students';

fetch(postApi,{
    method: 'GET',
    mode: 'cors',
    headers: {
        'Content-Type': 'application/json',
    }})
    //case lấy postApi thành công
    .then(function(response) {
        //nhờ có fetch : response.json() nhận json postApi của promise chuyển đổi dữ liệu từ json -> js
        // là 1 array gồm nhiều bản ghi về post( bài viết)
        return response.json();
    })
    .then(function(posts) {
        console.log(posts);
        let htmls = posts.map(function(post) {
            return `<li>
                <h2>${post.id}</h2>
                <p>${post.name}</p>
                <p>${post.sex}</p>
                <p>${post.cccd}</p>
                <p>${post.nameClazz}</p>
                <p>${post.nameMajor}</p>
            </li>`;
        });

        let html = htmls.join('');
        document.getElementById('postBlock').innerHTML = html;
    })
    //case lấy postApi thất bại
    .catch(function(err) {
        console.log(err);
    })
