package www.silver.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import www.silver.vo.BoardVO;
import www.silver.vo.PageVO;

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

	@Override
	public List<BoardVO> selectAll(PageVO pagevo) throws Exception {
		return sqlsession.selectList(mapperQuery + ".selectall", pagevo);
	}

	@Override
	public void deleteBoard(String delno) throws Exception {
		sqlsession.delete(mapperQuery + ".delone", delno);

	}

	@Override
	public BoardVO selectOne(String title) throws Exception {
		// (mybatis 두 개 변수 넘길 때)
		// parameter는 하나만 보낼 수 있는데, 검색 시 두 가지 이상의 조건으로 검색을 원하면 hashmap을 이용하면 된다.
//		HashMap aa = new HashMap<String, String>();
//		aa.put("tt", title);
//		aa.put("dd", date); - 위 매개변수에 String date 추가
		// >> 아래 parameter를 aa로 넘기고 mapper에서 tt와 dd로 사용 가능
		return sqlsession.selectOne(mapperQuery + ".selectTitle", title);
	}

	@Override
	public void updateBoard(BoardVO boardvo) throws Exception {
		sqlsession.update(mapperQuery+".update", boardvo);
		
	}

	@Override
	public int cntBoard() throws Exception {
		// TODO Auto-generated method stub
		return sqlsession.selectOne(mapperQuery+".allcnt");
	}

	@Override
	public void insertAttach(String fname) throws Exception {
		// TODO Auto-generated method stub
		sqlsession.insert(mapperQuery+".insertattach",fname);
	}

	@Override
	public List<String> selectAllAttach(String no) throws Exception {

		return sqlsession.selectList(mapperQuery+".selectattach",no);
	}

}
