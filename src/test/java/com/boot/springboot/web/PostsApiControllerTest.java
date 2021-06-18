package com.boot.springboot.web;

import com.boot.springboot.domain.posts.Posts;
import com.boot.springboot.domain.posts.PostsRepository;
import com.boot.springboot.web.dto.PostsSaveRequestDto;
import com.boot.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// @SpringBootTest : @SpringBootApplication을 찾아서 테스트를 위한 빈 생성 / RANDOM_PORT : 내장 톰캣 사용, 기본값 MOCK(내장 톰캣 사용 안 함)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)// + JPA 기능 사용
public class PostsApiControllerTest {

    @LocalServerPort//
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;// 통합테스트 / @WebMvcTest + JPA 기능 사용
    // 내장 톰캣 사용 시 MockMvc 대신 RestTemplate을 사용 -> 내장 톰캣과 Interaction

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void registerPosts() throws Exception {
        // given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
        // url, 요청 객체, 응답 타입
        // Create a new resource by POSTing the given object to the URL, and returns the response as ResponseEntity.

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();// All List
        assertThat(all.get(0).getTitle()).isEqualTo(title);// 0번째 객체가 가장 나중에 넣은 객체
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void updatePosts() throws Exception {
        // given
        Posts savedPosts = postsRepository.save(Posts.builder()
            .title("title")
            .content("content")
            .author("author")
            .build());

        Long updatedId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updatedId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate
                .exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();// All List
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);// 0번째 객체가 가장 나중에 넣은 객체
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}
