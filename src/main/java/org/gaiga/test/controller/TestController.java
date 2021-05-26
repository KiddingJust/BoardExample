package org.gaiga.test.controller;

import java.util.ArrayList;
import java.util.List;

import org.gaiga.test.service.TestService;
import org.gaiga.test.vo.TestInitVO;
import org.gaiga.test.vo.TestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	TestService testService;
	
	@RequestMapping(value="/home")
	public String home() {
		return "index.html";
	}
	
	//View가 아닌 data를 반환해야 하는 경우라면 @ResponseBody를 사용
	//간단하게는 String, Map, JSON을 전달할 수 있음. 
	@ResponseBody
	@RequestMapping("valueTest")
	public String valueTest() {
		String value="테스트 String";
		return value;
	}
	
	@RequestMapping("/testJSP")
	public ModelAndView test() throws Exception{
		ModelAndView mav = new ModelAndView("testJSP");
		mav.addObject("name", "gaiga");
		
		List<String> testList = new ArrayList<String>();
		testList.add("a");
		testList.add("b"); 
		testList.add("c");
		mav.addObject("list", testList);
		
		return mav;
	}
	
	@RequestMapping(value = "testdata")
	public ModelAndView testdata() throws Exception{
		ModelAndView mav = new ModelAndView("test");
		List<TestVO> testList = testService.selectTest();
		mav.addObject("list", testList);
		
		return mav;
	}

	
	@RequestMapping("/thymeleafTest")
	public String thymeleafTest(Model model) {
		TestInitVO testModel = new TestInitVO("gaiga", "김가익");
		model.addAttribute("testModel", testModel);
//		List<TestVO> testModelDB = testService.selectTest();
//		model.addAttribute("testModelDB", testModelDB);	
		
		return "thymeleafTest";
	}
	


	
}
