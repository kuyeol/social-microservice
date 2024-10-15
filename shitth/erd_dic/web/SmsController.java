package com.packt.cantata.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.packt.cantata.domain.User;
import com.packt.cantata.repository.UserRepository;
import com.packt.cantata.service.LoginService;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@RestController
public class SmsController {
	final DefaultMessageService messageService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private UserRepository usrrepo;
	private Map<String, String> phoneVerificationMap = new HashMap<>();
    public SmsController() {
        // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
        this.messageService = NurigoApp.INSTANCE.initialize("api키", "토큰", "https://api.coolsms.co.kr");
    }

	@RequestMapping(value="/tel")
	public ResponseEntity<SingleMessageSentResponse> telCheck(@RequestParam("tel") String to) {
		 Random random = new Random();
	     int randomNumber = random.nextInt(900000) + 100000;
	     String randomString = String.valueOf(randomNumber);
	     Message message = new Message();
	        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
	     message.setFrom("010-4162-0646");
	     message.setTo(to);
	     message.setText("[회원인증] 부산 오페라하우스 : "+randomString);
	     phoneVerificationMap.put(to,randomString);
	     SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        return ResponseEntity.ok(response);
	}
	@RequestMapping(value="/verify")
	public ResponseEntity verify(@RequestParam("tel") String to, @RequestParam("code") String code) {
		String storedCode = phoneVerificationMap.getOrDefault(to, "");

        if (storedCode.equals(code)) {
            return ResponseEntity.ok("Verification successful.");
        } else {
            return ResponseEntity.badRequest().body("Verification failed.");
        }

	}
	@RequestMapping(value="/Duple", method=RequestMethod.GET)
	public ResponseEntity<Boolean> checkDuple(@RequestParam("type") String type, @RequestParam("value") String value) {
		System.out.println(loginService.checkDuplicate(type, value));
		 return ResponseEntity.ok(loginService.checkDuplicate(type, value));
    }

	@RequestMapping(value="/findid", method=RequestMethod.GET)
	public ResponseEntity<Optional<User>> showId(@RequestParam("tel") String tel) {
		System.out.println(tel);
		 return ResponseEntity.ok(usrrepo.findByTel(tel));
    }
}
