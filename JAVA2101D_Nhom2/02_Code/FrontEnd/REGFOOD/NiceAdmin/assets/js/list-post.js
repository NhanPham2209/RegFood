$(document).ready(function(){
    var adminToken = localStorage.getItem("adminToken");
    var adminInfo = JSON.parse(localStorage.getItem('adminInfo')) || [];
    $("#userName").text(adminInfo.fullName);

    if(adminToken == null){
        
        window.location.href="./pages-login.html"
    }

    $.ajax({
        method: 'GET',
        url: "http://localhost:8080/post",
        contentType: "application/json;charset=utf-8",
        dataType:"JSON"
        
        // headers: {
        //     'Authorization' : `Bearer ${adminToken}`
        // }
    }).done(function(msg){
        if(msg.success){
            var postHtml = ``;
            var value = msg.data;
            $.each(value, function(index,data){
                postHtml += `  <tr>
                                    <td>${data.title}</td>
                                    <td><img src="http://localhost:8080/index/restaurant/${data.image}" alt="" style="max-width: 100px"></td>                     
                                    <td>${data.userDTO.userName}</td>
                                    <td><a href="edit-post.html?postId=${data.id}"><button type="button" class="btn btn-warning"><i class="bi bi-pencil-square"></i></button></a> | <button type="button" class="btn btn-danger deletePost" data-id="${data.id}"><i class="bx bx-x"></i></button></td>
                                </tr>`
            })
            $("#list-post").append(postHtml);
            $(".deletePost").click(function(){
                var postId = $(this).data('id');
                swal({
                    title: "Are you sure?",
                    text: "Sau khi xóa, bạn sẽ không thể khôi phục ",
                    icon: "warning",
                    buttons: true,
                    dangerMode: true,
                  })
                  .then((willDelete) => {
                    if (willDelete) {
                        $.ajax({
                            type: 'POST',
                            url: "http://localhost:8080/post/delete-post/"+postId,
                            contentType: "application/json;charset=utf-8",
                            dataType:"JSON"
                            // headers: {
                            //     'Authorization' : `Bearer ${adminToken}`
                            // }
                        }).done(function(result){
                            swal({
                                text: "Xóa thành công",
                                icon: "success",
                                button: "OK",
                              }).then((ok) => {
                                // Kiểm tra khi nút "OK" được bấm
                                if (ok) {
                                  // Chuyển hướng sau khi bấm nút "OK"
                                  location.reload();
                                }
                              });
                              
                            
                        })  
                     
                    } 
                  });
            })
        }
    })

    
    $("#log-out").click(function(){
        localStorage.removeItem("adminInfo");
        localStorage.removeItem("adminToken");
        window.location.href = './pages-login.html';
    })

})