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
import otherClasses.DataBaseAccess;

/**
 *
 * @author Tom
 */
@WebServlet(name = "PaymentDetails", urlPatterns = {"/PaymentDetails"})
public class PaymentDetails extends HttpServlet {

    /**
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
            
            DataBaseAccess dbac = new DataBaseAccess(out);
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String billAdd = request.getParameter("BillAdd");
            String cardType = request.getParameter("card Type");
            String YY = request.getParameter("YY");
            String MM = request.getParameter("MM");
            String cardNo = request.getParameter("cardNo");
            
            
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PaymentDetails</title>");            
            out.println("</head>");
            out.println("<body>");
            
            if (!dbac.makeConnection()) {
                out.println("<h2>Could not make a connection :(</h2>");
                out.println("<a href=\"HomePageLoad\">Go back to the home page</a>");
                out.println("</body>");
                out.println("</html>");
                return;
            }
            
            
            dbac.doUpdate(""
                    + "INSERT INTO customer "
                    + "VALUES ('"+name+"',"
                    + "        '"+email+"',"
                    + "        '"+billAdd+"',"
                    + "        '"+cardType+"',"
                    + "        '"+MM+"/"+YY+"',"
                    + "        '"+cardNo+"'"                    
                    + ");");
            
            
            out.println("<p>payment details have been collected!"
                    + " Your order and payment details and order are shown below"
                    + " and you can change them at anytime up until the date of hire<p>");
           
            
            
            

            
            
            
            out.println("</body>");
            out.println("</html>");
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
