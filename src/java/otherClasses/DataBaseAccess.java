

package otherClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            return false;
        }
              
        return true;        
    }
    
    
    public boolean doQuery(String query){
        try {
            statement.executeQuery(query);
            resultSet = statement.getResultSet();
        } catch (SQLException ex) {
            return false;
        }        
        return true;
    }
    
    public boolean doUpdate(String update){
        try {
            statement.executeUpdate(update);
        } catch (SQLException ex) {
            return false;
        }        
        return true;
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
