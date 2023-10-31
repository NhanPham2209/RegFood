$(document).ready(function(){
    
    $("#submit").click(function(){

        var email_input = $("#email").val();
         var password = $("#password").val();
         var fullname = $("#fullName").val();
         var numberphone = $("#numberPhone").val();
         var signupRequest = {
            fullName: fullname,
            email : email_input,
            numberPhone :numberphone,
            passWord : password
         }
        if(fullname=="" ){
            swal("không để trống họ tên");
        }else if(email_input==""){
            swal("không để trống email");
        }else if(numberphone==""){
            swal("không để trống số điện thoại");
        }else if(password==""){
            swal("không để trống mật khẩu");
        }else{
             $.ajax({
            method: "POST",
            url: "http://localhost:8080/login/user-signup",
            data: JSON.stringify(signupRequest),
            contentType: "application/json;charset=utf-8",
                dataType:"JSON"
          })
            .done(function( msg ) {
                if(msg.data){
                    swal("Đăng kí thành công", "You clicked the button!", "success");
                   
                    window.location.href="./sign_in.html"
                }else{
                    swal("Tài khoản đã tồn tại");
                }
              
            });
        }
      
    })
})