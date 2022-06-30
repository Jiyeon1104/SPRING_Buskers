let artistService = (function () {
    function getArtistList(artistSortingType, page, callback, error) {
        let pageNum = page || 1;
        console.log("아티스트리스트 작동");
        $.ajax({
            url: "/artist/artistList/" + artistSortingType + "/" + pageNum,
            type: "get",
            dataType: "json",
            contentType: "application/json",
            success: function (artistPageDTO) {
                if(callback) {
                    callback(artistPageDTO.total, artistPageDTO.list);
                }
            },
            error: function (xhr, status, er) {
                if(error)   {
                    error(xhr, status, er);
                }
            }
        })
    }



    return {getArtistList:getArtistList}
})();


let pageNum = 1;
let artistSortingType = "NEW";
// let artistCnt = $(".li-sec-result");
let artistDiv = $(".ms-list-imgs");
let artistCntNumber = 0;


// 아티스트 수 출력
// function artistCount() {
//     artistService.getArtistList(artistSortingType, page, function (total, list) {
//         let str = "";
//         let artistCnt = 1;
//        
//         $.each(list, function(i, artist){
//             artistCnt += 1;
//         });
//         str += "현재 등록된 아티스트는 <span id=\"ListCntText\">" + artistCnt + "</span> 명 입니다."
//
//
//         artistCnt.html(str);
//         artistPage(artistSortingType, total);
//     }, function (a, b, c) {
//         console.log(a, b, c)
//     });
// }


function artistList(artistSortingType, page){


    artistService.getArtistList(artistSortingType, page, function (total, list) {
        let str = "";
        console.log("출력중");

        if(list == null || list.length == 0){
            artistDiv.html("");
            return;
        }



        $.each(list, function(i, artist){
            let artistInfoNumber = Number(artist.artistNumber);

            $.getJSON("/file/artist/file", {artistNumber: artistInfoNumber}, function(file){
                let src = "/file/artist/display?fileName=" + file.uploadPath + "/" + file.uuid + "_"  + file.fileName;
                $("#img-" + artistInfoNumber).attr("src", src);
            })

            str += "<a class='getinfo' style='cursor:pointer;' href='/artist/artistInfo?pageNum=" + page + "&amount=15&type&keyword&artistNumber=" + artistInfoNumber +"' target='_self'>"
            str += "<div class='list-bigger-wrap'><div class='artistImg-wrap'>"
            str += "<img id='img-" + artistInfoNumber + "' class='lazyload' src='' alt='#' style=''/></div>"
            str += "<div class='list-bigger-txt'>"
            str += "<p class='list-b-tit1 v2 v2'>" + artist.artistName + "</p>"
            str += "</div>"
            str += "</div>"
            str += "</a>"
            // artistCntNumber += 1;
        });

        // $.each(total, function(i, artist){
        //
        //     artistCntNumber += 1;
        // });
        

        artistDiv.html(str);
        artistPage(artistSortingType, total);
        console.log("출력끝");
    }, function (a, b, c) {
        console.log(a, b, c)
    });
}

$(document).ready(function () {
    artistList(artistSortingType, pageNum);
    console.log("새로고침");
})

let currentSort = ".NEW";
function sortChange(e) {
    $(currentSort).attr("id", "non-current");
    currentCategory = e;
    $(currentSort).attr("id", "current");
}

function artistPage(artistSortingType, total) {
    let endPage = Math.ceil(pageNum / 10.0) * 10;
    let startPage = endPage - 9;
    let realEnd = Math.ceil(total / 10.0);
    const $paging = $(".paging");

    if (endPage > realEnd) {
        endPage = realEnd;
    }

    let prev = startPage > 1;
    let next = endPage * 10 < total;
    let str = "";

    str += "<div class='big-width mypage-pageStyle' style='text-align: center'>"
    str += "<a class='mypage-page-first'>" + "<<" + "</a>"

    if (prev) {
        str += "<a class='changePage' href='" + (startPage - 1) +  "><code>&lt;</code></a>"
    }
    for (let i = startPage; i <= endPage; i++) {
        str += pageNum == i ? "<code>" + i + "</code>" : "<a class='changePage mypage-page-next' href='" + i + "'><code>" + i + "</code></a>";
    }
    if (next) {
        str += "<a class='changePage' href='" + (endPage + 1) + "'><code>&gt;</code></a>"
    }

    str += "<a class='mypage-page-last'>>></a></div>"
    $paging.html(str);

    artistCntNumber = total;
    document.getElementById("ListCntText").innerHTML=artistCntNumber;

}


