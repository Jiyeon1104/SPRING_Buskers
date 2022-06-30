package com.example.teamprojeect.mapper.artist;

import com.example.teamprojeect.domain.vo.artist.ArtistDTO;
import com.example.teamprojeect.domain.vo.artist.ArtistFileVO;
import com.example.teamprojeect.domain.vo.artist.ArtistVO;
import com.example.teamprojeect.domain.vo.list.ListDTO;
import com.example.teamprojeect.domain.vo.paging.Criteria;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ArtistMapperTests {

    @Autowired
    private ArtistMapper artistMapper;
    private ArtistFileMapper artistFileMapper;

    // 아티스트 등록 신청
    @Test
    public void insertApplyTest(){
        ArtistVO artistVO = new ArtistVO();
        ArtistFileVO artistFileVO = new ArtistFileVO();

        for(Long i = 163L ; i < 310L ; i++) {
            artistVO.setUserNumber(i);
            artistVO.setArtistName("페퍼톤스");
            artistVO.setArtistDescription("수많은 목소리로 변치 않는 한 가지를 이야기하는 뮤지션. 음악 속에서 영롱하게 빛나는 선명한 청춘. 길위에서 만나요.");
            artistVO.setArtistType(3L);
            artistVO.setArtistAccount("하나은행 123412341234");
            artistMapper.insertApply(artistVO);
        }
    }

    // 아티스트 정보 수정
//    @Test
//    public void updateApplyTest(){
//        ArtistVO artistVO = new ArtistVO();
//
//        artistVO.setArtistNumber(2L);
//        artistVO.setArtistName("은솔");
//        artistVO.setArtistDescription("안녕!");
//        artistVO.setArtistType(1L);
//
//        artistMapper.updateApply(artistVO);
//    }

    // 아티스트 삭제
//    @Test
//    public void deleteTest(){
//        log.info("DELETE COUNT : " + artistMapper.delete(4L));
//    }

    //최신순 정렬 테스트
//    @Test
//    public void getListTest(){
//        artistMapper.getList(new Criteria(1, 10), new ListDTO("A","1","2","like","3"))
//                .stream().map(ArtistVO::toString).forEach(log::info);
//    }
//

//    @Test
//    public void getListTest(){
//        artistMapper.getList(new Criteria(1,5), new ListDTO("A","1","2","LIKE","3"))
//
//                .stream().map(ArtistVO::toString).forEach(log::info);
//    }

//    @Test
//    public void getListTest() {
//        artistMapper.getList(new Criteria(1, 10), new ListDTO("A", "1", "2", "LIKE", "3"))
//
//                .stream().map(ArtistVO::toString).forEach(log::info);
//    }
//
//    @Test
//    public void getListTesM() {
//        artistMapper.getListMusician(new Criteria(1, 10), new ListDTO("A", "1", "2", "NEW", "3"))
//
//                .stream().map(ArtistVO::toString).forEach(log::info);
//    }
//
//    @Test
//    public void getListTestP() {
//        artistMapper.getListPerformance(new Criteria(1, 10), new ListDTO("A", "1", "2", "NEW", "3"))
//
//                .stream().map(ArtistVO::toString).forEach(log::info);
//    }


//
//    @Test
//    public void getListMusicianTest(){
//        artistMapper.getListMusician(new ListDTO("A","1","2","NEW","3"))
//
//                .stream().map(ArtistVO::toString).forEach(log::info);
//    }
//
//    @Test
//    public void getListPerformanceTest(){
//        artistMapper.getListPerformance(new ListDTO("A","1","2","NEW","3"))
//
//                .stream().map(ArtistVO::toString).forEach(log::info);
//    }


//    @Test
//    public void rankingListAllTest(){
//        Criteria criteria = new Criteria();
//        ListDTO listDTO = new ListDTO();
//        listDTO.setDetailDate("2022");
//        listDTO.setRankingSortingDate("year");
////        listDTO.setDetailDate("2022-05");
////        listDTO.setRankingSortingDate("month");
////        listDTO.setDetailDate("06-01");
////        listDTO.setRankingSortingDate("week");
//        artistMapper.rankingListAll(criteria, listDTO).stream().map(ArtistDTO::toString).forEach(log::info);
//    }

//    @Test
//
//    public void rankingListCommentTest(){
//        Criteria criteria = new Criteria();
//        ListDTO listDTO = new ListDTO();
//        listDTO.setDetailDate("2022");
//        listDTO.setRankingSortingDate("year");
////        listDTO.setDetailDate("2022-05");
////        listDTO.setRankingSortingDate("month");
////        listDTO.setDetailDate("06-01");
////        listDTO.setRankingSortingDate("week");
//        artistMapper.rankingListComment(criteria, listDTO).stream().map(ArtistDTO::toString).forEach(log::info);
//    }


//    @Test
//    public void rankingListMusicianTest(){
//        Criteria criteria = new Criteria();
//        ListDTO listDTO = new ListDTO();
//        listDTO.setDetailDate("2022");
//        listDTO.setRankingSortingDate("year");
////        listDTO.setDetailDate("2022-05");
////        listDTO.setRankingSortingDate("month");
////        listDTO.setDetailDate("06-01");
////        listDTO.setRankingSortingDate("week");
//        artistMapper.rankingListMusician(criteria, listDTO).stream().map(ArtistDTO::toString).forEach(log::info);
//    }

//    @Test
//    public void rankingListPerformanceTest(){
//        Criteria criteria = new Criteria();
//        ListDTO listDTO = new ListDTO();
//        listDTO.setDetailDate("2022");
//        listDTO.setRankingSortingDate("year");
////        listDTO.setDetailDate("2022-05");
////        listDTO.setRankingSortingDate("month");
////        listDTO.setDetailDate("06-01");
////        listDTO.setRankingSortingDate("week");
//        artistMapper.rankingListPerformance(criteria, listDTO).stream().map(ArtistDTO::toString).forEach(log::info);
//    }

    @Test
    public void changeStatusTest() {
        artistMapper.changeStatus(127L);
    }
}