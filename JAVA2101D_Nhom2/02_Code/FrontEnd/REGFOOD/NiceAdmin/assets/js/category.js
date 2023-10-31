$(document).ready(function(){
    var adminToken = localStorage.getItem("adminToken");
    var adminInfo = JSON.parse(localStorage.getItem('adminInfo')) || [];
    $("#userName").text(adminInfo.fullName);
    if(adminToken == null){
        
        window.location.href="./pages-login.html"
    }
   
    
    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/category",
        contentType: "application/json;charset=utf-8",
        dataType:"JSON"
        // headers: {
        //     'Authorization' : `Bearer ${adminToken}`
        // }
    }).done(function(result){
        if(result.success){
            var value = result.data;
            var categoryHtml = ``;
            var status = ``;
            $.each(value,function(index,data){
                var dateObj = new Date(data.createDate);

                // Lấy thông tin về giờ, phút và ngày tháng
                var hours = dateObj.getUTCHours();
                var minutes = dateObj.getUTCMinutes();
                var day = dateObj.getUTCDate();
                var month = dateObj.getUTCMonth() + 1; // Tháng bắt đầu từ 0 nên cộng thêm 1
                var year = dateObj.getUTCFullYear();

                // Tạo chuỗi mới theo định dạng mong muốn
                var formattedDate = `${hours}:${minutes} ${day}/${month}/${year}`;
                if(data.status==true){
                    status = `<span class="badge bg-success">Đang hoạt động</span>`;
                }else{
                    status = `<span class="badge bg-danger">Tạm dừng</span>`;
                }
                categoryHtml += `<tr>
                                    <th scope="row">${index+1}</th>
                                    <td>${data.categoryName}</td>
                                    <td>${status}</td>                     
                                    <td>${formattedDate}</td>
                                    <td><a href="edit-category.html?id=${data.id}"><button type="button" class="btn btn-warning"><i class="bi bi-pencil-square"></i></button></a> | <button type="button" class="btn btn-danger deleteCategory " data-id="${data.id}"><i class="bx bx-x"></i></button></td>
                                </tr>`
            })

            $("#list-category").append(categoryHtml);

            $(".deleteCategory").click(function(){
                var cateId = $(this).data('id');
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
                            url: "http://localhost:8080/category/delete-category/"+cateId,
                            contentType: "application/json;charset=utf-8",
                            dataType:"JSON"
                            // headers: {
                            //     'Authorization' : `Bearer ${adminToken}`
                            // }
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
                              
                            
                        })  
                     
                    } 
                  });
            })
        }
         
       
    })

    $("#btn-add-category").click(function(){
        var cateName = $("#category-name").val();
        var cateStatus = $("#category-status").val();
        var categoryDTO = {
            categoryName : cateName,
            status : cateStatus
        }
        $.ajax({
            method: 'POST',
            url: "http://localhost:8080/category/add-category",
            data: JSON.stringify(categoryDTO),
            contentType: "application/json;charset=utf-8",
            dataType:"JSON"
            // headers: {
            //     'Authorization' : `Bearer ${adminToken}`
            // }
        }).done(function(msg){
            if(msg.data){
                swal({
                    text: "Thêm danh mục mới thành công",
                    icon: "success",
                    button: "OK",
                  }).then((ok) => {
                    // Kiểm tra khi nút "OK" được bấm
                    if (ok) {
                      // Chuyển hướng sau khi bấm nút "OK"
                      window.location.href = 'category-manage.html';
                    }
                  });
            }
        })
        
    })

    $("#log-out").click(function(){
        localStorage.removeItem("adminInfo");
        localStorage.removeItem("adminToken");
        window.location.href = './pages-login.html';
    })
    

})