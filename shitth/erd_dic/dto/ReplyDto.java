package com.packt.cantata.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.packt.cantata.domain.Brd_post;
import com.packt.cantata.domain.Reply;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReplyDto {
//	private Long repNo; // 답변번호
//	@JsonIgnore
//    private Brd_post postNo; // 글번호
////    private User Id; // 회원ID
//    private String repSub; // 글내용
//    private Date repDate; // 작성일
//
//    // 생성자, 게터, 세터 등 필요한 메서드 추가
//
//    public ReplyDto(Reply reply) {
//        this.repNo = reply.getRepNo();
//        this.repSub = reply.getRepSub();
//        this.repDate = reply.getRepDate();
//
//        // Lazy Loading 회피를 위해 postNo 및 userId 추가
//        if (reply.getPostNo() != null) {
//            this.postNo = reply.getPostNo();
//        }
////
////        if (reply.getId() != null) {
////            this.Id = reply.getId();
////        }
//    }
}
