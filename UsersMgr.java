import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jamieoneill
 */
public class UsersMgr {

    //For login userdetails
    private static ArrayList<Users> users = new ArrayList<Users>();
    public static ArrayList<String> userDetails = new ArrayList<String>();
    private String itemID;

    public static void getusers(String usersID) {
        String fileName = "databases/userdetails.txt";
        Scanner scan = new Scanner(UsersMgr.class.getResourceAsStream(fileName)).useDelimiter(",");

        userDetails.clear(); //Clear arraylist for error handling

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (line.contains(usersID)) {
                String[] x = line.split(","); //Splits string
                for (int i = 0; i < x.length; i++) {
                    userDetails.add(x[i]);
                }

                //Assign details to variables 
                String ID = userDetails.get(0);
                String name = userDetails.get(1);
                String password = userDetails.get(2);

                //Add new users
                users.add(new Users(ID, name, password));

            }
        }
    }

    public static void signIn() {
        getusers(Login.id.getText()); //Gets users ID from above method
        if (Users.getpassword().equals(String.valueOf(Login.password.getPassword()))) {

            new OnlineForm().setVisible(true);

            //Display Name and ID on GUI
            OnlineForm.id.setText("ID: " + userDetails.get(0));
            OnlineForm.name.setText("Name: " + userDetails.get(1));
        } else {
            try {
                //If details don't match
                JOptionPane.showMessageDialog(null, "Sorry incorrect details!");
                new Login().setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(UsersMgr.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void signUp() {
        try {
            //Gets text from text fields on signup page, and stores to variables
            String ID = SignUp.cid.getText();
            String name = SignUp.cusername.getText();
            String password = SignUp.cpassword.getText();

            //Writes userdetails to database, using filewriter
            try (FileWriter fw = new FileWriter("/Users/jamieoneill/Documents/Year 2/"
                    + "Design and Analysis of Data Structures and algorithms/Coursework/"
                    + "SubscriptionService/src/databases/userdetails.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter pw = new PrintWriter(bw)) {
                //Variables with assigned text gets written to the file
                pw.println(ID + "," + name + "," + password + ",");
            } catch (IOException x) {
            }
            //Allows program to go back to homepage, for (Error handling)
            Login.runProgram();
        } catch (IOException ex) {
            Logger.getLogger(UsersMgr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}

