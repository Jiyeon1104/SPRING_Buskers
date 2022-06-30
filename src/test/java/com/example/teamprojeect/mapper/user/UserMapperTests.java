package com.example.teamprojeect.mapper.user;

import com.example.teamprojeect.domain.vo.list.UserListDTO;
import com.example.teamprojeect.domain.vo.paging.Criteria;
import com.example.teamprojeect.domain.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class UserMapperTests {
    @Autowired
    private UserMapper userMapper;

//    회원가입 : 회원가입 매퍼테스트 - 성공
    @Test
    public void joinTest() {
        UserVO userVO = new UserVO();
        for(int i = 51 ; i < 200 ; i ++) {
            userVO.setUserId("busker" + i);
            userVO.setUserPw("12341234r");
            userVO.setUserName("왕고냥" + i);
            userVO.setUserPhoneNumber("0101234123" + i);
            userMapper.join(userVO);
        }
    }

    @Test
    public void kakaoJoin() {
        UserVO userVO = new UserVO();
        String userId = "q3686@adnfa";
        userVO.setUserId(userId);
        userMapper.kakaoJoin(userVO);
    }
    @Test
    public void kakaoLoginTest() {
        String userId = "q3676@naver.com";
        userMapper.kakaoLogin(userId);
        log.info(userMapper.kakaoLogin(userId).toString());
    }

//    회원가입 : 아이디 찾기
    @Test
    public void searchIdTest() {
        String userId = "12313123";
        userMapper.searchId(userId);
    };

//    마이페이지 : 유저 정보 조회 - 성공
    @Test
    public void getUserDetailTest(){
        Long userNumber = 32L;
        log.info(userMapper.getUserDetail(userNumber).toString());
    }

//    마이페이지 : 유저 정보 수정 - 성공
    @Test
    public void updateTest(){
        Long userNumber = 1L;
        UserVO userVO = userMapper.getUserDetail(userNumber);

        if(userVO == null) {log.info("NO USER"); return;}

        userVO.setUserEmail("jiyeon1104@nate.com");
        userVO.setUserName("핑공");
        userVO.setUserPhoneNumber("01022222222");

        userMapper.update(userVO);
    }

//    마이페이지 : 비밀번호 수정 - 성공
    @Test
    public void updatePwTest(){
        userMapper.updatePw(1L, "1235");
    }

//    마이페이지 : 회원 탈퇴 - 성공
    @Test
    public void deleteTest(){
        log.info("DELETE COUNT : " + userMapper.delete(1L));
    }

// 로그인 - 성공
    @Test
    public void loginTest(){
         userMapper.login("tndus", "1234");
    }

    // 유저 리스트 // 잘 됨
    @Test
    public void getUserListTest() {
        Criteria criteria = new Criteria(13, 10);
        userMapper.getUserList(criteria, false).stream().map(UserVO::toString).forEach(log::info);
    }

    // 총 유저 수 // 잘 됨
    @Test
    public void getTotalTest() {
        userMapper.getTotal();
    }

    @Test
    public void findTest() {
        userMapper.find("01075013676");
    }
}

