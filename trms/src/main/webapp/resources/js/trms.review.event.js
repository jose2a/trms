$(document).ready(function() {

     let eventId = 0;

     function getEvent () {

          eventId = getUrlParamValue("eventId");

          $("#eventId").val(eventId);
          
          ajaxGetRequest(`./event/id/${eventId}`, {}, showSucessfulEventResponse, showBadRequestEventResponse);
     }

     
     let showSucessfulEventResponse = function(event) {
          console.log(event);

          renderEmployee(event.employee);
          renderEvent(event);
          renderGradingFormat(event.gradingFormat);
          renderEventType(event.eventType);

          renderButtons(event);
     }
     
     let showBadRequestEventResponse = function(response) {
          console.log(response);
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
          //reimbursementStatus
          if (event.reimbursementStatus === "Pending") {
               $("#approval_denial_btng").show();
               $("#confirm_btng").hide();
          }

          if (event.reimbursementStatus === "Approved") {
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

     }

     let showBadFormRequestResponse = function (response) {
          showArrayOfErrorsInUL('errors_ul', response);
     }

     getEvent();

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
          }, showBadFormRequestResponse);
          
     });

     $("#deny_btn").click(function (e) {
          e.preventDefault();

          console.log("Denying request");

          let url = $(this).attr("href");

          bootbox.prompt({
               title: "Enter the resson why you are denying this request.",
               centerVertical: true,
               callback: function (result) {
                    if (result === true) {
                         let objParam = {
                              eventId: eventId,
                              reason: result
                         };

                         toastr.success(`Request denied.`);

                         ajaxPostRequest(url, JSON.stringify(objParam), function () {
                              redirect("./dashboard.html");
                         }, showBadFormRequestResponse);
                    }
               }
          });
     });

     $("#conf_pres_btn").click(function (e) {
          e.preventDefault();

          console.log("Conf. presentation");

          ajaxPutRequest($(this).attr("href"), { eventId: eventId }, function () {
               redirect("./dashboard.html");
          }, showBadFormRequestResponse);

     });

     $("#conf_grade_btn").click(function (e) {
          e.preventDefault();

          console.log("Conf. grade");

          ajaxPutRequest($(this).attr("href"), { eventId: eventId }, function () {
               redirect("./dashboard.html");
          }, showBadFormRequestResponse);

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
                              }, showBadFormRequestResponse);
                         }
                    }
               });

     });

     $("#req_inf_emp").click(function (e) {
          e.preventDefault();

          console.log("Request Inf Emp");

     });

     $("#req_inf_ds").click(function (e) {
          e.preventDefault();

          console.log("Request Inf DS");

     });

     $("#req_inf_hd").click(function (e) {
          e.preventDefault();

          console.log("Request Inf DH");

     });
});