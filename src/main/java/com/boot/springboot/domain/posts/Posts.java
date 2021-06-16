package com.boot.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter// NO SETTER in Entity Class !!!!! -> 값 변경 필요 시 목적과 의도를 나타내는 메소드 추가!
@NoArgsConstructor// Lombok annotation
@Entity// 테이블과 링크될 클래스 표시, JPA annotation(주요 어노테이션을 클래스에 가깝게 위치 -> 언어 전환 시 쉽게 삭제)
public class Posts {// -> posts table, 테이블 설계 = 여기서 Entity 설계

    @Id// PK 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY)// PK 생성규칙, auto_increment
    private Long id;

    @Column(length = 500, nullable = false)// @Column 안 써도 칼럼됨, 문자열 default VARCHAR(255)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)// nullable default: true
    private String content;

    private String author;

    // <생성자를 통해> 값 설정 후 DB에 Insert ! (No Setter)
    @Builder// 빌더 패턴 클래스 생성
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
