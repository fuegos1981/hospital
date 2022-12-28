
function clickBtn(el){
    console.log(el);
    document.getElementById("pat_comment").value = (el);
    const btnSubmit= document.getElementById("sub");
    console.log(document.getElementById("pat_comment").value);
}

function clickPagePatient(el){
    console.log(el);
    document.getElementById("pat_comment").value = (el);
    const btnSubmit= document.getElementById("sub");
    console.log(document.getElementById("pat_comment").value);
    btnSubmit.click();
}

function clickPageDoctor(el){
   document.getElementById("pat_comment_doctor").value = (el);
   document.getElementById("sub").click();
}

function  myReadPatientInfo(el){
    document.getElementById("sub").click();
}

function clickSort(){
   document.getElementById("sub").click();
}