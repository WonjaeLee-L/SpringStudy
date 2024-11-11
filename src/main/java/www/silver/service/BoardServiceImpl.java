package www.silver.service;

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

}
