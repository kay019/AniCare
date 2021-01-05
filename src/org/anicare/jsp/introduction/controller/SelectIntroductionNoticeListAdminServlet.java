package org.anicare.jsp.introduction.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.anicare.jsp.introduction.model.service.NoticeService;
import org.anicare.jsp.introduction.model.vo.IntroNotice;
import org.anicare.jsp.introduction.model.vo.PageInfo;

/**
 * Servlet implementation class SelectIntroductionNoticeListAdminServlet
 */
@WebServlet("/selectNoticeAdmin.in")
public class SelectIntroductionNoticeListAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectIntroductionNoticeListAdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//search process
		String searchingFor = "";
		if(request.getParameter("searchingFor") != null) {
			searchingFor = request.getParameter("searchingFor");
		}
		//category process
		String category = "";
		if(request.getParameter("category") != null) {
			category = request.getParameter("category");
		}
		
		//page process
		int currentPage;
		int limit;
		int maxPage;
		int startPage;
		int endPage;
		
		currentPage = 1;
		
		
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		limit = 100;
		
		NoticeService ns = new NoticeService();
		int listCount = ns.getSearchListCount(searchingFor, category);
		
		maxPage = (int)((double)listCount / limit + 0.99);
		startPage = (((int)((double)currentPage / limit + 0.99) - 1) * 10 + 1);
		endPage = startPage + limit - 1;
		
		if(maxPage < endPage) {
			endPage = maxPage;
		}
		
		PageInfo pi = new PageInfo(currentPage, listCount, limit, maxPage, startPage, endPage);
		
		ArrayList<IntroNotice> list = ns.searchNotice(searchingFor, category, pi); 
		
		System.out.println(pi);
		
		String path = "";
		if(list != null) {
			path = "views/admin/introduction/adminSelectNotice.jsp";
			request.setAttribute("list", list);
			request.setAttribute("pi", pi);
			request.setAttribute("searchingFor", searchingFor);
			request.setAttribute("category", category);
		} else {
			path = "views/common/errorPage.jsp";
			request.setAttribute("message", "공지사항 목록 불러오기 실패");
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
