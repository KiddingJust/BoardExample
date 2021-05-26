package org.gaiga.dto;

import java.time.LocalDateTime;

import org.gaiga.domain.entity.Board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//Controller와 Service사이에서 데이터 주고받는 역할
//비즈니스 로직은 Entity를 기준으로 돌아가기 때문에 Entity를 Request, Response 용도로 사용하는 건 좋지 않음.
//따라서 데이터 전달 용도로써, DTO를 통해 service의 savePost에서 Repository에 데이터 집어넣는다. 
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {

	private Long id;
	private String writer;
	private String title;
	private String content;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	
	//dto에서 필요한 부분을 빌더패턴을 통해 entity로 만드는 역할 
	public Board toEntity() {
		Board build = Board.builder()
				.id(id)
				.writer(writer)
				.title(title)
				.content(content)
				.build();
		return build;
	}
	
	@Builder
	public BoardDto(Long id, String title, String content, String writer, LocalDateTime createdDate, LocalDateTime modifiedDate) {
		this.id = id;
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}
}
