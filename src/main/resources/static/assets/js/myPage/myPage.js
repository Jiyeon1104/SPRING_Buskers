let divCheck = false;
let divCurrentName = ".myInfo-pwCheck";
let currentHeader = ".myInfo-tab";
let selectedMenu = ".smallMenu-info";

function resetText() {
  
$("#checkPw").text("");
$("#notEqualPw").text("");
$("#checkNickname").text("");
$("#checkNickname").text("");
$("#checkPhoneNumber").text("");
$("#checkEmail").text("");
$("#checkEmail").text("");
$("#newPassword").text("");
$("#newPassword").text("");
$("#newPassword").text("");
$("#newPasswordNotEqual").text("");
}

function formReset() {
  $("#pwCheckForm")[0].reset();
  $("#myInfoForm")[0].reset();
  $("#newPwForm")[0].reset();
  $("div#pwImgNew").css("background-image", "url('/images/mypage/eye_hide.png')");
  $("div#pwImgNewCheck").css("background-image", "url('/images/mypage/eye_hide.png')");
  $("input[name='newPassword']").attr("type", "password");
  $("input[name='newPasswordEqual']").attr("type", "password");
}

// 페이지 첫 설정
$(document).ready(function () {
  $(currentHeader).attr("id", "current");
  $(selectedMenu).attr("id", "selected");
  divCurrentName = ".myInfo-update";
});

// 마이페이지 Header css 변경
function header(e) {
  $(currentHeader).attr("id", "non-current");
  currentHeader = e;
  $(currentHeader).attr("id", "current");
  if (
    e == ".myArtist-tab" ||
    e == ".myWork-tab" ||
    e == ".ArtistInfo-tab" ||
    e == ".myReply-tab"
  ) {
    $(".mypage-smallMenu").hide();
  }
  if (e == ".ArtistInfo-tab") {
    $(".donation-user-tab").show();
    $(".artistUser-smallMenu").show();
  } else {
    $(".donation-user-tab").hide();
    $(".artistUser-smallMenu").hide();
  }

  formReset();
  resetText();
}

// 마이페이지 smallMenu css 변경
function smallMenu(e) {
  $(selectedMenu).attr("id", "non-selected");
  selectedMenu = e;
  $(selectedMenu).attr("id", "selected");
  resetText();
  formReset();
}

// 마이페이지 헤더 변경시 본문 div
function tabChange(e) {
  $(divCurrentName).hide();
  divCurrentName = e;
  $(divCurrentName).show();
  $("#notEqualPw").text("");
  $("#checkPw").text("");
  resetText();
  formReset();
}

// 마이페이지 소메뉴 변경시 본문 div
function informationTabChange(e) {
  $(divCurrentName).hide();

  $(".myInfo-pwCheck").show();
  divCurrentName = e;
  resetText();
  formReset();
}

// 비밀번호 인증
// 유효성 검사
function pwCheck() {
  if (!pwCheckForm.userPassword.value) {
    $("#userPassword").focus();
    $("#notEqualPw").text("");
    $("#checkPw").text("비밀번호를 입력해주세요.");
    return;
  }

  let userPassword;
  myPageService.read(userNumber, function(user){
    userPassword = user.userPw;
    console.log(userPassword);
  })

  if (!(($("#userPassword").val() == userPassword))) {
    $("#userPassword").focus();
    $("#checkPw").text("");
    $("#notEqualPw").text("비밀번호가 일치하지 않습니다.");
    
    return;
  }

  $(".myInfo-pwCheck").hide();
  $("#checkPw").text("");
  $("#notEqualPw").text("");
  $(divCurrentName).show();
  $("input[name='userPassword']").val("");
  console.log(divCurrentName);
  // pwCheckForm.submit();
}

// 비밀번호 인증 div
function pwCheckHide() {
  if ($(".myInfo-pwCheck").show()) {
    $(".myInfo-pwCheck").hide();
  }
}




// 마이페이지 헤더 클릭
// 개인정보 관리 탭
$(".myInfo-tab").click(function (e) {
  e.preventDefault();
  header(".myInfo-tab");
  smallMenu(".smallMenu-info");
  $(".mypage-smallMenu").show();
  informationTabChange(".myInfo-update");
  resetText();
  formReset();
});

