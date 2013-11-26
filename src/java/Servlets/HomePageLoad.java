/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Tom
 */
@WebServlet(name = "HomePageLoad", urlPatterns = {"/HomePageLoad"})
public class HomePageLoad extends HttpServlet {

    /**This servlet is used to make the homepage load with an up to date bike list 
     * that shows what bikes are available
     * 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            String message = "Greeting adagg";
            request.setAttribute("message", message); // This will be available as ${message}
            request.getRequestDispatcher("HomePage.jsp").forward(request, response);
            
            
            
            
            
            /*
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Hiring Bikes Homepage</title>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");  
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"stylesheet.css\">");  
            out.println("</head>");
            
            out.println("<body>");
            out.println("<h1>Welcome to the Hiring Bikes website</h1>");  
            out.println("<p><a href= \"ProcessOrder\" class =\"button\">Click to Book a Bike</a></p>"); 
            out.println("<p><a href= \"BikeGallery.html\" class = \"button\">Click to view information on Bikes</a></p>");          
            out.println("</body>");
            out.println("</html>");
            
            */
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {                  
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
