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
import otherClasses.InputCheck;

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
            InputCheck ic = new InputCheck(out);
            
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String billAdd = request.getParameter("BillAdd");
            String cardType = request.getParameter("card Type");
            String YY = request.getParameter("YY");
            String MM = request.getParameter("MM");
            String cardNo = request.getParameter("cardNo");
            String bId = request.getParameter("bId");
            
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>TC bike hire</title>");     
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"stylesheet.css\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1 class=\"heading\" id=\"fred\">TC Bike Hire</h1>");
            
            if (!ic.checkString(name,48) || !ic.checkString(billAdd,200) ||  !ic.checkNumber(MM,2) ||  !ic.checkNumber(YY,2) || !ic.checkNumber(cardNo,16)){
                
                
                out.println("<h2>Bad input</h2>");
                out.println("<br/><p>One of the fields was filled in wrong, go back and re-try</p>");
                out.println("</body>");
                out.println("</html>");
                return;
            }
            
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
                    + " Your order is shown below<p>");
           
            dbac.doQuery("SELECT bike.bike_id,"
                    + "          bike.bike_type"
                    + "   FROM bike "
                    + "   INNER JOIN booked_bike"
                    + "   ON bike.bike_id=booked_bike.bike_id"
                    + "   WHERE booking_id='"+bId+"'       ;");
            
            
            out.println("<table>");
            while(dbac.nextRow()==1){
                out.println("<tr>");
                out.println("<td>");
                out.println(dbac.getResult("bike_id"));
                out.println("</td>");
                out.println("<td>");
                out.println(dbac.getResult("bike_type"));
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</table>");
           
            out.println("<br/> <a href=\"HomePageLoad\">Back to home page</a>");
            
            dbac.closeConnection();
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
