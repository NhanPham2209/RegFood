$(document).ready(function () {
    var token = localStorage.getItem("token");
    var link = "http://localhost:8080/index";
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
        url: link,
        contentType: "application/json;charset=utf-8",
        dataType: "JSON",
        headers: {
            'Authorization': `Bearer ${token}`
        }

    })
        .done(function (msg) {

            if (msg.success) {
                var value = msg.data;

                var menuHtml = ``;

                $.each(value.categoryDTOs, function (index, data) {

                    $.each(data.foodDTOs, function (index1, menuItem) {
                        menuHtml = menuHtml + ` <div class="col-xxl-3 col-sm-6 col-lg-4 ${data.categoryName} wow fadeInUp" data-wow-duration="1s" >
                                                    <div class="tf__menu_item">
                                                        <div class="tf__menu_item_img">
                                                            <img src="${link}/restaurant/${menuItem.foodImageDTOs[0].imageName}" alt="menu" class="img-fluid w-100">
                                                        </div>
                                                        <div class="tf__menu_item_text">
                                                            <a class="category" href="#">${data.categoryName}</a>
                                                            <a class="title" href="menu_details.html?id=${menuItem.id}">${menuItem.foodName}</a>
                                                            <p class="rating"> `

                        for (var i = 0; i < Math.floor(menuItem.ratingPoint); i++) {
                            menuHtml += `<i class="fas fa-star"></i>`
                        }

                        // <i class="fas fa-star-half-alt"></i>


                        menuHtml += `
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
                $('#search-button').click(function() {
                    var searchValue = $('#search-input').val().toLowerCase(); // Lấy giá trị nhập từ input và chuyển thành chữ thường
              
                    // Lặp qua từng sản phẩm để kiểm tra tên sản phẩm
                    $('.tf__menu_item').each(function() {
                      var productName = $(this).find('.title').text().toLowerCase(); // Lấy tên sản phẩm và chuyển thành chữ thường
              
                      // Kiểm tra xem tên sản phẩm có chứa từ khóa tìm kiếm hay không
                      if (productName.includes(searchValue)) {
                        $(this).show(); // Hiển thị sản phẩm nếu tìm thấy kết quả
                      } else {
                        $(this).hide(); // Ẩn sản phẩm nếu không tìm thấy kết quả
                      }
                    });
                  });
                $("#list-menu").append(menuHtml);
                $('.tf__add_to_cart').click(function () {
                    // Lấy giá trị của thuộc tính 'data-id' của nút được bấm, đó chính là ID sản phẩm

                    var productId = $(this).data('id');
                    $.ajax({
                        type: 'GET',
                        url: `${link}/food?id=${productId}`,
                        contentType: "application/json;charset=utf-8",
                        dataType: "JSON",

                    }).done(function (msg1) {
                        product = msg1.data;
                        // Lấy danh sách sản phẩm đã được lưu trong localStorage

                        // Nếu không có danh sách nào, sẽ tạo một mảng rỗng
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
                            cart.push({ id: productId, quantity: 1, price: product.price });
                            swal("Add to cart success", "click to continue", "success");
                            $("#cart_view_quantity").text(calculateTotalQuantity());
                        }
                        localStorage.setItem('cart', JSON.stringify(cart));
                    })
                    function checkId(obj) {
                        return obj.id == productId;
                    }

                });

                $(".category-button").click(function () {
                    var filter = $(this).data("filter");

                    // Ẩn tất cả các sản phẩm
                    $(".grid .col-xxl-3").hide();

                    // Hiển thị sản phẩm tương ứng với filter được chọn
                    $(".grid .col-xxl-3" + filter).show();
                });
                var order = { address_Id: null, totalAmount: null, promotionId: null, quantity: null, note: null, status: 1, payment: null, totalPrice: null };

                localStorage.setItem('order', JSON.stringify(order));

               
            }


        });






})