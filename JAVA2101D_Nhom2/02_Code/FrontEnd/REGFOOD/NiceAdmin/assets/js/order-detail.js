
$(document).ready(function(){
    var adminToken = localStorage.getItem("adminToken");
    let searchParam = new URLSearchParams(window.location.search);
    var billId = searchParam.get('billId') ;
    var adminInfo = JSON.parse(localStorage.getItem('adminInfo')) || [];
    $("#userName").text(adminInfo.fullName);
  //   if(adminToken == null){
        
  //     window.location.href="./pages-login.html"
  // }
   
    
    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/order/order-detail?id="+billId,
        contentType: "application/json;charset=utf-8",
        dataType:"JSON",
        headers: {
            'Authorization' : `Bearer ${adminToken}`
        }
    }).done(function(result){
       if(result.success){
        
        var value = result.data;
        var avatarHtml = `<img src="http://localhost:8080/index/restaurant/${value.userDTO.avatar}" alt="Profile" class="rounded-circle">`;
        $("#adminInfo").prepend(avatarHtml);
        $("#userName").text(value.userDTO.userName);
        var dateObj = new Date(value.createdDate);

        // Lấy thông tin về giờ, phút và ngày tháng
        var hours = dateObj.getUTCHours();
        var minutes = dateObj.getUTCMinutes();
        var day = dateObj.getUTCDate();
        var month = dateObj.getUTCMonth() + 1; // Tháng bắt đầu từ 0 nên cộng thêm 1
        var year = dateObj.getUTCFullYear();

        // Tạo chuỗi mới theo định dạng mong muốn
        var formattedDate = `${hours}:${minutes}  ${day}/${month}/${year}`;
        $("#time").text("Thời gian : "+ formattedDate);
        $("#fullName").text(value.userDTO.userName);
        $("#numberPhone").text(value.userDTO.numberPhone);
        $("#email").text(value.userDTO.email);
        $("#userName1").text(value.userDTO.userName);
        $("#address").text(value.addressDTO.addressDetail);
        $("#quantity").text(value.quantity);
        $("#totalAmount").text(value.totalAmount+"$");
        var paymentHtml = ``;
        if(value.payment==1){
            paymentHtml = `<span class="badge bg-secondary">Tiền mặt</span>`
        }else{
            paymentHtml=`<span class="badge bg-primary">Chuyển khoản </span>`
        }
        $("#payment").append(paymentHtml);
        $("#note").text(value.note);

        statusHtml = `<option value="1" ${value.status === 1 ? 'selected' : ''}>Đang tiến hành</option>
                    <option value="2" ${value.status === 2 ? 'selected' : ''} >Đã hoàn thành</option>
                    <option value="0" ${value.status === 0 ? 'selected' : ''}>Đã hủy</option>`
       }
       if(value.status==2 || value.status==0){
        $("#optionSelect").prop('disabled', true);
       }
       $("#optionSelect").append(statusHtml);

       var billDetailHtml = ``;
       $.each(value.billDetailDTOs, function(index,item){
        billDetailHtml += `<tr>
                                <td >${item.foodDTOs.foodName}</td>
                                <td>${item.quantity}</td>
                                <td>${item.quantity *item.foodDTOs.price}$</td>
                                
                            </tr>`
       })
       $("#order-detail").append(billDetailHtml);
       
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

    $("#optionSelect").change(function () {
        var selectedValue = $(this).val();
        
        // Nếu giá trị là 2 hoặc 0, hiển thị nút "Cập nhật trạng thái"
        if (selectedValue == "2" || selectedValue == "0") {
          $("#updateStatusBtn").show();
        } else {
          $("#updateStatusBtn").hide();
        }
      });
    
      // Bắt sự kiện click trên nút "Cập nhật trạng thái"
      $("#updateStatusBtn").click(function () {
        // Thực hiện gọi API cập nhật trạng thái ở đây
        var status = $("#optionSelect").val();
        
        
        $.ajax({
            type: 'POST',
            url: `http://localhost:8080/order/edit-status?billId=${billId}&status=${status}`,
            contentType: "application/json;charset=utf-8",
            dataType:"JSON",
            headers: {
              'Authorization' : `Bearer ${adminToken}`
          }
           
        }).done(function(msg){
            if(msg.data){
                swal({
                    text: "Cập nhật đơn thành công",
                    icon: "success",
                    button: "OK",
                  }).then((ok) => {
                    // Kiểm tra khi nút "OK" được bấm
                    if (ok) {
                        $("#updateStatusBtn").hide();
                        $("#optionSelect").prop('disabled', true); // Tắt chức năng chọn
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
    
      });
    
      $("#log-out").click(function(){
        localStorage.removeItem("adminInfo");
        localStorage.removeItem("adminToken");
        window.location.href = './pages-login.html';
    })
    

})