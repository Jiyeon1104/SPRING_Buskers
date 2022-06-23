// ajax 생성
let showService = (function () {
    function goConcertPlan(type, page, callback, error) {
        console.log("getList")
        let pageNum = page || 1;
        $.ajax({
            url: "/concert/concertPlanList/" + type + "/" + pageNum,
            type: "get",
            // data: JSON.stringify(type),
            dataType: "json",
            contentType: "application/json",
            success: function (showPageDTO) {
                if(callback) {
                    callback(showPageDTO.total, showPageDTO.list);
                }
            },
            error: function (xhr, status, er) {
                if(error)   {
                    error(xhr, status, er);
                }
            }
        })
    }

    return {goConcertPlan:goConcertPlan}
})();

// 기본 전역변수
let pageNum = 1;
let type = "A"
let showDiv = $(".show-wrap");


// div 변하는 function 선언
function concertPlanList(type, page){
    console.log("새로고침 됐슈")
    showService.goConcertPlan(type, page, function (total, list) {
        let str = "";
        console.log("concertPlanList 안까지 들어왔슈  ", type, "  변수는 ", page)

        if(list == null || list.length == 0){
            showDiv.html("");
            return;
        }

        $.each(list, function(i, show){
            let infoNumber = Number(show.showNumber);
            str += "<a class='goRead' href='/concert/concertPlanInfo?pageNum=" + page + "&amount=15&type&keyword&showNumber=" + infoNumber +"' target='_self'>"
            str += "<div class='list-bigger-wrap'>"
            str += "<img class='lazyload' src='http://tkfile.yes24.com/upload2/perfblog/202205/20220516/20220516-42246.jpg/dims/quality/70/' alt='/show_view'/>"
            str += "<div class='list-bigger-txt'>"
            str += "<p class='list-b-tit1'>" + show.showName + "</p>"
            str += "<p class='list-b-tit2'>" + show.showLocation + "</p>"
            str += "<p class='list-b-circle'>" + "D-" + show.dday + "</p>"
            str += "</div>"
            str += "</div>"
            str += "</a>"
        });

        showDiv.html(str);
        concertPage(type, total);
    }, function (a, b, c) {
        console.log(a, b, c)
    });
}


function concertPage(type, total) {
    let endPage = Math.ceil(pageNum / 10.0) * 10; // 올림
    console.log("endPage");
    console.log(endPage);
    let startPage = endPage - 9;
    console.log("startPage");
    console.log(startPage);
    let realEnd = Math.ceil(total / 15.0);  // 올림
    console.log("realEnd");
    console.log(realEnd);
    const $paging = $(".paging");
    if (endPage > realEnd) {
        endPage = realEnd;
    }

    let prev = startPage > 1;
    let next = endPage * 15 < total;
    let str = "";

    console.log("prev");
    console.log(prev);
    console.log("next");
    console.log(next);
    str += "<div class='big-width mypage-pageStyle' style='text-align: center'>"
    // str += "<a class='mypage-page-first'>" + "<<" + "</a>"

    if (prev) {
        str += "<a class='changePage prevList' href='" + (startPage - 1) +  "'><code>&lt;</code>Prev</a>"
    }
    for (let i = startPage; i <= endPage; i++) {
        str += pageNum == i ? "<code>" + i + "</code>" : "<a class='changePage mypage-page-next' href='" + i + "'><code>" + i + "</code></a>";
    }
    if (next) {
        str += "<a class='changePage nextList' href='" + (endPage + 1) + "'>Next<code>&gt;</code></a>"
    }
    str += "</div>"
    // str += "<a class='mypage-page-last'>>></a></div>"
    $paging.html(str);

}


$(".paging").on("click", "a.changePage", function (e) {
    e.preventDefault();
    pageNum = $(this).attr("href");
    concertPlanList(type, pageNum);
})




$(document).ready(function () {
    // 새로고침 첫 실행 (type : A, pageNum : 1 (기본값))
    concertPlanList(type, pageNum);
})

let currentCategory = ".all";
function categoryChange(e) {
    $(currentCategory).attr("id", "non-current");
    currentCategory = e;
    $(currentCategory).attr("id", "current");
}

$(".all").click(function (e) {
    categoryChange(".all");
    e.preventDefault();
    console.log("all 됐슈")
    pageNum = 1;
    concertPlanList("A", pageNum);
})

$(".musician").click(function (e) {
    categoryChange(".musician");
    e.preventDefault();
    console.log("musician 됐슈")
    pageNum = 1;
    concertPlanList("M", pageNum);
})

$(".performance").click(function (e) {
    categoryChange(".performance");
    e.preventDefault();
    console.log("performance 됐슈")
    pageNum = 1;
    concertPlanList("P", pageNum);
})

$("a.goRead").click(function (e) {
    e.preventDefault();
    location.href = "/concert/concertPlanInfo" + paging + "&showNumber=" + $(this).attr('href');
});


