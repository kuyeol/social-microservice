package com.packt.cantata.dto;

import com.packt.cantata.domain.Brd_post;
import com.packt.cantata.domain.Performance;
import com.packt.cantata.domain.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PathAndEntities {

    String midPath;
    User user;
    Performance performance;
    Brd_post brdPost;
  
}