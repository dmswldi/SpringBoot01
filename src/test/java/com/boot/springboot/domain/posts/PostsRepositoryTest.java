package com.boot.springboot.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)// JUnit 5
@SpringBootTest// -> H2 DB 자동 실행
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach// JUnit4의 @After, 단위 테스트 끝날 때마다 수행되는 메소드
    public void cleanup(){
        postsRepository.deleteAll();// delete from posts where id = ?
    }

    @Test
    public void fetchBoard(){
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()// posts 테이블에 insert(id X)/update(id O) 쿼리 실행
                .title(title)
                .content(content)
                .author("ez@gmail.com")
                .build());

        List<Posts> postsList = postsRepository.findAll();// 테이블 posts의 모든 데이터 조회

        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }


    @Test
    public void BaseTimeEntity_register(){
        // given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0, 0);
        postsRepository.save(Posts.builder()
            .title("title")
            .content("content")
            .author("author")
            .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        System.out.println(">>>>>>> createdDate = " + posts.getCreatedDate()
            + ", modifiedDate = " + posts.getModifiedDate());

//        assertThat(posts.getCreatedDate().isAfter(now));
//        assertThat(posts.getModifiedDate().isAfter(now));
        assertThat(posts.getModifiedDate().isBefore(now));
    }
}
