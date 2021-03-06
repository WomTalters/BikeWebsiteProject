

package otherClasses;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 *
 * @author Tom
 */
public class DataBaseAccess {
    
    
    String DatabaseURL = "jdbc:postgresql:postgres";
    String DatabaseUserName = "postgres";
    String DatabasePassword = "hello";
    
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    PrintWriter p;
    
    public DataBaseAccess(PrintWriter p){
        this.p = p;
    }
    
    
    
    public boolean makeConnection(){
        
        try {
            Class.forName("org.postgresql.Driver");           
        }catch (ClassNotFoundException ex){
            
            return false;
        }
        
        try {
            connection = DriverManager.getConnection(DatabaseURL,DatabaseUserName,DatabasePassword);
            statement = connection.createStatement();
        }catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
              
        return true;        
    }
    
    
    public boolean doQuery(String query){
        if (statement==null){
            return false;
        }
        try {
            statement.executeQuery(query);
            resultSet = statement.getResultSet();
        } catch (SQLException ex) {
            ex.printStackTrace(p);
            p.println("<br/>--------------------------<br/>");
            p.println(query);
            p.println("<br/>--------------------------<br/>");
            return false;
        }        
        return true;
    }
    
    public boolean doUpdate(String update){
        if (statement==null){
            p.println("yo");
            return false;
        }
        
        try {
            statement.executeUpdate(update);  
        } catch (SQLException ex) {
            ex.printStackTrace(p);
            p.println("<br/>--------------------------<br/>");
            p.println(update);
            p.println("<br/>--------------------------<br/>");
            return false;
        }        
        return true;
    }
    
    public int nextRow(){
        if (resultSet==null){
            return 0;
        }
        
        try {
            if(resultSet.next()){
                return 1;
            }else{
                return 3;
            }
        } catch (SQLException ex) {
            return 2;
        }        
        
    }
    
    public String getResult(String column){
        if (resultSet==null){
            return null;
        }
        try {
            return resultSet.getString(column);
            
        } catch (SQLException ex) {
            return null;
        }
        
    }
    
    
    public boolean closeConnection(){
        try {
            connection.close();
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }
    
    
    
}
