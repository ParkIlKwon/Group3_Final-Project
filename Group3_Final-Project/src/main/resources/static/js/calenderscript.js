(function(){
    $(function(){
        // 드래그 박스 취득
        var containerEl = $('#external-events-list')[0];
        // 설정하기
        new FullCalendar.Draggable(containerEl, {
            itemSelector: '.fc-event',
            eventData: function(eventEl) {
                return {
                    title: eventEl.innerText.trim()
                }
            }
        });
        // 드래그 아이템 추가하기
        for(var i=1; i<=5;i++) {
            var $div = $("<div class='fc-event fc-h-event fc-daygrid-event fc-daygrid-block-event'></div>");
            $event = $("<div class='fc-event-main'></div>").text("Event "+i);
            $('#external-events-list').append($div.append($event));
        }
        // calendar element 취득
        var calendarEl = $('#calendar1')[0];
        // full-calendar 생성하기
        var calendar = new FullCalendar.Calendar(calendarEl, {
            // 해더에 표시할 툴바
            headerToolbar: {
                left: 'prev,next today',
                center: 'title',
                right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
            },
            initialDate: '2021-07-15', // 초기 날짜 설정 (설정하지 않으면 오늘 날짜가 보인다.)
            locale: 'ko', // 한국어 설정
            editable: true, // 수정 가능
            droppable: true,  // 드래그 가능
            drop: function(arg) { // 드래그 엔 드롭 성공시
                // 드래그 박스에서 아이템을 삭제한다.
                arg.draggedEl.parentNode.removeChild(arg.draggedEl);
            }
        });
        // 캘린더 랜더링
        calendar.render();
    });
})();