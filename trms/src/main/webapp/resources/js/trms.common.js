function ajaxPostRequest(url, paramObj, callbackFuncSucc, callbackFuncBadReq, sync) {
     $.ajax(url, {
          type: "POST",
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

function ajaxUploadFile(url, data, uploadFileSucc, uploadAttachmentBadR) {
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
               uploadAttachmentBadR(response);
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