package com.example.teamprojeect.mapper.artist;

import com.example.teamprojeect.domain.vo.artist.ArtistFileVO;
import com.example.teamprojeect.domain.vo.user.UserFileVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
@Slf4j
public class ArtistFileMapperTests {
    @Autowired
    ArtistFileMapper artistFileMapper;

    // 아티스트 사진 추가
    @Test
    public void insertTest() {
        ArtistFileVO artistFileVO = new ArtistFileVO();
//
//        artistFileVO.setArtistNumber(1L);
//        artistFileVO.setUuid("fileUuid" + 1);
//        artistFileVO.setUploadPath("2022/06/28");
//        artistFileVO.setFileName("file 이름 테스트");
//        artistFileMapper.insert(artistFileVO);

        for(Long i = 58L ; i < 232L ; i++ ) {
            artistFileVO.setArtistNumber(i);
            artistFileVO.setUuid(i + "사진");
            artistFileVO.setUploadPath("2022/06/30");
            artistFileVO.setFileName("TJ8A9941.jpeg.jpg");
            artistFileMapper.insert(artistFileVO);
        }
    }
}
