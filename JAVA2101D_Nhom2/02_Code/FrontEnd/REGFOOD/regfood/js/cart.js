$(document).ready(function () {
    // Lấy thông tin giỏ hàng từ localStorage (nếu có)
    var cart = JSON.parse(localStorage.getItem('cart')) || [];
    var order = JSON.parse(localStorage.getItem('order')) || [];
    // var userInfo = JSON.parse(localStorage.getItem('userInfo')) || [];
    
    //  tải sản phẩm từ API dựa trên id và hiển thị lên giao diện
    function loadProductInfo(id, quantity) {
        $.ajax({
            url: 'http://localhost:8080/index/food?id='+id, // Thay đổi URL tương ứng với API của bạn
            method: 'GET',
            dataType: 'json',
            success: function (product) {
                var value = product.data;
                // Tạo dòng mới cho sản phẩm trong bảng giỏ hàng
                var newRow = `<tr>
                                    <td class="tf__pro_img">
                                        <img src="http://localhost:8080/index/restaurant/${value.foodImageDTOs[0].imageName}" alt="product" class="img-fluid w-100">
                                    </td>

                                    <td class="tf__pro_name">
                                        <a href="#" >${value.foodName}</a>
                                        
                                    </td>

                                    <td class="tf__pro_status">
                                        <h6 id ="price-${value.id}">${value.price}$</h6>
                                    </td>

                                    <td class="tf__pro_select">
                                        <div class="quentity_btn">
                                            <button class="btn btn-danger decrement"><i class="fal fa-minus" aria-hidden="true"></i></button>
                                            <input type="text" placeholder="" id = "quantity-${value.id}" value = "${quantity}" class="quantity">
                                            <button class="btn btn-success increment"><i class="fal fa-plus" aria-hidden="true"></i></button>
                                        </div>
                                    </td>

                                    <td class="tf__pro_tk">
                                        <h6 id="total-price-${value.id}">${value.price * quantity}$</h6>
                                    </td>

                                    <td class="tf__pro_icon">
                                        <a href="" class = "delete-cartItem" data-id = "${value.id}"><i class="far fa-times" aria-hidden="true"></i></a>
                                    </td>
                                </tr>`;
                
                $('#cart-table').append(newRow);
            },
            error: function (error) {
                console.error('Lỗi khi gọi API: ', error);
            }
        });
    }
    // hàm tính tổng tiền
    function calculateTotalPrice() {
        var totalPrice = 0;
        for (var i = 0; i < cart.length; i++) {
            totalPrice += cart[i].quantity * cart[i].price;
        }
        return totalPrice;
    }
    // hàm tính tổng số lượng 
    function calculateTotalQuantity() {
        var totalQuantity = 0;
        for (var i = 0; i < cart.length; i++) {
            totalQuantity += cart[i].quantity ;
        }
        return totalQuantity;
    }
    // hàm tính tổng tiền khi có mã giảm giá
    function calculateTotalPriceHasPromotion(){
        var totalMoney = parseFloat($("#total-money").text().replace("$", ""));
        var percent = $("#promotionInput").find("option:selected").text();
        var percentNumber = parseInt(percent.replace("%", ""));
        billMoney = totalMoney - totalMoney/100*percentNumber;
        return billMoney;
    }
    
   
    // Duyệt qua danh sách sản phẩm trong giỏ hàng và gọi API để hiển thị thông tin
   
    cart.forEach(function (item) {
        loadProductInfo(item.id, item.quantity);
    });
    // sau khi load xong sản phẩm tiến hành gọi hàm tính tổng tiền 
    $("#total-money").text(calculateTotalPrice()+"$");
    $("#bill-money").text(calculateTotalPrice() + "$");
    $("#cart_view_quantity").text(calculateTotalQuantity());
    $("#total-quantity").text("total cart (" + calculateTotalQuantity() + ")" )

    order.totalAmount = calculateTotalPrice();
    order.quantity = calculateTotalQuantity();
    localStorage.setItem('order', JSON.stringify(order)); 
    // Sự kiện khi người dùng bấm nút "Xóa" sản phẩm khỏi giỏ hàng
    $('#cart-table').on('click', '.delete-cartItem', function (event) {
        var productId = $(this).data('id');
        // Lọc ra sản phẩm cần xóa khỏi giỏ hàng
        cart = cart.filter(function (item) {
            return item.id !== productId;
        });
        // Lưu lại giỏ hàng mới sau khi xóa vào localStorage
        localStorage.setItem('cart', JSON.stringify(cart));
        // Cập nhật lại giao diện giỏ hàng
        event.preventDefault(); // ngăn chặn load lại trang
        $(this).closest('tr').remove();
        $("#total-money").text(calculateTotalPrice()+"$");
        $("#total-quantity").text("total cart (" + calculateTotalQuantity() + ")" )
        $("#cart_view_quantity").text(calculateTotalQuantity);
        order.totalAmount = calculateTotalPrice();
        order.quantity = calculateTotalQuantity();
        localStorage.setItem('order', JSON.stringify(order));
    });

    
    // Sự kiện khi người dùng bấm nút "+" để tăng quantity
    $('#cart-table').on('click', '.increment', function () {
        // // Lấy productId từ data-id của nút "Xóa" trong cùng hàng
        var productId = $(this).closest('tr').find('.delete-cartItem').data('id');
        
        var $quantityInput = $(this).siblings('.quantity');
        var newQuantity = parseInt($quantityInput.val()) + 1;
        $quantityInput.val(newQuantity);

        // Cập nhật giá trị quantity trong danh sách giỏ hàng
        cart.forEach(function (item) {
            if (item.id == productId) {
                item.quantity = newQuantity;
            }
        });
        // lấy giá trị của giá sản phẩm , số lượng và tổng tiền của sản phẩm theo id
        var price = parseFloat($(`#price-${productId}`).text());
        var quantity = $(`#quantity-${productId}`).val();
         var total = price*quantity;
        $(`#total-price-${productId}`).text(total + `$`);
        // Lưu lại giỏ hàng mới sau khi cập nhật vào localStorage
        localStorage.setItem('cart', JSON.stringify(cart));
        // gọi lại hàm tính tổng tiền
        $("#total-money").text(calculateTotalPrice()+"$");
        $("#bill-money").text(calculateTotalPriceHasPromotion() + "$")
        $("#total-quantity").text("total cart (" + calculateTotalQuantity() + ")" )
        $("#cart_view_quantity").text(calculateTotalQuantity);

        order.totalAmount = calculateTotalPriceHasPromotion();
        order.quantity = calculateTotalQuantity();
        order.totalPrice = calculateTotalPrice();
        localStorage.setItem('order', JSON.stringify(order));

    })

    // Sự kiện khi người dùng bấm nút "-" để xóa quantity
    $('#cart-table').on('click', '.decrement', function () {
        // // Lấy productId từ data-id của nút "Xóa" trong cùng hàng
        var productId = $(this).closest('tr').find('.delete-cartItem').data('id');
       
        var $quantityInput = $(this).siblings('.quantity');
        var newQuantity = parseInt($quantityInput.val()) - 1;
        if(newQuantity>=1){
            $quantityInput.val(newQuantity);

            // Cập nhật giá trị quantity trong danh sách giỏ hàng
            cart.forEach(function (item) {
                if (item.id == productId) {
                    item.quantity = newQuantity;
                }
            });
            var price = parseFloat($(`#price-${productId}`).text());
            var quantity = $(`#quantity-${productId}`).val();
            var total = price*quantity;
            $(`#total-price-${productId}`).text(total + `$`)
            
            // Lưu lại giỏ hàng mới sau khi cập nhật vào localStorage
            localStorage.setItem('cart', JSON.stringify(cart));
            $("#total-money").text(calculateTotalPrice()+"$");
            $("#bill-money").text(calculateTotalPriceHasPromotion() + "$")
            $("#total-quantity").text("total cart (" + calculateTotalQuantity() + ")" )
            $("#cart_view_quantity").text(calculateTotalQuantity);
            order.totalAmount = calculateTotalPriceHasPromotion();
            order.quantity = calculateTotalQuantity();
            order.totalPrice = calculateTotalPrice();

            localStorage.setItem('order', JSON.stringify(order));
        }
        
        
    })

    
    
        $.ajax({
            url: 'http://localhost:8080/index/promotion', // Thay đổi URL tương ứng với API của bạn
            method: 'GET',
            dataType: 'json',
            success: function (promotion) {

                var value = promotion.data;
                var row = ``;
              
                $.each(value,function(index,data){
                     row = row + `<option value="${data.id}" ${data.id === 1 ? 'selected' : ''}>${data.percent}%</option>`;
                 })

                  
                 $('#promotionInput').append(row);
            },
            error: function (error) {
                console.error('Lỗi khi gọi API: ', error);
            }
        });
    
        $("#promotionInput").change(function () {
            $("#bill-money").text(calculateTotalPriceHasPromotion() + "$")
            order.totalAmount = calculateTotalPriceHasPromotion();
            order.quantity = calculateTotalQuantity();
            order.totalPrice = calculateTotalPrice();

            order.promotionId = parseInt($("#promotionInput").val());
            localStorage.setItem('order', JSON.stringify(order));
        });

        $("#checkout").click(function(){
            if(cart.length==0){
                swal("Please add menu to cart")
            }else{
                window.location.href="./check_out.html"
            }
        })

    

    
});