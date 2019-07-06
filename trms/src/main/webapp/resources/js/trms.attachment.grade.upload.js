$(document).ready(function () {

	let eventId = getUrlParamValue("eventId");

	$("#eventId").val(eventId);
   
     
});

$("#approval_attachment_form").submit(function (e) {
    e.preventDefault();

    $("#errors_ul").html('');

    let data = new FormData();
    
    data.append("eventId", $("#eventId").val());
    data.append("fileContent", $("#fileContent")[0].files[0]);
    data.append("finalGrade", $("#finalGrade").val());
    data.append("documentType", 3);

    ajaxUploadFile('./attachment/upload', data, uploadAttachmentSucc, uploadAttachmentBadReq);
});

let uploadAttachmentSucc = function(attachment) {
    toastr.success(`Your grade was received successfully.`);

    redirect("dashboardEmp.html");
};

let uploadAttachmentBadReq = function(response) {
    showArrayOfErrorsInUL("errors_ul", response);
};