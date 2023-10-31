$(document).ready(function(){
    var token = localStorage.getItem("token");
    var userInfo = JSON.parse(localStorage.getItem('userInfo')) || [];
    var order = JSON.parse(localStorage.getItem('order')) || [];
    var cart = JSON.parse(localStorage.getItem('cart')) || [];
    // if(token == null){
        
    //     window.location.href="./sign_in.html"
    // }
    
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
        var orderRequest = {
            userId : userInfo.userId,
            addressId : order.address_Id,
            totalAmount : order.totalAmount,
            promotionId : order.promotionId,
            quantity : order.quantity,
            note : order.note,
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
        
        e.preventDefault(); // Ngăn chặn chuyển hướng mặc định
       
    })
    
    
    
   

    
})