// 소메뉴 클릭
// 개인정보 변경 탭
$(".smallMenu-info").click(function (e) {
  e.preventDefault();
  smallMenu(".smallMenu-info");
  informationTabChange(".myInfo-update");
  resetText();
  formReset();


});

// 개인정보 변경
// 유효성 검사
function myInfoChange() {
  let userName = $("#userName").val();
  let userPhoneNumber = $("#userPhoneNumber").val();
  let userEmailId = $("#userEmailId").val();
  let userEmailDomain = $("#userEmailDomain").val();
  let selectEmail = $("#userEmailDomain").val();

  $("#checkNickname").text("");
  $("#checkPhoneNumber").text("");
  $("#checkEmail").text("");

  if (!userName) {
    $("#checkNickname").text("닉네임을 입력해 주세요.");
    $("#userName").focus();
  } else if (userName.search(/\s/) != -1) {
    $("#checkNickname").text("공백을 제외하고 입력해 주세요.");
    $("#userName").focus();
  } else if (!userPhoneNumber) {
    $("#checkPhoneNumber").text("휴대폰 번호를 입력해 주세요.");
    $("#userPhoneNumber").focus();
  } else if (userPhoneNumber.search(/\s/) != -1) {
    $(".phoneCheck").css("color", "#ff4d78");
    $(".userPhoneNumber").css("font-weight", "bold");
    $("#userPhoneNumber").focus();
  } else if (!userEmailId) {
    $("#checkEmail").text("이메일을 입력해 주세요.");
    $("#userEmailId").focus();
  } else if (!userEmailDomain) {
    $("#checkEmail").text("이메일을 입력해 주세요.");
    $("#userEmailDomain").focus();
  } else if (
    userEmailId.search(/\s/) != -1 ||
    userEmailDomain.search(/\s/) != -1
  ) {
    $("#checkEmail").text("공백을 제외하고 입력해 주세요.");
    $("#userEmailId").focus();
  }
}



// 모달창

const modal = document.getElementById("modal");
const btnModal = document.getElementById("btn-modal");
btnModal.addEventListener("click", (e) => {
  modal.style.display = "flex";
  e.preventDefault();
});

const closeBtn = modal.querySelector(".close-area");
closeBtn.addEventListener("click", (e) => {
  modal.style.display = "none";
});

modal.addEventListener("click", (e) => {
  const evTarget = e.target;
  if (evTarget.classList.contains("modal-overlay")) {
    modal.style.display = "none";
  }
});

window.addEventListener("keyup", (e) => {
  if (modal.style.display === "flex" && e.key === "Escape") {
    modal.style.display = "none";
  }
});

// 프로필 사진 기본이미지로 변경
$("#sample-profile-image").click(function () {
  $("#img-sample").attr("src", "/images/mypage/user-icon.png");
});



//이메일 입력방식 선택
$("#selectEmail").change(function () {
  $("#selectEmail option:selected").each(function () {
    if ($(this).val() == "1") {
      //직접입력일 경우
      $("#userEmailDomain").val(""); //값 초기화
      $("#userEmailDomain").attr("disabled", false); //활성화
    } else {
      //직접입력이 아닐경우
      $("#userEmailDomain").val($(this).text()); //선택값 입력
      $("#userEmailDomain").attr("disabled", true); //비활성화
    }
  });
});

// 비밀번호 변경 탭
$(".smallMenu-pwChange").click(function (e) {
  e.preventDefault();
  smallMenu(".smallMenu-pwChange");
  // pwCheck의 input칸 새로고침
  informationTabChange(".myInfo-pwChange");
});

// 비밀번호 노출 아이콘
$("div#pwImgNew").click(function () {
  const $input = $("input[name='newPassword']");
  if ($input.attr("type") == "password") {
    $input.attr("type", "text");
    $(this).css("background-image", "url('/images/mypage/eye_visible.png')");
  } else {
    $input.attr("type", "password");
    $(this).css("background-image", "url('/images/mypage/eye_hide.png')");
  }
});

$("div#pwImgNewCheck").click(function () {
  const $input = $("input[name='newPasswordEqual']");
  if ($input.attr("type") == "password") {
    $input.attr("type", "text");
    $(this).css("background-image", "url('/images/mypage/eye_visible.png')");
  } else {
    $input.attr("type", "password");
    $(this).css("background-image", "url('/images/mypage/eye_hide.png')");
  }
});

