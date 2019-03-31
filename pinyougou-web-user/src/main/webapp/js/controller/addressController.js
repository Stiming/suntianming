app.controller('addressController', function($scope, $controller, $location, addressService){
    $scope.entity = {};
    //查询省
    $scope.selectProvincesList = function(){
        //查询省级集合
        addressService.selectProvincesList().success(function (response){
            $scope.provincesList = response;

        });

    };
    // 查询市:
    $scope.$watch("entity.address.provinceid",function(newValue,oldValue){
        addressService.selectCityList(newValue).success(function(response){
            $scope.citiesList = response;
            $scope.areasList ="[]";
        });
    });
// 查询县:
    $scope.$watch("entity.address.cityid",function(newValue,oldValue){
        addressService.selectTownList(newValue).success(function(response){
            $scope.areasList = response;
        });
    });

});