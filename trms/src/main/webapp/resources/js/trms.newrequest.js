$(document).ready(function() {

     (function() {
          getEmployee();
          // Getting event types
          ajaxGetRequest("./event/type", {}, displayEventTypes);
          
          $('#event_date').datepicker();
          $('#event_date').datepicker('date', new Date());
          $("#event_date").datepicker('update', new Date());
          
          $('#event_time').timepicker({
        	     showInputs: false,
               showMeridian: false,
               showSeconds: true
          });
     }());

     
});


$("#gradingFormatId").click(function () {
    $(this).unbind('click');
    getGradingFormats();
});

$("#event_cost").change(function() {
	 getProjected();
});

$("#eventTypeId").change(function() {
	 getProjected();
});

function getProjected() {
	 let paramObj = {
			 cost : $("#event_cost").val(),
			 eventTypeId: $("#eventTypeId").val()
	 };
	 
	 ajaxGetRequest("./event/projectedamt", paramObj, function(result) {
		 console.log("Projected Reimb");
		 $("#event_projected").val(result);
	 });
}

function getEmployee() {
    ajaxGetRequest("./employeeinfo", {}, displayEmployee);
}

function getGradingFormats() {
    ajaxGetRequest("./gradingformat", {}, displayGradingFormats);
}


function displayEmployee(employee) {
    $("#emp_firstname").val(employee.firstName);
    $("#emp_lastname").val(employee.lastName);
    $("#emp_email").val(employee.email);
}

function displayEventTypes(eventTypes) {

    $.each(eventTypes, function (i, item) {
         $('#eventTypeId').append($('<option>', {
              value: item.eventTypeId,
              text: `${item.name} (${item.reimburseCoverage})`
         }));
    });
}

function displayGradingFormats(gradingFormats) {

    $.each(gradingFormats, function (i, item) {
         $('#gradingFormatId').append($('<option>', {
              value: item.gradingFormatId,
              text: `From: ${item.fromRange}, To: ${item.toRange}`
         }));
    });
}

$.validator.addMethod(
    "selectGradingFormat",
    function (value, element) {
         if( value === '') {
              if ($("#from").val() !== '' && $("#to").val() !== '') {
                   return true;
              }

              return false;
         }
         
         return true;
    },
    "A grading format must be seleted. Or you should provide a grade range."
);


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
         event_gradingFormatId: {
              selectGradingFormat: true
         },
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
 		sendForm(form);
   }
});

function sendForm(form) {
    let url = $(form).attr('action');

    let event_date = $("#event_date").val();
    let event_time = $("#event_time").val();
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

let showSucessfulFormRequestResponse = function (event) {
    toastr.success(`Your request for reimbursement was received successfully.`);
    
    bootbox.confirm({
         message: "Do you have any approval document?",
         buttons: {
              confirm: {
                   label: 'Yes',
                   className: 'btn-success'
              },
              cancel: {
                   label: 'No',
                   className: 'btn-default'
              }
         },
         callback: function (result) {

              if (result == true) {

                   redirect(`approvalAttachment.html?eventId=${event.eventId}`);
              } else {
                   
                   redirect("dashboardEmp.html");
              }

         }
    });
};

let showBadFormRequestResponse = function(response) {
    showArrayOfErrorsInUL('errors_ul', response);
};

function makeEventForSubmit(event_date, event_time, event_location,
		event_description, event_cost, event_workJustification,
		event_requiredPresentation, event_eventTypeId, event_gradingFormatId,
		event_workTimeMissed, event_gradeCutoff, grade_from, grade_to) {

	return {
		dateOfEvent : event_date,
		timeOfEvent : event_time,
		location : event_location,
		description : event_description,
		cost : event_cost,
		workJustification : event_workJustification,
		requiredPresentation : event_requiredPresentation,
		eventTypeId : event_eventTypeId,
		gradingFormatId : event_gradingFormatId,
		workTimeMissed : event_workTimeMissed,
		gradeCutoff : event_gradeCutoff,
		from : grade_from,
		to : grade_to
	};
}