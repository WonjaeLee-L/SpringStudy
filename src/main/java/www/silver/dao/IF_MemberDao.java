package www.silver.dao;

import www.silver.vo.MemberVO;

public interface IF_MemberDao {
	// DB 작업을 메서드로 정의합니다.
	public void insertOne(MemberVO membervo) throws Exception;
	// 컨트롤러는 서비스의 join을 호출하고 서비스의 join은 dao의 얘를 호출해서 데이터베이스 작업
}
