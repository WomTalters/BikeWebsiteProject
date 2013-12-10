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
 * 
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
     * 
     * 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            
            String day = request.getParameter("day");            
            String month = request.getParameter("month");
            String year = request.getParameter("year");
            String period = request.getParameter("period");
            String email = request.getParameter("email");
            
            
             

            DataBaseAccess dbac = new DataBaseAccess(out);
            InputCheck ic = new InputCheck(out);

            String date = year + "-" + month + "-" + day;

            String[] bike_types = {"womens_mtb", "mens_mtb", "childs_mtb", "womens_hybrid", "mens_hybrid", "tandem"};
            String[] numSlcBT = new String[bike_types.length];
            for (int i = 0; i < bike_types.length; i++) {
                numSlcBT[i] = request.getParameter("selectNumOf" + bike_types[i]);
            }

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("\t<head>");
            out.println("\t\t<title>TC bike hire</title>");
            out.println("\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"stylesheet.css\">");
            out.println("\t</head>");
            out.println("\t<body>");
            out.println("\t\t<h1 class=\"heading\">TC Bike Hire</h1>");
            out.println("\t\t<div class=\"navbar\">");            
            out.println("\t\t\t<a class=\"button1\" href=\"HomePageLoad\">Home</a>");
            out.println("\t\t\t<a class=\"button1\" href=\"BikeGallery.html\">Bike gallery</a>");
            out.println("\t\t\t<a class=\"button1\" href=\"CycleRoutes.html\">Cycle routes</a>");
            out.println("\t\t\t<a class=\"button1\" href=\"LocCon.html\">General information</a>");
            out.println("\t\t</div>");
            
            if (!ic.checkNumber(day,2) || !ic.checkNumber(month,2) || !ic.checkNumber(year,4) || !ic.checkEmail(email)){
                
                out.println("\t\t<h2>Bad input</h2><br/>");
                out.println("\t\t<p>One of the fields was filled in wrong, go back and re-try</p><br/>");
                out.println("\t\t<a href=\"HomePageLoad\">Back to home Page</a>");
                out.println("\t</body>");
                out.println("</html>");
                return;
                
            }
            
            for (int i = 0; i < bike_types.length; i++) {
                if (!ic.checkNumber(numSlcBT[i],-1)){
                    out.println("\t\t<h2>Bad input</h2><br/>");
                    out.println("\t\t<p>One of the fields was filled in wrong, go back and re-try</p><br/>");
                    out.println("\t\t<a href=\"HomePageLoad\">Back to home Page</a>");
                    out.println("\t</body>");
                    out.println("</html>");
                    return;
                }
                
                 
            }
            
            

            if (!dbac.makeConnection()) {
                out.println("\t\t<h2>Could not make a connection :(</h2>");
                out.println("\t\t<a href=\"HomePageLoad\">Go back to the home page</a>");
                out.println("\t</body>");
                out.println("</html>");
                return;
            }

            /*
             checks if enough bikes are avaliable. 
             If there is an incefeciant number of any bike type requested, 
             the html page is ended with a warning message 
             and the the servelet stops.
             */
            for (int i = 0; i < bike_types.length; i++) {
                if (Integer.parseInt(numSlcBT[i]) != 0) {
                    /*
                     This query gets the number of bikes of a certain type that 
                     are not booked on the requested booking date and period.                     
                     */
                    dbac.doQuery(""
                            + "SELECT COUNT(bike_id) "
                            + "FROM bike "
                            + "WHERE bike_type='" + bike_types[i] + "' "
                            + "AND bike_id "
                            + "NOT IN (SELECT booked_bike.bike_id"
                            + "        FROM booked_bike"
                            + "        INNER JOIN booking"
                            + "        ON booked_bike.booking_id=booking.booking_id"
                            + "        WHERE (booking.booking_date='" + date + "'"
                            + "               AND     (booking.booking_period='" + period + "'"
                            + "               OR   booking.booking_period='ALL'"
                            + "               OR   'ALL'='" + period + "'"
                            + "                       )"
                            + "              )"
                            + "       );"
                            + "");
                    dbac.nextRow();
                    //ends the servlet if the number of a certain bike type requested is less than the availble number 
                    if (Integer.parseInt(numSlcBT[i]) > Integer.parseInt(dbac.getResult("count"))) {
                        out.println("\t\t<h2>Bad Order</h2>");
                        out.println(""
                                + "\t\t<p>One of the bikes you selected has just become "
                                + "unavalible, please go back to the "
                                + "homePage and try again</p>"                                
                                + "<br/> ");
                        out.println("\t\t<a href=\"HomePageLoad\">Back to home page</a>");
                        out.println("\t</body>");
                        out.println("</html>");
                        return;
                    }
                }
            }

            /*
             Calculates the cost of the order            
             */
            double amount = 0;
            for (int i = 0; i < bike_types.length; i++) {
                dbac.doQuery(""
                        + "SELECT day,"
                        + "       half "
                        + "FROM   rates "
                        + "WHERE  bike_type = '" + bike_types[i] + "';"
                        + "");
                dbac.nextRow();
                String all = "ALL";
                if (all.equals(period)) {
                    amount += Double.parseDouble(dbac.getResult("day")) * Integer.parseInt(numSlcBT[i]);
                } else {
                    amount += Double.parseDouble(dbac.getResult("half")) * Integer.parseInt(numSlcBT[i]);
                }
            }

            //Make a new booking number by getting the current biggest one and adding 1 to it
            dbac.doQuery(""
                    + "SELECT MAX(booking_id) "
                    + "FROM   booking;"
                    + "");
            dbac.nextRow();
            int booking_id = Integer.parseInt(dbac.getResult("max")) + 1;
            //update booking table
            dbac.doUpdate(""
                    + "INSERT INTO booking "
                    + "           (booking_id,"
                    + "            customer_email,"
                    + "            booking_date,"
                    + "            booking_period,"
                    + "            amount) "
                    + "VALUES ('" + booking_id + "',"
                    + "        '" + email + "',"
                    + "        '" + date + "',"
                    + "        '" + period + "',"
                    + "        '" + amount + "'"
                    + "       );"
                    + "");
            //update booked_bike table

            for (int i = 0; i < bike_types.length; i++) {
                for (int j = 0; j < Integer.parseInt(numSlcBT[i]); j++) {

                    dbac.doUpdate(""
                            + "INSERT INTO booked_bike "
                            + "           (booking_id,"
                            + "            bike_id) "
                            + "VALUES (" + booking_id + ","
                            + "         (SELECT bike_id "
                            + "          FROM bike"
                            + "          WHERE bike_type='" + bike_types[i] + "'"
                            + "          AND bike_id"
                            + "          NOT IN (SELECT booked_bike.bike_id"
                            + "                      FROM booked_bike"
                            + "                      INNER JOIN booking"
                            + "                      ON booked_bike.booking_id=booking.booking_id"
                            + "                      WHERE (booking.booking_date='" + date + "'"
                            + "                      AND     (booking.booking_period='" + period + "'"
                            + "                      OR   booking.booking_period='ALL'"
                            + "                      OR   'ALL'='" + period + "'"
                            + "                              )"
                            + "                            )"
                            + "                     )"
                            + "         LIMIT 1))"
                            + "       ;"
                            + "");
                }
            }

            dbac.doQuery(""
                    + "SELECT COUNT(customer_email) "
                    + "FROM customer "
                    + "WHERE customer_email='" + email + "';"
                    + "");
            dbac.nextRow();
            if (Integer.parseInt(dbac.getResult("count")) == 1) {
                //if there is an email address display the order
                
                out.println("<p>\t\tWe already have your payment details."
                        + " Your order is shown below<p>");
                dbac.doQuery("SELECT bike.bike_id,"
                        + "          bike.bike_type"
                        + "   FROM bike "
                        + "   INNER JOIN booked_bike"
                        + "   ON bike.bike_id=booked_bike.bike_id"
                        + "   WHERE booking_id='" + booking_id + "'       ;");

                out.println("\t\t<table>");
                while (dbac.nextRow() == 1) {
                    out.println("\t\t\t<tr>");
                    out.println("\t\t\t\t<td>");
                    out.println(dbac.getResult("bike_id"));
                    out.println("\t\t\t\t</td>");
                    out.println("\t\t\t\t<td>");
                    out.println(dbac.getResult("bike_type"));
                    out.println("\t\t\t\t</td>");
                    out.println("\t\t\t</tr>");
                }
                out.println("\t\t</table><br/> ");
    
                out.println("\t\t<a href=\"HomePageLoad\">Back to home page</a>");
                
                
                
            } else {
                //otherwise display a form for inputing payment details
                
                out.println("\t\t<p>You need to enter your payment details. Make sure they are correct before submitting<p>");
                out.println("\t\t<form action =\"PaymentDetails\" method=\"POST\">");
                out.println("\t\t\tName: <input type=\"text\" name=\"name\"><br/>");
                out.println("\t\t\tEmail: <input type=\"text\" value=\"" + email + "\" name=\"email\" readonly><br/>");
                out.println("\t\t\tBilling address: <input type=\"text\" name=\"BillAdd\"><br/>");
                out.println("\t\t\tCard Type:<select name=\"card Type\">");
                out.println("\t\t\t\t<option value=\"V\">Visa</option>");
                out.println("\t\t\t\t<option value=\"MC\">Master Card</option>");
                out.println("\t\t\t\t<option value=\"AE\">American Express</option>");
                out.println("\t\t\t</select><br/>");
                out.println("\t\t\tExpiry MM/YY: <input type=\"text\" name=\"MM\"><input type=\"text\" name=\"YY\"><br/>");
                out.println("\t\t\tCard number: <input type=\"text\" name=\"cardNo\"><br/>");
                out.println("\t\t\tBooking ID: <input type=\"text\" value=\"" + booking_id + "\" name=\"bId\" readonly><br/>");
                out.println("\t\t\t<input type=\"submit\" value=\"submit\"><br/>");
                out.println("\t\t</form>");
            }
            dbac.closeConnection();

            out.println("\t</body>");
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
