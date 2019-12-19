<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="importAll()">一键导入商品数据到索引库</a>
</div>
<script type="text/javascript">
function importAll() {
	// 移除原有按钮
	$('.l-btn-text').remove("span");
	// 追加不可点击按钮
	$('.l-btn-left').after('<input type="button" disabled="true" id="disabledButton" value="正在导入商品搜索索引库，请稍后......">');
	$.post("/index/importAll",null,function(data) {
		// 移除不可点击按钮
		$('#disabledButton').remove("input");
		// 追加原有按钮
		$('.l-btn-left').after('<span class="l-btn-text">一键导入商品数据到索引库</span>');
		if (data.status==200) {
			$.messager.alert('提示','商品数据导入索引库成功！');
		} else {
			$.messager.alert('提示','商品数据导入索引库失败！');
		}
	});	
}
</script>