// 유효성 검사
function changePassword() {
  $("#newPassword").text("");
  $("#newPasswordNotEqual").text("");
  var pw = $("#newPasswordInput").val();
  var num = pw.search(/[0-9]/g);
  var eng = pw.search(/[a-z]/gi);
  if (pw.length < 8 || pw.length > 20) {
    $("#newPassword").text("8자리 ~ 20자리 이내로 입력해주세요.");
    $("#newPasswordInput").focus();
    return;
  } else if (pw.search(/\s/) != -1) {
    $("#newPassword").text("비밀번호는 공백 없이 입력해주세요.");
    $("#newPasswordInput").focus();
    return;
  } else if (num < 0 || eng < 0) {
    $("#newPassword").text("영문, 숫자를 혼합하여 입력해주세요.");
    $("#newPasswordInput").focus();
    return;
  } else {
    if (
      !(
        $("input[name='newPassword']").val() ==
        $("input[name='newPasswordEqual']").val()
      )
    ) {
      $("#newPasswordNotEqual").text("비밀번호가 일치하지 않습니다.");
      $("#newPasswordEqualInput").focus();
      return;
    }
  }
  myPageService.modifyPw(userNumber, $("input#newPasswordEqualInput").val(), function(result){
    console.log($("input#newPasswordEqualInput").val());
    alert("비밀번호 수정이 완료되었습니다.");
    location.href = "/myPage/myPage"
  })
}




// 회원 탈퇴 탭
$(".smallMenu-userDelete").click(function (e) {
  e.preventDefault();
  smallMenu(".smallMenu-userDelete");
  informationTabChange(".myInfo-delete");
});

// 관심 아티스트 카테고리 css 변경
function smallArtistMenu(c, s) {
  $(currentArtistDot).attr("id", "non-current");
  currentArtistDot = c;
  $(currentArtistDot).attr("id", "current");

  $(selectedArtistMenu).attr("id", "non-selected");
  selectedArtistMenu = s;
  $(selectedArtistMenu).attr("id", "selected");
}

// 관심 아티스트 카테고리에 따른 div 변경
function myArtistTabChange(e) {
  $(myArtistDivCurrentName).hide();
  myArtistDivCurrentName = e;
  $(myArtistDivCurrentName).show();
}

// 관심 아티스트 탭
let currentArtistDot = ".artist-All-Dot";
let selectedArtistMenu = ".artist-All-button";
let myArtistDivCurrentName = ".myArtistList-All";
$(".myArtist-tab").click(function (e) {
  e.preventDefault();
  header(".myArtist-tab");
  pwCheckHide();
  tabChange(".myArtist-list");
  smallArtistMenu(".artist-All-Dot", ".artist-All-button");
  myArtistTabChange(".myArtistList-All");
});

// 관심 아티스트 > 나의 후원 탭
$(".myDonation-user").click(function (e) {
  e.preventDefault();
  informationTabChange(".myArtistDonation-List");
});

// 관심 아티스트 카테고리 전체
$(".artist-All-button").click(function (e) {
  e.preventDefault();
  myArtistTabChange(".myArtistList-All");
  smallArtistMenu(".artist-All-Dot", ".artist-All-button");
});

// 관심 아티스트 카테고리 뮤지션
$(".artist-Musician-button").click(function (e) {
  e.preventDefault();
  // myArtistTabChange(".myArtistList-Musician");
  smallArtistMenu(".artist-Musician-Dot", ".artist-Musician-button");
});

// 관심 아티스트 카테고리 퍼포먼스
$(".artist-Performance-button").click(function (e) {
  e.preventDefault();
  // myArtistTabChange(".myArtistList-Performance");
  smallArtistMenu(".artist-Performance-Dot", ".artist-Performance-button");
});

// 나의 후원 탭
$(".myDonationUser-tab").click(function (e) {
  e.preventDefault();
  informationTabChange(".myArtistDonation-List");
});

// 하트 변경
// $(".heart-selected").click(function(e){
//   e.preventDefault();
//   $(".heart-nonSelected").show();
// })
// $(".heart-nonSelected").click(function(e){
//   e.preventDefault();
//   $(".heart-nonSelected").attr("id", "heart-selected");
// })

// 관심 작품 카테고리 css 변경
function smallWorkMenu(c, s) {
  $(currentWorkDot).attr("id", "non-current");
  currentWorkDot = c;
  $(currentWorkDot).attr("id", "current");

  $(selectedWorkMenu).attr("id", "non-selected");
  selectedWorkMenu = s;
  $(selectedWorkMenu).attr("id", "selected");
}

