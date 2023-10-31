$(document).ready(function(){
    var adminToken = localStorage.getItem("adminToken");
    var adminInfo = JSON.parse(localStorage.getItem('adminInfo')) || [];
    $("#userName").text(adminInfo.fullName);
    // if(adminToken == null){
        
    //     window.location.href="./pages-login.html"
    // }
   
    
    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/category",
        contentType: "application/json;charset=utf-8",
        dataType:"JSON",
        headers: {
            'Authorization' : `Bearer ${adminToken}`
        }
    }).done(function(result){
        if(result.success){
            var categoryHtml = ``;
            $.each(result.data, function(index,value){
                categoryHtml += ` <option value="${value.id}">${value.categoryName}</option>`
            })
            $("#category").append(categoryHtml)
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

    $("#btn-add-food").click(function(){
        var foodName = $("#food-Name").val();
        var category = $("#category").val();
        var foodPrice = $("#food-Price").val();
        var foodDescription = $("#food-description").val();
        var files = $('#formFile')[0].files;

        var isNumeric = /^-?\d*\.?\d+$/.test(foodPrice);
        var formData = new FormData();
        if(foodName==""){
            swal("Không để trống tên món");
        }else if(foodPrice==""){
            swal("Không để trống giá");
        }else if(!isNumeric){
            swal("Giá không hợp lệ");
        }else if(foodDescription==""){
            swal("Không để trống mô tả");
        }
        formData.append('foodName', foodName);
        formData.append('price', foodPrice);
        formData.append('categoryId', category);
        formData.append('description', foodDescription);
    
        
        for (var i = 0; i < files.length; i++) {
            formData.append('files', files[i]);
        }
        $.ajax({
            method: 'POST',
            url: "http://localhost:8080/admin/add-food",
            data: formData,
            contentType: false,
            processData: false,
            headers: {
                'Authorization' : `Bearer ${adminToken}`
            }
        }).done(function(msg){
            if(msg.data){
                swal({
                    text: "Thêm món mới thành công",
                    icon: "success",
                    button: "OK",
                  }).then((ok) => {
                    // Kiểm tra khi nút "OK" được bấm
                    if (ok) {
                      // Chuyển hướng sau khi bấm nút "OK"
                      window.location.href = './list-product.html';
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
        
    })

    $("#log-out").click(function(){
        localStorage.removeItem("adminInfo");
        localStorage.removeItem("adminToken");
        window.location.href = './pages-login.html';
    })
    

})