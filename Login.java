package com.welcome;

import static com.constants.Attributes.*;
import static com.constants.Paths.*;
import static com.constants.Keys.*;
import static com.constants.Queries.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(LOGIN_SERVLET)
public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);

        RequestDispatcher dispatcher = null;
        Connection connection = null;
        HttpSession session = request.getSession();
        
        try {
            Class.forName(MYSQL_DEPENDENCY);
            connection = DriverManager.getConnection(
                MYSQL_URL, 
                MYSQL_USER, 
                MYSQL_PASSWORD
            );

            String query = String.join(SPACE, new String[]{
                SELECT, STAR, FROM, USERS_TABLE, 
                WHERE, EMAIL, EQUAL, QUESTION_MARK, 
                AND, PASSWORD, EQUAL, QUESTION_MARK
            });
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, password);
            
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                session.setAttribute(NAME, rs.getString(FIRST_NAME));
                session.setAttribute(USERID, rs.getInt(ID));
                dispatcher = request.getRequestDispatcher(ACTIVITIES_JSP);
            } else {
                request.setAttribute(STATUS, rs.getString(FAILED));
                dispatcher = request.getRequestDispatcher(LOGIN_JSP);
            }
            dispatcher.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }
}
        