// 관심 작품 카테고리에 따른 div 변경
function myWorkTabChange(e) {
  $(myWorkDivCurrentName).hide();
  myWorkDivCurrentName = e;
  $(myWorkDivCurrentName).show();
}

// 관심 작품 탭
let currentWorkDot = ".work-All-Dot";
let selectedWorkMenu = ".work-All-button";
let myWorkDivCurrentName = ".myWorkList-All";

$(".myWork-tab").click(function (e) {
  e.preventDefault();
  alert("서비스를 준비 중입니다. 빠른 시일내에 조치하겠습니다.")
  // pwCheckHide();
  // header(".myWork-tab");
  // tabChange(".myWork-list");
  // smallWorkMenu(".work-All-Dot", ".work-All-button");
  // myWorkTabChange(".myWorkList-All");
});

// 관심 작품 카테고리 전체
$(".work-All-button").click(function (e) {
  e.preventDefault();
  myWorkTabChange(".myWorkList-All");
  smallWorkMenu(".work-All-Dot", ".work-All-button");
});

// 관심 작품 카테고리 뮤지션
$(".work-Musician-button").click(function (e) {
  e.preventDefault();
  myWorkTabChange(".myWorkList-Musician");
  smallWorkMenu(".work-Musician-Dot", ".work-Musician-button");
});

// 관심 작품 카테고리 퍼포먼스
$(".work-Performance-button").click(function (e) {
  e.preventDefault();
  myWorkTabChange(".myWorkList-Performance");
  smallWorkMenu(".work-Performance-Dot", ".work-Performance-button");
});

// 하트 클릭
function heartClick(e) {}
$("#myArtist-1").click(function (e) {
  console.log('d2')
  if ($("#heart-icon-1").css("display") == "none") {
    $("#heartNon-icon-1").hide();
    $("#heart-icon-1").show();
  } else if ($("#heartNon-icon-1").css("display") == "none") {
    $("#heart-icon-1").hide();
    $("#heartNon-icon-1").show();
  }
});

// 아티스트 계정 탭 소메뉴 css 변경
let selectedArtistUserMenu = ".artistUserList-tab";
function artistUserSmallMenu(e) {
  $(selectedArtistUserMenu).attr("id", "non-selected-artistUser");
  selectedArtistUserMenu = e;
  $(selectedArtistUserMenu).attr("id", "selected-artistUser");
}

// 아티스트 계정 탭
$(".ArtistInfo-tab").click(function (e) {
  $(selectedArtistUserMenu).attr("id", "non-selected-artistUser");
  e.preventDefault();
  $(".myInfo-pwCheck").hide();
  header(".ArtistInfo-tab");

  if(sessionArtistNumber == null) {
    // 소메뉴, 후원탭 숨기기
    $(".artistUser-smallMenu").hide();
    $(".myDonation").hide();


    console.log(sessionArtistNumber);
    if (confirm("아티스트 계정이 존재하지 않습니다. 새로 등록하시겠습니까?")) {
      tabChange(".artistJoin-desc");
    } else {
      header(".myInfo-tab");
      smallMenu(".smallMenu-info");
      $(".mypage-smallMenu").show();
      informationTabChange(".myInfo-update");
      resetText();
      formReset();
    }
  } else {
    console.log(sessionArtistNumber);
    $(divCurrentName).hide();
    artistUserSmallMenu(".artistUserList-tab");
    informationTabChange(".artistUser-Info");
  }

});

// 소메뉴 클릭
//  아티스트 계정 목록 탭
// $(".artistUserList-tab").click(function (e) {
//   console.log("dd");
//   e.preventDefault();
//   artistUserSmallMenu(".artistUserList-tab");
//   informationTabChange(".artistUser-List");
// });

// 아티스트 정보 수정 탭
$(".artistUserList-tab").click(function (e) {
  e.preventDefault();
  artistUserSmallMenu(".artistUserList-tab");
  informationTabChange(".artistUser-Info");
});

// 아티스트 계정 삭제 탭
$(".artistUserDelete-tab").click(function (e) {
  e.preventDefault();
  artistUserSmallMenu(".artistUserDelete-tab");
  informationTabChange(".artistUser-Delete");
});

