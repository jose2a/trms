$(document).ready(function () {

//     (function checkIfSessionIsLoggedIn() {
//          console.log('checking user in session');
//          console.log(isEmployeeInSession());
//
//          if (isEmployeeInSession()) {
//               console.log('Redirecting');
//
//               redirectEmployee(getEmployeeFromSession());
//          }
//     }());

     $("#login_form").submit(function (e) {
          e.preventDefault();

          let url = $(this).attr('action');

          let paramObj = {
               username: $("[name='username']").val(),
               password: $("[name='password']").val()
          };

          ajaxPostRequest(url, paramObj, showSucessfulLoginResponse, showBadRequestLoginResponse);
     });

     let showSucessfulLoginResponse = function (employee) {
          console.log(employee);
          
          redirectEmployee(employee);
     }

     let showBadRequestLoginResponse = function (response) {
          showArrayOfErrorsInUL('errors_ul', response);
     }
});