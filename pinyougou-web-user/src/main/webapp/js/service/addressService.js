app.service('addressService',function($http){
    this.selectProvincesList=function(){
        return $http.get('../address/selectProvincesList.do');
    }
    this.selectCityList=function(provinceId){
        return $http.get('../address/selectProvincesList.do?provinceId='+provinceId);
    }
    this.selectTownList=function(cityId){
        return $http.get('../address/selectProvincesList.do?cityId='+cityId);
    }

});