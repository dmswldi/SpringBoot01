package com.boot.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {// <Entity Class, PK Type>
    // MyBatis의 DAO 역할 = JPA의 Repository(i)
    // -> CRUD 자동 생성
    // Entity 클래스와 Entity Repository는 /domain에 함께 위치
}
