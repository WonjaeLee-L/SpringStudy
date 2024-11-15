package www.silver.util;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
// controller > 주입 목적, 다운로드 자체 정의가 필요한데, 다른 클래스에 넣기보다는 여기가 적당.
@Controller
public class FileDataUtil {
	
	private ArrayList<String> extNameArray = new ArrayList<String>() // 허용하는 확장자 정의를 한 것.
	{
		{
			add("gif");
			add("jpg");
			add("png");
		}
	};     //<-- 현재 코드는 활용하지는 않는다.. 얘는 선언이지 기능이 동작하지는 않는다. 절대 미리 예측 금지..
	// 프로젝트에서 사용하려면 for문 돌려서 거르기
	
	// Resource(name으로 주입받겠다) >> container로부터 주입받는 어노테이션 uploadPath라는 이름으로 주입받겠다 이이름의 객체를 주세요
	// container에 등록하려면 설정해야한다. uploadPath이름으로 bean을 만드는 설정. root or servlet context에 이 객체를 만들어놔야 한다.
	//첨부파일 업로드 경로 변수값으로 가져옴 servlet-context.xml
	@Resource(name="uploadPath")	
	private String uploadPath;
	// (1) uploadPath 변수의 의미는 첨부파일의 위치. 이 곳에 저장하겠다는 의미 :
	// IOC의 개념(개발이 역전됐다) 개발의 주체가 Spring container. 내가 만들거야 그 값은 나에게 주세요. 주는대로 받는 것.
	// 자바소스를 찾아보지 않고, 환경설정만 본다. servlet, root context만 보면 이 프로젝트의 내용을 다 알 수 있다.
	// (2) 처럼 코드를 하면, 프로젝트 파악을 위해 모든 곳을 다 찾아봐야 한다. (1)의 방식으로 사용한다.
//	private String uploadPath="/tmp";
	// (2)별도의 폴더 지정위해 경로 내 맘대로 설정 : 개발자가 직접 집어 넣겠다.
	
	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	/**
	 * 게시물 상세보기에서 첨부파일 다운로드 메서드 구현(공통)
	 */
	@RequestMapping(value="/download", method=RequestMethod.GET)
	@ResponseBody   // 어떤 데이터를 포함하여 전송.. 어노테이션.. view지정하지 않고 바로 클라이언트 요청으로 응답.
	// view리졸브 영향받는애가 아니라, 이 리턴값을 가지고 파일 자체를 클라이언트에 전송할거라는 것.
	// responseBody는 리턴타입이 view의 이름이 아니라 파일 자체라는 의미

	public FileSystemResource fileDownload(@RequestParam("filename") String fileName, HttpServletResponse response) {
		File file = new File(uploadPath + "/" + fileName);
		response.setContentType("application/download; utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		return new FileSystemResource(file);
	}
	
	/**
	 * 파일 업로드 메서드(공통)
	 * @throws IOException 
	 */
	public String[] fileUpload(MultipartFile[] file) throws IOException {
		String[] files = new String[file.length];
		for(int i=0; i < file.length; i++) {
			if(file[i].getOriginalFilename()!="") {  // 실제 file객체가 존재한다면
				String originalName = file[i].getOriginalFilename();//확장자가져오기 위해서 전체파일명을 가져옴.
				UUID uid = UUID.randomUUID();//랜덤문자 구하기 맘에안든다. 
				String saveName = uid.toString() + "." + originalName.split("\\.")[1];//한글 파일명 처리 때문에...
				// 파일명.jpg 라고하면 파일명은 첫번째배열 jpg는 두번째배열에 넣겠다. 그중 [1]을 가져온다.
				
//			String[] files = new String[] {saveName}; //형변환  files[0] 파일명이 들어 간다..
				// 파일을 저장 위해 2진수로 (binary)로 바꾼다.
				byte[] fileData = file[i].getBytes();
				
				// uploadPath 위치에 savename이름으로 파일을 만든다.(로컬에)
				File target = new File(uploadPath, saveName);
				
				// 2진수로 바꾼 파일데이터를 저장. 위에서 만든 target에 binary를 카피해라
				FileCopyUtils.copy(fileData, target);
				
				// 변경된 파일명을 저장해라
				files[i]=saveName;
				// 얘는 변경된 파일 명만 가지고있다.
			}			
		}		
		return files;
	}

	public ArrayList<String> getExtNameArray() {
		return extNameArray;
	}

	public void setExtNameArray(ArrayList<String> extNameArray) {
		this.extNameArray = extNameArray;
	}
}
