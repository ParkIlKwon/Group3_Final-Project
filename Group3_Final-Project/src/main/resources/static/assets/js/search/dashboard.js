
var ref = [
    {key:1, name:'프로젝트',val:"project"},
    {key:2, name:'메일',val:""},
    {key:3, name:'캘린더',val:""},
    {key:4, name:'드라이브',val:""},
    {key:5, name:'주소록',val:""},
    {key:6, name:'결재',val:""},
    {key:7, name:'ADMIN',val:""},
    {key:8, name:'회의',val:""},
    {key:9, name:'새프로젝트',val:""},
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

