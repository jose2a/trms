$(document).ready(function () {

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
          redirectEmployee(employee);
     }

     let showBadRequestLoginResponse = function (response) {
          showArrayOfErrorsInUL('errors_ul', response);
     }
});