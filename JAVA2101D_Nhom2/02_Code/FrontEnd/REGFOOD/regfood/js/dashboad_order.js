$(document).ready(function(){
    var token = localStorage.getItem("token");
    var userInfo = JSON.parse(localStorage.getItem('userInfo'));
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
        var orderHtml = ``;
        var statusHtml = ``;
        var avatarHtml = `<img src="http://localhost:8080/index/restaurant/${value.avatar}" alt="user" class="img-fluid w-100">`;
        $("#avatar").prepend(avatarHtml);
        $.each(value.billDTOs, function(index,data){
          orderHtml += `<tr>
                            <td>
                                <h5>${data.id}</h5>
                            </td>
                            <td>
                            `
                            var inputDate = data.createdDate;

                            // Tạo một đối tượng Date từ chuỗi ban đầu
                            var dateObj = new Date(inputDate);

                            // Lấy thông tin về giờ, phút và ngày tháng
                            var hours = dateObj.getUTCHours();
                            var minutes = dateObj.getUTCMinutes();
                            var day = dateObj.getUTCDate();
                            var month = dateObj.getUTCMonth() + 1; // Tháng bắt đầu từ 0 nên cộng thêm 1
                            var year = dateObj.getUTCFullYear();

                            // Tạo chuỗi mới theo định dạng mong muốn
                            var formattedDate = `${hours}:${minutes} ${day}/${month}/${year}`;
                            if(data.status==1){
                                statusHtml = `<span class="active">active</span>`
                            }else if(data.status==2){
                                statusHtml = `<span class="complete">Complated</span>`
                            }else if(data.status==0){
                                statusHtml =  `<span class="cancel">cancel</span>`
                            }
                            orderHtml +=
                            `
                                <p>${formattedDate}</p>
                            </td>
                            <td>
                                ${statusHtml}
                            </td>
                            <td>
                                <h5>${data.totalAmount}$</h5>
                            </td>
                            <td><a href="dashboard_order_invoice.html?bill-detail=${data.id}">View Details</a></td>
                        </tr>`
        })
        $("#list-order").append(orderHtml);
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
    $("#logout").click(function(){
        localStorage.removeItem("cart");
        localStorage.removeItem("order");
        localStorage.removeItem("userInfo");
        localStorage.removeItem("token");
        window.location.href = 'sign_in.html';
    })
    
})