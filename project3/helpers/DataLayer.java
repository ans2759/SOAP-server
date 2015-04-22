package helpers;

import java.util.*;
import java.sql.SQLException;
import java.text.*;

// Make sure mysql-connector-java-5.1.27-bin.jar is in the classpath
// For glassfish, needs to be in the glassfish/lib folder
// Need to add user and grant access to beerprices

public class DataLayer
{
   private DatabaseAccess db;
   
   public DataLayer()
   {
      try
      {
         String dbName = "beerprices";
         String user = "test";
         String pswd = "testing";
         String host = "localhost";
         String port = "3306";
         
         // Create an object of the utility class that you will use to do your queries
         db = new DatabaseAccess(dbName, user, pswd, host, port);
      }
      catch(SQLException e)
      {
         e.printStackTrace();
      }
      catch(ClassNotFoundException e)
      {
         e.printStackTrace();
      }
   }
   
   public ArrayList<ArrayList<String>> testUser (String username) throws SQLException{
     String query = "SELECT id, password, age FROM users WHERE username = ?";
     ArrayList<String> params = new ArrayList<String>();
     params.add(username);
     return db.getDataPS(query, params);
   }
   
   public void storeToken(int id, String token, String exp) throws SQLException{
     String query = "INSERT INTO token SET tok = ?, userId = ?, exp = ?";
     String idSt = Integer.toString(id);
     ArrayList<String> params = new ArrayList<String>();
     params.add(token);
     params.add(idSt);
     params.add(exp);
     
     db.nonSelect(query, params);
   }
   
   public ArrayList<ArrayList<String>> getToken (int id)throws SQLException{
     String query = "SELECT tok, exp FROM token WHERE userId = ?";
     ArrayList<String> params = new ArrayList<String>();
     params.add(Integer.toString(id));
     return db.getDataPS(query, params);
   }
   
   public ArrayList<ArrayList<String>> checkToken(String token)throws SQLException{
     String query = "SELECT exp FROM token WHERE tok = ?";
     ArrayList<String> params = new ArrayList<String>();
     params.add(token);
     return db.getDataPS(query, params);
   }
   
   public void deleteToken(int id)throws SQLException{
     String query = "DELETE FROM token WHERE userId = ?";
     ArrayList<String> params = new ArrayList<String>();
     params.add(Integer.toString(id));
     db.nonSelect(query, params);
   }
   
   public ArrayList<ArrayList<String>> getMethods()throws SQLException {
     String query = "SELECT method FROM methods";
     return db.getData(query);
   }
   
   public ArrayList<ArrayList<String>> getPrice(String beer)throws SQLException{
     String query = "SELECT beerprice FROM beers WHERE beername = ?";
     ArrayList<String> params = new ArrayList<String>();
     params.add(beer);
     return db.getDataPS(query, params);
   }
   
   public int setPrice(String beer, double price)throws SQLException{
     String query = "UPDATE beers SET beerprice = ? WHERE beername  = ?";
     ArrayList<String> params = new ArrayList<String>();
     String priceSt = Double.toString(price);
     params.add(priceSt);
     params.add(beer);
     return db.nonSelect(query, params);     
   }
   
   public ArrayList<ArrayList<String>> getBeers() throws SQLException{
     String query = "SELECT beername FROM beers";
     return db.getData(query);
   }
   
   public ArrayList<ArrayList<String>> getCheapest() throws SQLException{
     String query = "SELECT beername, beerprice FROM beers ORDER BY beerprice LIMIT 1";
     return db.getData(query);
   }
   
   public ArrayList<ArrayList<String>> getCostliest() throws SQLException{
     String query = "SELECT beername, beerprice FROM beers ORDER BY beerprice desc LIMIT 1";
     return db.getData(query);
   }
   
   public static void main(String[] args) throws SQLException, ClassNotFoundException{
     DataLayer dl = new DataLayer();
     
     Date date = new Date();
     SimpleDateFormat format = new SimpleDateFormat();
     
     
     //dl.storeToken(1, "abcdefg", format.format(date) );
       /*  
     ArrayList<ArrayList<String>> res = dl.getMethods();
     
     for(ArrayList<String> row : res){
       System.out.println(row + " : " + row.get(0));
     }
     
     int updated = dl.setPrice("Bud", 10.49);
     if(updated > 0)
       System.out.println("\nSuccess");
     else
       System.out.println("\nFail");
     
     res = dl.getPrice("Bud");
     System.out.println("\nBud : " + res.get(0).get(0));
     
     res = dl.getBeers();
     for(ArrayList<String> row : res){
       System.out.println(row + " : " + row.get(0));
     }
     
     res = dl.getCheapest();
     System.out.println(res.get(0).get(0) + " : " + res.get(0).get(1));
     
     res = dl.getCostliest();
     System.out.println(res.get(0).get(0) + " : " + res.get(0).get(1));
     */
     
     ArrayList<ArrayList<String>> res = dl.checkToken("82223153");
     String dateSt = res.get(0).get(0);
     try{
     date = format.parse(dateSt);
     }
     catch(ParseException e){
     e.printStackTrace();
     }
     Date newDate = new Date();
     System.out.println(date.getTime() - newDate.getTime());   
     
          
   }
   
   
} //class