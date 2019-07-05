$(document).ready(function () {

     let events = [];

     function getEventsForCustomers() {
          ajaxGetRequest("./event/employee", {}, successfulGetEventsForEmployee);
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
                                  cancel = `<a class="btn btn-md btn-danger" href="./event/cancel/${e.eventId}">
                                       <i class="fa fa-remove"></i> Cancel Req.
                                  </a>`;
                             }
                             
                             if (e.requiredPresentation && e.presentationUploaded === false && e.bencoEventStatus === "Approved") {
                                  return `<a class="btn btn-md btn-primary" href="presentationAttachment.html?eventId=${e.eventId}">
                                       <i class="fa fa-file"></i> Upload Pres.
                                  </a>
                                  ${cancel}`;
                             } else if (!e.requiredPresentation && e.finalGrade === null && e.bencoEventStatus === "Approved") {

                             return `<a class="btn btn-md btn-primary" href="gradeAttachment.html?eventId=${e.eventId}">
                                       <i class="fa fa-file"></i> Upload Grade
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