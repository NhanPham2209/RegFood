
$(document).ready(function(){
    var adminToken = localStorage.getItem("adminToken");
    // let searchParam = new URLSearchParams(window.location.search);
    // var foodId = searchParam.get('foodId') ;
    var adminInfo = JSON.parse(localStorage.getItem('adminInfo')) || [];
    $("#userName").text(adminInfo.fullName);
    // if(adminToken == null){
        
    //     window.location.href="./pages-login.html"
    // }
   
    
    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/order/get-all",
        contentType: "application/json;charset=utf-8",
        dataType:"JSON",
        headers: {
            'Authorization' : `Bearer ${adminToken}`
        }
    }).done(function(result){
       if(result.success){
        var value = result.data;
        var orderHtml = ``;
        var paymentHtml =``;
        var statusHtml = ``;
        $.each(value,function(index,data){
            var dateObj = new Date(data.createdDate);

                // Lấy thông tin về giờ, phút và ngày tháng
                var hours = dateObj.getUTCHours();
                var minutes = dateObj.getUTCMinutes();
                var day = dateObj.getUTCDate();
                var month = dateObj.getUTCMonth() + 1; // Tháng bắt đầu từ 0 nên cộng thêm 1
                var year = dateObj.getUTCFullYear();

                // Tạo chuỗi mới theo định dạng mong muốn
                var formattedDate = `${hours}:${minutes} ${day}/${month}/${year}`;
            if(data.status==0){
                statusHtml = `<span class="badge bg-danger">Đã hủy</span>`
            }else if(data.status==1){
                statusHtml=`<span class="badge bg-warning text-dark">Đang tiến hành</span>`
            }else if(data.status==2){
                statusHtml = `<span class="badge bg-success">Hoàn thành</span>`
            }
            if(data.payment==1){
                paymentHtml = `<span class="badge bg-secondary">Tiền mặt</span>`
            }else{
                paymentHtml=`<span class="badge bg-primary">Chuyển khoản </span>`
            }
            orderHtml += `<tr>
                            <td>${data.id}</td>
                            <td>${data.userDTO.userName}</td>
                            <td>${data.totalAmount}$</td>
                            <td>${paymentHtml}</td>
                            <td>${statusHtml}</td>
                            <td><a href="order-detail.html?billId=${data.id}"><button type="button" class="btn btn-outline-warning">Xem chi tiết</button></a></td>
                            <td>${formattedDate}</td>
                        </tr>`
        })
       }
    $("#list-bills").append(orderHtml);
    $(document).ready(function () {
        $('#myTable').DataTable({
            "pageLength": 100,
            "language": {
                "sProcessing": "Đang xử lý...",
                "sLengthMenu": "Xem _MENU_ mục",
                "sZeroRecords": "Không tìm thấy dòng nào phù hợp",
                "sInfo": "Đang xem _START_ đến _END_ trong tổng số _TOTAL_ mục",
                "sInfoEmpty": "Đang xem 0 đến 0 trong tổng số 0 mục",
                "sInfoFiltered": "(được lọc từ _MAX_ mục)",
                "sInfoPostFix": "",
                "sSearch": "Tìm:",
                "sUrl": "",
                "oPaginate": {
                    "sFirst": "Đầu",
                    "sPrevious": "Trước",
                    "sNext": "Tiếp",
                    "sLast": "Cuối"
                }
            }
        });
    });   
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

    $("#btn-edit-food").click(function(){
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
        formData.append('foodId',foodId)
        formData.append('foodName', foodName);
        formData.append('price', foodPrice);
        formData.append('categoryId', category);
        formData.append('description', foodDescription);
    
        
        for (var i = 0; i < files.length; i++) {
            formData.append('files', files[i]);
        }
        $.ajax({
            method: 'POST',
            url: "http://localhost:8080/admin/edit-food",
            data: formData,
            contentType: false,
            processData: false,
            headers: {
                'Authorization' : `Bearer ${adminToken}`
            }
        }).done(function(msg){
            if(msg.data){
                swal({
                    text: "Cập nhật món thành công",
                    icon: "success",
                    button: "OK",
                  }).then((ok) => {
                    // Kiểm tra khi nút "OK" được bấm
                    if (ok) {
                      // Chuyển hướng sau khi bấm nút "OK"
                      window.location.href = 'list-products.html';
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