
$(document).ready(function(){
    var adminToken = localStorage.getItem("adminToken");
    let searchParam = new URLSearchParams(window.location.search);
    var categoryId = searchParam.get('id')  
    var adminInfo = JSON.parse(localStorage.getItem('adminInfo')) || [];
    $("#userName").text(adminInfo.fullName);
    if(adminToken == null){
        
        window.location.href="./pages-login.html"
    }
   
    
    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/category/getCategory?categoryId="+categoryId,
        contentType: "application/json;charset=utf-8",
        dataType:"JSON"
        // headers: {
        //     'Authorization' : `Bearer ${adminToken}`
        // }
    }).done(function(result){
        if(result.success){
           $("#category-name").val(result.data.categoryName)
        }
         
       
    })

    $("#btn-edit-category").click(function(){
        var cateName = $("#category-name").val();
        var cateStatus = $("#category-status").val();
        var categoryDTO = {
            categoryName : cateName,
            status : cateStatus
        }
        $.ajax({
            method: 'POST',
            url: "http://localhost:8080/category/edit-category?categoryId="+categoryId,
            data: JSON.stringify(categoryDTO),
            contentType: "application/json;charset=utf-8",
            dataType:"JSON"
            // headers: {
            //     'Authorization' : `Bearer ${adminToken}`
            // }
        }).done(function(msg){
            if(msg.data){
                swal({
                    text: "Sửa danh mục thành công",
                    icon: "success",
                    button: "OK",
                  }).then((ok) => {
                    // Kiểm tra khi nút "OK" được bấm
                    if (ok) {
                      // Chuyển hướng sau khi bấm nút "OK"
                      window.location.href = 'category-manage.html';
                    }
                  });
            }
        })
        
    })

    $("#log-out").click(function(){
        localStorage.removeItem("adminInfo");
        localStorage.removeItem("adminToken");
        window.location.href = './pages-login.html';
    })

})