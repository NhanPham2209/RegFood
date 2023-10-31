$(document).ready(function(){
    var token = localStorage.getItem("token");
    var userInfo = JSON.parse(localStorage.getItem('userInfo'))
    // if(token == null){
        
    //     window.location.href="./sign_in.html"
    // }
    

    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/user?email="+userInfo.email,
        headers: {
            'Authorization' : `Bearer ${token}`
        },
        contentType: "application/json;charset=utf-8",
        dataType:"JSON",
        
        
    }).done(function(msg) { 
       
      if(msg.success){
        var value = msg.data;

        var avatarHtml = `<img src="http://localhost:8080/index/restaurant/${value.avatar}" alt="user" class="img-fluid w-100">`;
        $("#avatar").prepend(avatarHtml);
        $("#name").append(value.userName);
        $("#fullName").append(value.userName);
        $("#email").append(value.email);
        $("#phone").append(value.numberPhone)

        
      }

    }).fail(function(){
        swal({
            text: "Please login",
            icon: "error",
            button: "OK",
          }).then((ok) => {
            // Kiểm tra khi nút "OK" được bấm
            if (ok) {
              
                window.location.href="./sign_in.html"
            }
          });
       
    });
    $('#upload').on('change', function() {
       
        var selectedFile = this.files[0];

       
        if (selectedFile) {
            // Tạo đối tượng FormData để chứa ảnh
            var formData = new FormData();
            formData.append('file', selectedFile);
            formData.append('email',userInfo.email)
            

           
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/user/change-avatar', 
                data: formData,
                processData: false,
                contentType: false,
                headers: {
                    'Authorization' : `Bearer ${token}`
                }
                
                
                
               
            }).done(function(result){
                location.reload();
            }).fail(function(){
                swal({
                    text: "Please login",
                    icon: "error",
                    button: "OK",
                  }).then((ok) => {
                    // Kiểm tra khi nút "OK" được bấm
                    if (ok) {
                      
                        window.location.href="./sign_in.html"
                    }
                  });
               
            })
        }
    });

    $("#logout").click(function(){
        window.location.href = './sign_in.html';
        localStorage.removeItem("cart");
        localStorage.removeItem("order");
        localStorage.removeItem("userInfo");
        localStorage.removeItem("token");
       
    })
})