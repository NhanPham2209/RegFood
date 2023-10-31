$(document).ready(function(){
    var adminToken = localStorage.getItem("adminToken");
    var adminInfo = JSON.parse(localStorage.getItem('adminInfo')) || [];
    $("#userName").text(adminInfo.fullName);
    // if(adminToken == null){
        
    //     window.location.href="./pages-login.html"
    // }
    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/admin/getAllFood",
        contentType: "application/json;charset=utf-8",
        dataType:"JSON",
        headers: {
            'Authorization' : `Bearer ${adminToken}`
        }
        
    }).done(function(msg){
        if(msg.success){
            var value = msg.data;
            var productHtml =``;
            $.each(value ,function(index,data){
                productHtml += `  <tr>
                                    <th scope="row"><a href="#"><img src="http://localhost:8080/index/restaurant/${data.foodImageDTOs[0].imageName}" alt=""></a></th>
                                    <td class="fw-bold">${data.foodName}</td>
                                    <td>${data.price}$</td>
                                    <td >${data.categoryDTO.categoryName}</td>
                                    <td><a href="edit-product.html?foodId=${data.id}"><button type="button" class="btn btn-warning"><i class="bi bi-pencil-square"></i></button></a> | <button type="button" class="btn btn-danger deleteFood" data-id="${data.id}"><i class="bx bx-x"></i></button></td>
                                </tr>`
            })

            $("#list-product").append(productHtml);
            $(".deleteFood").click(function(){
                var foodId = $(this).data('id');
                swal({
                    title: "Are you sure?",
                    text: "Sau khi xóa, bạn sẽ không thể khôi phục ",
                    icon: "warning",
                    buttons: true,
                    dangerMode: true,
                  })
                  .then((willDelete) => {
                    if (willDelete) {
                        $.ajax({
                            type: 'POST',
                            url: "http://localhost:8080/admin/delete-food/"+foodId,
                            contentType: "application/json;charset=utf-8",
                            dataType:"JSON",
                            headers: {
                                'Authorization' : `Bearer ${adminToken}`
                            }
                        }).done(function(result){
                            swal({
                                text: "Xóa thành công",
                                icon: "success",
                                button: "OK",
                              }).then((ok) => {
                                // Kiểm tra khi nút "OK" được bấm
                                if (ok) {
                                  // Chuyển hướng sau khi bấm nút "OK"
                                  location.reload();
                                }
                              });
                              
                            
                        }).fail(function(){
                            swal({
                                text: "Lỗi Xóa sản phẩm",
                                icon: "error",
                                button: "OK",
                              }).then((ok) => {
                                // Kiểm tra khi nút "OK" được bấm
                                if (ok) {
                                  
                                    location.reload();
                                }
                              });
                           
                        })  
                     
                    } 
                  });
            })
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
    $("#log-out").click(function(){
        localStorage.removeItem("adminInfo");
        localStorage.removeItem("adminToken");
        window.location.href = './pages-login.html';
    })
})