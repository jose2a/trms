$(document).ready(function() {

     let attachments = [];

     function getAttachments () {
          let id =  $("#eventId").val();

          console.log(id);
          
          ajaxGetRequest(`./attachment/event/${id}`, {}, showSucessfulAttachmentResponse, 
                         showBadRequestAttachmentResponse);
     }

     $(".att > a").click(function() {
          console.log('Click tab 2');
          getAttachments();
     });
     
     let showSucessfulAttachmentResponse = function(list) {
          attachments = list || [];

          console.log(attachments);

          $("#attach_table").DataTable({
               "bProcessing": true,
               "aaData": attachments,
               "aoColumns": [
                    { "mData": "attachmentId" },
                    { "mData": "fileName" },
                    { "mData": "dateSubmitted" },
                    { "mData": "contentType" },
                    { "mData": "documentType" },
                    {
                         "mData": null,
                         "bSortable": false,
                         "mRender": function (e) {

                              return `<a class="btn btn-md btn-primary" href="./attachment/${e.attachmentId}">
                                       <i class="fa fa-file"></i> View Attachment
                                  </a>`;
                         }
                    }
               ]
          });
     }
     
     let showBadRequestAttachmentResponse = function(response) {
          console.log(response);
     };

     // getAttachments();

});