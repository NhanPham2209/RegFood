$(document).ready(function(){
    var adminToken = localStorage.getItem("adminToken");
    var adminInfo = JSON.parse(localStorage.getItem('adminInfo')) || [];
    $("#userName").text(adminInfo.fullName);
    if(adminToken == null){
        
        window.location.href="./pages-login.html"
    }
    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/restaurant",
        contentType: "application/json;charset=utf-8",
        dataType:"JSON"
        
    }).done(function(msg){
        if(msg.success){
            var value = msg.data;
            var avatarHtml = `<img src="http://localhost:8080/index/restaurant/${value.imageLogo}" alt="Profile" class="rounded-circle" id="imageElement" style="max-width:300px">`
            $("#res-info").prepend(avatarHtml);
            $("#res-name").text(value.restaurantName);
            $("#res-numberPhone").text(value.numberPhone);
            $("#res-email").text(value.email);
            $("#res-address").text(value.address);
            $("#res-description").text(value.description);

            $("#edit-name").val(value.restaurantName);
            $("#edit-address").val(value.address);
            $("#edit-numberPhone").val(value.numberPhone);
            $("#edit-email").val(value.email);
            $("#edit-description").val(value.description);    
        }
    })
    
    $('#fileInput').on('change', function() {
       
        var selectedFile = this.files[0];
        
       
        if (selectedFile) {
            // Tạo đối tượng FormData để chứa ảnh
            var formData = new FormData();
            formData.append('file', selectedFile);
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/restaurant/change-avatar', 
                data: formData,
                processData: false,
                contentType: false,
                
                
                
                
               
            }).done(function(result){
                location.reload();
            })
        }
    });
    $("#btn-edit").click(function(){
        var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
        var phonePattern = /^(0[234][0-9]{8}|1[89]00[0-9]{4})$/;
       var resName =  $("#edit-name").val();
        var resAddress= $("#edit-address").val();
        var resNumberPhone= $("#edit-numberPhone").val();
       var resEmail =  $("#edit-email").val();
        var resDescription= $("#edit-description").val(); 
        
        if(resName==""){
            swal("Không để trống tên nhà hàng");
        }else if(resAddress==""){
            swal("Không để trống địa chỉ nhà hàng");
        }else if(resNumberPhone==""){
            swal("Không để trống số điện thoại nhà hàng");
        }else if(phonePattern.test(resNumberPhone)===false){
            swal("Số điện thoại không hợp lệ");

        }
        else if(resEmail==""){
            swal("Không để trống email nhà hàng");
        }else if(emailPattern.test(resEmail)===false){
            swal("email không hợp lệ !");

        }else if(resDescription==""){
            swal("Không để trống mô tả nhà hàng");

        }
        else{
            $.ajax({
                type: 'POST',
                url: "http://localhost:8080/restaurant/edit-restaurant",
                data:{
                    restaurantName : resName,
                    address : resAddress,
                    numberPhone : resNumberPhone,
                    email : resEmail,
                    description : resDescription,
                }
                
                
            }).done(function(msg){
                if(msg.data){
                    swal({
                        text: "Cập nhật thông tin thành công",
                        icon: "success",
                        button: "OK",
                      }).then((ok) => {
                        // Kiểm tra khi nút "OK" được bấm
                        if (ok) {
                          // Chuyển hướng sau khi bấm nút "OK"
                          location.reload();
                        }
                      });
                }
            })
        }
    })
    $("#log-out").click(function(){
        localStorage.removeItem("adminInfo");
        localStorage.removeItem("adminToken");
        window.location.href = './pages-login.html';
    })
})