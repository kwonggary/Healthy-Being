package com.welcome;

import static com.constants.Attributes.*;
import static com.constants.Paths.*;
import static com.constants.Keys.*;
import static com.constants.Queries.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(REGISTRATION_SERVLET)
public class Registration extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String repeatPassword = request.getParameter(PASSWORDCONFIRMATION);
        
        RequestDispatcher dispatcher = null;
        Connection connection = null;

        if (password != repeatPassword) {
            request.setAttribute(STATUS, MISMATCHEDPASSWORDS);
            dispatcher = request.getRequestDispatcher(REGISTRATION_JSP);
            dispatcher.forward(request, response);
        }

        try {
            Class.forName(MYSQL_DEPENDENCY);
            connection = DriverManager.getConnection(
                    MYSQL_URL, 
                    MYSQL_USER, 
                    MYSQL_PASSWORD
            );

            String columns = String.join(COMMA, new String[]{FIRST_NAME, LAST_NAME, EMAIL, PASSWORD});
            String values = String.join(COMMA, new String[]{QUESTION_MARK, QUESTION_MARK, QUESTION_MARK, QUESTION_MARK});
            PreparedStatement pst = connection.prepareStatement(
                INSERT_INTO + USERS_TABLE + OPEN_PARENTHESES + columns + CLOSED_PARENTHESES + SPACE + 
                VALUES + OPEN_PARENTHESES + values + CLOSED_PARENTHESES
            );

            pst.setString(1, firstName);
            pst.setString(2, lastName);
            pst.setString(3, email);
            pst.setString(4, password);

            int rowCount = pst.executeUpdate();
            dispatcher = request.getRequestDispatcher(REGISTRATION_JSP);

            if (rowCount > 0) {
                request.setAttribute(STATUS, SUCCESS);
            } else {
                request.setAttribute(STATUS, FAILED);
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
