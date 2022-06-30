package com.example.teamprojeect.service;

import com.example.teamprojeect.domain.dao.artist.ArtistDAO;
import com.example.teamprojeect.domain.dao.artist.ArtistFileDAO;
import com.example.teamprojeect.domain.dao.artist.ArtistReplyDAO;
import com.example.teamprojeect.domain.dao.artist.PaymentDAO;
import com.example.teamprojeect.domain.dao.user.LikeDAO;
import com.example.teamprojeect.domain.vo.artist.ArtistFileVO;
import com.example.teamprojeect.domain.vo.artist.ArtistReplyVO;
import com.example.teamprojeect.domain.vo.artist.ArtistVO;
import com.example.teamprojeect.domain.vo.list.ListDTO;
import com.example.teamprojeect.domain.vo.paging.Criteria;
import com.example.teamprojeect.domain.vo.show.ShowFileVO;
import com.example.teamprojeect.domain.vo.show.ShowReplyVO;
import com.example.teamprojeect.domain.vo.show.ShowVO;
import com.example.teamprojeect.domain.vo.user.LikeVO;
import com.example.teamprojeect.mapper.artist.ArtistMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtistService {
    //필드
    private final ArtistDAO artistDAO;
    private final ArtistFileDAO artistFileDAO;
    private final ArtistReplyDAO artistReplyDAO;
    private final LikeDAO likeDAO;
    private final PaymentDAO paymentDAO;
    private final ArtistMapper artistMapper;
    private final ListDTO listDTO;

    @Transactional(rollbackFor = Exception.class)
    // 아티스트 등록 신청
    public void registerApply (ArtistVO artistVO){
        artistDAO.registerApply(artistVO);
        ArtistFileVO artistFileVO = artistVO.getFile();
        log.info("===========================================");
        log.info((artistVO.getFile()).toString());
        log.info(artistVO.getArtistNumber().toString());
        artistFileVO.setArtistNumber(artistVO.getArtistNumber());
        artistFileDAO.insert(artistFileVO);
        log.info("===========================================");
        log.info(artistFileVO.toString());
    }

    // 아티스트 정보 수정 신청
    @Transactional(rollbackFor = Exception.class)
    public boolean modifyApply(ArtistVO artistVO){
        log.info("===========================================");
        ArtistFileVO artistFileVO = artistVO.getFile();
        artistFileVO.setArtistNumber(artistVO.getArtistNumber());
        artistFileDAO.delete(artistVO.getArtistNumber());
        artistFileDAO.insert(artistFileVO);
        log.info("===========================================");
        return artistDAO.modifyApply(artistVO);
    }

    // 아티스트 등록, 수정 신청 리스트
    public List<ArtistVO> getArtistApplyList(Criteria criteria, boolean isUpdate) {
        return artistDAO.getArtistApplyList(criteria, isUpdate);
    }

    public ArtistFileVO find(Long artistNumber) { return artistDAO.find(artistNumber);}
    // 아티스트 등록, 수정 승인
    public boolean approveArtistApply(Long artistNumber){
        return artistDAO.approveArtistApply(artistNumber);
    }

    // 아티스트 신청 승인시 유저 스테이터스 변경
    public boolean changeStatus(Long userNumber) {
        return artistDAO.changeStatus(userNumber);
    }

    // 아티스트 등록, 수정 반려
    public boolean rejectArtistApply(Long artistNumber){
        return artistDAO.rejectArtistApply(artistNumber);
    }

    // 전체 아티스트 리스트
    public List<ArtistVO> getArtistList(Criteria criteria) {
        return artistDAO.getArtistList(criteria);
    }

    // 아티스트 탈퇴
    public boolean removeArtist(Long artistNumber){
        return artistDAO.remove(artistNumber);
    }

    //아티스트 전체목록
    public List<ArtistVO> getList(Criteria criteria, ListDTO listDTO) {
        return artistDAO.getList(criteria, listDTO);
    }
    //아티스트 뮤지션 목록
    public List<ArtistVO> getListMusician(Criteria criteria, ListDTO listDTO) {
        return artistDAO.getListMusician(criteria, listDTO);
    }
    //아티스트 퍼포먼스 목록
    public List<ArtistVO> getListPerformance(Criteria criteria, ListDTO listDTO) {
        return artistDAO.getListPerformance(criteria, listDTO);
    }

    //아티스트 갯수
    public int getTotal(ListDTO listDTO) {
        return artistMapper.getTotal(listDTO);
    }
    public int getTotalMusician(ListDTO listDTO) {
        return artistMapper.getTotalMusician(listDTO);
    }

    public int getTotalPerformance(ListDTO listDTO) {
        return artistMapper.getTotalPerformance(listDTO);
    }

    //아티스트 상세정보
    public ArtistVO getDetail(Long artistNumber) {
        return artistDAO.getDetail(artistNumber);
    }

    public void registerReply(ArtistReplyVO artistReplyVO) {
        artistReplyDAO.register(artistReplyVO);
    }

    public boolean modifyReply(ArtistReplyVO artistReplyVO) {
        return artistReplyDAO.modify(artistReplyVO);
    }

    public boolean removeReply(Long replyNumber) {
        return artistReplyDAO.remove(replyNumber);
    }

    public int getTotalReply(Long artistNumber) {
        return artistReplyDAO.getTotal(artistNumber);
    }

    public List<ArtistReplyVO> getListReply(Criteria criteria, Long showNumber) {
        return  artistReplyDAO.getList(criteria, showNumber);
    }


    // 아티스트 등록, 수정 신청 아티스트 수
    public int getArtistApplyTotal(boolean isUpdate) {

        return artistDAO.getArtistApplyTotal(isUpdate);
    }

    // 등록된 아티스트 수
    public int getAllArtistTotal() {
        return artistDAO.getAllArtistTotal();
    }


    public void registerUserLike(LikeVO likeVO) {
        likeDAO.registerArtistLike(likeVO);
    }

    public void removeUserLike(LikeVO likeVO) {
        likeDAO.removeArtistLike(likeVO);
    }

    public int checkArtistLike(Long artistNumber, Long userNumber) {return artistDAO.checkArtistLike(artistNumber, userNumber);}

    public int artistLikeCount(Long artistNumber) {return artistDAO.artistLikeCount(artistNumber);}
    // 아티스트 세션용
    public ArtistVO getDetail2(Long userNumber) {return artistDAO.getDetail2(userNumber);}

}
