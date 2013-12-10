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
            
            DataBaseAccess dbac = new DataBaseAccess(out);
           
            
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("\t<head>");
            out.println("\t\t<title>TC bike hire</title>");
            out.println("\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");  
            out.println("\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"stylesheet.css\">"); 
            out.println("\t\t<script src=\"hps.js\"></script>");
            out.println("\t</head>");
            out.println("\t<body>");
            out.println("\t\t<h1 class=\"heading\">TC Bike Hire</h1>");
            out.println("\t\t<div class=\"navbar\">");            
            out.println("\t\t\t<a class=\"button1\" href=\"HomePageLoad\">Home</a>");
            out.println("\t\t\t<a class=\"button1\" href=\"BikeGallery.html\">Bike gallery</a>");
            out.println("\t\t\t<a class=\"button1\" href=\"CycleRoutes.html\">Cycle routes</a>");
            out.println("\t\t\t<a class=\"button1\" href=\"LocCon.html\">General information</a>");
            out.println("\t\t</div>");
            
            if (!dbac.makeConnection()){
                out.println("\t\t<h2>Could not connect to the database :(</h2>");                        
                out.println("\t</body>");
                out.println("</html>");
                return;
            }
            
            String [] bike_types = {"womens_mtb","mens_mtb","childs_mtb","womens_hybrid","mens_hybrid","tandem"};
            String [] bike_typesp = {"Womens mountain bike","Mens mountain bike","Childs mountain bike","Womens hybrid","Mens hybrid","Tandem"};
            
            
            //This script contains arrays that store bike booking information used on the homepage
            out.println("\t\t<script>");
            
            
            out.println("\t\t\tvar numBikeType = new Array();");
            out.println("\t\t\tvar bikeType = new Array();");
            for (int j = 0;j<bike_types.length;j++){
               dbac.doQuery(""
                       + "SELECT COUNT(bike_type) "
                       + "FROM bike "
                       + "WHERE bike_type='"+bike_types[j]+"';"
                       + "");
               dbac.nextRow();
               out.println("\t\t\tbikeType["+j+"] = '"+ bike_types[j] +"';");
               out.println("\t\t\tnumBikeType["+j+"] = "+ Integer.parseInt(dbac.getResult("count")) +";");                                 
            }
            
            
            dbac.doQuery(""
                    + "SELECT booking.booking_date,"
                    + "       booking.booking_period,"
                    + "       table2.bike_type "
                    + "FROM   booking "
                    + "       INNER JOIN (SELECT bike.bike_type,"
                    + "                          booked_bike.booking_id"
                    + "                   FROM bike "
                    + "                        INNER JOIN booked_bike "
                    + "                        ON bike.bike_id=booked_bike.bike_id"
                    + "                  ) AS table2 "
                    + "       ON booking.booking_id=table2.booking_id;");
            
            
            out.println("\t\t\tvar booking_bike_type = new Array()");
            out.println("\t\t\tvar booking_date = new Array()");
            out.println("\t\t\tvar booking_period = new Array()");
            int i = 0;
            while (dbac.nextRow()==1){
                out.println("\t\t\tbooking_bike_type["+i+"] = '"+ dbac.getResult("bike_type") +"';");
                out.println("\t\t\tbooking_date["+i+"] = '"+ dbac.getResult("booking_date") +"';");
                out.println("\t\t\tbooking_period["+i+"] = '"+ dbac.getResult("booking_period") +"';");
                i++;
            }
            
            
            
            
            out.println("\t\t</script>"); 
            
            out.println("\t\t<form action=\"ProcessOrder\" method=\"POST\">");   
            out.println("\t\t\t<p class=\"text1\">");
            out.println("\t\t\t\tYear:<input class=\"textbox1\" value=\"2013\" type=\"text\" name=\"year\" id=\"year\" maxlength=\"4\" onchange=\"dateYearChange()\">");
            out.println("\t\t\t\tMonth:<input class=\"textbox1\" value=\"1\" type=\"text\" name=\"month\" id=\"month\" maxlength=\"2\" onchange=\"dateMonthChange()\">");
            out.println("\t\t\t\tDay:<input class=\"textbox1\" value=\"1\" type=\"text\" name=\"day\" id=\"day\" maxlength=\"2\" onchange=\"dateDayChange()\">");
            out.println("\t\t\t\tPeriod:<select class=\"dropdown1\" name=\"period\" id=\"period\" onchange=\"dateChange()\"> <option value=\"AM\">AM</option><option value=\"PM\">PM</option><option value=\"ALL\">ALL</option></select>");
            out.println("\t\t\t\tEmail Address:<input class=\"textbox2\" type=\"text\" name=\"email\">");
            out.println("\t\t\t\t<input class =\"button1\" type=\"submit\" value=\"submit\">"); 
            out.println("\t\t\t<p>");
            out.println("\t\t\t<table class=\"box2\">");
            out.println("\t\t\t\t<tr>");
            out.println("\t\t\t\t\t<td class=\"label1\">");
            out.println("\t\t\t\t\t\tBike Type");
            out.println("\t\t\t\t\t</td>");
            out.println("\t\t\t\t\t<td class=\"label1\">");
            out.println("\t\t\t\t\t\tBike Image");
            out.println("\t\t\t\t\t</td>");
            out.println("\t\t\t\t\t<td class=\"label1\">");
            out.println("\t\t\t\t\t\tNumber available");
            out.println("\t\t\t\t\t</td>");
            out.println("\t\t\t\t\t<td class=\"label1\">");
            out.println("\t\t\t\t\t\tNumber selected");
            out.println("\t\t\t\t\t</td>");
            out.println("\t\t\t\t</tr>");
            for (int j = 0;j<bike_types.length;j++){
                out.println("\t\t\t\t<tr>");
                out.println("\t\t\t\t\t<td class=\"text2\">");
                out.println("\t\t\t\t\t\t"+bike_typesp[j]);
                out.println("\t\t\t\t\t</td>");
                out.println("\t\t\t\t\t<td class=\"selectionElement\">");
                out.println("\t\t\t\t\t\t<img class=\"bikePic\" alt=\""+bike_typesp[j]+"\" src=\""+ bike_types[j] +".jpg\">");
                out.println("\t\t\t\t\t</td>");
                out.println("\t\t\t\t\t<td class=\"selectionElement\">");
                out.println("\t\t\t\t\t\t<div class=\"text3\" id=\"numberOf"+bike_types[j]+"\">0</div>");
                out.println("\t\t\t\t\t</td>");
                out.println("\t\t\t\t\t<td class=\"selectionElement\">");
                out.println("\t\t\t\t\t\t<input class=\"textbox3\" type=\"text\" value=\"0\" name=\"selectNumOf"+bike_types[j]+"\">");
                out.println("\t\t\t\t\t</td>");
                out.println("\t\t\t\t</tr>");
            }
    
            out.println("\t\t\t</table>");
            
            
            
            
            out.println("\t\t</form>");
            out.println("\t\t<script>dateChange();</script>");
            
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
