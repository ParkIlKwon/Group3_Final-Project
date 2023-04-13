
var ref = [
    {key:1, name:'데이터1'},
    {key:2, name:'데이터2'},
    {key:3, name:'자바스크립트'},
    {key:4, name:'Json'},
];
$('#search_area').keypress(function(){
    var isComplete = false;  //autoMaker 자식이 선택 되었는지 여부
    $('#search_area').keyup(function(){
        var txt = $(this).val();
        if(txt != ''){  //빈줄이 들어오면
            $('#autoMaker').children().remove();

            ref.forEach(function(arg){
                if(arg.name.indexOf(txt) > -1 ){
                    $('#autoMaker').append(
                        $('<li>').text(arg.name).attr({'key':arg.key})
                    );
                }
            });
            $('#autoMaker').children().each(function(){
                $(this).click(function(){
                    $('#search_area').val($(this).text());
                    $('#insert_target').val("key : "+$(this).attr('key')+ ", data : " + $(this).text());
                    $('#autoMaker').children().remove();
                    isComplete = true;
                });
            });
        } else {
            $('#autoMaker').children().remove();
        }
    });


});

