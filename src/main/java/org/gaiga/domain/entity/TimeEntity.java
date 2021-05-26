package org.gaiga.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

//데이터 조작 시 자동으로 날짜를 수정해주는 JPA의 Auditing 기능을 사용하는 entity
//그리고 JPA Auditing 기능을 사용하기 위해 main 클래스에 @EnableJpaAuditing 추가
@Getter
@MappedSuperclass //테이블로 매핑하지 않고, 자식 Entity에게 매핑정보를 상속하기 위한 어노테이션
@EntityListeners(AuditingEntityListener.class) //JPA에게 해당 Entity는 Auditing 기능을 사용한다는 것을 명시  
public class TimeEntity {
	
	
	@CreatedDate	//Entity가 처음 생성될 때의 생성일을 주입
	@Column(updatable = false)	//생성일은 update가 필요 없음. 
	private LocalDateTime createdDate;
	
	@LastModifiedDate
	private LocalDateTime modifiedDate;
	
}
