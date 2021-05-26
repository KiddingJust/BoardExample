package org.gaiga.domain.repository;

import java.util.List;

import org.gaiga.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

//Repository는 데이터 조작을 담당. 
//interface로 생성하며  JpaRepository를 상속 
//crud등은 기본적으로 구현되어있으므로 만들어주지 않아도 됨.
public interface BoardRepository extends JpaRepository<Board, Long> {
	
	//JPA에서 By 뒷부분은 SLQ의 Where 조건 절에 해당됨. 
	//Containing을 붙여주면 Like 검색이 됨 
	List<Board> findByTitleContaining(String keyword);
}
