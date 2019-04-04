 //控制层 
app.controller('seckillController' ,function($scope,$controller,$location,typeTemplateService ,itemCatService,uploadService ,seckillService){
	
	$controller('baseController',{$scope:$scope});//继承

	$scope.searchEntity={};//定义搜索对象 
	//搜索
	$scope.search=function(page,rows){			
		seckillService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}


    // 定义一个数组:
    $scope.selectIds = [];
    // 更新复选框：
    $scope.updateSelection = function($event,id){
        // 复选框选中
        if($event.target.checked){
            // 向数组中添加元素
            $scope.selectIds.push(id);
        }else{
            // 从数组中移除
            var idx = $scope.selectIds.indexOf(id);
            $scope.selectIds.splice(idx,1);
        }

    }


    // 删除品牌:
    $scope.dele = function(){
		if ($scope.selectIds.length > 0){
		if (confirm("您确定要删除吗?")){
        seckillService.dele($scope.selectIds).success(function(response){
            // 判断删除是否成功:
            if(response.flag==true){
                // 删除成功
                alert(response.message);
                $scope.reloadList();
                $scope.selectIds = [];
            }else{
                // 保存失败
                alert(response.message);
            }
        });

        }
        }
    }

});	
