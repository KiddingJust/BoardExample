package org.gaiga.jpatest.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
CREATE TABLE IF NOT EXISTS TEST.MEMBER ( 
		MBR_NO BIGINT NOT NULL AUTO_INCREMENT,
		ID VARCHAR(200), 
		NAME VARCHAR(200), 
		PRIMARY KEY(MBR_NO)  - AUTO_INCREMENT 컬럼 단일 PK
);
 */

//Lombok
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//JPA. Entity가 붙은 클래스는 JPA가 관리. 테이블과 매핑하는 부분
@Entity(name="member")
public class MemberVO {

	@Id	//기본키(PK) 지정 
	//AUTO_INCREMENT. 기본 키 생성을 데이터베이스에 위임  
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mbrNo;
	
	private String id;
	
	private String name;
	
	@Builder
	public MemberVO(String id, String name) {
		this.id = id;
		this.name = name;
	}
}
