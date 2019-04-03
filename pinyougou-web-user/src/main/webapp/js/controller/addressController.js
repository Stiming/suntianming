app.controller('addressController', function($scope, $controller, $location, addressService,loginService){
    $scope.address = {};


    $scope.showName=function(){

        loginService.showName().success(
            function(response){
                $scope.loginName=response.loginName;
            }
        );
    };

    //查询省
    $scope.selectProvincesList = function(){
        //查询省级集合
        addressService.selectProvincesList().success(function (response){
            $scope.provincesList = response;

        });

    };
    // 查询市:
    $scope.$watch("address1.provinceid",function(newValue,oldValue){
        addressService.selectCityList(newValue).success(function(response){
            $scope.citiesList = response;
            $scope.areasList ="[]";
        });
    });
// 查询县:
    $scope.$watch("address1.cityid",function(newValue,oldValue){
        addressService.selectTownList(newValue).success(function(response){
            $scope.areasList = response;
        });
    });

    //保存
    $scope.save=function(){
        addressService.save($scope.address1).success(function (response) {
            alert(response.message);
            location.href="home-setting-address.html";
        })

    };
    //修改默认地址
    $scope.setDefaultAddress=function(aid){

        addressService.setDefaultAddress(aid).success(function(response){
            if(response.flag){
                //重新查询
                alert(response.message);
                location.href="home-setting-address.html";
            }else{
                alert(response.message);
            }

        })
    };
    $scope.deleteAddress=function(address){
        addressService.deleteAddress(address).success(function(response){
            if(response.flag){
                //重新查询
                alert(response.message);
                location.href="home-setting-address.html";
            }else{
                alert(response.message);
            }

        })
    };

    //编辑地址传参
    $scope.updateAddress=function(address){
        $scope.address1=address;
        alert(address.provinceId)
    };
    //初始化地址集合
    $scope.findAddressList=function(){

        addressService.findAddressList().success(
            function(response){
                $scope.addressList=response;
                for(var i=0;i<$scope.addressList.length;i++){
                    if($scope.addressList[i].isDefault=='1'){
                        $scope.address=$scope.addressList[i];
                        break;
                    }
                }

            }
        );
    }
});