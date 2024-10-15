package com.packt.cantata.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCredentials { // 계정자격증명
	private String id;
	private String password;
	private String Auth;

	public AccountCredentials(String id, String password, String Auth){
		this.id=id;
		this.password=password;
		this.Auth=Auth;
	}

	}
