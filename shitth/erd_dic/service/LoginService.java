package com.packt.cantata.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.packt.cantata.repository.UserRepository;

import lombok.RequiredArgsConstructor;
@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

	private final UserRepository usrrepo;

	 public boolean checkDuplicate(String type, String value) {
	        switch (type) {
	            case "id":
	                return usrrepo.findById(value) != null;
	            case "email":
	                return usrrepo.findByEmail(value) != null;
	            case "tel":
	                return usrrepo.findByTel(value) != null;
	            default:
	                throw new IllegalArgumentException("잘못된 유형");
	        }
	    }
}
