$(document).ready(function(){
    var token = localStorage.getItem("token");
    var userInfo = JSON.parse(localStorage.getItem('userInfo'))
    let searchParam = new URLSearchParams(window.location.search);
    var billId = searchParam.get('bill-detail');
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
        url: "http://localhost:8080/order/order-detail?id="+billId,
        contentType: "application/json;charset=utf-8",
        dataType:"JSON",
        headers: {
            'Authorization' : `Bearer ${token}`
        }
        
    }).done(function(msg) { 
       
      if(msg.success){
        var result = msg.data;
        var statusHtml = '';
        var item = ``;
        var payment = '';
        $("#order-address").text(result.addressDTO.addressDetail);
        if(result.status==1){
            statusHtml = 'Active'
        }else if(result.status==2){
            statusHtml = "Complete"
        }else if(result.status==0){
            statusHtml = "Cancel"
        }
        $("#status").text("Status :"+statusHtml)
        $("#order-id").text(result.id);
            var inputDate = result.createdDate;
            var dateObj = new Date(inputDate);

            // Lấy thông tin về giờ, phút và ngày tháng
            var hours = dateObj.getUTCHours();
            var minutes = dateObj.getUTCMinutes();
            var day = dateObj.getUTCDate();
            var month = dateObj.getUTCMonth() + 1; // Tháng bắt đầu từ 0 nên cộng thêm 1
            var year = dateObj.getUTCFullYear();

            // Tạo chuỗi mới theo định dạng mong muốn
            var formattedDate = `${hours}:${minutes} ${day}/${month}/${year}`;
        $("#order-date").text(formattedDate)

        var total = 0;
        var quantity=0;
        $.each(result.billDetailDTOs , function(index,data){
            item += `  <tr>
                            <td class="sl_no">${index+1}</td>
                            <td class="package">
                                <p>${data.foodDTOs.foodName}</p>
                                
                            </td>
                            <td class="price">
                                <b>${data.foodDTOs.price}$</b>
                            </td>
                            <td class="qnty">
                                <b>${data.quantity}</b>
                            </td>
                            <td class="total">
                                <b>${data.foodDTOs.price * data.quantity}$</b>
                            </td>
                        </tr>`
            total+= data.foodDTOs.price * data.quantity;
            quantity+=data.quantity;
        })
        $("#list-item").append(item);
        $("#quantity").text(quantity)
        $("#total-money").text(total +"$")
        $("#promotion").text(result.promotionDTO.percent +"%")
        $("#total-bill").text(result.totalAmount + "$")
        if(result.payment==1){
            payment = 'pay down'
        }else if(result.payment==2){
            payment = 'Bank transfer'
        }
        $("#payment").text(payment);
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