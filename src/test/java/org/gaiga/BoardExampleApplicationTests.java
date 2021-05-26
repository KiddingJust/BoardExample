package org.gaiga;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.gaiga.jpatest.service.MemberService;
import org.gaiga.jpatest.vo.MemberVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardExampleApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	MemberService memberService;
	


}
