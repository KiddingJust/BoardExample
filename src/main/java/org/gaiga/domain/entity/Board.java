package org.gaiga.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//Entity는 DB테이블과 매핑되는 것 
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)	//파라미터가 없는 기본 생성자를 추가하는 어노테이션 
@Table(name="board")	//명시하지 않아도 됨. 다른 패키지에 이름 같은 엔티티 있을 때나 명시 
public class Board extends TimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//Mysql류. AUTO로 하면 DB종류에 따라 알아서. SEQUANCE는 오라클 
	private Long id;
	
	@Column(length = 10, nullable = false)
	private String writer;
	
	@Column(length = 100, nullable = false)
	private String title;
	
	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;
	
	//Setter 대신 사용하는 것. 
	//무분별한 Setter는 안정성을 보장받기 어려우므로 builder 사용. 
	@Builder
	public Board(Long id, String title, String content, String writer) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.writer = writer;
	}
}
