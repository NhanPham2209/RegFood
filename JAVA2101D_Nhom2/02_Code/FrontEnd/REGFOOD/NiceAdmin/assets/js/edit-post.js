
$(document).ready(function(){
    var adminToken = localStorage.getItem("adminToken");
    let searchParam = new URLSearchParams(window.location.search);
    var postId = searchParam.get('postId')  ;
    var adminInfo = JSON.parse(localStorage.getItem('adminInfo')) || [];
    $("#userName").text(adminInfo.fullName);

    if(adminToken == null){
        
        window.location.href="./pages-login.html"
    }
   
    
    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/post/getPost?postId="+postId,
        contentType: "application/json;charset=utf-8",
        dataType:"JSON",
        headers: {
            'Authorization' : `Bearer ${adminToken}`
        }
    }).done(function(result){
        if(result.success){
            var image = `<img src="http://localhost:8080/index/restaurant/${result.data.image}" alt="Image 1" style="max-width: 20%;">`
           $("#title").val(result.data.title);
           $("#content").val(result.data.content);
           $("#imagePreviews").append(image);

        }
         
       
    }).fail(function(){
        swal({
            text: "Please login",
            icon: "error",
            button: "OK",
          }).then((ok) => {
            // Kiểm tra khi nút "OK" được bấm
            if (ok) {
              
                window.location.href="./pages-login.html"
            }
          });
       
    })

    $("#btn-edit-post").click(function(){
        var title = $("#title").val();
        var content = $("#content").val();
        var userId = adminInfo.userId;
        var file = $('#formFile')[0].files[0];
        if(title==""){
            swal("Không để trống tiêu đề");
        }else if(content==""){
            swal("Không để trống nội dung");

        }else{
            var formData = new FormData();
            formData.append('postId',postId);
            formData.append('file',file);
            formData.append('title',title);
            formData.append('userId',userId);
            formData.append('content', content);
            $.ajax({
                method: 'POST',
                    url: "http://localhost:8080/post/edit-post",
                    data: formData,
                    contentType: false,
                    processData: false,
                    headers: {
                        'Authorization' : `Bearer ${adminToken}`
                    }
                
            }).done(function(msg){
                if(msg.data){
                    swal({
                        text: "Sửa bài viết thành công",
                        icon: "success",
                        button: "OK",
                    }).then((ok) => {
                        // Kiểm tra khi nút "OK" được bấm
                        if (ok) {
                        // Chuyển hướng sau khi bấm nút "OK"
                        window.location.href = 'list-post.html';
                        }
                    });
                }
            }).fail(function(){
                swal({
                    text: "Please login",
                    icon: "error",
                    button: "OK",
                  }).then((ok) => {
                    // Kiểm tra khi nút "OK" được bấm
                    if (ok) {
                      
                        window.location.href="./pages-login.html"
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