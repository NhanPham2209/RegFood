$(document).ready(function(){
    var token = localStorage.getItem("token");
    var userInfo = JSON.parse(localStorage.getItem('userInfo')) || [];
    var order = JSON.parse(localStorage.getItem('order')) || [];
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
    $("#add-address").click(function(){
        if($("#title").val()==""){
            swal("Do not leave the title blank")
        }else if($("#address-detail").val()==""){
            swal("Do not leave the detailed address blank");
        }else{
            var addressRequest = {
                title : $("#title").val(),
                addressDetail: $("#address-detail").val(),
                userId : userInfo.userId
            }
            $.ajax({
                url: "http://localhost:8080/address/add-address" ,
                    method: "POST",
                    data: JSON.stringify(addressRequest),
                    contentType: "application/json;charset=utf-8",
                    dataType:"JSON",
                    headers: {
                        'Authorization' : `Bearer ${token}`
                    }
            }).done(function(result){
               if(result.data){
               
                swal({
                    text: "add address successfully",
                    icon: "success",
                    button: "OK",
                  }).then((ok) => {
                    // Kiểm tra khi nút "OK" được bấm
                    if (ok) {
                      
                      location.reload();
                    }
                  });
                $("#address_modal").modal("hide");
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
               
            })

        }
       

    })
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/address/"+userInfo.userId,
        contentType: "application/json;charset=utf-8",
        dataType:"JSON",
        headers: {
            'Authorization' : `Bearer ${token}`
        }
      })
        .done(function( msg ) {
            if(msg.success){
                var listAddress = msg.data;
                var addressHtml = ``;
                $.each(listAddress,function(index,data){
                   addressHtml = addressHtml + `<div class="col-md-6">
                   <div class="tf__checkout_single_address">
                            <div class="form-check">
                                <label class="form-check-label" for="home">
                                    <input class="form-check-input" type="radio" name="address"
                                        id="home-${data.id}" value = "${data.id}">
                                    <span class="icon"><i class="fas fa-home"></i>${data.title}</span>
                                    <span class="address">${data.addressDetail}</span>
                                </label>
                            </div>
                        </div>
                    </div>`

                })
                $("#address-form").append(addressHtml);
                $("input[type='radio'][name='address']").change(function () {
                    address_id = $("input[name='address']:checked").val();
                    order.address_Id = parseInt(address_id);
                    localStorage.setItem('order', JSON.stringify(order));
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
                  
                    window.location.href="./sign_in.html"
                }
              });
           
        });
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/promotion/getPromotion/"+order.promotionId,
            contentType: "application/json;charset=utf-8",
            dataType:"JSON",
            headers: {
                'Authorization' : `Bearer ${token}`
            }
        }).done(function(result){
            $("#promotion").text(result.data.percent +"%")
        })

    $("#total-price").text(order.totalPrice + "$");
    $("#total-amount").text(order.totalAmount +"$")
   
    $("#payment").change(function(){
        var payment = parseInt($("#payment").val());
        order.payment = payment
        localStorage.setItem('order', JSON.stringify(order));
    })
    $("#noteInput").on("input", function () {
        var note = $(this).val();
        if(note ===""){order.note = ""}
        order.note = note;
        localStorage.setItem('order', JSON.stringify(order));
      })
      

    var itemFoodRequests = cart.map(item => ({
        foodId: item.id,
        quantity: item.quantity
      }));
      
 
    $("#checkout").click(function(e){
        if($("input[name='address']:checked").val() ==null){
            swal("Please select your address");
        }
         else if($("#payment").val()==0){
            swal("Please choose payment method")
        }else if(order.payment==1){
            var orderRequest = {
                userId : userInfo.userId,
                addressId : order.address_Id,
                totalAmount : order.totalAmount,
                promotionId : order.promotionId,
                quantity : order.quantity,
                note : $("#noteInput").val(),
                status: order.status,
                payment : order.payment,
                itemFoodRequests
                
            }
        
            $.ajax({
                url: "http://localhost:8080/order" ,
                    method: "POST",
                    data: JSON.stringify(orderRequest),
                    contentType: "application/json;charset=utf-8",
                    dataType:"JSON",
                    headers: {
                        'Authorization' : `Bearer ${token}`
                    }
            }).done(function(result){
               if(result.data){
                swal({
                    text: "Order placed successfully",
                    icon: "success",
                    button: "OK",
                  }).then((ok) => {
                    // Kiểm tra khi nút "OK" được bấm
                    if (ok) {
                      // Chuyển hướng sau khi bấm nút "OK"
                      window.location.href = 'dashboard_order.html';
                    }
                  });
                
               
                localStorage.removeItem("cart");
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
               
            })
            
        }else if(order.payment == 2){
            window.location.href = 'payment.html';
        }
        
        e.preventDefault(); // Ngăn chặn chuyển hướng mặc định
       
    })
    
    
    
   

    
})