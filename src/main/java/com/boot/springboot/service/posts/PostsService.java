package com.boot.springboot.service.posts;

import com.boot.springboot.domain.posts.PostsRepository;
import com.boot.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor// lombok annotation, 생성자로 bean 주입(DI)
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }
}
