package server;

//java API for XML Web Services (JAX-WS)
import javax.jws.*;
import java.util.*;
import helpers.*;

//no exception handling (bad code)

//defaults to classnameService if not specified
@WebService(serviceName="BeerService")
public class Beer{
  
  private BusLayer bus;
  
  public Beer(){
    bus = new BusLayer();
  }

  //defaults to method name if not specified
  //@WebMethod(operationName="SpanishHello")
  public String getToken(String username, String password){
    String token = bus.getToken(username, password);
    
    return token;
  }
  
  //@WebMethod(operationName="FrenchHello")
  public ArrayList<String> getMethods(){
    ArrayList<String> methods = bus.getMethods();
    
    return methods;
  }
  
  public double getPrice(String beer, String token){
    double price = 0;
    if(bus.checkToken(token)){
      return bus.getPrice(beer);
    }
    else
      return price;
  }
  
  //won't be exposed in the WSDL (default is false)
  //@WebMethod(exclude=true)
  public boolean setPrice(String beer, double price, String token){
    boolean success = false;
    
    return success;
  }
  
  public ArrayList<String> getBeers(String token){
    ArrayList<String> beers = new ArrayList<String>();
    
    return beers;
  }
  
  public String getCheapest(String token){
    String beer = new String();
    
    return beer;
  }
  
  public String getCostliest(String token){
    String beer = new String();
    
    return beer;
  }
  
}// end beer class