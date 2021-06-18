package com.boot.springboot.service.posts;

import com.boot.springboot.domain.posts.Posts;
import com.boot.springboot.domain.posts.PostsRepository;
import com.boot.springboot.web.dto.PostsResponseDto;
import com.boot.springboot.web.dto.PostsSaveRequestDto;
import com.boot.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor// lombok annotation, 생성자로 bean 주입(DI)
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
        // DB 접근 : dto(PostsSaveRequestDto) -> entity(Posts)
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)// Optional Class (non-null value)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));

        return new PostsResponseDto(entity);
    }
}
