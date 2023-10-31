$(document).ready(function(){
    var token = localStorage.getItem("token");
    
    var userInfo = JSON.parse(localStorage.getItem('userInfo')) || [];
    let searchParam = new URLSearchParams(window.location.search);
    var foodId = searchParam.get('id')
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
    
    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/index/food?id="+foodId,
        contentType: "application/json;charset=utf-8",
        dataType:"JSON",
        headers: {
            'Authorization' : `Bearer ${token}`
        }
        
    })
    .done(function(msg) { 
       
        if(msg.success){
            var value = msg.data;
            var imgFood = ``;
            $.each(value.foodImageDTOs,function(index,data){
                imgFood += ` <li><img class="zoom ing-fluid w-100" src="http://localhost:8080/index/restaurant/${data.imageName}" alt="product" style ="margin-top: 50.1355px; width: 376.016px;"></li>`
            })
            
            $("#food-price").append(value.price + "$")
            var star =``;
            for(var i = 0;i<Math.ceil(value.ratingPoint);i++){
                star += `<i class="far fa-star" style = "font-weight :600"></i>`
            }
            $("#food-rating").append(star);
            $("#food-name").append(value.foodName)
            $("#list-img").append(imgFood);
            $("#description").append(value.description);
            $("#addToCart").append(`<li><a class="common_btn" id="addCart" href="" data-id="${foodId}">add to cart</a></li>`)


                $('#addCart').click(function(e) {
                    // Lấy giá trị của thuộc tính 'data-id' của nút được bấm, đó chính là ID sản phẩm
                    // Lấy danh sách sản phẩm đã được lưu trong localStorage
                    // Nếu không có danh sách nào, sẽ tạo một mảng rỗng
                    var productId = $(this).data('id');
                    // var cart = JSON.parse(localStorage.getItem('cart')) || [];
                    // Kiểm tra xem sản phẩm có tồn tại trong danh sách sản phẩm tải từ API không
                    var checkCart = cart.find(checkId); 
                    console.log(checkCart)  
                    if (checkCart) {
                      // Nếu sản phẩm tồn tại, thêm sản phẩm vào giỏ hàng (mảng cart)
                      checkCart.quantity++;
                      swal("Add to cart success", "click to continue", "success");
                      $("#cart_view_quantity").text(calculateTotalQuantity());

                    } else {
                      // Nếu sản phẩm chưa có, thêm vào giỏ hàng với số lượng là 1
                    cart.push({ id: foodId, quantity: 1 ,price : value.price });
                    swal("Add to cart success", "click to continue", "success");
                    $("#cart_view_quantity").text(calculateTotalQuantity());
                    }
                    localStorage.setItem('cart', JSON.stringify(cart)); 
                    
                    function checkId(obj) {
                        return obj.id == productId;
                      }
                      e.preventDefault();
                  });
            var listComment = ``;
            $.each(value.ratingFoodDTOs,function(index,data){
                listComment += `<div class="tf__single_comment">
                                    <img src="http://localhost:8080/index/restaurant/${data.userDTO.avatar}" alt="review" class="img-fluid">
                                    <div class="tf__single_comm_text">
                                        <h3>${data.userDTO.userName}<span>${data.createdDate}</span></h3>
                                        <span class="rating">
                                        `
                                    for(var i =1 ;i<=parseInt(data.ratingPoint);i++){
                                        listComment += `<i class="fas fa-star" aria-hidden="true"></i>`
                                    }
                                        listComment += `
 
                                        </span>
                                        <p>${data.content}</p>
                                    </div>
                                </div>`;
            })      
            $("#list-comment").prepend(listComment);
        }
        

    });
    
    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/index",
        contentType: "application/json;charset=utf-8",
        dataType:"JSON",
      
        
    })
    .done(function(msg) { 
       
        if(msg.success){
            var value = msg.data;
           

                var menuHtml = ``;
               
                $.each(value.categoryDTOs,function(index,data){
                   
                    $.each(data.foodDTOs,function(index1,menuItem){
                        menuHtml = menuHtml + ` <div class="col-xxl-3 col-sm-6 col-lg-4 ${data.categoryName} wow fadeInUp" data-wow-duration="1s" >
                                                    <div class="tf__menu_item">
                                                        <div class="tf__menu_item_img">
                                                            <img src="http://localhost:8080/index/restaurant/${menuItem.foodImageDTOs[0].imageName}" alt="menu" class="img-fluid w-100">
                                                        </div>
                                                        <div class="tf__menu_item_text">
                                                            <a class="category" href="#">${data.categoryName}</a>
                                                            <a class="title" href="menu_details.html">${menuItem.foodName}</a>
                                                            <p class="rating">
                                                                <i class="fas fa-star"></i>
                                                                <i class="fas fa-star"></i>
                                                                <i class="fas fa-star"></i>
                                                                <i class="fas fa-star-half-alt"></i>
                                                                <i class="far fa-star"></i>
                                                               
                                                            </p>
                                                            <h5 class="price">${menuItem.price}$</h5>
                                                            <a class="tf__add_to_cart" href="#" data-bs-toggle="modal" data-bs-target="#cartModal" data-id="${menuItem.id}">add
                                                                to cart</a>
                                                            <ul class="d-flex flex-wrap justify-content-end">
                                                                <li><a href="#"><i class="fal fa-heart"></i></a></li>
                                                                <li><a href="menu_details.html?id=${menuItem.id}"><i class="far fa-eye"></i></a></li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>`
                        
                    })
                })
                $("#list-menu").append(menuHtml); 
                 

                $('.tf__add_to_cart').click(function() {
                    // Lấy giá trị của thuộc tính 'data-id' của nút được bấm, đó chính là ID sản phẩm
                    
                    var productId = $(this).data('id');
                    $.ajax({
                        type: 'GET',
                        url: `http://localhost:8080/index/food?id=${productId}`,
                        contentType: "application/json;charset=utf-8",
                        dataType:"JSON",
                        
                    }).done(function(msg1){
                        product = msg1.data;
                    // Lấy danh sách sản phẩm đã được lưu trong localStorage
            
                    // Nếu không có danh sách nào, sẽ tạo một mảng rỗng
                    var cart = JSON.parse(localStorage.getItem('cart')) || [];
                    
                   
                    // Kiểm tra xem sản phẩm có tồn tại trong danh sách sản phẩm tải từ API không
                    var checkCart = cart.find(checkId); 
                    console.log(checkCart)  
                    if (checkCart) {
                      // Nếu sản phẩm tồn tại, thêm sản phẩm vào giỏ hàng (mảng cart)
                      checkCart.quantity++;
                      swal("Add to cart success", "click to continue", "success");

                    } else {
                      // Nếu sản phẩm chưa có, thêm vào giỏ hàng với số lượng là 1
                    cart.push({ id: productId, quantity: 1 ,price : product.price });
                    swal("Add to cart success", "click to continue", "success");
                    }
                    localStorage.setItem('cart', JSON.stringify(cart)); 
                    })
                    function checkId(obj) {
                        return obj.id == productId;
                      }
                    
                  });

               
                
        }
        

    });
    
    $("#add-review").click(function(){
        var ratePoint = $("#ratingPoint").val();
        var content = $("#content").val();
        var ratingFoodRequest = {
            userId : userInfo.userId,
            foodId :foodId,
            point : ratePoint,
            content: content
        }
       if(ratePoint==null){
         swal("Please select rate point");
       }else if(content==""){
        swal("Please write a review");
       }else{
        if(token == null){
        
            window.location.href="./sign_in.html"
        }
        $.ajax({
            method: "POST",
            url: "http://localhost:8080/rating-food/add-ratingfood",
            data: JSON.stringify(ratingFoodRequest),
            contentType: "application/json;charset=utf-8",
            dataType:"JSON",
            headers: {
                'Authorization' : `Bearer ${token}`
            }
        }).done(function(msg){
            if(msg.data){
               
                swal({
                    text: "add your review successfully",
                    icon: "success",
                    button: "OK",
                  }).then((ok) => {
                    // Kiểm tra khi nút "OK" được bấm
                    if (ok) {
                      
                      location.reload();
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
                  
                    window.location.href="./sign_in.html"
                }
              });
           
        })
       }
    })
    

})