$(".paging").on("click", "a.changePage", function (e) {
    e.preventDefault();
    pageNum = $(this).attr("href");
    artistList(artistSortingType, pageNum);
})

$("a.getinfo").click(function (e) {
    e.preventDefault();
    location.href = "/artist/artistInfo" + paging + "&artistNumber=" + $(this).attr('href');
});


    $(function () {
        $('.lazyload').Lazy({
            effect: "fadeIn",
            effectTime: 200,
            defaultImage: 'http://tkfile.yes24.com' + '/imgNew/common/noimg.gif'
        });
    });
    $(function () {

        $(".li-sec-tit2").on('click', function (e) {
            if ($(this).is(".on")) {
                e.preventDefault();
                $(this).removeClass("on");
                $(".li-sec-select").slideUp();

            } else {
                e.preventDefault();
                $(this).addClass("on");
                $(".li-sec-select").slideDown();
            }
        });
        $(document).mouseup(function (e) {
            var container = $(".li-sec-tit2");
            if (!container.is(e.target) && container.has(e.target).length === 0) {
                $(".li-sec-select").slideUp();
                $(".li-sec-tit2").removeClass("on");
            }
        });
    });



function go_to_url(url) {
    location.href=url;
}

function go_to_artist_info(artist_no) {
    location.href=artist_no;
}


function artistSort1() {

    $('.li-sec-tag a').removeClass("on");
    $('.li-sec-tag').find("a[token='3']").addClass("on")
}



function artistSort2() {

    $('.li-sec-tag a').removeClass("on");
    $('.li-sec-tag').find("a[token='0']").addClass("on")
}

$(".NEW").click(function (e) {
    sortChange(".NEW");
    e.preventDefault();

    pageNum = 1;
    artistList("NEW", pageNum);
})

$(".LIKE").click(function (e) {
    sortChange(".LIKE");
    e.preventDefault();

    pageNum = 1;
    artistList("LIKE", pageNum)
})




document.getElementById("ListCntText").innerHTML=artistCntNumber;



// $(document).ready(function () {
//     jsf_genre_GetGenreList('15456', '1', '3');
//
//     $('.li-sec-tag a').each(function () {
//         $(this).unbind('click.btnGenreListTab').bind('click.btnGenreListTab', function (e) {
//             e.preventDefault();
//             $("#pCurPage").val(1);
//             jsf_genre_GetGenreList('15456', '1', $(this).attr('token'));
//         });
//
//     });
//
// });
//
// //Scroll Event
// var asyncType = false;
// var didScroll;
// var lastScrollTop;
//
// $(window).scroll(function (event) {
//     didScroll = true;
// });
//
// setInterval(function () {
//     if (didScroll) {
//         hasScrolled();
//         didScroll = false;
//     }
// }, 1);
//
// function hasScrolled() {
//     var scrollT = $(this).scrollTop();
//     var scrollH = $(this).height();
//     var list_secH = $(".list-sec").height();
//
//     if (scrollT > lastScrollTop) {
//         //Scroll Down
//         if (scrollT + scrollH + 150 >= list_secH) {
//             var curPage = parseInt($("#pCurPage").val());
//             var lastPage = Math.ceil($("#ListTotalCnt").val() / $("#pPageSize").val());
//             if (curPage < lastPage) {
//                 if(asyncType){
//                     return;
//                 }
//                 $("#pCurPage").val(curPage + 1);
//                 jsf_genre_GetGenreList('15456', '1', $('.li-sec-tag').find('a[class*="on"]').attr('token') );
//                 asyncType = true;
//             }
//         }
//     }
//
//     lastScrollTop = scrollT
// }


