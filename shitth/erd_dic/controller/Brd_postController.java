package com.packt.cantata.controller;

import java.io.IOException;
import java.util.List;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.packt.cantata.CantataApplication;
import com.packt.cantata.domain.Brd_division;
import com.packt.cantata.repository.Brd_divisionRepository;
import com.packt.cantata.domain.Brd_post;
import com.packt.cantata.repository.Brd_postRepository;
import com.packt.cantata.domain.User;
import com.packt.cantata.repository.UserRepository;
import com.packt.cantata.dto.Brd_postDto;
import com.packt.cantata.service.Brd_postService;
import com.packt.cantata.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brd_posts")
public class Brd_postController {
    private static final Logger logger = LoggerFactory.getLogger(CantataApplication.class);

    @Autowired
    private Brd_postRepository postRepository;

    @Autowired
    private Brd_postService postService;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Brd_divisionRepository divisionRepository;

   @GetMapping("/allPosts")
    public List<Brd_post> getBrd_Posts() {
        return postRepository.findAll();
    }

    @GetMapping("/searchBrdNo/{brdNo}")
    public List<Brd_post>getBrd_postsNo(Brd_division brdNo){
    	return postRepository.findByBrdNo(brdNo);
    }

    @GetMapping("/searchUserId/{id}")
    public List<Brd_post>getUserId(User id){
    	return postRepository.findIdById(id);
    }

    @GetMapping("/searchPostNo/{postNo}")
    public Brd_post getPostNo(Brd_post brdPost){
    	Brd_post returnPostNo = postRepository.findByPostNo(brdPost.getPostNo());
    	return returnPostNo;
    }

    @GetMapping("/postByPostNo/{postNo}")
    public ResponseEntity<Brd_postDto> getPostByPostNo(@PathVariable Long postNo) {
        try {
            // 특정 postNo에 해당하는 글을 찾아옴
            Brd_post post = postRepository.findByPostNo(postNo);

            if (post == null) {
                return ResponseEntity.notFound().build();
            }

            // 글의 외래키에 해당하는 정보를 찾아옴
            Brd_division brdNo = post.getBrdNo();
            User userId = post.getId();

            // Brd_postDto에 필요한 정보를 설정
            Brd_postDto brdPostDto = new Brd_postDto();

            brdPostDto.setId(userId.getId());
            brdPostDto.setPostNo(post.getPostNo());
            brdPostDto.setPostTitle(post.getPostTitle());
            brdPostDto.setPostSub(post.getPostSub());
            brdPostDto.setPostDeadline(post.getPostDeadline());
            brdPostDto.setPostStatus(post.getPostStatus());
            brdPostDto.setPostDate(post.getPostDate());
            return ResponseEntity.ok(brdPostDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/lastPostNo")
    public ResponseEntity<Long> getLastPostNo() {
        Brd_post lastPostNo = postRepository.findTopByOrderByPostNoDesc();
        logger.info("Last Post No: " + lastPostNo);
        if (lastPostNo != null) {
            return ResponseEntity.ok().body(lastPostNo.getPostNo());
        }
        return ResponseEntity.ok().body(0L);  // 0을 반환하거나 다른 기본값을 반환
    }

     @GetMapping("/latest/{brdNo}")
    public ResponseEntity<Brd_post> getLatestPost(@PathVariable Brd_division brdNo) {
        Brd_post latestPost = postService.findLatestPostByStatusAndBrdNo(true, brdNo);
        if (latestPost != null) {
            return ResponseEntity.ok(latestPost);
        } else {
            // 원하는 응답 형태에 맞게 수정
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/lastPostNum/{brdNo}")
    public ResponseEntity<Long> getLastPostNumForBrdNo(@PathVariable Long brdNo) {
        try {
            Long lastPostNum = postService.getLastPostNumForBrdNo(brdNo);
            return ResponseEntity.ok(lastPostNum);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/updatePost/{postNo}") // 수정된 부분: postNum 대신 postNo
    public ResponseEntity<Void> updatePost(@PathVariable Long postNo, @RequestBody Brd_post brdPost) {
        try {
            postService.updatePost(postNo, brdPost); // 수정된 글 정보를 서비스로 전달하여 업데이트
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//    @GetMapping("/lastPostNum")
//    public ResponseEntity<Long> getLastPostNum() {
//        try {
//            Long lastPostNum = postService.getLastPostNum();
//            return ResponseEntity.ok(lastPostNum);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @PutMapping("/postView/{postNo}")
    public ResponseEntity<Void> postView(@PathVariable Long postNo){
    	try {
    		postService.postView(postNo);
    		return ResponseEntity.ok().build();
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    	}

    }

    @PostMapping("/newPost")
    public ResponseEntity<Brd_post> createNewPost(@RequestBody Brd_post brdPost) {
        try {
            //게시물 저장 코드
            Brd_post savedPost = postService.savePost(brdPost); //Brd_postService에서 savePost값을 가져온다.
            return ResponseEntity.ok(savedPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/newEventPost")
    public ResponseEntity<Brd_post> createNewPost(@RequestPart("postFile") MultipartFile postFile,
    												@RequestPart("post") String postJson){
//    												@RequestPart("postNum") long postNumJson,
//    												@RequestPart("brdNo") Long brdNoJson {


        //Json 데이터 처리
    	ObjectMapper objectMapper = new ObjectMapper();
    	Brd_postDto brdPostDto = null;
        try {
        	brdPostDto = objectMapper.readValue(postJson, Brd_postDto.class);
        } catch (JsonProcessingException e1) {
        	// TODO Auto-generated catch block
    		e1.printStackTrace();
        }

        Brd_post newBrdPost = new Brd_post();

        //외래키 입력
        Brd_division brdNo = divisionRepository.findByBrdNo(brdPostDto.getBrdNo());
        newBrdPost.setBrdNo(brdNo);
        Optional<User> id = userRepository.findById(brdPostDto.getId());

//        newBrdPost.setPostNum(brdPostDto.getPostNum());
        newBrdPost.setPostTitle(brdPostDto.getPostTitle());
        newBrdPost.setPostSub(brdPostDto.getPostSub());
        newBrdPost.setPostDeadline(brdPostDto.getPostDeadline());

     // 첨부파일 처리
//        newBrdPost.setPostFile1(postFilePath);
      //첨부파일 처리
        ResponseEntity<Long> number = getLastPostNo();
        Long incrementedNum;
        if (number.getStatusCode().is2xxSuccessful() && number.getBody() != null) {
    	    incrementedNum = number.getBody() + 1;
    	} else {
    		incrementedNum = (long) 1;
    	}


        try {
            newBrdPost.setPostFile1(fileService.attachUploadAndGetUri(postFile, "brd_post", incrementedNum));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        //새로운 글 등록
        Brd_post savedBrdPost = postService.savePost(newBrdPost);
        return ResponseEntity.ok(savedBrdPost);
    }

}
