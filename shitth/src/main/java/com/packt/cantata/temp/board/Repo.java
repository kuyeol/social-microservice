package com.packt.cantata.temp.board;

import com.packt.cantata.temp.HallEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  Repo  extends JpaRepository<HallEntity, String> {
}
