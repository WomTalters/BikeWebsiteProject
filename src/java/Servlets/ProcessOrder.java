/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "ProcessOrder", urlPatterns = {"/ProcessOrder"})
public class ProcessOrder extends HttpServlet {

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
            
            DataBaseAccess dbac = new DataBaseAccess();
            
            String customer_email = request.getParameter("email");
            String booking_day = request.getParameter("booking_day");
            String booking_month = request.getParameter("booking_month");
            String booking_year = request.getParameter("booking_year");
            String booking_period = request.getParameter("booking_period");
            int num_biketypes = Integer.parseInt(request.getParameter("num_biketypes"));     
            String note = request.getParameter("note");
            
            String date = booking_day + "/" + booking_month + "/" + booking_year;
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Payment information</title>");            
            out.println("</head>");
            out.println("<body>");           
            
            
            out.println("hello");
            
            
            if (!dbac.makeConnection()){
                out.println("<h2>Could not make a connection :(</h2>");
                out.println("<a href=\"HomePageLoad\">Go back to the home page</a>");
                out.println("</body>");
                out.println("</html>");
                return;
            }
            
            
            
            for (int i =0;i<num_biketypes;i++){
                
                
                String bikemodel = request.getParameter("bikemodel_" + i);
                int number_ordered = Integer.parseInt(request.getParameter("num_ordered_" + i));
                
                
                //add something for servicing later. Should probably change this. worth checking thourougly
                //this selects a bike that is either not in the booking table or in the booking table but booked for a different time or date
                dbac.doQuery("SELECT COUNT(bike_id) FROM bike WHERE (bike_id NOT IN (SELECT bike_id FROM booked_bike) OR bike_id NOT IN (SELECT bike_id FROM booked_bike WHERE booking_id IN (SELECT booking_id FROM booking WHERE booking_date='"+date+"' AND (booking_period='"+booking_period+"' OR booking_period='ALL')) )) AND  model="+bikemodel+";");
                               
                //checking avaliblity of bikes
                if (number_ordered > Integer.parseInt(dbac.getResult("COUNT(bike_id)"))){
                   
                    out.println("<h2>One or more of the bikes you requested has become unvailable since you loaded up the booking page:(</h2>");
                    out.println("<a href=\"HomePageLoad\">Please go back to the booking page and try again</a>");                    
                    out.println("</body>");
                    out.println("</html>");
                    return;                                       
                }                              
            }
            
            
            for (int i =0;i<num_biketypes;i++){
                
                
                String bikemodel = request.getParameter("bikemodel_" + i);
                int number_ordered = Integer.parseInt(request.getParameter("num_ordered_" + i));
                
                
                //add something for servicing later. Should probably change this. worth checking thourougly
                
                dbac.doQuery("SELECT bike_id FROM bike WHERE (bike_id NOT IN (SELECT bike_id FROM booked_bike) OR bike_id NOT IN (SELECT bike_id FROM booked_bike WHERE booking_id IN (SELECT booking_id FROM booking WHERE booking_date='"+date+"' AND (booking_period='"+booking_period+"' OR booking_period='ALL')) )) AND  model="+bikemodel+";");
                               
                for (int j = number_ordered;j>=0;j--){
                    //dbac.doUpdate("INSERT INTO booked_bike VALUES ()")
                    dbac.getResult("bike_id");
                    
                    
                }
                                        
            }
            
            
            
            
            
            
                    
            while (dbac.nextRow()){
                
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
