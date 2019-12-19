var TT = TAOTAO = {
	checkLogin : function(){
		var _ticket = $.cookie("TT_Token");
		//var _ticket = "SsoForSession"+_ticket;
		//alert(_ticket)
		if(!_ticket){
			return ;
		}
		$.ajax({
			url : "http://120.78.192.106:8090/user/token/SsoForSession" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				if(data.status == 200){
					alert(data)
					var username = data.data.username;
					var html = username + "，欢迎来到淘淘！<a href=\"http://120.78.192.106:8090/user/logout/SsoForSession"+ _ticket +"\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
				}
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});