package com.packt.cantata.repository;

import com.packt.cantata.domain.Brd_post;
import com.packt.cantata.domain.Reply;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ReplyRepository extends JpaRepository<Reply, Long> {

	List<Reply> findByPostNo(@Param("postNo") Brd_post postNo);

}
