function ajaxPostRequest(url, paramObj, callbackFunc) {
     $.ajax(url, {
          type: "POST",
          data: paramObj,
          statusCode: {
               200: function (response) {
                    // OK
                    console.log('success');
                    callbackFunc(response);
               },
               201: function (response) {
                   // Created
               },
               400: function (response) {
                    // Bad Request (Validation errors)
                    console.log(response.responseJSON);
                    for (const err of response.responseJSON) {
                         let li = $('<li>').html(err);
                         $('#errors_ul').append(li);
                    }
               },
               401: function(response) {
                    // Unauthorized
               },
               403: function(response) {
                    // Forbidden
               },
               404: function (response) {
                    // Not Found
                    console.log(response);
               }
          }
     });
     
}

function ajaxGetRequest(url, paramObj, callbackFunc) {
     $.ajax(url, {
          type: "GET",
          data: paramObj,
          statusCode: {
               200: function (response) {
                    // OK
                    console.log('success');
                    callbackFunc(response);
               },
               201: function (response) {
                    // Created
               },
               400: function (response) {
                    // Bad Request (Validation errors)
                    console.log(response.responseJSON);
                    for (const err of response.responseJSON) {
                         let li = $('<li>').html(err);
                         $('#errors_ul').append(li);
                    }
               },
               401: function (response) {
                    // Unauthorized
               },
               403: function (response) {
                    // Forbidden
               },
               404: function (response) {
                    // Not Found
                    console.log(response);
               }
          }
     });

}

function redirect(url) {
     window.location = url;
}

function saveEmployeeToSession(employee) {
     sessionStorage.setItem('employee', JSON.stringify(employee));
}

function getEmployeeFromSession() {
     let emp = JSON.parse(sessionStorage.getItem('employee'));

     return emp;
}

function isEmployeeInSession() {
     let emp = getEmployeeFromSession();

     console.log(emp);
     return emp !== null;
}

function deleteEmployeeFromSession() {
     sessionStorage.removeItem('employee');
}