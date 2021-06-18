package com.boot.springboot.domain.posts;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass// JPA Entity 클래스가 이 클래스 상속 시 필드들 칼럼으로 인식하도록 설정
@EntityListeners(AuditingEntityListener.class)// JPA Auditing 사용
public abstract class BaseTimeEntity {

    @CreatedDate// Entity 생성 시 시간 자동 저장
    private LocalDateTime createdDate;

    @LastModifiedDate// 조회한 entity 수정 시 시산 자동 저장
    private LocalDateTime modifiedDate;
}
