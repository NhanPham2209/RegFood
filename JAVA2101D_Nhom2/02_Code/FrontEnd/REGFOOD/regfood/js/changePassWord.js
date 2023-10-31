$(document).ready(function(){
    var token = localStorage.getItem("token");
    var userInfo = JSON.parse(localStorage.getItem('userInfo'))
    // hàm tính tổng số lượng 
    
    var cart = JSON.parse(localStorage.getItem('cart')) || [];
    function calculateTotalQuantity() {
        var totalQuantity = 0;
        for (var i = 0; i < cart.length; i++) {
            totalQuantity += cart[i].quantity;
        }
        return totalQuantity;
    }
   
    $("#cart_view_quantity").text(calculateTotalQuantity());
    // if(token == null){
        
    //     window.location.href="./sign_in.html"
    // }

    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/user?email="+userInfo.email,
        contentType: "application/json;charset=utf-8",
        dataType:"JSON",
        headers: {
            'Authorization' : `Bearer ${token}`
        }
        
    }).done(function(msg) { 
       
      if(msg.success){
        var value = msg.data;

        var avatarHtml = `<img src="http://localhost:8080/index/restaurant/${value.avatar}" alt="user" class="img-fluid w-100">`;
        $("#avatar").prepend(avatarHtml);
        $("#fullName").append(value.userName);
        

        
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

   $("#changePassWord").click(function(){
    var oldPass = $("#oldPass").val();
    var newPass = $("#newPass").val();
    var confirmPass = $("#confirmNewPass").val();
    var changePassWordRequest = {
        email : userInfo.email,
        oldPass: oldPass,
        newPass : newPass
    }

    if(oldPass==""){
        swal("Do not leave the Current Password blank");
    }else if(newPass==""){
        swal("Do not leave the New Password blank");
    }else if(confirmPass==""){
        swal("Do not leave the Confirm Password blank");
    }
    if(newPass == confirmPass){
        $.ajax({
            type: 'POST',
            url: "http://localhost:8080/user/change-password",
            data: JSON.stringify(changePassWordRequest),
            contentType: "application/json;charset=utf-8",
            dataType:"JSON",
            headers: {
                'Authorization' : `Bearer ${token}`
            }
            
        }).done(function(msg) { 
           
          if(msg.data){
            swal({
                text: "Change Password successfully",
                icon: "success",
                button: "OK",
              }).then((ok) => {
                // Kiểm tra khi nút "OK" được bấm
                if (ok) {
                  
                  location.reload();
                }
              });

          }
    
        })
    }else{
        swal("New password and confirm password must match");
    }

   })

   $("#logout").click(function(){
    localStorage.removeItem("cart");
    localStorage.removeItem("order");
    localStorage.removeItem("userInfo");
    localStorage.removeItem("token");
    window.location.href = 'sign_in.html';
})
})