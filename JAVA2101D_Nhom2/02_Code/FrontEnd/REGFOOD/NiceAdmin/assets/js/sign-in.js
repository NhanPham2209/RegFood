$(document).ready(function(){
    
    $("#submit").click(function(){

        var email_input = $("#yourEmail").val();
         var password = $("#yourPassword").val();
        $.ajax({
            method: "POST",
            url: "http://localhost:8080/login/admin-signin",
            data: { email: email_input,
                    passWord: password 
                    }
          })
            .done(function( msg ) {
                if(msg.success){
                    
                    swal({
                        text: "Đăng nhập thành công",
                        icon: "success",
                        button: "OK",
                      }).then((ok) => {
                        // Kiểm tra khi nút "OK" được bấm
                        if (ok) {
                          
                            window.location.href="./index.html"
                        }
                      });
                   
                    localStorage.setItem("adminToken",msg.data) // khi đăng nhập thành công lưu lại token bằng localStorage
                    // window.location.href="./index.html"
                }else{
                    swal("Sai tài khoản hoặc mật khẩu");
                }
              
            }).fail(function(){
                swal({
                    text: "Tài khoản không tồn tại ",
                    icon: "error",
                    button: "OK",
                  }).then((ok) => {
                    // Kiểm tra khi nút "OK" được bấm
                    if (ok) {
                      
                        window.location.href="./pages-login.html"
                    }
                  });
               
            });

            $.ajax({
                method: "GET",
                url: "http://localhost:8080/login/user-info/"+email_input,
                dataType: 'json',
              })
                .done(function(result ) {
                    
                    // var adminInfo = JSON.parse(localStorage.getItem('adminInfo')) || [];
                   var  adminInfo = {userId : result.data.id, fullName: result.data.userName, email : result.data.email};
                    localStorage.setItem('adminInfo', JSON.stringify(adminInfo)); 
                });
    })
})