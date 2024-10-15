package com.packt.cantata.service;

import org.springframework.stereotype.Service;

@Service
public class ReplyService {

//	@Autowired
//	private ReplyRepository replyRepository;
//
//	@Transactional
//    public Reply addReply(ReplyDto replyRequest) {
//    	try {
//        // ReplyRequest는 댓글을 추가할 때 필요한 정보를 담은 DTO입니다.
//        // 예시로 답변 내용(repSub)만 받아온다고 가정합니다.
//
//        // 필요한 로직을 수행하고 댓글을 저장합니다.
//        Reply reply = new Reply();
//        reply.setPostNo(replyRequest.getPostNo());
//        reply.setRepSub(replyRequest.getRepSub());
//        reply.setRepDate(new Date()); // 현재 시간으로 설정하거나, 필요에 따라 설정
//
//        return replyRepository.save(reply);
//    	}catch (Exception e) {
//            // 발생한 예외를 로깅합니다.
//            e.printStackTrace();
//            throw new RuntimeException("댓글 추가 중 오류 발생");
//        }
//    }
//
//    public List<Reply> getAllReplies() {
//        List<Reply> replies = replyRepository.findAll();
//
//        // 프록시를 언로딩하여 초기화
//        for (Reply reply : replies) {
//            if (reply.getPostNo() != null) {
//                reply.getPostNo().getPostTitle(); // 프록시 초기화를 위해 필요한 필드 호출
//            }
//        }
//
//        return replies;
//    }
}
