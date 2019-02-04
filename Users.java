import java.util.ArrayList;

/**
 *
 * @author jamieoneill
 */
public class Users {

    private static String ID;
    private static String username;
    private static String password;
    public static int numberBorrowed = 0;
    public static ArrayList<Integer> userrequestedlist = new ArrayList<Integer>();
    
    
    public Users(String ID, String username, String password) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.numberBorrowed = numberBorrowed;
        
    }

    public static Integer getID() { //Make static to display ID on onlineform GUI page
        return Integer.parseInt(ID);
    }

    public void setID() {
        this.ID = ID;
    }

    public static String getusername() { //Make static to display username on onlineform GUI page
        return username;
    }

    public void setusername() {
        this.username = username;
    }

    public static String getpassword() {
        return password;
    }

    public void setpassword() {
        this.password = password;
    }
     
    @Override
    public String toString() {
        return "\nID: " + ID + "\nUsername: " + "\nPassword: " + password;
    }

}
