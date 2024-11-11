package www.silver.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import www.silver.vo.MemberVO;

@Repository
public class MemberDaoImpl implements IF_MemberDao {
	// 서비스단에서 디비작업위해 memberdaoimpl 객체 필요하고 이 객체는 IF_memberdao가 정의함.
	// 이 객체 변수가 필요, 스프링에서는 인터페이스타입으로 객체 요청하게 되어있음. 그래서 서비스단에서 IF_MemberDAO
	// memberdao로 객체 만든다.
	// 인터페이스로 객체를 만든 서비스단을 수정하지 않아도, 이 디비작업이 수정되어야한다면, 이 파일을 고치지 않고 새로 implement받은
	// 클래스파일을
	// 새로 만들고 IF_memberdao로 구현받으면, 파일명만 바꿔치기하면, 서비스단의 객체 만든걸 수정하지 않아도 바로 사용이 가능하다.

	// 서비스단에서 dao로 넘어온거고 dao에서 mybatis로

	// sqlSession이 필요하다. Mybatis에서 제공하는 객체
	// 주소가 올바르게 주입되려면 root-context.xml에서 설정이 잘 되어 있어야 한다.
	@Inject // SqlSession이라는 객체를 찾아서(spring내 컨테이너에서) 나에게 주세요
	SqlSession sqlSession;

	private static String mapperQuery = "www.silver.dao.IF_MemberDao";

	@Override
	public void insertOne(MemberVO membervo) throws Exception {

//		System.out.println(membervo.toString());

		// sqlSessionTemplate의 객체의 메서드를 call한다.
//		sqlSession.insert("maper와 매핑정보, mapper에 있는 여러 쿼리 중에서 쿼리 선택하기", "mapper의 parametertype에 넘길 파라미터값");
		sqlSession.insert(mapperQuery + ".insertOne", membervo);

		// www.silver.dao.IF_MemberDao.insertOne << 매핑정보
		// Dao까지 namespace를 찾고, 그 중에 insertOne의 id를 가진 쿼리를 매칭한다.

	}

}
