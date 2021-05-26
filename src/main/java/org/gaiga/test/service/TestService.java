package org.gaiga.test.service;

import java.util.List;

import org.gaiga.test.mapper.TestMapper;
import org.gaiga.test.vo.TestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public TestMapper mapper;
	
	public List<TestVO> selectTest(){
		return mapper.selectTest();
				
	}
}
