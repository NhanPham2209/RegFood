$(document).ready(function(){
    var token = localStorage.getItem("token");
    var userInfo = JSON.parse(localStorage.getItem('userInfo'))
    
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

    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/user/list-rating-food?userId="+userInfo.userId,
        contentType: "application/json;charset=utf-8",
        dataType:"JSON",
        headers: {
            'Authorization' : `Bearer ${token}`
        }
        
    }).done(function(msg) { 
       
      if(msg.success){
        var value = msg.data;
        reviewHtml = ``;
        $.each(value, function(index,data){
            reviewHtml += `<div class="tf__single_comment">
                                <img src="http://localhost:8080/index/restaurant/${data.foodDTO.foodImageDTOs[0].imageName}" alt="review" class="img-fluid">
                                <div class="tf__single_comm_text">
                                    <h3><a href="#">${data.foodDTO.foodName}</a> <span>${data.createdDate}</span>
                                    </h3>
                                    <span class="rating">
                                        
                                     `
                                    for(var i=0;i<data.ratingPoint;i++){
                                        reviewHtml += `<i class="fas fa-star"></i>`
                                    }
                                     
                                    reviewHtml +=    
                                    `</span>
                                    <p>${data.content}</p>
                                    <span class="status active">active</span>
                                </div>
                            </div>`
        })
        
        $("#list-review").prepend(reviewHtml);
        
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

    $("#logout").click(function(){
        localStorage.removeItem("cart");
        localStorage.removeItem("order");
        localStorage.removeItem("userInfo");
        localStorage.removeItem("token");
        window.location.href = 'sign_in.html';
    })
})