$(document).ready(function () {

     let events = [];

     ajaxGetRequest("./event/pending", {}, successfulGetEventsForEmployee);

});

function successfulGetEventsForEmployee(list) {
    events = list;

    $("#events").DataTable({
         "bProcessing": true,
         "aaData": events,
         "aoColumns": [
              { "mData": "eventId" },
              { "mData": "dateOfEvent" },
              { "mData": "timeOfEvent" },
              { "mData": "location" },
              { "mData": "description" },
              { 
              	"mData": null,
              	"mRender": function(e) {
              		if (e.requiredPresentation) {
              			return "Yes";
              		}
              		
              		return "No";
              	}
              },
              { "mData": "cost" },
              { "mData": "projectedAmountReimbused" },
              { "mData": "acceptedAmountReimbursed" },
              { "mData": "reimbursementStatus" },
              {
                  "mData": null,
                  "bSortable": false,
                  "mRender": function (e) {
                       
                            return `<a class="btn btn-md btn-primary" href=./reviewEvent.html?eventId=${e.eventId}>
                                 <i class="fa fa-edit"></i> Review Request
                            </a>`;
                   }
             }
         ]
    });
    
    ajaxGetRequest("./employeeinfo", {}, function(employee) {
    	if (employee.employeeTypes.includes("Benefits_Coordinator")) {
			$("#req-info-li").hide();
		} else {
			ajaxGetRequest("./inforeq", {}, renderReqInfoList);
		}
    });
}