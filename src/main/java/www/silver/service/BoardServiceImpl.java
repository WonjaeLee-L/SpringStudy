package www.silver.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import www.silver.dao.IF_BoardDao;
import www.silver.vo.BoardVO;

@Service
public class BoardServiceImpl implements IF_BoardService {

	@Inject
	IF_BoardDao boarddao;
	// 객체 필요(dao에게 넘기기 위해서)
	// 객체는 주입받아 사용

	@Override
	public void addBoard(BoardVO boardvo) throws Exception {
		// controller가 service에 넘기고,
		// service는 client의 요청과 관계된 일을 처리하고, dao에 넘기는데 dao는 DB 쿼리 작업 코드를 작성.
		// 아래처럼, 조건에 따라 값을 바꾸어 dao에 넘겨서 DB에 저장시키거나
		// 검색어를 검증(욕설 등)해서 넘기는 등의 역할을 한다.
		if (boardvo.getViewmember() != null) {
			boardvo.setViewmember("공개");
		} else {
			boardvo.setViewmember("비공개");
		}
		// dao에 넘겨서 mapper를 통해 DB insert
		boarddao.insertBoard(boardvo);
	}

	@Override
	public List<BoardVO> boardList() throws Exception {
		// 처리하다가 DB작업 필요
//		return boarddao.selectAll();
		// return은 controller에, controller는 model객체로 view에

		// 날짜를 뒤에 자르고 년월일까지만 출력 >> 서비스단에서 처리하거나 view에서 처리
		// 서비스에서 처리하면 서버에 부담. view에서 처리하면 view에서 부담
		// 서비스단 보다는 view에서 처리한다. jstl문법을 이용하여 view에서 처리.
		// 서비스에서 날짜 자르기
		List<BoardVO> list = boarddao.selectAll();
		for (BoardVO b : list) {
			String date = b.getIndate();
			b.setIndate(date.substring(0, 10));
		}
		return list;
	}

}
