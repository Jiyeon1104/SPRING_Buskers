package com.example.teamprojeect.service;

import com.example.teamprojeect.domain.dao.user.LikeDAO;
import com.example.teamprojeect.domain.dao.user.UserDAO;
import com.example.teamprojeect.domain.dao.user.UserFileDAO;
import com.example.teamprojeect.domain.vo.list.ListDTO;
import com.example.teamprojeect.domain.vo.paging.Criteria;
import com.example.teamprojeect.domain.vo.user.LikeVO;
import com.example.teamprojeect.domain.vo.user.UserFileVO;
import com.example.teamprojeect.domain.vo.user.UserVO;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.ContinueResponseTiming;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDAO;
    private final UserFileDAO userFileDAO;
    private final LikeDAO likeDAO;

    // 마이페이지 : 유저 정보 조회
    public UserVO read(Long userNumber){return userDAO.read(userNumber);}

    // 마이페이지 : 유저 정보 수정
    public boolean modify(UserVO userVO){
        return userDAO.modify(userVO);
    }

    // 마이페이지 : 비밀번호 수정
    public boolean modifyPw(Long userNumber, String password){
        return userDAO.modifyPw(userNumber, password);
    }

    // 마이페이지 : 회원탈퇴
    public boolean remove(Long userNumber){
        return userDAO.remove(userNumber);
    }

    // 로그인
    public boolean login(String userId, String userPw) {return userDAO.login(userId,userPw);}

    // 좋아요 등록 (아티스트)
    public void registerArtistLike(LikeVO likeVO) {likeDAO.registerArtistLike(likeVO);}

    // 좋아요 등록 (작품)
    public void registerWorkLike(LikeVO likeVO) {likeDAO.registerWorkLike(likeVO);}

    // 좋아요 취소
    public boolean removeLike(Long userNumber, ListDTO listDTO, Long number) { return likeDAO.removeLike(userNumber, listDTO, number); }

    // 좋아요 아티스트 목록
    public List<LikeVO> getLikeArtistList(Criteria criteria, Long userNumber, ListDTO listDTO) { return likeDAO.getLikeArtistList(criteria, userNumber, listDTO); }

    // 좋아요 작품 목록
    public List<LikeVO> getLikeWorkList(Criteria criteria, Long userNumber) { return likeDAO.getLikeWorkList(criteria, userNumber); }

    // 좋아요한 아티스트 수
    public int getTotalArtist(Long userNumber, ListDTO listDTO) { return likeDAO.getTotalArtist(userNumber, listDTO); }

    // 좋아요한 작품 수
    public int getTotalWork(Long userNumber) { return likeDAO.getTotalWork(userNumber); }

    //회원가입
    public void join(UserVO userVO) {userDAO.join(userVO);}
}