// 아티스트 후원 탭
$(".myDonation").click(function (e) {
  // e.preventDefault();
  $(selectedArtistUserMenu).attr("id", "non-selected-artistUser");
  informationTabChange(".artistUserDonation-List");

  UserGetDonation(userNumber, "new", 1);
});

// 아티스트 계정 등록
$("#artist-join-profile").click(function () {
  let artistFileInput = document.getElementById("artist-file-input");
  artistFileInput.click();
});

let replyCheck = true;
// 내가 쓴 댓글
$(".myReply-tab").click(function (e) {
  e.preventDefault();
  header(".myReply-tab");
  $(".myInfo-pwCheck").hide();
  tabChange(".myReplyList-div");
  $("input[name='reply-check']").prop("checked", false);
  replyCheck = true;
});

// 내가 쓴 댓글 전체선택
$("#reply-all-button").click(function () {
  if (replyCheck) {
    $("input[name='reply-check']").prop("checked", true);
    replyCheck = false;
  } else if (!replyCheck) {
    $("input[name='reply-check']").prop("checked", false);
    replyCheck = true;
  }
});

// 댓글 삭제
$("button[name='reply-delete-button']").click(function () {
  if (confirm("댓글을 삭제하시겠습니까?") == true) {
    alert("댓글이 삭제되었습니다.");
  }
});



// 아티스트 등록, 수정 유효성 검사
$(".artist-join-submit").click(function(){

})

$(".artist-update-submit").click(function(){
  
})

// ajax
console.log("myPage Module....");

let myPageService = (function(){

  // 유저 정보 조회
  function read(userNumber, callback){
    console.log("read............");
    $.ajax({
      url: "/myPage/" + userNumber,
      type: "get",
      dataType: "json",
      async: false,
      success: function(user){
        if(callback) {
          callback(user);
        }
      }
    })
  }

  // 유저 정보 수정
  function modify(userVO, callback){
    console.log("modify...........");
    $.ajax({
      url: "/myPage/" + userVO.userNumber,
      type: "patch",
      data: JSON.stringify(userVO),
      contentType: "application/json; charset=utf-8",
      success: function(result){
        if(callback){
          callback(result);
        }
      }
    });
  }

  // 회원 탈퇴
  function remove(userNumber, callback){

    console.log("remove............");
    $.ajax({
      url: "/myPage/" + userNumber,
      type: "delete",
      success: function(result){
        if(callback){
          callback(result);
        }
      }
    });
  }

  // 비밀번호 변경
  function modifyPw(userNumber, password, callback){
    console.log("modifyPw..........");
    $.ajax({
      url: "/myPage/" + userNumber,
      type: "post",
      data: {password:password},
      contentType: "text/plain",
      success: function(result){
        console.log(result);
        if(callback){
          callback(result);
        }
      }
    });
  }

  return {modify:modify, modifyPw:modifyPw, remove:remove, read:read}
})();

// ajax userService.......
let userService = (function(){
  // 관심 아티스트 좋아요 목록 뽑기
  function getArtist(userNumber, type, page, callback, error){
    console.log("getArtist.................");
    let pageNum = page || 1;
    console.log(page);
    $.ajax({
      url: "/myPage/" + userNumber + "/" + type + "/" + pageNum,
      type: "get",
      dataType: "json",
      contentType: "application/json",
      success: function(LikePageDTO){
        if(callback){
          callback(LikePageDTO.total, LikePageDTO.list);
        }
      }, error: function(xhr, status, er){
        if(error){
          error(xhr, status, er);
        }
      }
    })
  }

  function getWork(userNumber, page, callback, error){
    console.log("getWork...............");
    let pageNum = page || 1;
    $.ajax({
      url: "/myPage/" + userNumber + "/" + pageNum,
      type: "get",
      dataType: "json",
      contentType: "application/json",
      success: function(LikePageDTO){
        if(callback){
          callback(LikePageDTO.total, LikePageDTO.list);
        }
      }, error: function(xhr, status, er){
        if(error){
          error(xhr, status, er);
        }
      }
    })
  }

  // 좋아요 취소하기
  function removeLike(userNumber, type, number, callback, error){
    console.log("removeLike.........");
    $.ajax({
      url: "/myPage/" + userNumber + "/" + type + "/" + number,
      type: "delete",
      success: function(result){
        if(callback){
          callback(result);
        }
      }
    })
  }

  function getArtistReply(userNumber, page, callback, error){
    console.log("get ArtistReplyList----------");
    let pageNum = page || 1;
    $.ajax({
      url: "/myPage/reply/artist/" + userNumber + "/" + pageNum,
      type: "get",
      dataType: "json",
      contentType: "application/json",
      success: function(ArtistReplyPageDTO) {
        if(callback){
          callback(ArtistReplyPageDTO.total, ArtistReplyPageDTO.list);
        }
        }, error: function(xhr, status, er){
        if(error){
          error(xhr, status, er);
        }
      }
    })
  }

    function getShowUserReply(userNumber, page, callback, error){
      console.log("get getShowUserReply----------");
      let pageNum = page || 1;
      $.ajax({
        url: "/myPage/reply/show/" + userNumber + "/" + pageNum,
        type: "get",
        dataType: "json",
        contentType: "application/json",
        success: function(ShowReplyPageDTO) {
          if(callback){
            callback(ShowReplyPageDTO.total, ShowReplyPageDTO.list);
          }
        }, error: function(xhr, status, er){
          if(error){
            error(xhr, status, er);
          }
        }
      })
    }

  return {getArtist:getArtist, getWork:getWork, removeLike:removeLike, getArtistReply:getArtistReply, getShowUserReply:getShowUserReply}
})();

