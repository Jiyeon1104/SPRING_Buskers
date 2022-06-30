package com.example.teamprojeect.domain.dao.artist;

import com.example.teamprojeect.domain.vo.artist.ArtistDTO;
import com.example.teamprojeect.domain.vo.artist.ArtistFileVO;
import com.example.teamprojeect.domain.vo.artist.ArtistVO;
import com.example.teamprojeect.domain.vo.list.ListDTO;
import com.example.teamprojeect.domain.vo.paging.Criteria;
import com.example.teamprojeect.domain.vo.paging.artist.ArtistPageDTO;
import com.example.teamprojeect.domain.vo.show.ShowFileVO;
import com.example.teamprojeect.mapper.artist.ArtistFileMapper;
import com.example.teamprojeect.mapper.artist.ArtistMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ArtistDAO {
    private final ArtistMapper artistMapper;
    private final ArtistFileMapper artistFileMapper;


    public ArtistFileVO find(Long artistNumber) { return artistFileMapper.find(artistNumber);}
    // 아티스트 정보 //
    // 아티스트 등록 신청 (status 1)
    public void registerApply(ArtistVO artistVO) {
        artistMapper.insertApply(artistVO);
    };

    // 관리자 등록 승인 (status -> 3)
    public void registerAdmin(Long artistNumber){
        artistMapper.insertAdmin(artistNumber);
    }

    // 아티스트 정보 수정 신청 (status 2)
    public boolean modifyApply(ArtistVO artistVO) {
        return artistMapper.approveArtistApply(artistVO) == 1;
    }

    // 관리자 수정 승인 (status -> 3)
    public void modifyAdmin(Long artistNumber) {
        artistMapper.updateAdmin(artistNumber);
    }

    // 아티스트 삭제
    public boolean remove(Long artistNumber) {
        return artistMapper.delete(artistNumber) == 1;
    }

    // 아티스트 목록
    // (매개변수로 artistType을 전달해서 'A'(전체)인지 'M'(아티스트) 인지 'P'(퍼포먼스)인지 구분)
    // (매개변수로 sortingType을 전달해서 '최신순'과 '좋아요순' 구분)
    public List<ArtistVO> getList(Criteria criteria, ListDTO listDTO) {
        return artistMapper.getList(criteria, listDTO);
    }
    //아티스트 전체목록
//    public List<ArtistVO> getList(Criteria criteria ListDTO list) {
//        return artistMapper.getList(criteria);
//    }
    //아티스트 뮤지션 목록
    public List<ArtistVO> getListMusician(Criteria criteria, ListDTO listDTO) {
        return artistMapper.getListMusician(criteria, listDTO);
    }
    //아티스트 퍼포먼스 목록
    public List<ArtistVO> getListPerformance(Criteria criteria, ListDTO listDTO) {
        return artistMapper.getListPerformance(criteria, listDTO);
    }

    /*
    artistType likeType (최신, 좋아요 순)
    * * */
    // 아티스트 목록 개수
    // 랭킹 목록 개수
    public int getTotal(ListDTO listDTO) {
        return artistMapper.getTotal(listDTO);
    }

    public int getTotalMusician(ListDTO listDTO) {
        return artistMapper.getTotalMusician(listDTO);
    }

    public int getTotalPerformance(ListDTO listDTO) {
        return artistMapper.getTotalPerformance(listDTO);
    }

    // 랭킹 //
    // 랭킹 목록
    // (매개변수로 artistType을 전달해서 'A'(전체)인지 'M'(아티스트) 인지 'P'(퍼포먼스)인지 구분)
    // (매개변수로 사용자 클릭에 따른 date를 전달, dateToday로 현재 시간도 전달)
    public List<ArtistDTO> rankingListAll(@Param("criteria") Criteria criteria, @Param("listDTO") ListDTO listDTO) {
        return artistMapper.rankingListAll(criteria, listDTO);
    }
    //뮤지션 랭킹
    public List<ArtistDTO> rankingListMusician(@Param("criteria") Criteria criteria, @Param("listDTO") ListDTO listDTO) {
        return artistMapper.rankingListMusician(criteria, listDTO);
    }
    //퍼포먼스 랭킹
    public List<ArtistDTO> rankingListPerformance(@Param("criteria") Criteria criteria, @Param("listDTO") ListDTO listDTO) {
        return artistMapper.rankingListPerformance(criteria, listDTO);
    }



    // 아티스트 상세 정보
    public ArtistVO getDetail(Long artistNumber) {
        return artistMapper.getDetail(artistNumber);
    }


    // 아티스트 등록,수정 신청 목록
    public List<ArtistVO> getArtistApplyList(Criteria criteria, boolean isUpdate) {
        return artistMapper.getArtistApplyList(criteria, isUpdate);
    }

    // 아티스트 등록, 수정 신청 승인
    public boolean approveArtistApply(Long artistNumber) {
        return artistMapper.insertAdmin(artistNumber) == 1;
    }

    public boolean changeStatus(Long userNumber) {
        return artistMapper.changeStatus(userNumber) == 1;
    }

    // 아티스트 등록, 수정 신청 반려
    public boolean rejectArtistApply(Long artistNumber) {
        return artistMapper.rejectArtistApply(artistNumber) == 1;
    }

    // 아티스트 등록, 수정 신청 아티스트 수
    public int getArtistApplyTotal(boolean isUpdate) {
        return artistMapper.getArtistApplyTotal(isUpdate);
    }

    // 전체 아티스트 리스트
    public List<ArtistVO> getArtistList(Criteria criteria) {
        return artistMapper.getArtistList(criteria);
    }

    // 등록된 아티스트 수
    public int getAllArtistTotal() {
        return artistMapper.getAllArtistTotal();
    }


    public int checkArtistLike(Long artistNumber, Long userNumber) {return artistMapper.checkArtistLike(artistNumber, userNumber);}

    public int artistLikeCount(Long artistNumber) {return artistMapper.artistLikeCount(artistNumber);}

    public ArtistVO getDetail2(Long userNumber) {return artistMapper.getDetail2(userNumber);}

}
