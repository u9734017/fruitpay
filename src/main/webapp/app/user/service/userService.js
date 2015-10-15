'use strict';
 
	angular
        .module('app')
        //.factory('userService', userLocalStorageService);
		.factory('userService', userService);
	
	userService.$inject = ['$http'];
	function userService($http){
		var service = {};
		service.signup = signup;
		service.login = login;
		
		return service;
		
		function signup(user){
			return $http.post('loginCtrl/signup', user).then(successCallback, errorCallback);
		}
		
		function login(user){
			
		}
		
		function successCallback(response){
			var returnData = response.data;
			if(returnData.errorCode == '0'){
				return true;
			}else{
				console.log(response);
				return false;
			}
		}
		
		function errorCallback(response){
			console.log(response);
			return false;
		}
	}
	
	
 
    userLocalStorageService.$inject = ['$timeout', '$filter', '$q'];
    function userLocalStorageService($timeout, $filter, $q) {
 
        var service = {};
 
        service.getByUserEmail = getByUserEmail;
        service.create = create;
        service.update = update;
        service.del = del;
 
        return service;
 
        function getByUserEmail(email) {
			
            var deferred = $q.defer();
            var filtered = $filter('filter')(getUsers(), { email: email });
            var user = filtered.length ? filtered[0] : null;
            deferred.resolve(user);
            return deferred.promise;
        }
 
        function create(user) {
            var deferred = $q.defer();
			
            // simulate api call with $timeout
            $timeout(function () {
                getByUserEmail(user.email)
                    .then(function (duplicateUser) {
                        if (duplicateUser !== null) {
                            deferred.resolve({ success: false, message: 'Email "' + user.email + '" is already taken' });
                        } else {
                            var users = getUsers();
 
                            // assign id
                            var lastUser = users[users.length - 1] || { id: 0 };
                            user.id = lastUser.id + 1;
 
                            // save to local storage
                            users.push(user);
                            setUsers(users);
 
                            deferred.resolve({ success: true });
                        }
                    });
            }, 1000);
 
            return deferred.promise;
        }
 
        function update(user) {
            var deferred = $q.defer();
 
            var users = getUsers();
            for (var i = 0; i < users.length; i++) {
                if (users[i].id === user.id) {
                    users[i] = user;
                    break;
                }
            }
            setUsers(users);
            deferred.resolve();
 
            return deferred.promise;
        }
 
        function del(id) {
            var deferred = $q.defer();
 
            var users = getUsers();
            for (var i = 0; i < users.length; i++) {
                var user = users[i];
                if (user.id === id) {
                    users.splice(i, 1);
                    break;
                }
            }
            setUsers(users);
            deferred.resolve();
 
            return deferred.promise;
        }
 
        // private functions
 
        function getUsers() {
            if(!localStorage.users){
                localStorage.users = JSON.stringify([]);
            }
			console.log(localStorage.users);
            return JSON.parse(localStorage.users);
        }
 
        function setUsers(users) {
            localStorage.users = JSON.stringify(users);
        }
	}