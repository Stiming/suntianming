 //控制层 
app.controller('seckillController' ,function($scope,$controller,itemCatService   ,seckillService){
	
	$controller('baseController',{$scope:$scope});//继承


	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
        seckillService.dele( $scope.selectIds ).success(
			function(response){
				if(response.flag){
					$scope.reloadList();//刷新列表
					$scope.selectIds = [];
				}						
			}		
		);				
	}
	
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
    
	// 显示状态
	$scope.status = ["未审核","审核通过","审核未通过","关闭"];

	
	// 审核的方法:
	$scope.updateStatus = function(status){
        seckillService.updateStatus($scope.selectIds,status).success(function(response){
			if(response.flag){
                alert(response.message);
				$scope.reloadList();//刷新列表
				$scope.selectIds = [];
			}else{
				alert(response.message);
			}
		});
	}
});	
