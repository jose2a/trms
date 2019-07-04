$(document).ready(function () {

     (function() {

          let eventId = getUrlParamValue("eventId");

          $("#eventId").val(eventId);
          
     }());
   
     $("#approval_attachment_form").submit(function (e) {
          e.preventDefault();

          $("#errors_ul").html('');

          let data = new FormData();
          data.append("eventId", $("#eventId").val());
          data.append("fileContent", $("#fileContent")[0].files[0]);
          data.append("documentType", $("#documentType").val());

          ajaxUploadFile('./attachment/upload', data, uploadAttachmentSucc, uploadAttachmentBadReq);
     });

     let uploadAttachmentSucc = function(attachment) {
          console.log(attachment);
          $("#fileContent").val("");

          toastr.success(`Your attachment was received successfully.`);
     }

     let uploadAttachmentBadReq = function(response) {
          showArrayOfErrorsInUL("errors_ul", response);
     }
});