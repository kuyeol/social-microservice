package com.packt.cantata.repository;

import com.packt.cantata.domain.Brd_division;
import com.packt.cantata.domain.Brd_post;
import com.packt.cantata.domain.User;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface Brd_postRepository extends JpaRepository<Brd_post, Long> {

	List<Brd_post> findByBrdNo(@Param("brdNo") Brd_division brdNo);
	List<Brd_post> findIdById(@Param("id") User id);

	//토막상식 아래의 findByBrdNoAndPostNum을 findByPostNum으로 바꾸면 SpringData JPA네이밍규칙 위반으로 실행 불가
	List<Brd_post> findByBrdNoAndPostNum(@Param("brdNo") Brd_division brdNo, @Param("postNum") Long postNum);

	@Query("SELECT MAX(p.postNum) FROM Brd_post p WHERE p.brdNo.brdNo = :brdNo")//brdNo별 가장 큰 postNum 호출
	Long findLastPostNumForBrdNo(@Param("brdNo") Long brdNo);

	Brd_post findTopByPostStatusAndBrdNoOrderByPostNoDesc(Boolean postStatus, @Param("brdNo") Brd_division brdNo);

	//	@Query("SELECT MAX(p.postNum) FROM Brd_post p")
//	Long findLastPostNum();

//	List<Brd_post> findByPostNo(@Param("postNo")Long postNo);
//	List<Brd_post> findByPostTitle(@Param("postTitle")String postTitle);
	Brd_post findTopByOrderByPostNoDesc(); //프론트에서 brdNo와 연결시 ${SERVER_URL}/brd_posts/search/findByBrdNo?brdNo=brd_post/숫자
	Brd_post findByPostNo(Long postNo);

}
