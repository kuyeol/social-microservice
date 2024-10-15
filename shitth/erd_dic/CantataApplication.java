package com.packt.cantata;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.packt.cantata.domain.Brd_division;
import com.packt.cantata.repository.Brd_divisionRepository;
import com.packt.cantata.domain.Brd_post;
import com.packt.cantata.repository.Brd_postRepository;
import com.packt.cantata.domain.Perform_time;
import com.packt.cantata.repository.Perform_timeRepository;
import com.packt.cantata.domain.Performance;
import com.packt.cantata.repository.PerformanceRepository;
import com.packt.cantata.domain.Plant;
import com.packt.cantata.repository.PlantRepository;
import com.packt.cantata.domain.User;
import com.packt.cantata.repository.UserRepository;

@SpringBootApplication
@EnableJpaAuditing
public class CantataApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(CantataApplication.class);

	SimpleDateFormat dtFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm");

	@Autowired
	private PerformanceRepository pfRepository;
	@Autowired
	private Perform_timeRepository timeRepository;
	@Autowired
	private PlantRepository plantRepository;

	@Autowired
	private Brd_divisionRepository brdRepository;

	@Autowired
	private Brd_postRepository postRepository;

	@Autowired
	private UserRepository urepository;

	public static void main(String[] args) {
		SpringApplication.run(CantataApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		Plant plant1 = new Plant("오디토리움", "공연장", null, 1500, null,null,null, 100000,null, true, 3);
		Plant plant2 = new Plant("퍼포먼스홀", "공연장", null, 1500,null ,null,null, 100000,null, true, 3);
		Plant plant3 = new Plant("사용불가공연장", "공연장", null, 1500, null ,null,null, 100000,null, false, 1);
		Plant plant4 = new Plant("리허설실1", "리허설실", null, 1500, null ,null,null, 100000,null, true, 1);
		Performance pf1 = new Performance("공연",
				"맘마미아",
				"예술기획성우",
				"1599-1980",
				"https://storage.googleapis.com/cantata_opera/performance/mokeup/%EB%A7%98%EB%A7%88%EB%AF%B8%EC%95%84%20%ED%8F%AC%EC%8A%A4%ED%84%B0.jpg",
				"<p><img src=\"https://storage.googleapis.com/cantata_opera/performance/mokeup/23015397-01.jpg\"></p>",
				"공연공지사항",
				dtFormat.parse("2023-12-15 00:00"), dtFormat.parse("2023-12-26 23:59"),
				100,
				1500000,150000,130000,100000,70000,50000,plant1);
		Performance pf2 = new Performance("음악",
				"젊은음악회",
				"부산시립국악관현악단",
				"607-6000",
				"https://storage.googleapis.com/cantata_opera/performance/mokeup/youngMusic.jpg",
				"상세설명이에요",
				"공연공지사항",
				dtFormat.parse("2023-11-23 00:00"), dtFormat.parse("2023-12-01 23:59"),
				120,
				2000,2000,2000,2000,2000,2000,true,plant2);
		Performance pf3 = new Performance("공연",
				"난타",
				"예술기획성우",
				"1599-1980",
				"https://storage.googleapis.com/cantata_opera/performance/mokeup/1666678895-9-0_wonbon_N_7_255x357_70_2.jpg",
				"<p>난타 공연 내용</p>",
				"난타 공연 공지사항",
				dtFormat.parse("2023-12-28 00:00"), dtFormat.parse("2024-01-08 23:59"),
				100,
				1500000,150000,130000,100000,70000,50000,true,plant2);
		Performance pf4 = new Performance("음악",
				"2024신년음악회",
				"필하모닉오케스트라",
				"1599-1980",
				"https://storage.googleapis.com/cantata_opera/performance/mokeup/2023music.jpg",
				"<p>2024신년음악회 공연 내용</p>",
				"2024신년음악회 공연 공지사항",
				dtFormat.parse("2024-02-10 00:00"), dtFormat.parse("2024-02-13 23:59"),
				100,
				1500000,150000,130000,100000,70000,50000,true,plant2);
		Performance pf5 = new Performance("공연",
				"제14회 장댄스프로젝트 장현희의 춤",
				"장댄스프로젝트",
				"010-0000-0000",
				"https://storage.googleapis.com/cantata_opera/performance/mokeup/%5B%ED%81%AC%EA%B8%B0%EB%B3%80%ED%99%98%5D2023%EC%9E%A5%EB%8C%84%EC%8A%A4%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%20%EB%B0%B0%EB%84%88%EC%9D%B4%EB%AF%B8%EC%A7%80.jpg",
				"<p>제14회 장댄스프로젝트 장현희의 춤 공연 내용</p>",
				"제14회 장댄스프로젝트 장현희의 춤 공연 공지사항",
				dtFormat.parse("2024-02-08 00:00"), dtFormat.parse("2024-02-09 23:59"),
				100,
				0,0,0,0,0,0,true,plant2);
		Performance pf6 = new Performance("공연",
				"리빙:어떤인생",
				"<캐롤>제작진",
				"0000-0000",
				"https://storage.googleapis.com/cantata_opera/performance/mokeup/87775_320.jpg",
				"<p>리빙:어떤인생</p>",
				"리빙:어떤인생",
				dtFormat.parse("2024-02-10 00:00"), dtFormat.parse("2024-02-14 23:59"),
				100,
				1500000,150000,130000,100000,70000,50000,true,plant1);
		Perform_time pt1 = new Perform_time(pf2,dtFormat.parse("2023-11-23 10:00"),dtFormat.parse("2023-11-23 12:00"),true);
		Perform_time pt2 = new Perform_time(pf2,dtFormat.parse("2023-11-24 10:00"),dtFormat.parse("2023-11-24 12:00"),true);
		Perform_time pt3 = new Perform_time(pf3,dtFormat.parse("2023-12-29 10:00"),dtFormat.parse("2023-12-29 11:40"),true);
		Perform_time pt4 = new Perform_time(pf3,dtFormat.parse("2023-12-30 12:00"),dtFormat.parse("2023-12-30 13:40"),true);
		Perform_time pt5 = new Perform_time(pf3,dtFormat.parse("2024-01-08 10:00"),dtFormat.parse("2024-01-08 11:40"),true);
		Perform_time pt6 = new Perform_time(pf3,dtFormat.parse("2024-01-08 13:00"),dtFormat.parse("2024-01-08 14:40"),true);
		Perform_time pt7 = new Perform_time(pf3,dtFormat.parse("2024-01-08 16:00"),dtFormat.parse("2024-01-08 17:40"),true);

		plantRepository.saveAll(Arrays.asList(plant1,plant2,plant3,plant4));
		pfRepository.saveAll(Arrays.asList(pf1,pf2,pf3,pf4,pf5,pf6));
		timeRepository.saveAll(Arrays.asList(pt1,pt2,pt3,pt4,pt5,pt6,pt7));



		for (Performance pf : pfRepository.findAll()) {
			logger.info(pf.getPfCode() + " " +pf.getPfCate() + " " + pf.getPfTitle());
			System.out.println(pf.getPfCode());
		}

		Brd_division brd1 = new Brd_division("센터소개");

		Brd_division brd2 = new Brd_division("시설소개");

		Brd_division brd3 = new Brd_division("센터소식");

		Brd_division brd4 = new Brd_division("이벤트");

		Brd_division brd5 = new Brd_division("자주하는 질문");

		Brd_division brd6 = new Brd_division("1:1 문의");

		brdRepository.saveAll(Arrays.asList(brd1, brd2, brd3, brd4, brd5, brd6));

		for (Brd_division brd : brdRepository.findAll()) {

		}

		for (Brd_post post : postRepository.findAll()) {
			logger.info(post.getPostTitle());
		}
		urepository.save(new User());

	}
}
