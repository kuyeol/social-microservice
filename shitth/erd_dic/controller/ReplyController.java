package com.packt.cantata.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.packt.cantata.CantataApplication;
import com.packt.cantata.domain.Brd_post;
import com.packt.cantata.repository.Brd_postRepository;
import com.packt.cantata.domain.Reply;
import com.packt.cantata.repository.ReplyRepository;

@RestController
@RequestMapping("/replies")
public class ReplyController {
	private static final Logger logger = LoggerFactory.getLogger(CantataApplication.class);


	@Autowired
	private ReplyRepository replyRepository;

	@Autowired
	private Brd_postRepository postRepository;

	@GetMapping("/allReply")
    public ResponseEntity<List<Reply>> getReply() {
        try {
            List<Reply> replies = replyRepository.findAll();
            return ResponseEntity.ok(replies);
        } catch (Exception e) {
            // 예외가 발생하면 클라이언트에게 500 Internal Server Error를 반환합니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


	@GetMapping("/searchPostNo/{postNo}")
	public List<Reply>getReplyNo(Brd_post postNo){
		return replyRepository.findByPostNo(postNo);
	}

	@RequestMapping("/addReply")
	public ResponseEntity<String> addReply(@RequestBody Reply reply){
		try {
			//Reply에 Brd_post를 가져오기
			Brd_post post = reply.getPostNo();

			if(post != null && post.getPostNo() == null) {
				postRepository.save(post);
			}
			replyRepository.save(reply);

			return ResponseEntity.ok("저장완료.");
		}catch(Exception e) {
			logger.error("댓글저장 오류 발생", e);
			return ResponseEntity.status(500).body("저장되지 않았습니다.");
		}
	}




}
