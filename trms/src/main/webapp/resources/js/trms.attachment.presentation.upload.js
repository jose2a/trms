$(document).ready(function () {

     let eventId = 0;

     (function() {

          eventId = getUrlParamValue("eventId");

          $("#eventId").val(eventId);
          
     }());
   
     $("#approval_attachment_form").submit(function (e) {
          e.preventDefault();

          $("#errors_ul").html('');

          let data = new FormData();
          data.append("eventId", $("#eventId").val());
          data.append("fileContent", $("#fileContent")[0].files[0]);
          data.append("documentType", 4);

          ajaxUploadFile('./attachment/upload', data, uploadAttachmentSucc, uploadAttachmentBadReq);
     });

     let uploadAttachmentSucc = function(attachment) {
          toastr.success(`Your presentation was received successfully.`);

          redirect("dashboardEmp.html");
     }

     let uploadAttachmentBadReq = function(response) {
          showArrayOfErrorsInUL("errors_ul", response);
     }
});