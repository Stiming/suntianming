//服务层
app.service('seckillService',function($http){

	//删除
	this.dele=function(ids){
		return $http.get('../seckill/delete.do?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../seckill/search.do?page='+page+"&rows="+rows, searchEntity);
	}    	
});
