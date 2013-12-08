/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

            DataBaseAccess dbac = new DataBaseAccess(out);

            boolean badOrder = false;
            String[] bike_types = {"womens_mtb", "mens_mtb", "childs_mtb", "womens_hybrid", "mens_hybrid", "tandem"};
            String[] numSelectedOfBikeTypes = new String[bike_types.length];
            

            for (int i = 0; i < bike_types.length; i++) {
                numSelectedOfBikeTypes[i] = request.getParameter("selectNumOf" + bike_types[i]);
            }

            String day = request.getParameter("day");
            String month = request.getParameter("month");
            String year = request.getParameter("year");
            String period = request.getParameter("period");
            
            out.println(day);

            String date = year + "-" + month + "-" + day;

            String email = request.getParameter("email");

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Payment information</title>");
            out.println("</head>");
            out.println("<body>");

            out.println(email);

            if (!dbac.makeConnection()) {
                out.println("<h2>Could not make a connection :(</h2>");
                out.println("<a href=\"HomePageLoad\">Go back to the home page</a>");
                out.println("</body>");
                out.println("</html>");
                return;
            }

            for (int i = 0; i < bike_types.length; i++) {
                
                
                if (Integer.parseInt(numSelectedOfBikeTypes[i]) != 0) {
                    String q1 = "select bike.bike_type,booked_bike.booking_id,booked_bike.bike_id from bike inner join booked_bike on bike.bike_id=booked_bike.bike_id where bike.bike_type='" + bike_types[i] + "'";
                    String q2 = "select booking.booking_date,booking.booking_period,table2.bike_type,table2.bike_id from booking inner join (" + q1 + ") AS table2 on booking.booking_id=table2.booking_id;";
                    dbac.doQuery(q2);
                    int n = 0;
                    while (dbac.nextRow() == 1) {
                        String s = dbac.getResult("booking_period");
                        String s2 = dbac.getResult("booking_date");
                        if (!(s2.equals(date) && (s.equals(period) || s.equals("ALL") || period.equals("ALL")))) {
                            n++;
                            
                        }
                    }
                    if (Integer.parseInt(numSelectedOfBikeTypes[i]) > n) {
                        badOrder = true;
                        break;
                    }
                }
            }

            if (badOrder) {
                out.println("<h1>Bad Order</h1>");
                out.println("<p>One of the bikes you selected has just become unavalible, please go back to the homePage and try again</p>");
            } else {
                
                
                double amount = 0;
                for (int i = 0; i < bike_types.length; i++) {
                    dbac.doQuery("select * from rates where bike_type='"+bike_types[i]+"';");
                    
                    dbac.nextRow();
                    String all = "ALL";
                    if (all.equals(period)){
                        amount += Double.parseDouble(dbac.getResult("day"))*Integer.parseInt(numSelectedOfBikeTypes[i]);
                        
                    }else{
                        amount += Double.parseDouble(dbac.getResult("half"))*Integer.parseInt(numSelectedOfBikeTypes[i]);
                        
                    }
                    
                    
                }
                
                dbac.doUpdate("insert into booking (booking_id,customer_email,booking_date,booking_period,amount) values (9991,'"+email+"','"+date+"','"+period+"','"+amount+"');");
                out.println("yarr");
                for (int i = 0; i < bike_types.length; i++) {
                    for (int j = 0; j < Integer.parseInt(numSelectedOfBikeTypes[i]); j++) {
                        dbac.doUpdate("insert into booked_bike (booking_id,bike_id) values (9991,(select table2.bike_id from booking inner join (select bike.bike_type,booked_bike.booking_id,booked_bike.bike_id from bike inner join booked_bike on bike.bike_id=booked_bike.bike_id where bike.bike_type='" +
                                bike_types[i] + "') AS table2 on booking.booking_id=table2.booking_id where (booking.booking_date='"+date+"' and (booking.booking_period='"+period
                                +"' or booking.booking_period='ALL' or 'ALL'='"+period+"'))=false limit 1)) ;");
                    }
                }
                
                
            }
            
/*
            dbac.doQuery("select count(customer_email) from customer where customer_email=" + email + ";");
            dbac.nextRow();
            if (Integer.parseInt(dbac.getResult("count")) == 1) {

            }
            dbac.closeConnection();
*/
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
