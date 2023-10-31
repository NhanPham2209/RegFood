$(document).ready(function(){
    
    $("#submit").click(function(){
        var emailPattern = /^[A-Za-z][\w$.]+@[\w]+\.\w+$/;
        var phonePattern = /^(0[234][0-9]{8}|1[89]00[0-9]{4})$/;
        var email_input = $("#yourEmail").val();
         var password = $("#yourPassword").val();
         var fullname = $("#yourName").val();
         var numberphone = $("#yourNumberPhone").val();
       
         
        if(fullname=="" ){
            swal("không để trống họ tên");
        }else if(email_input==""){
            swal("không để trống email");
        }
        else if(numberphone==""){
            swal("không để trống số điện thoại");
        }
        else if(password==""){
            swal("không để trống mật khẩu");
        }else{
            if(emailPattern.test(email_input)===false){
                swal("email ko hợp lệ");
            }
            else if(phonePattern.test(numberphone)===false){
                swal("số điện thoại ko hợp lệ");
            }else{
                var signupRequest = {
                    fullName: fullname,
                    email : email_input,
                    numberPhone :numberphone,
                    passWord : password
                 }
                $.ajax({
                    method: "POST",
                    url: "http://localhost:8080/login/admin-signup",
                    data: JSON.stringify(signupRequest),
                    contentType: "application/json;charset=utf-8",
                        dataType:"JSON"
                  })
                    .done(function( msg ) {
                        if(msg.data){
                            swal({
                                text: "Đăng kí thành công",
                                icon: "success",
                                button: "OK",
                              }).then((ok) => {
                                // Kiểm tra khi nút "OK" được bấm
                                if (ok) {
                                  // Chuyển hướng sau khi bấm nút "OK"
                                  window.location.href = 'pages-login.html';
                                }
                              });
                        }else{
                            swal("Tài khoản đã tồn tại");
                        }
                      
                    });
               
            }
        }
      
    })
})