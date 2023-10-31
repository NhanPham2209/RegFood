$(document).ready(function(){
    
    $("#submit").click(function(){

        var email_input = $("#email").val();
         var password = $("#password").val();
        $.ajax({
            method: "POST",
            url: "http://localhost:8080/login/user-signin",
            data: { email: email_input,
                    passWord: password 
                    }
          })
            .done(function( msg ) {
                if(msg.success){
                    
                    swal({
                        text: "Change Password successfully",
                        icon: "success",
                        button: "OK",
                      }).then((ok) => {
                        // Kiểm tra khi nút "OK" được bấm
                        if (ok) {
                          
                            window.location.href="./index.html"
                        }
                      });
                   
                    localStorage.setItem("token",msg.data) // khi đăng nhập thành công lưu lại token bằng localStorage
                    // window.location.href="./index.html"
                }else{
                    swal("Sai tài khoản hoặc mật khẩu");
                }
              
            });

            $.ajax({
                method: "GET",
                url: "http://localhost:8080/login/user-info/"+email_input,
                dataType: 'json',
              })
                .done(function(result ) {
                    
                    // var userInfo = JSON.parse(localStorage.getItem('userInfo')) || [];
                   var  userInfo = {userId : result.data.id, fullName: result.data.userName, email : result.data.email};
                    localStorage.setItem('userInfo', JSON.stringify(userInfo)); 
                });
    })
})