package schoolmanagementsystem;

import java.sql.*;
import java.sql.Connection;

public class JDBconnector {
    //jdbc-url
    //jdbc-username
    //jdbc-password
    private static final String url= "jdbc:postgresql://localhost:5432/postgres";
    private static final String user = "postgres";
    private static final String password= "gn1234";
    static Connection con=null;
    public static Connection get_connection(){
        try{
            Class.forName("org.postgresql.Driver");

            con= DriverManager.getConnection(url, user, password);
            if(con!=null){
                System.out.println("Connected to testdb!");}
            else {
                System.out.println("OOPS! something went wrong");

            }
        }catch(ClassNotFoundException e){
            System.out.println("PostgreSQL JDBC Driver not found");
            e.printStackTrace();
        }
        catch(SQLException e){
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
        return con;    }
    public static void main(String[] args) {
        JDBconnector sqlConnect=new JDBconnector();
        sqlConnect.get_connection();

    }
}

