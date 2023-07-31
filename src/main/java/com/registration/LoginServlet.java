package com.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String uemail = req.getParameter("username");
		String upwd = req.getParameter("password");
		HttpSession session = req.getSession();
		RequestDispatcher dispatcher = null;
		
		
		if(uemail==null || uemail.equals("")) {
			req.setAttribute("status","InvalidEmail");
			dispatcher = req.getRequestDispatcher("login.jsp");
			dispatcher.forward(req, res);
			
		}
		
		if(upwd==null || upwd.equals("")) {
			req.setAttribute("status","InvalidUpwd");
			dispatcher = req.getRequestDispatcher("login.jsp");
			dispatcher.forward(req, res);
			
		}
		
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection  con=DriverManager.getConnection("jdbc:mysql://localhost:3306/signupandlogin?useSSL=false","root","Root@Mysql123");
			PreparedStatement pst = con.prepareStatement("select * from user where uemail=? and upwd=?");
			pst.setString(1, uemail);
			pst.setString(2, upwd);
		ResultSet re = pst.executeQuery();
		if(re.next()) {
			session.setAttribute("name",re.getString("uname"));
			dispatcher = req.getRequestDispatcher("index.jsp");
			
		}else {
			req.setAttribute("status","failed");
			dispatcher = req.getRequestDispatcher("login.jsp");
			
		}
		dispatcher.forward(req, res);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
