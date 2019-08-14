
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
	if (!window.location.href.endsWith("index.html")) {
		redirect("./index.html");
	}
	
	console.log("Login page");
}

function redirect(url) {
//     var dialog = bootbox.dialog({
//          closeButton: false,
//          message: '<p><i class="fa fa-spin fa-spinner"></i> Loading...</p>'
//     });

//     dialog.init(function () {
//          setTimeout(function () {
//               window.location = url;
//          }, 800);
//     });
	
	setTimeout(function () {
        window.location = url;
    }, 800);
     
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
		
		if (emp !== null) {
			$(".fullname").text(`${emp.firstName} ${emp.lastName}`);
			
			if (window.location.href.endsWith("trms/")) {
	    		redirectEmployee(emp);
	    	}
		}
    });
	
}());

function renderReqInfoList(reqInfoList) {
	 reqInfCounter = reqInfoList.length;
	 
	 $("#reqInfoCounter").text(reqInfCounter);
	 
	 let tableBody = $("#inf-req-table > tbody");
	 tableBody.html('');
	 
	 for (let infReq of reqInfoList) {
		 let role = "";
		 
		 if(infReq.requireBy.employeeTypes.includes("Benefits_Coordinator")) {
			 role = "BenCo";
		 } else if(infReq.requireBy.employeeTypes.includes("Head_Department")) {
			 role = "Department Head";
		 } else if(infReq.requireBy.employeeTypes.includes("Direct_Supervisor")) {
			 role = "Direct Supervisor";
		 } 
		 
		 tableBody.html(
		`<tr>
		 	<td>${infReq.eventId}</td>
			<td>${infReq.information}</td>
			<td>${infReq.requireBy.firstName} ${infReq.requireBy.lastName} (${role})</td>
			<td><input class="chk_provide" id="chk_provide_${infReq.eventId}" type="checkbox" value="${infReq.eventId}"></td>
		</tr>`);
	}
	 
	 $(".chk_provide").change(function (e) {
	   	 let eventId = $(this).val();
	   	 let tr = $(this).closest("tr");
	   	 
	   	 let objParam = {
	   			 eventId: eventId
	   	 };
	   	 
	   	 ajaxPutRequest("./inforeq", JSON.stringify(objParam), function() {
	   		 tr.parent().remove();
	       	 
	   		 reqInfCounter--;
	   		 $("#reqInfoCounter").text(reqInfCounter);
	   	 });
	 });
	 
}

function redirectEmployee(employee) {
	if (employee.employeeTypes.includes("Direct_Supervisor")
			|| employee.employeeTypes.includes("Head_Department")
			|| employee.employeeTypes.includes("Benefits_Coordinator")) {
		
		redirect("dashboard.html");
	} else if (employee.employeeTypes.includes("Associate")) {
		redirect("dashboardEmp.html");
	}
}