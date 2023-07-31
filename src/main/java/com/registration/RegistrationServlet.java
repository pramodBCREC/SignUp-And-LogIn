package com.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String uname = req.getParameter("name");
		String uemail = req.getParameter("email");
		String upwd = req.getParameter("pass");
		String repwd = req.getParameter("re_pass");
		String umobile = req.getParameter("contact");
		RequestDispatcher dispatcher = null;
		Connection con = null;
		
		if(uname==null || uname.equals("")) {
			req.setAttribute("status","InvalidUname");
			dispatcher = req.getRequestDispatcher("registration.jsp");
			dispatcher.forward(req, res);
			
		}
		
		if(uemail==null || uemail.equals("")) {
			req.setAttribute("status","InvalidEmail");
			dispatcher = req.getRequestDispatcher("registration.jsp");
			dispatcher.forward(req, res);
			
		}
		
		if(upwd==null || upwd.equals("")) {
			req.setAttribute("status","InvalidUpwd");
			dispatcher = req.getRequestDispatcher("registration.jsp");
			dispatcher.forward(req, res);
			
		}else if(!upwd.equals(repwd)) {
			req.setAttribute("status","InvalidConfirmPassword");
			dispatcher = req.getRequestDispatcher("registration.jsp");
			dispatcher.forward(req, res);
		}
		
		if(umobile==null || umobile.equals("")) {
			req.setAttribute("status","InvalidUmobile");
			dispatcher = req.getRequestDispatcher("registration.jsp");
			dispatcher.forward(req, res);
			
		}else if(umobile.length()>10) {
			req.setAttribute("status","InvalidMobileLength");
			dispatcher = req.getRequestDispatcher("registration.jsp");
			dispatcher.forward(req, res);
		}
		
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			  con=DriverManager.getConnection("jdbc:mysql://localhost:3306/signupandlogin?useSSL=false","root","Root@Mysql123");
			PreparedStatement pst = con.prepareStatement("insert into user(uname,upwd,uemail,umobile) values(?,?,?,?)");
			pst.setString(1,uname);
			pst.setString(2,upwd);
			pst.setString(3,uemail);
			pst.setString(4,umobile);
			
			int rowCount = pst.executeUpdate();
			dispatcher = req.getRequestDispatcher("registration.jsp");
			if(rowCount > 0) {
				req.setAttribute("status", "success");
			}else {
				req.setAttribute("status", "failed");
			}
			dispatcher.forward(req, res);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		
	}

}
