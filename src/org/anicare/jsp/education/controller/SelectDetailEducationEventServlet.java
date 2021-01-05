package org.anicare.jsp.education.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.anicare.jsp.education.model.service.EventService;
import org.anicare.jsp.education.model.vo.BoardAttachment;
import org.anicare.jsp.education.model.vo.Post;

/**
 * Servlet implementation class SelectDetailEducationEventServlet
 */
@WebServlet("/selectDetailEvent.ed")
public class SelectDetailEducationEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectDetailEducationEventServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int num = Integer.parseInt(request.getParameter("num"));
		
		HashMap<String, Object> hmap = new EventService().selectDetailEvent(num);
		
		String path = "";
		if(hmap != null) {
			Post post = (Post) hmap.get("post");
			ArrayList<BoardAttachment> fileList = (ArrayList<BoardAttachment>) hmap.get("boardAttachment");
			
			System.out.println(fileList);
			
			request.setAttribute("post", post);
			request.setAttribute("fileList", fileList);
			
			path = "views/education/trainingEventDetail.jsp";
		} else {
			request.setAttribute("message", "반려동물교육 게시판 상세보기 실패");
			
			path = "views/common/errorPage.jsp";
		}
		
		
	
		request.getRequestDispatcher(path).forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
