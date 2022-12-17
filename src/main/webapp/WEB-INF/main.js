

$(":button").bind("click", function() {

		clickBtn($(this).val());

});
function clickBtn(el){
    if el.substring(0, 3)=="pat"{
        document.getElementById("pat_comment").val(el.substring(4));
    }
    document.getElementById("base-form").submit();
}


