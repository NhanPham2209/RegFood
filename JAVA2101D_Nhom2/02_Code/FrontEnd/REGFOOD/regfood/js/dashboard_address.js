$(document).ready(function(){
    var token = localStorage.getItem("token");
    var userInfo = JSON.parse(localStorage.getItem('userInfo'))
    
    //  if(token == null){
        
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
        var addressHtml = ``;
        var avatarHtml = `<img src="http://localhost:8080/index/restaurant/${value.avatar}" alt="user" class="img-fluid w-100">`;
        $("#avatar").prepend(avatarHtml);
        $.each(value.addressDTOs, function(index,data){
           addressHtml += `<div class="col-md-6">
                                <div class="tf__checkout_single_address">
                                    <div class="form-check">
                                        <label class="form-check-label">
                                            <span class="icon"><i class="fas fa-home"></i>
                                                ${data.title}</span>
                                            <span class="address">${data.addressDetail}</span>
                                        </label>
                                    </div>
                                    
                                </div>
                            </div>` 
        })
        $("#list-address").append(addressHtml);
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

    $("#add-new-address").click(function() {

        if($("#address-title").val()==""){
            swal("Do not leave the title blank")
        }else if($("#address-detail").val()==""){
            swal("Do not leave the detailed address blank");
        }else{
            var addressRequest = {
                title : $("#address-title").val(),
                addressDetail: $("#address-detail").val(),
                userId : userInfo.userId
            }
            $.ajax({
                url: "http://localhost:8080/address/add-address" ,
                    method: "POST",
                    data: JSON.stringify(addressRequest),
                    contentType: "application/json;charset=utf-8",
                    dataType:"JSON",
                    headers: {
                        'Authorization' : `Bearer ${token}`
                    }
            }).done(function(result){
               if(result.data){
               
                swal({
                    text: "add address successfully",
                    icon: "success",
                    button: "OK",
                  }).then((ok) => {
                    // Kiểm tra khi nút "OK" được bấm
                    if (ok) {
                      
                        window.location.href="./dashboard_address.html"
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
                      
                        window.location.href="./sign_in.html"
                    }
                  });
               
            })
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