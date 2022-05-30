package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.PhoneVo;

public class PhoneDao {
	
	//필드
	private String id = "phonedb";
	private String pw = "phonedb";
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	//생성자
	
	
	//메소드-gs
	
	
	//메소드-일반
	//>DB 연결 메소드
		public void getConnection() {
			
			try {

				// 1. JDBC 드라이버 (Oracle) 로딩
				Class.forName(driver);
				
				// 2. Connection 얻어오기
				conn = DriverManager.getConnection(url, id, pw);

			} catch (ClassNotFoundException e) {
				System.out.println("error: 드라이버 로딩 실패 - " + e);
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
		}
		
		//>자원 정리 메소드
		public void close() {
			
			// 5. 자원정리
			try {
				
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
		}
		
		//정보 등록 메소드
		public int personInsert(PhoneVo phoneVo) {
			
			int count = -1;
			
			getConnection();

			try {

				// 3. SQL문 준비 / 바인딩 / 실행
				
				//SQL문 준비
				String query = "";
				query += " insert into person ";
				query += " values (seq_person_id.nextval, ?, ?, ?) ";
				
				//바인딩
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, phoneVo.getName());
				pstmt.setString(2, phoneVo.getHp());
				pstmt.setString(3, phoneVo.getCompany());
				
				//실행
				count = pstmt.executeUpdate();
				
				// 4.결과처리
				System.out.println("["+count + "건이 등록되었습니다.]");

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
			close();
			
			return count;
			
		}
		
		//정보 삭제 메소드
		public int personDelete(int personId) {
			
			int count = -1;
			
			getConnection();

			try {

				// 3. SQL문 준비 / 바인딩 / 실행
				
				//SQL문 준비
				String query = "";
				query += " delete from person ";
				query += " where person_id = ? ";
				
				//바인딩
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, personId);
				
				//실행
				count = pstmt.executeUpdate();
				
				// 4.결과처리
				System.out.println("["+count + "건이 삭제되었습니다.]");

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
			close();
			
			return count;
			
		}
		
		//정보 수정 메소드
		public int personUpdate(PhoneVo phoneVo) {
			
			int count = -1;
			
			getConnection();

			try {
				
				// 3. SQL문 준비 / 바인딩 / 실행
				
				//SQL문 준비
				String query = "";
				query += " update person ";
				query += " set name = ? ";
				query += "     , hp = ? ";
				query += "     , company = ? ";
				query += " where person_id = ? ";
				
				//바인딩
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, phoneVo.getName());
				pstmt.setString(2, phoneVo.getHp());
				pstmt.setString(3, phoneVo.getCompany());
				pstmt.setInt(4, phoneVo.getPersonId());
				
				//실행
				count = pstmt.executeUpdate();
				
				// 4.결과처리
				System.out.println("["+count + "건이 수정되었습니다.]");

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
			close();
			
			return count;
			
		}
		
		//정보 전체 조회 메소드
		public List<PhoneVo> personSelect() {
			
			//리스트 만들기
			List<PhoneVo> phoneList = new ArrayList<PhoneVo>();
			
			getConnection();
			
			try {

				// 3. SQL문 준비 / 바인딩 / 실행
				
				//SQL문 준비
				String query = "";
				query += " select person_id ";
				query += "        , name ";
				query += "        , hp ";
				query += "        , company ";
				query += " from person ";
				
				//바인딩
				pstmt = conn.prepareStatement(query);
				
				//실행
				rs = pstmt.executeQuery();
				
				// 4.결과처리
				//반복문으로 Vo 만들기 List에 추가하기
				while(rs.next()) {
					
					int personId = rs.getInt("person_id");
					String name = rs.getString("name");
					String hp = rs.getString("hp");
					String company = rs.getString("company");
					
					
					PhoneVo phoneVo = new PhoneVo(personId, name, hp, company);
					
					phoneList.add(phoneVo);
					
				}

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
			close();
			
			return phoneList;
			
		}
		
		//정보 검색 메소드
		public List<PhoneVo> personSelect(String keyword) {
			
			//리스트 만들기
			List<PhoneVo> phoneList = new ArrayList<PhoneVo>();
			
			getConnection();
			
			try {

				// 3. SQL문 준비 / 바인딩 / 실행
				
				//SQL문 준비
				String query = "";
				query += " select person_id ";
				query += "        , name ";
				query += "        , hp ";
				query += "        , company ";
				query += " from person ";
				query += " where (person_id Like '%"+keyword+"%' ";
				query += " or name Like '%"+keyword+"%' ";
				query += " or hp Like '%"+keyword+"%' ";
				query += " or company Like '%"+keyword+"%') ";
				
				
				//바인딩
				pstmt = conn.prepareStatement(query);
				
				//실행
				rs = pstmt.executeQuery();
				
				// 4.결과처리
				//반복문으로 Vo 만들기 List에 추가하기
				while(rs.next()) {
					
					int personId = rs.getInt("person_id");
					String name = rs.getString("name");
					String hp = rs.getString("hp");
					String company = rs.getString("company");
					
					
					PhoneVo phoneVo = new PhoneVo(personId, name, hp, company);
					
					phoneList.add(phoneVo);
					
				}
				
				//리스트 출력해 보기
				//System.out.println(phoneList.toString());

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
			close();
			
			return phoneList;
			
		}
		
		//1명 정보 가져오기
		public PhoneVo getPerson(int personId) {
			
			PhoneVo phoneVo = null;
			
			getConnection();

			try {
				
				// 3. SQL문 준비 / 바인딩 / 실행
				
				//SQL문 준비
				String query = "";
				query += " select person_id ";
				query += "        , name ";
				query += "        , hp ";
				query += "        , company ";
				query += " from person ";
				query += " where person_id = ? ";
				
				//바인딩
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, personId);
				
				//실행
				rs = pstmt.executeQuery();
				
				// 4.결과처리
				while(rs.next()) {

					int id = rs.getInt("person_id");
					String name = rs.getString("name");
					String hp = rs.getString("hp");
					String company = rs.getString("company");
					
					phoneVo = new PhoneVo(id, name, hp, company);
					
				}

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
			close();
			
			return phoneVo;
		}

}
