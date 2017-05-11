import java.util.ArrayList;

/**
 *
 * @author James Dunlap
 */
public class account {
    public ArrayList orders = new ArrayList();
    private String name;
    private String pass;
    
    public account(String n,String p){name = n;pass = p;}

    public String getName(){return name;}
    public String getPassword(){return pass;}
    public ArrayList getOrders(){return orders;}
    
    public void setName(String name){this.name = name;}
    public void setOrders(ArrayList orders){this.orders = orders;}
    public void setPassword(String pass){this.pass = pass;} 
}
