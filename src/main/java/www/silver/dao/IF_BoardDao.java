package www.silver.dao;

import www.silver.vo.BoardVO;

public interface IF_BoardDao {
	// DB작업이 목적
	// Mybatis mapper랑 매핑해서 DB작업을 수행
	public void insertBoard(BoardVO boardvo) throws Exception;
}
