 //控制层 
app.controller('tongjiController' ,function($scope,$controller,$location ,tongjiService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中
	$scope.findAll=function(){

		tongjiService.findAll().success(
			function(response){

                $scope.list=response;

			}

		);
	}    











});	
