package com.boot.springboot.web.dto;

import com.boot.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter// No Setter
@NoArgsConstructor// --------> ???
public class PostsSaveRequestDto {// view를 위한 클래스 -> 변경 잦음 / dto와 entity class 분리 !
    private String title;
    private String content;
    private String author;

    // <생성자를 통해> 값 설정 (No Setter)
    @Builder// 빌더 패턴 클래스 생성
    public PostsSaveRequestDto(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
