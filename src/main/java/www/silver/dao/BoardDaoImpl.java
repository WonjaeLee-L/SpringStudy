package www.silver.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import www.silver.vo.BoardVO;

@Repository
public class BoardDaoImpl implements IF_BoardDao {
	@Inject
	SqlSession sqlsession;
	
	// mapper의 namespace
	final String mapperQuery = "www.silver.dao.IF_BoardDao";

	@Override
	public void insertBoard(BoardVO boardvo) throws Exception {
		// sqlsession을 통해서 mapper와 매핑해야해서 정보를 넘겨준다. >> mapper 만든다
		sqlsession.insert(mapperQuery + ".inin", boardvo);

	}

}
