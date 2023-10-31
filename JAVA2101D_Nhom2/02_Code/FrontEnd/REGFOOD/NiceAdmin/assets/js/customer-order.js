$(document).ready(function(){
    var adminToken = localStorage.getItem("adminToken");
    let searchParam = new URLSearchParams(window.location.search);
    var emailParam = searchParam.get('email')  
    var adminInfo = JSON.parse(localStorage.getItem('adminInfo')) || [];
    $("#userName").text(adminInfo.fullName);
    // if(adminToken == null){
        
    //     window.location.href="./pages-login.html"
    // }

    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/user?email="+emailParam,
        contentType: "application/json;charset=utf-8",
        dataType:"JSON",
        headers: {
            'Authorization' : `Bearer ${adminToken}`
        }
        
    }).done(function(msg) { 
       
      if(msg.success){
        var value = msg.data;
        var avatarHtml = ` <img src="http://localhost:8080/index/restaurant/${value.avatar}" alt="Profile" class="rounded-circle">`;
        var orderHtml = ``;
        var statusHtml = ``;
        $("#profile").prepend(avatarHtml);
        $("#fullName").text(value.userName);
        $.each(value.billDTOs, function(index,data){
            if(data.status==0){
                statusHtml = `<span class="badge bg-danger">Đã hủy</span>`
            }else if(data.status==1){
                statusHtml=`<span class="badge bg-warning text-dark">Đang tiến hành</span>`
            }else if(data.status==2){
                statusHtml = `<span class="badge bg-success">Hoàn thành</span>`
            }
            var dateObj = new Date(data.createdDate);

                // Lấy thông tin về giờ, phút và ngày tháng
                var hours = dateObj.getUTCHours();
                var minutes = dateObj.getUTCMinutes();
                var day = dateObj.getUTCDate();
                var month = dateObj.getUTCMonth() + 1; // Tháng bắt đầu từ 0 nên cộng thêm 1
                var year = dateObj.getUTCFullYear();

                // Tạo chuỗi mới theo định dạng mong muốn
                var formattedDate = `${hours}:${minutes} ${day}/${month}/${year}`;
            orderHtml += ` <tr>
                                <th scope="row">${index+1}</th>
                                <td>${statusHtml}</td>
                                <td>${data.totalAmount}$</td>
                                <td>${formattedDate}</td>
                                <td><a href="order-detail.html?billId=${data.id}" class="btn btn-outline-primary">Xem chi tiết</a></td>
                            </tr>`
        })
        $("#list-order").append(orderHtml);

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
       
    });
   
    $("#log-out").click(function(){
        localStorage.removeItem("adminInfo");
        localStorage.removeItem("adminToken");
        window.location.href = './pages-login.html';
    })
})