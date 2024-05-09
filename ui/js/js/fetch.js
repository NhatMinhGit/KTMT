var postApi = 'https://jsonplaceholder.typicode.com/posts';

fetch(postApi)
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
                <h2>${post.title}</h2>
                <p>${post.body}</p>
            </li>`;
        });

        let html = htmls.join('');
        document.getElementById('postBlock').innerHTML = html;
    })
    //case lấy postApi thất bại
    .catchn(function(err) {
        console.log(err);
    })
