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
                    { "mData": "cost" },
                    {
                         "mData": null,
                         "bSortable": false,
                         "mRender": function (e) { 
                              return `<a class="btn btn-md btn-primary" href=#/?eventId=${e.eventId}>
                                        <i class="fa fa-edit"></i> Edit
                                   </a>`;
                          }
                    }
               ]
          });
     }

});