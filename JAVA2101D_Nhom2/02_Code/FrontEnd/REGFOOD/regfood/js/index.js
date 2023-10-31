$(document).ready(function () {
    var token = localStorage.getItem("token");
    var link = "http://localhost:8080/index";

    // if(token == null){

    //     window.location.href="./sign_in.html"
    // }
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
                var restaurntHtml = ` <div class="col-xl-6 col-sm-6 col-md-8">
                                    <ul class="tf__topbar_info d-flex flex-wrap d-none d-sm-flex">
                                        <li><a href="${value.email}"><i class="fas fa-envelope"></i> ${value.email}</a>
                                        </li>
                                        <li class="d-none d-md-block"><a href="callto:123456789"><i class="fas fa-phone-alt"></i>
                                                ${value.numberPhone}</a></li>
                                    </ul>
                                </div>`

                $("#restaurant-info").prepend(restaurntHtml);

                var restaurnFooter = `  <div class="container wow fadeInUp" data-wow-duration="1s">
                                        <div class="row justify-content-between">
                                            <div class="col-xxl-4 col-lg-4 col-sm-9 col-md-7">  
                                                <div class="tf__footer_content">
                                                    <a class="footer_logo" href="index.html">
                                                        <img src="${link}/restaurant/${value.imageLogo}" alt="RegFood" class="img-fluid w-100">
                                                    </a>
                                                    <span>${value.description}</span>
                                                    <ul class="social_link d-flex flex-wrap">
                                                        <li><a href="#"><i class="fab fa-facebook-f"></i></a></li>
                                                        <li><a href="#"><i class="fab fa-linkedin-in"></i></a></li>
                                                        <li><a href="#"><i class="fab fa-twitter"></i></a></li>
                                                        <li><a href="#"><i class="fab fa-behance"></i></a></li>
                                                        <li><a href="#"><i class="fab fa-instagram"></i></a></li>
                                                        <li><a href="#"><i class="fab fa-google-plus-g"></i></a></li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <div class="col-xxl-2 col-lg-2 col-sm-5 col-md-5">
                                                <div class="tf__footer_content">
                                                    <h3>Short Link</h3>
                                                    <ul>
                                                        <li><a href="#">Home</a></li>
                                                        <li><a href="#">About Us</a></li>
                                                        <li><a href="#">Contact Us</a></li>
                                                        <li><a href="#">Our Service</a></li>
                                                        <li><a href="#">gallery</a></li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <div class="col-xxl-2 col-lg-2 col-sm-6 col-md-5 order-md-4">
                                                <div class="tf__footer_content">
                                                    <h3>Help Link</h3>
                                                    <ul>
                                                        <li><a href="#">Terms & Conditions</a></li>
                                                        <li><a href="#">Privacy Policy</a></li>
                                                        <li><a href="#">Refund Policy</a></li>
                                                        <li><a href="#">FAQ</a></li>
                                                        <li><a href="#">contact</a></li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <div class="col-xxl-3 col-lg-4 col-sm-9 col-md-7 order-lg-4">
                                                <div class="tf__footer_content">
                                                    <h3>contact us</h3>
                                                    <p class="info"><i class="fas fa-phone-alt"></i> ${value.email}</p>
                                                    <p class="info"><i class="fas fa-envelope"></i> ${value.numberPhone}</p>
                                                    <p class="info"><i class="far fa-map-marker-alt"></i> ${value.address}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>`
                $("#restaurant-footer").prepend(restaurnFooter);

                var buttonCate = ``;
                var menuHtml = ``;
                var cartMenu = ``;
                $.each(value.categoryDTOs, function (index, data) {
                    buttonCate = buttonCate + `<button data-filter=".${data.categoryName}" class="category-button">${data.categoryName}</button>`
                    $.each(data.foodDTOs, function (index1, menuItem) {
                        menuHtml = menuHtml + ` <div class="col-xxl-3 col-sm-6 col-lg-4 ${data.categoryName} wow fadeInUp" data-wow-duration="1s" >
                                                    <div class="tf__menu_item">
                                                        <div class="tf__menu_item_img">
                                                            <img src="${link}/restaurant/${menuItem.foodImageDTOs[0].imageName}" alt="menu" class="img-fluid w-100">
                                                        </div>
                                                        <div class="tf__menu_item_text">
                                                            <a class="category" href="#">${data.categoryName}</a>
                                                            <a class="title" href="menu_details.html">${menuItem.foodName}</a>
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
                $("#list-menu").append(menuHtml);
                $("#category-button").append(buttonCate);
                $(".category-button").click(function () {
                    // Xóa lớp "active" khỏi tất cả các nút danh mục
                    $(".category-button").removeClass("active");
            
                    // Thêm lớp "active" vào nút danh mục được chọn
                    $(this).addClass("active");
            
                    // Thực hiện các tác vụ khác ở đây nếu cần
                });
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

                        }
                        $("#cart_view_quantity").text(calculateTotalQuantity());
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

    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/index/list-rating-restaurant",
        contentType: "application/json;charset=utf-8",
        dataType: "JSON",
        headers: {
            'Authorization': `Bearer ${token}`
        }
    }).done(function (result) {
        var value = result.data;
        var starRate = ``;
        var listRatingRestaurant = ``;
        $.each(value, function (index, item) {
            listRatingRestaurant = listRatingRestaurant + `<div class="col-xl-6 wow fadeInUp " data-wow-duration="1s">
                                                                <div class="tf__single_testimonial">
                                                                    <div class="tf__single_testimonial_img">
                                                                        <img src="${link}/restaurant/${item.userDTO.avatar}" alt="testimonial" class="img-fluid w-100">
                                                                    </div>
                                                                    <div class="tf__single_testimonial_text">
                                                                        <h4>${item.userDTO.userName}</h4>
                                                                        
                                                                        <p class="feedback">${item.content}</p>
                                                                        <span class="rating">`

            for (var i = 0; i < parseInt(item.ratingPoint); i++) {
                listRatingRestaurant += `<i class="fas fa-star"></i>`
            }
            listRatingRestaurant += `   
                                                                            
                                                                        </span>
                                                                    </div>
                                                                </div>
                                                            </div>`

        })

        $("#list-rating-restaurant").html(listRatingRestaurant);
        new WOW().init();

    })
    $("#dashboard").click(function () {
        if (token == null) {

            window.location.href = "./sign_in.html"
        } else {
            window.location.href = "./dashboard.html"
        }
    })
    
   
})
