$(document).ready(function () {

     (function checkIfSessionIsLoggedIn() {
          console.log('checking user in session');
          console.log(isEmployeeInSession());

          if (isEmployeeInSession()) {
               console.log('Redirecting');

               redirectEmployee(getEmployeeFromSession());
          }
     }());

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

          saveEmployeeToSession(employee);
          redirectEmployee(employee);
     }

     let showBadRequestLoginResponse = function (response) {
          showArrayOfErrorsInUL('errors_ul', response);
     }

     function redirectEmployee(employee) {
          for (const role of employee.employeeTypes) {

               if (role === "Direct_Supervisor"
                    || role == "Head_Department"
                    || role == "Benefits_Coordinator") {
                    redirect("dashboard.html");
               }
               if (role == "Associate") {
                    redirect("dashboardEmp.html");
               }
          }
     }
});