// Ajax Artist.........
let ArtistService = (function() {
  function registerArtist(artist, callback, error) {
    console.log("register artist............");
    $.ajax({
      url: "/myPage/new",
      type: "post",
      data: JSON.stringify(artist),
      contentType: "application/json",
      success: function (result) {
        if (callback) {
          callback(result);
        }
      },
      error: function (xhr, status, er) {
        if (error) {
          error(xhr, status, er);
        }
      }
    });
  }

    function modifyArtist(artist, callback, error) {
      console.log("modifyApply............");
      $.ajax({
        url: "/myPage/artist/" + sessionArtistNumber,
        type: "patch",
        data: JSON.stringify(artist),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
          if (callback) {
            callback(result);
          }
        }
      })
    };

      function removeArtist(artistNumber, callback, error){
        console.log("removeArtist............");
        $.ajax({
          url: "/myPage/artist/remove/" + artistNumber,
          type: "delete",
          success: function(result){
            if(callback){
              callback(result);
            }
          }
        })
      }

      function removeArtistReply(replyNumber, callback, error){
        console.log("removeArtistReply...........");
        $.ajax({
          url: "/myPage/artist/" + replyNumber,
          type: "delete",
          success: function(result){
            if(callback){
              callback(result);
            }
          }
        })
      }

  return {registerArtist: registerArtist, modifyArtist:modifyArtist, removeArtist:removeArtist, removeArtistReply:removeArtistReply};
})();

// Ajax Donation............

let DonationService = (function(){
  // 아티스트 - 후원 목록 뽑기
  function getDonation(artistNumber, type, page, callback, error){
    console.log("getDonation...........");
    let pageNum = page || 1;
    $.ajax({
      url: "/myPage/artist/" + artistNumber + "/" + type + "/" + pageNum,
      type: "get",
      dataType: "json",
      contentType: "application/json",
      success: function(DonationPageDTO){
        if(callback){
          callback(DonationPageDTO.total, DonationPageDTO.list);
        }
      }, error: function(xhr, status, er){
        if(error){
          error(xhr, status, er);
        }
      }
    })
  }

  // 후원 목록 (유저 관점)
  function getUserDonation(userNumber, type, page, callback, error){
    console.log("getUserDonation...........");
    let pageNum = page || 1;
    $.ajax({
      url: "/myPage/user/" + userNumber + "/" + type + "/" + pageNum,
      type: "get",
      dataType: "json",
      contentType: "application/json",
      success: function(DonationPageDTO){
        if(callback){
          callback(DonationPageDTO.total, DonationPageDTO.list);
        }
      }, error: function(xhr, status, er){
        if(error){
          error(xhr, status, er);
        }
      }
    })
  }
  return {getDonation:getDonation, getUserDonation:getUserDonation};
})();

let showService = (function(){
  function removeShowReply(replyNumber, callback, error){
    console.log("removeArtistReply...........");
    $.ajax({
      url: "/myPage/show/" + replyNumber,
      type: "delete",
      success: function(result){
        if(callback){
          callback(result);
        }
      }
    })
  }
  return {removeShowReply:removeShowReply};
})();