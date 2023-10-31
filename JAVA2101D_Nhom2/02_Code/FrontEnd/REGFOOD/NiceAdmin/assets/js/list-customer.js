$(document).ready(function(){
    var adminToken = localStorage.getItem("adminToken");
    var adminInfo = JSON.parse(localStorage.getItem('adminInfo')) || [];
    $("#userName").text(adminInfo.fullName);
    // if(adminToken == null){
        
    //     window.location.href="./pages-login.html"
    // }
    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/user/get-all-custommer",
        contentType: "application/json;charset=utf-8",
        dataType:"JSON",
        headers: {
            'Authorization' : `Bearer ${adminToken}`
        }
        
    }).done(function(msg){
        if(msg.success){
            var value = msg.data;
            var custommerHtml = ``;
            $.each(value,function(index,item){
                custommerHtml += `  <tr>
                                        <th scope="row">${item.id}</th>
                                        <td>${item.userName}</td>
                                        <td><img src="http://localhost:8080/index/restaurant/${item.avatar}" style="max-width: 80px;"></td>
                                        <td>${item.numberPhone}</td>                     
                                        <td>${item.email}</td>
                                        <td><a href="customer-order.html?email=${item.email}" class="btn btn-outline-primary">Xem chi tiết</a></td>
                                    </tr>`
            })
            $("#list-customer").append(custommerHtml);
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
    $("#log-out").click(function(){
        localStorage.removeItem("adminInfo");
        localStorage.removeItem("adminToken");
        window.location.href = './pages-login.html';
    })
})