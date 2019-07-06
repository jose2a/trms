$(document).ready(function() {

     let eventId = 0;
     
     eventId = getUrlParamValue("eventId");
     $("#eventId").val(eventId);
          
     ajaxGetRequest(`./event/id/${eventId}`, {}, showSucessfulEventResponse);
     
});

let showSucessfulEventResponse = function(event) {
    console.log(event);

    renderEmployee(event.employee);
    renderEvent(event);
    renderGradingFormat(event.gradingFormat);
    renderEventType(event.eventType);

    renderButtons(event);
    
    renderWizard(event);
    
    renderAskInf(event);
};

function renderAskInf(event) {
	 if(event.dsEventStatus === "Pending") {
		 $("#req_inf_ds").hide();
		 $("#req_inf_hd").hide();
	 } else if(event.hdEventStatus === "Pending") {
		 $("#req_inf_hd").hide();
	 }
}

function renderWizard(event) {
	 if(event.dsEventStatus === "Pending") {
		 $("#ds_step").addClass("btn-primary");
	 } else if(event.hdEventStatus === "Pending") {
		 $("#dh_step").addClass("btn-primary");
	 } else if(event.bencoEventStatus === "Pending") {
		 $("#benco_step").addClass("btn-primary");
	 }
}

function renderEmployee(employee) {
	 $("#emp_firstname").val(employee.firstName);
	 $("#emp_lastname").val(employee.lastName);
	 $("#emp_email").val(employee.email);
}

function renderEvent(event) {
    $("#event_date").val(event.dateOfEvent);
    $("#event_time").val(event.timeOfEvent);
    $("#event_location").val(event.location);
    $("#event_description").val(event.description);
    $("#event_cost").val(event.cost);
    $("#event_justification").val(event.workJustification);
    $("#event_worktimemissed").val(event.workTimeMissed);
    $("#event_reqpres").prop('checked', event.requiredPresentation);
    $("#event_gradecutoff").val(event.gradeCutoff);
    $("#event_finalgrade").val(event.finalGrade);
    $("#event_projected").val(event.projectedAmountReimbused);
    $("#event_accepted").val(event.acceptedAmountReimbursed);
    
    if (!event.urgent) {
         $("#event_isurgent").hide();
    }
}

function renderGradingFormat(gradingF) {
    $("#gradingFormatId").val(`From: ${gradingF.fromRange}, To: ${gradingF.toRange}`);
}

function renderEventType(eventT) {
    $("#eventTypeId").val(eventT.name);
}

function renderButtons(event) {
    // reimbursementStatus
    if (event.reimbursementStatus === "Pending") {
         $("#approval_denial_btng").show();
         $("#confirm_btng").hide();
    }

    if (event.bencoEventStatus === "Approved") {
         $("#approval_denial_btng").hide();
         $("#confirm_btng").show();
         $("#additional_inf_btng").hide();

         if (event.presentationUploaded) {
              $("#conf_pres_btn").show();
              $("#conf_grade_btn").hide();
         } else {
              $("#conf_grade_btn").show();
              $("#conf_pres_btn").hide();
         }
    }
    
    ajaxGetRequest("./employeeinfo", {}, function(employee) {
  	  if(!employee.employeeTypes.includes("Benefits_Coordinator")) {
  		  $("#change_amt").hide();
  	  }
    });

}

let showBadFormRequestResponse = function (response) {
    showArrayOfErrorsInUL('errors_ul', response);
};

// =========================== Events =============================

$("#approve_btn").click(function (e) {
    e.preventDefault();

    console.log("Approving request");

    let objParam = {
         eventId: eventId
    };

    ajaxPostRequest($(this).attr("href"), JSON.stringify(objParam), function() {
         toastr.success(`Request approved.`);
         redirect("./dashboard.html");
    });
    
});

$("#deny_btn").click(function (e) {
    e.preventDefault();

    console.log("Denying request");

    let url = $(this).attr("href");

    bootbox.prompt({
         title: "Enter the reason why you are denying this request.",
         callback: function (result) {
      	   
      	   console.log(result);
      	   
              if (result) {
                   let objParam = {
                        eventId: eventId,
                        reason: result
                   };

                   ajaxPostRequest(url, JSON.stringify(objParam), function () {
                  	 toastr.success(`Request denied.`);
                        redirect("./dashboard.html");
                   });
              }
         }
    });
});

$("#conf_pres_btn").click(function (e) {
    e.preventDefault();

    console.log("Conf. presentation");
    
    let objParam = {
  		eventId: eventId
    };

    ajaxPostRequest($(this).attr("href"), JSON.stringify(objParam), function () {
         redirect("./dashboard.html");
    });

});

$("#conf_grade_btn").click(function (e) {
    e.preventDefault();

    console.log("Conf. grade");
    
    let objParam = {
    		eventId: eventId
      };

    ajaxPostRequest($(this).attr("href"), JSON.stringify(objParam), function () {
         redirect("./dashboard.html");
    });

});

$("#change_amt").click(function (e) {
    e.preventDefault();

    console.log("Change Amt.");

    let url = $(this).attr("href");
    let oldAmt = $("#event_accepted").val();

    bootbox.confirm(`<form id='amtInfo' action=''>
                   <div class="form-group">
                        <label>New Amount </label>
                        <div class="input-group">
                             <span class="input-group-addon">$</span>
                             <input type="text" name="new_amount" id="new_amount" class="form-control" value="${oldAmt}" >
                             <span class="input-group-addon">.00</span>
                        </div>
                   </div>
                   <div class="form-group">
                        <label>Reason </label>
                        <input name="new_reason" id="new_reason" class="form-control">
                   </div>
                   </form>
                   `, function (result) {
              if (result) {
                   if (result === true) {
                        let newAmount = $('#new_amount').val();

                        let objParam = {
                             eventId: eventId,
                             newAmount: newAmount,
                             reason: $('#new_reason').val()
                        };
                        
                        ajaxPutRequest(url, JSON.stringify(objParam), function() {
                             toastr.success(`The amount for this project was changed successfully.`);
                             $("#event_accepted").val(newAmount);
                        });
                   }
              }
         });

});

function reqInfoEmp(infoFrom) {

   bootbox.prompt({
        title: "Enter what information you required from this employee.",
        callback: function (result) {
     	   
             if (result) {
                  let objParam = {
                  	requestInfoFrom: infoFrom,
                       eventId: eventId,
                       information: result
                  };

                  ajaxPostRequest("./inforeq", JSON.stringify(objParam), function () {
                  	toastr.success(`Your request for additional information was sent successfuly.`);
                  });
             }
        }
   });
}

$("#req_inf_emp").click(function (e) {
    e.preventDefault();

    console.log("Request Inf Emp");
    
    reqInfoEmp($(this).attr("href"));
});

$("#req_inf_ds").click(function (e) {
    e.preventDefault();

    console.log("Request Inf DS");

    reqInfoEmp($(this).attr("href"));
});

$("#req_inf_hd").click(function (e) {
    e.preventDefault();

    console.log("Request Inf DH");

    reqInfoEmp($(this).attr("href"));
});