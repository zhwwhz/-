<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	 <ul id="contentCategory" class="easyui-tree">  </ul>
</div>
<div id="contentCategoryMenu" class="easyui-menu" style="width:120px;" data-options="onClick:menuHandler">
    <div data-options="iconCls:'icon-add',name:'add'">添加</div>
    <div data-options="iconCls:'icon-remove',name:'rename'">重命名</div>
    <div class="menu-sep"></div>
    <div data-options="iconCls:'icon-remove',name:'delete'">删除</div>
</div>
<script type="text/javascript">
// 页面初始化完成之后，调用下面方法
$(function(){
	$("#contentCategory").tree({
		url : '/content/category/list', // 初始化tree控件
		animate: true, // 动画效果
		method : "GET",
		onContextMenu: function(e,node){
            e.preventDefault();
            $(this).tree('select',node.target); // 选中当前节点
            $('#contentCategoryMenu').menu('show',{
                left: e.pageX,
                top: e.pageY
            });
        },
        // 在添加节点被编辑之后触发
        onAfterEdit : function(node){
        	var _tree = $(this); // 获取树本身
        	if(node.id == 0){
        		// 新增节点
        		$.post("/content/category/create",{parentId:node.parentId,name:node.text},function(data){
        			if(data.status == 200){
        				_tree.tree("update",{
            				target : node.target, // 更新哪一个节点
            				id : data.data.id // 更新新增节点的id
            			});
        			}else{
        				$.messager.alert('提示','创建'+node.text+' 分类失败!');
        			}
        		});
        	}else{
        		// 节点改名
        		$.post("/content/category/update",{id:node.id,name:node.text});
        	}
        }
	});
});
// 处理点击菜单的事件
function menuHandler(item){
	// 获取树节点
	var tree = $("#contentCategory");
	// 获取被选中的节点，就是右击鼠标时的节点
	var node = tree.tree("getSelected");
	// 判断选择的是“添加”还是“重命名”还是“删除”
	if(item.name === "add"){  // 在js中，1==1 true 1=="1" true
		tree.tree('append', { // 在js中，1===1 true 1==="1" false
			// 在被点击的节点下追加一个子节点
            parent: (node?node.target:null), // 被追加的子节点的父
            data: [{
                text: '新建分类', // 节点的内容
                id : 0, // 节点的id
                parentId : node.id // 新建节点的父节点的id
            }]
        }); 
		var _node = tree.tree('find',0); // 获取根节点
		tree.tree("select",_node.target).tree('beginEdit',_node.target); // 开启编辑
	}else if(item.name === "rename"){
		tree.tree('beginEdit',node.target);
	}else if(item.name === "delete"){
		$.messager.confirm('确认','确定删除名为 '+node.text+' 的分类吗？',function(r){
			 if (r) {
				$.post("/content/category/delete/",{id:node.id},function(data){
					if(data.status == 200){
						// 删除前台的节点
						tree.tree("remove",node.target);
						$.messager.alert('提示',node.text+' 分类删除成功！');
					}else if(data.status == 500){
						// 提示服务端返回的自定义消息
						$.messager.alert('提示',data.msg);
					} else {
						$.messager.alert('提示',node.text+' 分类删除失败！');
					}
				});	
			}  
		}); 
	}
}
</script>