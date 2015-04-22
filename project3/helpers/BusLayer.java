package helpers;

import java.util.*;
import java.sql.SQLException;
import java.text.*;

public class BusLayer{

  private DataLayer dl;

  public BusLayer(){
    Date date = new Date();
    SimpleDateFormat time = new SimpleDateFormat("k");
    int hour= Integer.parseInt(time.format(date));
    //if(hour > 9)
      dl = new DataLayer();
   // else
    //  System.exit(0);
  }//end constructor
  
  public String getToken(String username, String password){
    String token = "";
    ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
    try{
      res = dl.testUser(username);
    }
    catch(SQLException e){
      e.printStackTrace();
    }
    int id = Integer.parseInt(res.get(0).get(0));
    String pw = res.get(0).get(1);
    int age = Integer.parseInt(res.get(0).get(2));
    if(password.equals(pw) && age > 20){
      try{
        dl.deleteToken(id);
      }
      catch(SQLException e){
        e.printStackTrace();
      }
      Date current = new Date();
      BusLayer bl = new BusLayer();
      return bl.createToken(id, current);
    }
    else
      return token;    
  }
  
  public boolean checkToken(String token){
    ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
    Date tokDate = new Date();
    Date current = new Date();
    SimpleDateFormat df = new SimpleDateFormat();
    try{
      res = dl.checkToken(token);
      String tokDateSt = res.get(0).get(0);
      tokDate = df.parse(tokDateSt);
    }
    catch(SQLException e){
      e.printStackTrace();
    }
    catch(ParseException e){
      e.printStackTrace();
    }
    
    if(current.getTime() - tokDate.getTime() > 300000){
      return false;
    }
    else 
      return true;
  }
  
  public String createToken(int id, Date current){
    Random rnd = new Random();
    String token = "";
    for(int i = 0; i < 8; i++){
      int num = rnd.nextInt(9);
      token += Integer.toString(num);
    }
    SimpleDateFormat df = new SimpleDateFormat();   
    try{
      dl.storeToken(id, token, df.format(current));
    }
    catch(SQLException e){
      e.printStackTrace();
    }
    
    return token;
  }
  
  public ArrayList<String> getMethods(){
    ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
    try{
      res = dl.getMethods();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    ArrayList<String> methods = new ArrayList<String>();
    for(ArrayList<String> row : res)
        methods.add(row.get(0));
    return methods;
  }
  
  public double getPrice(String beer){
    ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
    try{
      res = dl.getPrice(beer);
    }
    catch(SQLException e){
      e.printStackTrace();
    }
    return Double.parseDouble(res.get(0).get(0));
  }
  
  public boolean setPrice(String name, double price){
    int res = 0;
    try{
      res = dl.setPrice(name, price);
    }
    catch(SQLException e){
      e.printStackTrace();
    }
    if(res > 0)
      return true;
    else
      return false;
  }
  
  public ArrayList<String> getBeers(){
    ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
    try{
      res = dl.getBeers();
    }
    catch(SQLException e){
      e.printStackTrace();
    }
    ArrayList<String> beers = new ArrayList<String>();
    for(ArrayList<String> beer : res)
      beers.add(beer.get(0));
    return beers;
  }
  
  public String getCheapest(){
    String cheap = "";
    try{
      cheap = dl.getCheapest().get(0).get(0);
    }
    catch(SQLException e){
      e.printStackTrace();
    }
    return cheap;
  }
  
  public String getCostliest(){
    String cost = "";
    try{
      cost = dl.getCostliest().get(0).get(0);
    }
    catch(SQLException e){
      e.printStackTrace();
    }
    return cost;
  }
  
  public static void main(String[] args){
    BusLayer bus = new BusLayer();
   // String token = bus.getToken("ans2759", "alex");
   // System.out.println(token);
    boolean b = bus.checkToken("75780811");
    if(b)
      System.out.println("true");
    else
      System.out.println("false");/*     
    ArrayList<String> res = bus.getMethods();
    for(String method : res)
      System.out.println(method);
      
    System.out.println("\n"+bus.getPrice("Corona"));
    
    System.out.println("\n"+bus.setPrice("Corona", 13.99));
    
    res = bus.getBeers();
    for(String beer : res)
      System.out.println(beer);
    
    System.out.println("\n"+bus.getCheapest());
    
    System.out.println("\n"+bus.getCostliest());
    
    Date d = new Date();
     //SimpleDateFormat format = new SimpleDateFormat();
    
    bus.createToken(1,d );*/
  }

}//end class