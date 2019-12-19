var TT = TAOTAO = {
	checkLogin : function(){
		var _ticket = $.cookie("COOKIE_TOKEN_KEY");
		if(!_ticket){
			return ;
		}
		$.ajax({
			url : "http://39.108.185.153:8088/user/token/" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				if(data.status == 200){
					var username = data.data.username;
					var html = username + "，欢迎来到淘淘！<a href=\"http://39.108.185.153:8088/user/logout/" + _ticket + "\" class=\"link-logout\">[退出]</a>";
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