package com.example.teamprojeect.controller.work;

import com.example.teamprojeect.domain.vo.artist.ArtistVO;
import com.example.teamprojeect.domain.vo.list.ListDTO;
import com.example.teamprojeect.domain.vo.paging.Criteria;
import com.example.teamprojeect.domain.vo.paging.PageDTO;
import com.example.teamprojeect.domain.vo.paging.work.WorkApplyPageDTO;
import com.example.teamprojeect.domain.vo.paging.work.WorkPageDTO;
import com.example.teamprojeect.domain.vo.work.WorkFileVO;
import com.example.teamprojeect.domain.vo.work.WorkVO;
import com.example.teamprojeect.service.ArtistService;
import com.example.teamprojeect.service.WorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/work/*")
public class WorkController {
    // 필드 생성
    private final WorkService workService;
    private final ArtistService artistService;

    // 작품리스트 페이지 이동
    @GetMapping("/workList")
    public String goWorkList(Criteria criteria,Model model, HttpServletRequest request) {
        model.addAttribute("workList",workService.getKeyword(new Criteria(1, 100),new ListDTO()));
        model.addAttribute("pageDTO",new PageDTO(criteria, workService.getTotalListApply()));
        model.addAttribute("workTag", workService.getTag());
        HttpSession session = request.getSession();
        model.addAttribute("artistNumber",session.getAttribute("artistNumber"));
        return "/work/workList";
    }

    @ResponseBody
    @GetMapping("/workList/{tag}/{page}")
    public WorkApplyPageDTO goWorkList(@PathVariable("tag") String tag,@PathVariable("page") int pageNum, Model model) {
        ListDTO listDTO = new ListDTO();
        List<String> tagList = new ArrayList<String>();
        if(!tag.equals("null")) {
            System.out.println("----------------"+tag);
            tagList.add(tag);
            List<String> list = tagList.stream().distinct().collect(Collectors.toList());
            System.out.println("----------------"+tagList);
            listDTO.setTag(list);
        }else if(tag.equals("null")) {
            listDTO.setTag(null);
        }
        model.addAttribute("tagList",workService.getTag());
        return new WorkApplyPageDTO(workService.getKeyword(new Criteria(pageNum,100),listDTO),workService.getTotalListApply());
    }

    // 작품 상세보기 페이지 이동
    @GetMapping({"/workInfo","/workUpdate"})
    public void goWorkInfo(Long workNumber, Criteria criteria, HttpServletRequest request, Model model) {
        log.info("*************");
        String requestURL = request.getRequestURI();
        log.info(requestURL.substring(requestURL.lastIndexOf("/")));
        log.info("*************");
        log.info("================================");
        log.info(criteria.toString());
        log.info("================================");
        model.addAttribute("file",workService.find(workNumber));
        model.addAttribute("artistNumber",workService.getArtist(workNumber));
        model.addAttribute("work", workService.getDetail(workNumber));
    }

    // 작품 등록 신청 페이지 이동
    @GetMapping("/workRegister")
    public void goWorkRegister(Model model, HttpServletRequest request){
        log.info("*************");
        log.info("/register");
        log.info("*************");
        HttpSession session = request.getSession();
        log.info("-------------------------------session: "+ session.getAttribute("artistNumber"));
        ArtistVO artistVO = artistService.getDetail(Long.valueOf(String.valueOf((session.getAttribute("artistNumber")))));
        log.info("-------------------------------session: "+ artistVO.getArtistName());
        model.addAttribute("artistName", artistVO.getArtistName());
    }

    @PostMapping("/workRegister")
    public RedirectView goWorkRegister(WorkVO workVO,RedirectAttributes rttr, HttpServletRequest request) {
        log.info("*************");
        log.info("/register");
        log.info("*************");
        HttpSession session = request.getSession();
        log.info("test : "+workVO +" session: "+ session.getAttribute("artistNumber"));

        workVO.setArtistNumber(Long.valueOf(String.valueOf((session.getAttribute("artistNumber")))));
        workService.registerApply(workVO);
        rttr.addFlashAttribute("workNumber", workVO.getWorkNumber());
        return new RedirectView("/work/workList");
    }

    @PostMapping("/workUpdate")
    public RedirectView goWorkUpdate(WorkVO workVO, Criteria criteria, RedirectAttributes rttr, HttpServletRequest request) {
        log.info("*************");
        log.info("/update");
        log.info("*************");
        log.info("================================");
        log.info(criteria.toString());
        log.info("================================");
        HttpSession session = request.getSession();
        workVO.setArtistNumber(Long.valueOf(String.valueOf((session.getAttribute("artistNumber")))));
        if(workService.modifyApply(workVO)){
            rttr.addAttribute("workNumber", workVO.getWorkNumber());
            rttr.addAttribute("pageNum",criteria.getPageNum());
            rttr.addAttribute("amount",criteria.getAmount());
        }
        return new RedirectView("/work/workInfo");
    }


}
