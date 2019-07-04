$(document).ready(function () {

     let events = [];

     function getEventsForCustomers() {
          ajaxGetRequest("./event/employee/31", {}, successfulGetEventsForEmployee);
     }

     getEventsForCustomers();

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
                             let cancel = ``;

                             if (e.acceptedAmountReimbursed !== 0 && e.reimbursementStatus !== "Denied") {
                                  cancel = `<a class="btn btn-md btn-danger" href=#/?eventId=${e.eventId}>
                                       <i class="fa fa-edit"></i> Cancel Req.
                                  </a>`;
                             }
                             
                             if (e.requiredPresentation && e.reimbursementStatus === "Approved") {
                                  return `<a class="btn btn-md btn-primary" href=#/?eventId=${e.eventId}>
                                       <i class="fa fa-edit"></i> Upload Pres.
                                  </a>
                                  ${cancel}`;
                             } else if (!e.requiredPresentation && e.reimbursementStatus === "Approved") {

                             return `<a class="btn btn-md btn-primary" href=#/?eventId=${e.eventId}>
                                       <i class="fa fa-edit"></i> Upload Grade
                                  </a>
                                  ${cancel}`;
                             }

                             return "";
                         }
                   }
               ]
          });
     }

});