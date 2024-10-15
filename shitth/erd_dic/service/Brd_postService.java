package com.packt.cantata.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packt.cantata.domain.Brd_division;
import com.packt.cantata.domain.Brd_post;
import com.packt.cantata.repository.Brd_postRepository;

@Service
public class Brd_postService {

	@Autowired
	private Brd_postRepository postRepository;

	public Brd_post findLatestPostByStatusAndBrdNo(Boolean postStatus, Brd_division brdNo) {
        return postRepository.findTopByPostStatusAndBrdNoOrderByPostNoDesc(postStatus, brdNo);
    }

	public Long getLastPostNumForBrdNo(Long brdNo) {
	    Long lastPostNum = postRepository.findLastPostNumForBrdNo(brdNo);

	    return lastPostNum != null ? lastPostNum : 0L;
	}


	public Brd_post savePost(Brd_post brdPost) {
		Long lastPostNum = getLastPostNumForBrdNo(brdPost.getPostNo());
		brdPost.setPostNum(lastPostNum + 1);

		return postRepository.save(brdPost);
	}

	public void postView(Long postNo) {
		Brd_post post = postRepository.findById(postNo)
				.orElseThrow(() -> new NoSuchElementException("게시물을 찾을 수 없습니다."));
		post.setPostViews(post.getPostViews() + 1);

		postRepository.save(post);
	}

    public void updatePost(Long postNo, Brd_post updatedPost) {
        Brd_post existingPost = postRepository.findById(postNo)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postNo));


        existingPost.setPostTitle(updatedPost.getPostTitle());
        existingPost.setPostFile1(updatedPost.getPostFile1());
        existingPost.setPostSub(updatedPost.getPostSub());
        existingPost.setPostDeadline(updatedPost.getPostDeadline());

        postRepository.save(existingPost);
    }

//	public void savFileToPost(Long postNo, MultipartFile file) throws IOException {
//		Brd_post post = postRepository.findById(postNo).orElseThrow(EntityNotFoundException::new);
//
//		//MultipartFile을 byte 배열로 변환
//		byte[] fileBytes = file.getBytes();
//
//		//덴티티 파일에 데이터 설정
//		post.setPostFile1(fileBytes);
//
//		//저장된 엔티티 업데이트
//		postRepository.save(post);
//    }

}
