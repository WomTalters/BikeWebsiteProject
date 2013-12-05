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
            
            DataBaseAccess dbac = new DataBaseAccess();
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Hiring Bikes Homepage</title>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");  
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"stylesheet.css\">");  
            out.println("</head>");
            out.println("<body>");
            
            
            if (!dbac.makeConnection()){
                out.println("<h1>Could not connect to the database :(</h1>");                        
                out.println("</body>");
                out.println("</html>");
                return;
            }
            
            String [] bike_types = {"womens_mtb","mens_mtb","childs_mtb","womens_hybrid","mens_hybrid","tandem"};
            
            
            out.println("<script>");
            out.println("var numBikeType = new Array();");
            out.println("var BikeType = new Array();");
            for (int i = 0;i<bike_types.length;i++){
               dbac.doQuery("select count(bike_type) from bike where bike_type='"+bike_types[i]+"';");
               dbac.nextRow();
               out.println("BikeType["+i+"] = '"+ bike_types[i] +"';");
               out.println("numBikeType["+i+"] = "+ Integer.parseInt(dbac.getResult("count")) +";");                                 
            }
            
            String q1 = "select bike.bike_type,booked_bike.booking_id from bike inner join booked_bike on bike.bike_id=booked_bike.bike_id";
            String q2 = "select booking.booking_date,booking.booking_period,table2.bike_type from booking inner join ("+q1+") AS table2 on booking.booking_id=table2.booking_id;";
            
            dbac.doQuery(q2);
            out.println("var booking_bike_type = new Array()");
            out.println("var booking_date = new Array()");
            out.println("var booking_period = new Array()");
            int i = 0;
            while (dbac.nextRow()==1){
                out.println("booking_bike_type["+i+"] = '"+ dbac.getResult("bike_type") +"';");
                out.println("booking_date["+i+"] = '"+ dbac.getResult("booking_date") +"';");
                out.println("booking_period["+i+"] = '"+ dbac.getResult("booking_period") +"';");
                i++;
            }
            out.println("document.write(booking_bike_type[4]);");
            out.println("</script>"); 
            
            
            
            
               
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
