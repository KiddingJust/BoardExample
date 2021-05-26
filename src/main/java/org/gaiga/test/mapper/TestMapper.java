package org.gaiga.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gaiga.test.vo.TestVO;
import org.springframework.stereotype.Repository;


//<mapper namespace="org.gaiga.test.mapper.TestMapper"> namespace에 설정한 것과 경로를 꼭 맞추어야 함.
//<select id="selectTest" resultType="TestVO">  select tag 안에 선언한 id값과 각 method 이름을 같게 설정해주어야 함. 
@Repository
@Mapper
public interface TestMapper {
	List<TestVO> selectTest();
}
