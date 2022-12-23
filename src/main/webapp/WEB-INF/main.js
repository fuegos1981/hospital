
/*
$(":button").bind("click", function() {
        console.dir($(this));
		//clickBtn($(this).val());
		clickBtn($(this).val());

});
*/
function clickBtn(el){
   // if el.substring(0, 3)=="pat"{
   console.log(el);
//console.log(document.getElementById("pat_comment").value);
      document.getElementById("pat_comment").value = (el);
   // }

   //const formAdm= document.getElementById("base-form");
   const btnSubmit= document.getElementById("sub");

   //console.dir(btnSubmit);
   console.log(document.getElementById("pat_comment").value);
   //btnSubmit.click();

}

function clickPagePatient(el){
   // if el.substring(0, 3)=="pat"{
   console.log(el);
//console.log(document.getElementById("pat_comment").value);
      document.getElementById("pat_comment").value = (el);
   // }

   //const formAdm= document.getElementById("base-form");
   const btnSubmit= document.getElementById("sub");

   //console.dir(btnSubmit);
   console.log(document.getElementById("pat_comment").value);
   btnSubmit.click();

}
function clickPageDoctor(el){
   document.getElementById("pat_comment_doctor").value = (el);
   document.getElementById("sub").click();
}
function DeleteRowCard(){
    var td = event.target.parentNode;
    var tr = td.parentNode; // the row to be removed
    tr.parentNode.removeChild(tr);
}

function clickSort(){
   document.getElementById("sub").click();
}
function addRowMedication() {

          let table = document.getElementById("tableBody");
           var multiStr =' <tr>'+
           '<td><c:out value="${status.count}" /> </td>'+
           '<td>Medication</td>'+
           '<td>'+
           '<select class="form-control" name="medication_id" id="medicationes">'+
           '     <option>Select medicationes...</option>'+
           '     <c:forEach var="medication" items="${medicationes}" varStatus="status">'+
           '         <option value="${medication.getId()}">'+
           '             <c:out value="${medication.toString()}"/>'+
           '         </option>'+
           '     </c:forEach>'+
           '</select>'+

           '</td>'+
           '<td><input type="text" name="description" class="form-control"  value="${description}"></td>'+
           '<td><input type="button" value="Delete row" onclick="DeleteRowCard()"/></td>'+
           '</tr>';

          $('#AllCards').append(multiStr);
       }

function addRowProcedure() {

            let table = document.getElementById("tableBody");
             var multiStr =' <tr>'+
             '<td><c:out value="${status.count}" /> </td>'+
             '<td>Procedure</td>'+
             '<td>'+
             '<select class="form-control" name="procedure_id" id="procedures">'+
             '     <option>Select procedures...</option>'+
             '     <c:forEach var="procedure" items="${procedures}" varStatus="status">'+
             '         <option value="${procedure.getId()}">'+
             '             <c:out value="${procedure.toString()}"/>'+
             '         </option>'+
             '     </c:forEach>'+
             '</select>'+

             '</td>'+
             '<td><input type="text" name="description" class="form-control"  value="${description}"></td>'+
             '<td><input type="button" value="Delete row" onclick="DeleteRowCard()"/></td>'+
             '</tr>';

            $('#AllCards').append(multiStr);
}

function addRowOperation() {

            let table = document.getElementById("tableBody");
             var multiStr =' <tr>'+
             '<td><c:out value="${status.count}" /> </td>'+
             '<td>Operation</td>'+
             '<td>'+
             '<select class="form-control" name="operation_id" id="operation">'+
             '     <option>Select operationes...</option>'+
             '     <c:forEach var="operation" items="${operationes}" varStatus="status">'+
             '         <option value="${operation.getId()}">'+
             '             <c:out value="${operation.toString()}"/>'+
             '         </option>'+
             '     </c:forEach>'+
             '</select>'+

             '</td>'+
             '<td><input type="text" name="description" class="form-control"  value="${description}"></td>'+
             '<td><input type="button" value="Delete row" onclick="DeleteRowCard()"/></td>'+
             '</tr>';

            $('#AllCards').append(multiStr);
}