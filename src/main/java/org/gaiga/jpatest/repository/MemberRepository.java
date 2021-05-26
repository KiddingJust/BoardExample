package org.gaiga.jpatest.repository;

import java.util.List;

import org.gaiga.jpatest.vo.MemberVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//extends 시 VO명과 PK 타입 지정 
@Repository
public interface MemberRepository extends JpaRepository<MemberVO, Long> {
	
	public List<MemberVO> findById(String id);
	public List<MemberVO> findByName(String name);
	
	//Like 검색
	public List<MemberVO> findByNameLike(String keyword);
	
	/*
	EX)
	And => findByLastnameAndFirstname (EX. where x.lastname = ?1 and x.firstname = ?2)
	Or => findByLastnameOrFirstname (EX. where x.lastname = ?1 or x.firstname = ?2)
	Is, Equals => findByName,findByNameIs,findByNameEquals (EX. where x.name = 1?)
	Between => findBySalBetween(EX. where x.sal between 1? and ?2)
	 */
}
