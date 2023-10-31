$(document).ready(function(){
    var adminToken = localStorage.getItem("adminToken");
    var adminInfo = JSON.parse(localStorage.getItem('adminInfo')) || [];
    $("#userName").text(adminInfo.fullName);
    // if(adminToken == null){
        
    //     window.location.href="./pages-login.html"
    // }
   
    // lấy doanh số ngày hôm nay
    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/sales/getRevenueToday",
        contentType: "application/json;charset=utf-8",
        dataType:"JSON",
        headers: {
            'Authorization' : `Bearer ${adminToken}`
        }
    }).done(function(result){
        if(result.success){
            var value = result.data;
            $("#sales-today").text(value+"$");
        }
         
       
    })

    // lấy doanh số 1 tháng vừa qua
    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/sales/getRevenueLastMonth",
        contentType: "application/json;charset=utf-8",
        dataType:"JSON",
        headers: {
            'Authorization' : `Bearer ${adminToken}`
        }
    }).done(function(result){
        if(result.success){
            var value = result.data;
            $("#sale-this-month").text(value+"$");
        }
         
       
    })

    // lấy tổng số lượng khách hàng
    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/user/get-all-custommer",
        contentType: "application/json;charset=utf-8",
        dataType:"JSON",
        headers: {
            'Authorization' : `Bearer ${adminToken}`
        }
    }).done(function(result){
        if(result.success){
            var value = result.data;
            $("#Customers-length").text(value.length+" người");
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

    //lấy doanh số 7 tháng vừa qua
    $.get("http://localhost:8080/sales/getRevenueLastSixMonths", function (value) {
        new Chart(document.querySelector('#barChart'), {
            type: 'bar',
            data: {
                labels: ['Month 1', 'Month 2', 'Month 3', 'Month 4', 'Month 5', 'Month 6', 'Month 7'],
                datasets: [{
                    label: 'Monthly Revenue',
                    data: value.data,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgb(75, 192, 192)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    });

    $("#log-out").click(function(){
        localStorage.removeItem("adminInfo");
        localStorage.removeItem("adminToken");
        window.location.href = './pages-login.html';
    })
    

})