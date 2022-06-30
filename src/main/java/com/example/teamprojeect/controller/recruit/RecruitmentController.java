package com.example.teamprojeect.controller.recruit;


import com.example.teamprojeect.domain.vo.list.ListDTO;
import com.example.teamprojeect.domain.vo.paging.Criteria;
import com.example.teamprojeect.domain.vo.paging.PageDTO;
import com.example.teamprojeect.domain.vo.paging.recruitment.RecruitListPageDTO;
import com.example.teamprojeect.domain.vo.paging.recruitment.RecruitmentPageDTO;
import com.example.teamprojeect.domain.vo.paging.work.WorkApplyPageDTO;
import com.example.teamprojeect.domain.vo.recruitment.RecruitmentFileVO;
import com.example.teamprojeect.domain.vo.recruitment.RecruitmentVO;
import com.example.teamprojeect.domain.vo.show.ShowVO;
import com.example.teamprojeect.service.RecruitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/recruit/*")
public class RecruitmentController {
    // 필드 생성
    private final RecruitService recruitService;

    // 모집공고 페이지 이동
    @GetMapping("/recruitList")
    public String goRecruit() {
        return "recruit/recruitList";
    }

    // 관리자 페이지에 모집 공고 리스트 가져오기 (만들 예정) by REST
    @ResponseBody
    @GetMapping("/recruitmentList/{page}")
    public RecruitListPageDTO getRecruitList(@PathVariable("page") int pageNum) {
        int total = recruitService.getTotal(new ListDTO());
        return new RecruitListPageDTO(recruitService.getList(new Criteria(pageNum, 10), new ListDTO()), total);
    }
    @ResponseBody
    @GetMapping("/recruitmentList/{type}/{page}/{amount}")
    public RecruitListPageDTO getRecruitList(@PathVariable("page") int pageNum,@PathVariable("amount") int amount, @PathVariable("type") String recruitmentType) {
        ListDTO listDTO = new ListDTO();
        listDTO.setArtistType(recruitmentType);
        List<RecruitmentVO> recruitList = recruitService.getList(new Criteria(pageNum, amount), listDTO);

        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");

        recruitList.forEach(recruitmentVO -> {
            String recruitDay = recruitmentVO.getRecruitmentDay();
            String todayDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));

            try {
                Date date = new Date(dayFormat.parse(recruitDay).getTime());
                Date today = new Date(dayFormat.parse(todayDay).getTime());
                long calculate = date.getTime() - today.getTime();
                int Ddays = (int) (calculate / ( 24*60*60*1000));
                recruitmentVO.setDDay(Integer.toString(Ddays));
            } catch (ParseException e) {
                System.err.println("dateStr : " + recruitDay + ", datePattern:" + dayFormat);
                e.printStackTrace();
            }
        });

        int total = recruitService.getTotal(listDTO);
        return new RecruitListPageDTO(recruitList, total);
    }

    // 관리자 페이지에서 모집 공고 삭제
    @ResponseBody
    @GetMapping("/recruitmentDelete/{rno}")
    public String deleteRecruitment(@PathVariable("rno") Long recruitmentNumber){
        recruitService.remove(recruitmentNumber);
        return "모집 공고가 삭제되었습니다.";
    }


    // 모집공고 상세페이지 이동
    @GetMapping({"/recruitInfo","/recruitUpdate"})
    public void goRecruitInfo(Long recruitmentNumber, Criteria criteria, HttpServletRequest request, Model model) {
        log.info("*************");
        String requestURL = request.getRequestURI();
        log.info(requestURL.substring(requestURL.lastIndexOf("/")));
        log.info("*************");
        log.info("================================");
        log.info(criteria.toString());
        log.info("================================");
        model.addAttribute("file",recruitService.getFiles(recruitmentNumber));
        model.addAttribute("recruitment", recruitService.getDetail(recruitmentNumber));
    }

    @GetMapping("/recruitRegister")
    public void goRecruitRegister(){}

    // 모집공고 등록 페이지 이동
    @PostMapping("/recruitRegister")
    public RedirectView goRecruitRegister(RecruitmentVO recruitmentVO, RedirectAttributes rttr) {
        log.info("*************");
        log.info("/register");
        log.info("*************");
        log.info(recruitmentVO.getFile().toString());
        log.info(recruitmentVO.getFile().toString());
        log.info(recruitmentVO.getFile().toString());
        recruitService.register(recruitmentVO);
        rttr.addFlashAttribute("recruitmentNumber",recruitmentVO.getRecruitmentNumber());
        return new RedirectView("/recruit/recruitList");
    }

    // 모집공고 수정 페이지 이동
    @PostMapping("/recruitUpdate")
    public RedirectView goRecruitUpdate(RecruitmentVO recruitmentVO, Criteria criteria, RedirectAttributes rttr) {
        log.info("*************");
        log.info("/update");
        log.info("*************");
        log.info("================================");
        log.info(criteria.toString());
        log.info("================================");
        if(recruitService.modify(recruitmentVO)) {
            rttr.addAttribute("recruitmentNumber",recruitmentVO.getRecruitmentNumber());
            rttr.addAttribute("pageNum",criteria.getPageNum());
            rttr.addAttribute("amount",criteria.getAmount());
        }
        return new RedirectView("/recruit/recruitInfo");
    }

}
