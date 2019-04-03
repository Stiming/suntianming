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
    //增加
    this.save=function(address){
        return  $http.post('../address/save.do',address );
    };
    //修改默认地址
    this.setDefaultAddress=function(aid){
        return  $http.get('../address/setDefaultAddress.do?id='+aid );
    };
//获取当前登录账号的收货地址
    this.findAddressList=function(){
        return $http.get('address/findListByLoginUser.do');
    };
    this.deleteAddress=function(address){
        return $http.post('address/deleteAddress.do',address);
    }

});