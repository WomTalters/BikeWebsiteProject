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
            out.println("<head>");
            out.println("<title>TC bike hire</title>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");  
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"stylesheet.css\">"); 
            out.println("<script src=\"hps.js\"></script>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1 class=\"heading\" id=\"fred\">TC Bike Hire</h1>");
            
            if (!dbac.makeConnection()){
                out.println("<h2>Could not connect to the database :(</h2>");                        
                out.println("</body>");
                out.println("</html>");
                return;
            }
            
            String [] bike_types = {"womens_mtb","mens_mtb","childs_mtb","womens_hybrid","mens_hybrid","tandem"};
            String [] bike_typesp = {"Womens mountain bike","Mens mountain bike","Childs mountain bike","Womens hybrid","Mens hybrid","Tandem"};
            
            out.println("<script>");
            
            
            out.println("var numBikeType = new Array();");
            out.println("var bikeType = new Array();");
            for (int j = 0;j<bike_types.length;j++){
               dbac.doQuery("select count(bike_type) from bike where bike_type='"+bike_types[j]+"';");
               dbac.nextRow();
               out.println("bikeType["+j+"] = '"+ bike_types[j] +"';");
               out.println("numBikeType["+j+"] = "+ Integer.parseInt(dbac.getResult("count")) +";");                                 
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
            
            
            
            
            out.println("</script>"); 
            
            out.println("<form action=\"ProcessOrder\" method=\"POST\">");   
            out.println("<p class=\"text1\">");
            out.println("Year:<input class=\"textbox1\" value=\"2013\" type=\"text\" name=\"year\" id=\"year\" maxlength=\"4\" onchange=\"dateYearChange()\">");
            out.println("Month:<input class=\"textbox1\" value=\"1\" type=\"text\" name=\"month\" id=\"month\" maxlength=\"2\" onchange=\"dateMonthChange()\">");
            out.println("Day:<input class=\"textbox1\" value=\"1\" type=\"text\" name=\"day\" id=\"day\" maxlength=\"2\" onchange=\"dateDayChange()\">");
            out.println("Period:<select class=\"dropdown1\" name=\"period\" id=\"period\" onchange=\"dateChange()\"> <option value=\"AM\">AM</option><option value=\"PM\">PM</option><option value=\"ALL\">ALL</option></select>");
            out.println("Email Address:<input class=\"textbox2\"type=\"text\" name=\"email\">");
            out.println("<input class =\"button1\"type=\"submit\" value=\"submit\"><p>"); 
            out.println("<table class=\"box2\">");
            out.println("<tr>");
            out.println("<td class=\"label1\">");
            out.println("Bike Type");
            out.println("</td>");
            out.println("<td class=\"label1\">");
            out.println("Bike Image");
            out.println("</td>");
            out.println("<td class=\"label1\">");
            out.println("Number available");
            out.println("</td>");
            out.println("<td class=\"label1\">");
            out.println("Number selected");
            out.println("</td>");
            out.println("</tr>");
            for (int j = 0;j<bike_types.length;j++){
                out.println("<tr>");
                out.println("<td class=\"text2\">");
                out.println(bike_typesp[j]);
                out.println("</td>");
                out.println("<td class=\"selectionElement\">");
                out.println("<img class=\"bikePic\" src=\""+ bike_types[j] +".jpg\">");
                out.println("</td>");
                out.println("<td class=\"selectionElement\">");
                out.println("<text class=\"text3\" id=\"numberOf"+bike_types[j]+"\">0</text>");
                out.println("</td>");
                out.println("<td class=\"selectionElement\">");
                out.println("<input class=\"textbox3\" type=\"text\" value=\"0\" name=\"selectNumOf"+bike_types[j]+"\">");
                out.println("</td>");
                out.println("</tr>");
            }
    
            out.println("</table>");
            
            
            
            
            out.println("</form>");
            out.println("<script>dateChange();</script>");
            
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
