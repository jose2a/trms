
function ajaxPostRequest(url, paramObj, callbackFuncSucc, callbackFuncBadReq, sync) {
     $.ajax(url, {
          type: "post",
          data: paramObj,
          async: sync || true,
          statusCode: {
        	     200: function (response) {
        	          // OK
        	          callbackFuncSucc(response);
        	     },
        	     400: function (response) {
        	          // Bad Request (Validation errors)
        	          callbackFuncBadReq(response);
        	     },
        	     401: function (response) {
        	          // Unauthorized
        	    	 redirectIfNotLoginPage();
        	     },
        	     403: function (response) {
        	          // Forbidden
        	          console.log(response);
        	          // redirect("index.html");
        	     },
        	     404: function (response) {
        	          // Not Found
        	          console.log(response);
        	          redirect("404.html");
        	     },
        	     500: function (response) {
        	          redirect("500.html");
        	     }
        	}
     });
     
}

function ajaxGetRequest(url, paramObj, callbackFuncSucc, callbackFuncBadReq, syn) {
     $.ajax(url, {
          type: "GET",
          data: paramObj,
          async: syn || true,
          statusCode: {
        	     200: function (response) {
        	          // OK
        	          callbackFuncSucc(response);
        	     },
        	     400: function (response) {
        	          // Bad Request (Validation errors)
        	          callbackFuncBadReq(response);
        	     },
        	     401: function (response) {
        	          // Unauthorized
        	          console.log(response);
        	          redirectIfNotLoginPage();
        	     },
        	     403: function (response) {
        	          // Forbidden
        	          console.log(response);
        	          // redirect("index.html");
        	     },
        	     404: function (response) {
        	          // Not Found
        	          console.log(response);
        	          redirect("404.html");
        	     },
        	     500: function (response) {
        	          redirect("500.html");
        	     }
        	}
     });

}

function ajaxPutRequest(url, paramObj, callbackFuncSucc, callbackFuncBadReq, syn) {
     $.ajax(url, {
          type: "PUT",
          data: paramObj,
          async: syn || true,
          statusCode: {
        	     200: function (response) {
        	          // OK
        	          callbackFuncSucc(response);
        	     },
        	     400: function (response) {
        	          // Bad Request (Validation errors)
        	          callbackFuncBadReq(response);
        	     },
        	     401: function (response) {
        	          // Unauthorized
        	          console.log(response);
        	          redirectIfNotLoginPage();
        	     },
        	     403: function (response) {
        	          // Forbidden
        	          console.log(response);
        	          // redirect("index.html");
        	     },
        	     404: function (response) {
        	          // Not Found
        	          console.log(response);
        	          redirect("404.html");
        	     },
        	     500: function (response) {
        	          redirect("500.html");
        	     }
        	}
     });

}

function ajaxUploadFile(url, data, uploadFileSucc, uploadFileBadReq) {
     $.ajax({
          url: url,
          type: 'post',
          data: data,
          contentType: false,
          processData: false,
          success: function (response) {
               if (response != 0) {
                    uploadFileSucc(response);
               } 
          },
          error: function (response) {
               uploadFileBadReq(response);
          }
     });
}

function redirectIfNotLoginPage() {
	if (!window.location.href.endsWith("trms/")) {
		redirect("./");
	}
	
	console.log("Login page");
}

function redirect(url) {
     var dialog = bootbox.dialog({
          closeButton: false,
          message: '<p><i class="fa fa-spin fa-spinner"></i> Loading...</p>'
     });

     dialog.init(function () {
          setTimeout(function () {
               window.location = url;
          }, 1000);
     });
     
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

     return emp !== null;
}

function deleteEmployeeFromSession() {
     sessionStorage.removeItem('employee');
}

function getUrlParamValue(variableName) {
     let url = new URL(window.location.href);
     return url.searchParams.get(variableName);
}

function showArrayOfErrorsInUL(ulEle, response) {
     let ul = $(`#${ulEle}`).html('');

     for (const err of response.responseJSON) {
          let li = $('<li>').html(err);
          ul.append(li);
     }
}

(function checkEmployeeIsLoggedIn() {
	ajaxGetRequest("./employeeinfo", {}, function(emp) {
		console.log(emp);
		
		if (emp !== null) {
			$(".fullname").text(`${emp.firstName} ${emp.lastName}`);
			
			if (window.location.href.endsWith("trms/")) {
	    		redirectEmployee(emp);
	    	     
	    	}
		}
    }, function (response) {
    });
	
}());

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

function makeEventForSubmit(event_date, event_time, event_location, event_description,
     event_cost, event_workJustification, event_requiredPresentation, event_eventTypeId,
     event_gradingFormatId, event_workTimeMissed, event_gradeCutoff, grade_from, grade_to) {

     return {
          dateOfEvent: event_date,
          timeOfEvent: event_time,
          location: event_location,
          description: event_description,
          cost:  event_cost,
          workJustification: event_workJustification,
          requiredPresentation: event_requiredPresentation,
          eventTypeId: event_eventTypeId,
          gradingFormatId: event_gradingFormatId,
          workTimeMissed: event_workTimeMissed,
          gradeCutoff: event_gradeCutoff,
          from: grade_from,
          to: grade_to
     };
}