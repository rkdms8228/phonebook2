package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhoneDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.PhoneVo;


@WebServlet("/pbc")
public class PhoneController extends HttpServlet {
	
	//필드
	private static final long serialVersionUID = 1L;
	
	//생성자(디폴트[기본] 생성자 사용)
	/*
	public PhoneController() {
	        super();
	}
	*/
	
	//메소드-gs
	
	
	//메소드-일반
	//get방식으로 요청 시 호출 메소드
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//포스트 방식일 때 한글 깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
		//코드 작성
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("list".equals(action)) { //리스트일 때
			
			PhoneDao phoneDao = new PhoneDao();
			List<PhoneVo> phoneList = phoneDao.personSelect();
			System.out.println(phoneList);
			
			//request에 데이터 추가
			request.setAttribute("pList", phoneList);
			
			//데이터 + html --> jsp에게 시킨다.(forward)
			WebUtil.forward(request, response, "/WEB-INF/list.jsp"); //외부에서(인터넷 주소로) 요청할 수 없는 폴더(WEB-INF)
			
			/*
			RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response);
			*/
			
		}else if("writeForm".equals(action)) { //등록폼일 때
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/writeForm.jsp");
			
			/*
			RequestDispatcher rd = request.getRequestDispatcher("/writeForm.jsp");
			rd.forward(request, response);
			*/
			
		}else if("write".equals(action)) { //등록일 때
			
			//파라미터에서 값 꺼내기(name, hp, company)
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");
			
			
			//vo만들어서 값 초기화
			PhoneVo phoneVo = new PhoneVo(name, hp, company);
			System.out.println(phoneVo);
			
			//phoneDao.personInsert()를 통해 저장하기
			PhoneDao phoneDao = new PhoneDao();
			int count = phoneDao.personInsert(phoneVo);
			System.out.println(count);
			
			//리다이렉트 list
			WebUtil.redirect(request, response, "/phonebook2/pbc?action=list");
			
			//response.sendRedirect("/phonebook2/pbc?action=list");
			
		}else if("delete".equals(action)) { //등록일 때
			
			//파라미터에서 id 값 꺼내기
			int personId = Integer.parseInt(request.getParameter("personId"));

			//phoneDao.personInsert()를 통해 저장하기
			PhoneDao phoneDao = new PhoneDao();
			int count = phoneDao.personDelete(personId);
			System.out.println(count);
			
			//리다이렉트 list
			WebUtil.redirect(request, response, "/phonebook2/pbc?action=list");
			
			//response.sendRedirect("/phonebook2/pbc?action=list");
			
		}else if("updateForm".equals(action)) { //수정 폼일 때
			
			PhoneDao phoneDao = new PhoneDao();
			int personId = Integer.parseInt(request.getParameter("personId"));
			PhoneVo phoneVo = phoneDao.getPerson(personId);
			 
			request.setAttribute("phoneVo", phoneVo);
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/updateForm.jsp");
			
			/*
			RequestDispatcher rd = request.getRequestDispatcher("/updateForm.jsp");
			rd.forward(request, response);
			*/
			
		}else if("update".equals(action)) { //수정일 때
			
			//파라미터에서 값 꺼내기(personId, name, hp, company)
			int personId = Integer.parseInt(request.getParameter("personId"));
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");
			
			
			//vo만들어서 값 초기화
			PhoneVo phoneVo = new PhoneVo(personId, name, hp, company);
			System.out.println(phoneVo);
			
			//phoneDao.personInsert()를 통해 저장하기
			PhoneDao phoneDao = new PhoneDao();
			int count = phoneDao.personUpdate(phoneVo);
			System.out.println(count);
			
			//리다이렉트 list
			WebUtil.redirect(request, response, "/phonebook2/pbc?action=list");
			
			//response.sendRedirect("/phonebook2/pbc?action=list");
			
		}else {
			System.out.println("action 파라미터 없음");
		}
		
	}
	
	//post방식으로 요청 시 호출 메소드
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
