
function clickBtn(el){
    console.log(el);
    document.getElementById("pat_comment").value = (el);
    const btnSubmit= document.getElementById("sub");
    console.log(document.getElementById("pat_comment").value);
}
/*
function clickPagePatient(el){
    console.log(el);
    document.getElementById("pat_comment").value = (el);
    const btnSubmit= document.getElementById("sub");
    console.log(document.getElementById("pat_comment").value);
    btnSubmit.click();
}
*/
function click_page_doctor(el){
   document.getElementById("pat_comment_doctor").value = (el);
   document.getElementById("sub").click();
}
function click_page_patient(el){
   document.getElementById("pat_comment_patient").value = (el);
   document.getElementById("sub").click();
}
function click_page_schedule(el){
   document.getElementById("pat_comment_schedule").value = (el);
   document.getElementById("sub").click();
}

function click_page_appointment(el){
   document.getElementById("pat_comment_appointment").value = (el);
   document.getElementById("sub").click();
}

function  myReadPatientInfo(el){
    document.getElementById("sub").click();
}
function  myReadDoctor(el){
    document.getElementById("sub").click();
}

function clickSort(){
   document.getElementById("sub").click();
}