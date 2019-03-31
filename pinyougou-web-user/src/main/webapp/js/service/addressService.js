app.service('addressService',function($http){

    this.selectProvincesList=function(){
        return $http.get('../address/selectProvincesList.do');
    };

    this.selectCityList=function(provinceid){
        return $http.get('../address/selectCityList.do?provinceid='+provinceid);
    };

    this.selectTownList=function(cityid){
        return $http.get('../address/selectTownList.do?cityid='+cityid);
    };

});