package com.example.teamprojeect.controller.user;

import com.example.teamprojeect.controller.mypage.MypageController;
import com.example.teamprojeect.domain.vo.artist.ArtistVO;
import com.example.teamprojeect.domain.vo.user.UserVO;
import com.example.teamprojeect.service.ArtistService;
import com.example.teamprojeect.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoController {
    private final KakaoService kakaoService;
    private final ArtistService artistService;

    @GetMapping("/login")
    public RedirectView kakaoCallback(@RequestParam String code, HttpSession session, Model model) throws Exception {
        log.info(code);
        String token = kakaoService.getKaKaoAccessToken(code);
        log.info("==================================info.toString()");

        Long userNumber = kakaoService.getKakaoInfo(token, session);
        session.setAttribute("token", token);
        session.setAttribute("userNumber", kakaoService.getKakaoInfo(token, session)); // userNumber 세션에 카카오 유저 넘버 넣음

        // 아티스트 넘버가 있으면
        if(!(artistService.getDetail2(userNumber)==null)) {
            ArtistVO artistVO = artistService.getDetail2(userNumber);
            Long artistNumber = artistVO.getArtistNumber();
            session.setAttribute("artistNumber", artistNumber);
            log.info("혹시 널로 나오닡" + session.getAttribute("artistNumber"));

        } else { // 없으면
            // 아티스트 넘버 세션 null
        }

        log.info("kakaoService.getKakaoInfo(token).toString()================================" + kakaoService.getKakaoInfo(token, session).toString());
        session.setAttribute("sessionCheck", "k");

        if(session.getAttribute("kakaoJoin") == "kakaoJoin") {
            return new RedirectView("/myPage/myPage");
        } else {
            return new RedirectView("/main/");
        }
    }

    @GetMapping("/logout")
    public RedirectView kakaoLogout(HttpSession session, Model model) {
        log.info("logout");
        kakaoService.logoutKakao((String) session.getAttribute("token"));
        model.addAttribute("sessionCheck", "0");
        session.invalidate();
        return new RedirectView("/main/");
    }
}
