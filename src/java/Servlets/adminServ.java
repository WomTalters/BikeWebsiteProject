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
@WebServlet(name = "adminServ", urlPatterns = {"/adminServ"})
public class adminServ extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>TC Bike Hire: Admin</title>");    
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"stylesheet.css\">");
            out.println("</head>");
            out.println("<body>");
            out.println("\t\t<h1 class=\"heading\">TC Bike Hire</h1>");
            out.println("\t\t<div class=\"navbar\">");            
            out.println("\t\t\t<a class=\"button1\" href=\"HomePageLoad\">Home</a>");
            out.println("\t\t\t<a class=\"button1\" href=\"BikeGallery.html\">Bike gallery</a>");
            out.println("\t\t\t<a class=\"button1\" href=\"CycleRoutes.html\">Cycle routes</a>");
            out.println("\t\t\t<a class=\"button1\" href=\"LocCon.html\">General information</a>");
            out.println("\t\t</div>");
            
            String y = request.getParameter("y");
            String m = request.getParameter("m");
            String d = request.getParameter("d");
            String ymd = y+"-"+m+'-'+d;
            
            DataBaseAccess dbac = new DataBaseAccess(out);
            
            dbac.makeConnection();
            
            if (d!=null){
                dbac.doQuery(""
                        + "SELECT COUNT(bike.bike_id) "
                        + "FROM   bike "
                        + "       INNER JOIN (SELECT booked_bike.bike_id,"
                        + "                          booking.booking_date,"
                        + "                          booking.booking_period"
                        + "                   FROM   booked_bike "
                        + "                          INNER JOIN booking"
                        + "                          ON booked_bike.booking_id=booking.booking_id"
                        + "                  ) AS table2 "
                        + "       ON bike.bike_id=table2.bike_id "
                        + "WHERE table2.booking_date='"+ymd+"';"
                        + "");
                
                dbac.nextRow();
                out.println("DATE: "+ymd + " ____ TOTAL ORDERS: " +dbac.getResult("count")+"<br/>");
          
                dbac.doQuery(""
                        + "SELECT bike.bike_id,"
                        + "       bike.bike_type,"
                        + "       table2.booking_period "
                        + "FROM   bike "
                        + "       INNER JOIN (SELECT booked_bike.bike_id,"
                        + "                          booking.booking_date,"
                        + "                          booking.booking_period"
                        + "                   FROM   booked_bike "
                        + "                          INNER JOIN booking"
                        + "                          ON booked_bike.booking_id=booking.booking_id"
                        + "                  ) AS table2 "
                        + "       ON bike.bike_id=table2.bike_id "
                        + "WHERE table2.booking_date='"+ymd+"';"
                        + "");
                
                
                
                out.println("-------------------------------------------------------------------------<br/>");
                out.println("<table>");
                
                while(dbac.nextRow()==1){
                    out.println("<tr><td>"+dbac.getResult("bike_id")+"</td><td>"+dbac.getResult("bike_type")+"</td><td>"+dbac.getResult("booking_period")+"</td></tr>");
                    
                }
                out.println("</table>");
                  
                
            }else{
                
                
                dbac.doQuery(""
                        + "SELECT SUM(amount) "
                        + "FROM booking "
                        + "WHERE  date_part('month',booking_date) = "+m+" "
                        + "AND    date_part('year',booking_date)= "+y+";");
                
                
                dbac.nextRow();
                out.println("MM-YYYY:" +m+"-"+y+" ___ TOTAL INCOME INCOME FOR MONTH: "+dbac.getResult("sum"));
                
              
            }
        
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
