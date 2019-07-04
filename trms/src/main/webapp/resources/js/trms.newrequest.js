$(document).ready(function() {

     (function() {
          getEmployee();
          
          $('#event_date').datepicker();
          
          $('#event_time').timepicker({
        	  showInputs: false,
        	  showMeridian: false,
        	  showSeconds: true
          });
     }());

     $("#eventTypeId").click(function () {
          $(this).unbind('click');
          getEventTypes();
     });

     $("#gradingFormatId").click(function () {
          $(this).unbind('click');
          getGradingFormats();
     })

     function getEmployee() {
          ajaxGetRequest("./employeeinfo", {}, displayEmployee);
     }

     function getGradingFormats() {
          ajaxGetRequest("./gradingformat", {}, displayGradingFormats);
     }

     function getEventTypes() {
          ajaxGetRequest("./event/type", {}, displayEventTypes, false);
     }

     function displayEmployee(employee) {
          $("#emp_firstname").text(employee.firstName);
          $("#emp_lastname").text(employee.lastName);
          $("#emp_email").text(employee.email);
     }

     function displayEventTypes(eventTypes) {
          console.log(eventTypes);

          $.each(eventTypes, function (i, item) {
               $('#eventTypeId').append($('<option>', {
                    value: item.eventTypeId,
                    text: `${item.name} (${item.reimburseCoverage})`
               }));
          });
     }

     function displayGradingFormats(gradingFormats) {
          console.log(gradingFormats);

          $.each(gradingFormats, function (i, item) {
               $('#gradingFormatId').append($('<option>', {
                    value: item.gradingFormatId,
                    text: `From: ${item.fromRange}, To: ${item.toRange}`
               }));
          });
     }

     $("form[name='event_form']").validate({
          // Specify validation rules
          rules: {
               // The key name on the left side is the name attribute
               // of an input field. Validation rules are defined
               // on the right side
               event_date: "required",
               event_time: "required",
               event_location: "required",
               event_description: "required",
               event_cost: {
                    required: true,
                    min: 0
               },
               event_justification: "required",
               event_gradecutoff: "required",
               eventTypeId: "required"
          },
         // Specify validation error messages
         messages: {
              event_date: "Please enter the date of the even",
              event_time: "Please enter the time of the event",
              event_location: "Please enter the location of the event",
              event_description: "Please enter the description of the event",
              event_cost: "Cost required",
              event_justification: "Please enter a work-related justification",
              event_gradecutoff: "Please enter a grade cutoff",
              eventTypeId: "Please select the type of the event"
        },
         // Make sure the form is submitted to the destination defined
         // in the "action" attribute of the form when valid
         submitHandler: function (form) {
       		  console.log('sending');
//                 $(form).submit();
       		  sendForm(form);
         }
     });

     function sendForm(form) {

          let url = $(form).attr('action');
          
          console.log(url);

          let event_date = $("#event_date").val();
          let event_time = $("#event_time").val();
          
          console.log(event_time);
          let event_location = $("#event_location").val();
          let event_description = $("#event_description").val();
          let event_cost = $("#event_cost").val();
          let event_workJustification = $("#event_justification").val();
          let event_workTimeMissed = $("#event_worktimemissed").val();
          let event_requiredPresentation = $("#event_reqpres").is(":checked");
          let event_gradingFormatId = $("#gradingFormatId").val();
          let grade_from = $("#from").val();
          let grade_to = $("#to").val();
          let event_gradeCutoff = $("#event_gradecutoff").val();
          let event_eventTypeId = $("#eventTypeId").val();

          let paramObj = makeEventForSubmit(event_date, event_time, event_location,
               event_description, event_cost, event_workJustification, event_requiredPresentation,
               event_eventTypeId, event_gradingFormatId, event_workTimeMissed, event_gradeCutoff,
               grade_from, grade_to);
          

          ajaxPostRequest(url, JSON.stringify(paramObj),
                showSucessfulFormRequestResponse,
                showBadFormRequestResponse);
     }

     let showSucessfulFormRequestResponse = function (employee) {
         
     }

     let showBadFormRequestResponse = function(response) {
          showArrayOfErrorsInUL('errors_ul', response);
     }
});