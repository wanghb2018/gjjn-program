function getNoticeList(){
    var menuPage = $('#menu-page');
    menuPage.click();
    menuPage.addClass("mui-active");
    $('#jd-page').removeClass("mui-active");
    $('#menus').hide();
    $('#losejn').show();
    mui.get('getNoticeList',function(data){
        if(data && data.length > 0){
            var str = "";
            for(var i = 0;i<data.length;i++){
                var item = data[i];
                var date = new Date(item.createdTime);
                str = str + `<div style="color: wheat; line-height: 24px;"><h3 style="color: cyan">${date.getFullYear()}年${date.getMonth() + 1}月${date.getDate()}日</h3><h4>${item.content}</h4><p style="color: white; font-size: 16px;">${item.detail}</p><div><hr>`;
            }
            $('#losejnul').html(str);
        }
    })
}
function back_losejn(){
    $('#losejn').hide();$('#menus').show();
}