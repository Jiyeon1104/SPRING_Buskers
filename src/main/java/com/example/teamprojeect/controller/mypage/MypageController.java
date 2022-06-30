package com.example.teamprojeect.controller.mypage;

import com.example.teamprojeect.domain.vo.artist.ArtistVO;
import com.example.teamprojeect.domain.vo.list.ListDTO;
import com.example.teamprojeect.domain.vo.paging.Criteria;
import com.example.teamprojeect.domain.vo.paging.artist.ArtistReplyPageDTO;
import com.example.teamprojeect.domain.vo.paging.donation.DonationPageDTO;
import com.example.teamprojeect.domain.vo.paging.show.ShowReplyPageDTO;
import com.example.teamprojeect.domain.vo.paging.user.LikePageDTO;
import com.example.teamprojeect.domain.vo.user.LikeVO;
import com.example.teamprojeect.domain.vo.user.UserVO;
import com.example.teamprojeect.service.ArtistService;
import com.example.teamprojeect.service.DonationService;
import com.example.teamprojeect.service.ShowService;
import com.example.teamprojeect.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/myPage/*")
public class MypageController {
    // 필드 생성
    private final UserService userService;
    private final ArtistService artistService;
    private final DonationService donationService;
    private final ShowService showService;

    // 마이페이지 이동
    @GetMapping("/myPage")
    public String goMypage(Model model, HttpSession session) {
        log.info(String.valueOf(session.getAttribute("userNumber")));
        Long userNumber = Long.valueOf(String.valueOf(session.getAttribute("userNumber")));
        if(session.getAttribute("artistNumber") != null) {
            Long artistNumber = Long.valueOf(String.valueOf(session.getAttribute("artistNumber")));
            ArtistVO artistVO = artistService.getDetail(artistNumber);
            model.addAttribute("artist", artistVO);
        } else {
            ArtistVO artistVO = new ArtistVO();
            model.addAttribute("artist", artistVO);
        }
        UserVO userVO = userService.read(userNumber);
        String userEmail = userVO.getUserEmail();
        log.info("==========================================userEmail");
        log.info(userEmail);

        if(userEmail != null) {
            String[] str = userEmail.split("@");
            userVO.setUserEmailId(str[0]);
            userVO.setUserDomain(str[1]);
        }


        model.addAttribute("user",userVO);
        return "/myPage/myPage";
    }

//    유저 정보 조회 - Talend API Tester 통과
    @GetMapping("/{userNumber}")
    @ResponseBody
    public UserVO read(@PathVariable("userNumber") Long userNumber){
        log.info("read..." + userNumber);
        return userService.read(userNumber);
    }

    // 유저 정보 수정 - Talend API Tester 통과
    @PatchMapping(value={"/{userNumber}"}, consumes="application/json")
    @ResponseBody
    public String modify(@PathVariable("userNumber")Long userNumber, @RequestBody UserVO userVO){
        userVO.setUserEmail(userVO.getUserEmailId() + "@" + userVO.getUserDomain());
        log.info("modify....................." + userVO);
        log.info("modify....................." + userNumber);

        userVO.setUserNumber(userNumber);
        userService.modify(userVO);
        return "개인정보 수정이 완료되었습니다.";
    }

    // 비밀번호 수정 - Talend API Tester 통과
    @PostMapping(value="/{userNumber}", consumes = "text/plain")
    @ResponseBody
    public String modifyPw(@PathVariable("userNumber") Long userNumber, @RequestBody String password){
        log.info("modifyPw.................." + userNumber);

        password = password.substring(9);
        log.info("modifyPw.................." + password);

        userService.modifyPw(userNumber, password);

        return "비밀번호 수정이 완료되었습니다.";
    }

    // 회원 탈퇴 - Talend API Tester 통과
    @DeleteMapping("/{userNumber}")
    @ResponseBody
    public String remove(@PathVariable("userNumber")Long userNumber){
        userService.remove(userNumber);
        return "회원 탈퇴가 완료되었습니다. 감사합니다.";
    }

    // 관심 아티스트 좋아요 취소
    @DeleteMapping("/{userNumber}/{type}/{number}")
    @ResponseBody
    public String removeLike(@PathVariable("userNumber")Long userNumber, @PathVariable("type")String likeCategory, @PathVariable("number")Long number){
        ListDTO listDTO = new ListDTO();
        listDTO.setLikeCategory(likeCategory);
        userService.removeLike(userNumber, listDTO, number);

        return "좋아요 등록 취소 성공";
    }

    // 관심 아티스트 좋아요 목록 불러오기 - Talend API 통과
    @GetMapping("/{userNumber}/{type}/{page}")
    @ResponseBody
    public LikePageDTO getArtist(@PathVariable("userNumber") Long userNumber, @PathVariable ("type") String artistType, @PathVariable("page") int pageNum){
        ListDTO listDTO = new ListDTO();
        listDTO.setArtistType(artistType);

        return new LikePageDTO(userService.getLikeArtistList(new Criteria(pageNum, 10), userNumber, listDTO), userService.getTotalArtist(userNumber, listDTO));
    }

    // 관심 작품 좋아요 목록 불러오기 - Talend API 통과
    @GetMapping("/{userNumber}/{page}")
    @ResponseBody
    public LikePageDTO getWork(@PathVariable("userNumber") Long userNumber, @PathVariable("page") int pageNum){
        return new LikePageDTO(userService.getLikeWorkList(new Criteria(pageNum, 10), userNumber), userService.getTotalWork(userNumber));
    }

    // 아티스트 등록 신청
    @PostMapping(value="/new", consumes = "application/json", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> create(@RequestBody ArtistVO artistVO) throws UnsupportedEncodingException{
        log.info("라ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ아ㅏ아앙아ㅏ" + artistVO);
        log.info("artistVO" + artistVO);
        log.info("artistVO" + artistVO.getFile());
        artistService.registerApply(artistVO);
        return new ResponseEntity<>(new String("아티스트 등록 신청".getBytes(), "UTF-8"),HttpStatus.OK);
    }

    // 아티스트 정보 수정 신청
    @PatchMapping(value="/artist/{artistNumber}", consumes = "application/json")
    @ResponseBody
    public String modifyApply(@PathVariable("artistNumber") Long artistNumber, @RequestBody ArtistVO artistVO){
        log.info("modify..............");
        artistVO.setArtistNumber(artistNumber);
        artistService.modifyApply(artistVO);
        return "아티스트 정보 수정이 완료되었습니다.";
    }

    // 아티스트 계정 삭제
    @DeleteMapping("/artist/remove/{artistNumber}")
    @ResponseBody
    public String removeArtist(@PathVariable("artistNumber") Long artistNumber){
        artistService.removeArtist(artistNumber);
        return "아티스트 정보 삭제가 완료되었습니다. 감사합니다.";
    }

    // 후원 목록 (아티스트 관점)
    @GetMapping("/artist/{artistNumber}/{type}/{page}")
    @ResponseBody
    public DonationPageDTO donationList(@PathVariable("artistNumber")Long artistNumber, @PathVariable("type")String donationType, @PathVariable("page")int pageNum){
        ListDTO listDTO = new ListDTO();
        listDTO.setDonationType(donationType);
        log.info(" ********************************" + artistNumber);
        log.info("+++++++++++++++++++++++++++++++" + listDTO.toString());
        return new DonationPageDTO(donationService.donationList(new Criteria(pageNum, 10), artistNumber, listDTO), donationService.getDonationTotal(artistNumber, listDTO));
    }

    @GetMapping("/user/{userNumber}/{type}/{page}")
    @ResponseBody
    public DonationPageDTO donationUserList(@PathVariable("userNumber")Long userNumber, @PathVariable("type")String donationType, @PathVariable("page")int pageNum){
        ListDTO listDTO = new ListDTO();
        listDTO.setDonationType(donationType);

        return new DonationPageDTO(donationService.donationUserList(new Criteria(pageNum, 10), userNumber, listDTO), donationService.getDonationUserTotal(userNumber, listDTO));
    }

    // 내가 남긴 댓글 - 아티스트
    @GetMapping("/reply/artist/{userNumber}/{page}")
    @ResponseBody
    public ArtistReplyPageDTO getUserReply(@PathVariable("userNumber")Long userNumber, @PathVariable("page")int pageNum){
        return new ArtistReplyPageDTO(userService.getUserReplyList(new Criteria(pageNum,10), userNumber), userService.getTotalUserReply(userNumber));
    }

    // 내가 남긴 댓글 - 쇼
    @GetMapping("/reply/show/{userNumber}/{page}")
    @ResponseBody
    public ShowReplyPageDTO getUserShowReply(@PathVariable("userNumber") Long userNumber, @PathVariable("page") int pageNum){
        return new ShowReplyPageDTO(userService.getUserShowReply(new Criteria(pageNum, 10), userNumber), userService.getTotalUserShowReply(userNumber));
    }

    // 내가 남긴 댓글 삭제 - 쇼
    @DeleteMapping("/reply/show/{replyNumber}")
    public String removeArtistReply(@PathVariable("replyNumber") Long replyNumber) {
        artistService.removeReply(replyNumber);
        return "댓글이 삭제되었습니다.";
    }

    // 내가 남긴 댓글 삭제 - 아티스트
    @DeleteMapping("/reply/artist/{replyNumber}")
    public String removeShowReply(@PathVariable("replyNumber") Long replyNumber) {
        showService.removeReply(replyNumber);
        return "댓글이 삭제되었습니다.";
    }
}
