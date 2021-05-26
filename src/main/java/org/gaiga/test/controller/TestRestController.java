package org.gaiga.test.controller;

import org.gaiga.test.vo.TestInitVO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


//Spring 4.0 이상은 Controller와 ResponseBody 어노테이션 추가 대신 @RestControlle를 제공
//@ResponseBody를 추가할 필요 없고 기본적으로 활성화되어있음. 
@RestController
public class TestRestController {

	@RequestMapping(value="/valueTestRest", method = RequestMethod.GET)
	public String getTestValueRest() {
		String TestValue = "Rest Controller Value Test";
		return TestValue;
	}
	
	
	
}
