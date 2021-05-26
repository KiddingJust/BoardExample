package org.gaiga.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.gaiga.domain.entity.Board;
import org.gaiga.domain.repository.BoardRepository;
import org.gaiga.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

//실제로 비즈니스 로직을 시행해주는 역할 
@Service
public class BoardService {

	private BoardRepository boardRepository;
	private static final int BLOCK_PAGE_NUM_COUNT = 5; //블럭에 존재하는 페이지 수
	private static final int PAGE_POST_COUNT = 4; //한 페이지에 존재하는 ㄱ시글 수 
	
	//이런 거 왜 하는지 알아봐야해. 생성자? 마찬가지로 AllArgsConstructor 해주어도 됨. 
	//--> 생성자로 Bean객체를 받도록 하며, AllArgsConstructor를 통해 해결. 
	//--> 모든 필드를 인자값으로 하는 생성자를 Lombok에서 .. 
	//--> 생성자를 직접 안쓰고 Lombok어노테이션 사용하는 이유는, 해당 클래스의 의존성
	//    관계가 변경될때마다 생성자 코드를 계속해서 수정하는 번거로움을 해결하기 위해
	//    즉, 위 서비스에서 새로운 Repository 추가해도 생성자 코드는 그대로 둬도 됨. 
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	
	@Transactional
	public Long savePost(BoardDto boardDto) {
		//save() 는 JpaRepository에 정의된 method로 db에 insert,update 진
		return boardRepository.save(boardDto.toEntity()).getId();
	}
	
	//repository에서 모든 데이터를 가져와, 데이터만큼 반복하면서,
	//BoardDto 타입의 List에 파싱하여 집어넣고 리턴해준다. 
//	@Transactional
//	public List<BoardDto> getBoardList(){
//		List<Board> boards = boardRepository.findAll();
//		List<BoardDto> boardDtoList = new ArrayList<>();
//		
//		for(Board board : boards) {
//			BoardDto boardDto = BoardDto.builder()
//					.id(board.getId())
//					.title(board.getTitle())
//					.content(board.getContent())
//					.writer(board.getWriter())
//					.createdDate(board.getCreatedDate())
//					.build();
//			boardDtoList.add(boardDto);
//		}
//	
//		return boardDtoList;
//	}	

	//페이징 처리 이후의 getBoardList
	//한 페이지에 4개의 게시글, 페이지 목록은 총 5페이지까지로 설정
	//findAll에 Pageable 인터페이스 구현체(PageRequest.of)를 전달하면 페이징 구현 가능
	//PageRequest.of(limit값, 가져올 양, 정렬방식) -> 즉 (int page, int size, Sort sort)
	//limit는 실제 페이지 번호와 SQL의 limit이 다르므로 '현재페이지 -1'을 해준다. 
	@Transactional
	public List<BoardDto> getBoardList(Integer pageNum){
		
		Page<Board> page = boardRepository
				.findAll(PageRequest
						.of(pageNum-1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "createdDate")));
	
		//Page객체의 getContent를 호출하면 엔티티를 리스트로 꺼낼 수 있다. 
		List<Board> boards = page.getContent();
		List<BoardDto> boardDtoList = new ArrayList<>();
		
		for(Board board : boards) {
			boardDtoList.add(this.convertEntityToDto(board));
		}
		
		return boardDtoList;
	}	
	
	//총 게시글 갯수 불러오는 부분
	//getBoardCount로 repository에서 총 데이터(게시글)의 수를 받아오고,
	//게시글을 한 페이지당 보이지게 할 게시글 수로 나누어준다(소수점이 있으면 올림)
	//현재 페이지를 기준으로 마지막 페이지 번호를 계산하고 페이지 리스트를 리턴한다. 
	public Integer[] getPageList(Integer curPageNum) {
		Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];
		
		//총 게시글 수 
		Double postsTotalCount = Double.valueOf(this.getBoardCount());
		
		//총 게시글 수를 기준으로 계산한 마지막 페이지 번호
		Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));
		
//		//현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
//		Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
//				? curPageNum + BLOCK_PAGE_NUM_COUNT // 4페이지면 마지막은 6, 5->7 이 되어야 함. 
//			    : totalLastPageNum; 	// 1페이지여도 2가 되어야 함. 
		
		//페이지 시작 번호 조정
		Integer startPageNum = (curPageNum<=3) ? 1 : curPageNum-2;

		//현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
		//default: 페이지 시작번호가 1이면 5, 2면 6 ...
		//그런데 총 페이지 수가 5인데 내가 보고 있는 페이지가 4면(시작 번호는 2)이고 끝번호는 6이 되어야 함. 
		Integer blockLastPageNum = (totalLastPageNum > startPageNum + BLOCK_PAGE_NUM_COUNT -1)
				? startPageNum + BLOCK_PAGE_NUM_COUNT-1 
			    : totalLastPageNum; 
		
		System.out.println("curPageNum: " + curPageNum);
		System.out.println("blockLastPageNum: " + blockLastPageNum);
		//페이지 번호 할당
		for(int val=startPageNum, i=0; val<=blockLastPageNum; val++, i++) {
			pageList[i] = val;
		}
		return pageList;
	}
	
	@Transactional
	public Long getBoardCount() {
		return boardRepository.count();
	}
	
	
	
	
	@Transactional
	public BoardDto getPost(Long id) {
		Optional<Board> boardWrapper = boardRepository.findById(id);
		Board board = boardWrapper.get();
		
		BoardDto boardDto = BoardDto.builder()
				.id(board.getId())
				.title(board.getTitle())
				.content(board.getContent())
				.writer(board.getWriter())
				.createdDate(board.getCreatedDate())
				.build();
		
		return boardDto;
	}
	
	@Transactional
	public void deletePost(Long id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public List<BoardDto> searchPosts(String keyword){
		List<Board> boards = boardRepository.findByTitleContaining(keyword);
		List<BoardDto> boardDtoList = new ArrayList<>();
		
		//Optional로 바꿔서 해보기 
		if(boards.isEmpty()) return boardDtoList;
		
		for(Board board : boards) {
			boardDtoList.add(this.convertEntityToDto(board));
		}
		
		return boardDtoList;
	}
	
	//반복작업 피하기 위해 분리 
	private BoardDto convertEntityToDto(Board board) {
		return BoardDto.builder()
				.id(board.getId())
				.title(board.getTitle())
				.content(board.getContent())
				.writer(board.getWriter())
				.createdDate(board.getCreatedDate())
				.build();
	}
}
