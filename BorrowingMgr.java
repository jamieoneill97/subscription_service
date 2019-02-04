import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author jamieoneill
 */
public class BorrowingMgr {

    public static ArrayList<String> borrowedItems = new ArrayList<String>();
    public static ArrayList<String> queueItems = new ArrayList<String>();
    public static DefaultListModel listmodel = new DefaultListModel();
    public static DefaultListModel listmodel2 = new DefaultListModel();
    public static ArrayList<String> transactions = new ArrayList<String>();
    public static int transactionID;

    public static Items getSelectedItem(JList list) { //Function finds what user has selected in Jlist

        String selected = list.getSelectedValue().toString(); //Get the selected item in JList
        String[] x = selected.split(","); //Splits the String
        String[] y = new String[3]; //Creates 3 sperate Strings

        //Get the items Info
        String fileName = "databases/itemsList.txt";
        Scanner scan = new Scanner(BorrowingMgr.class.getResourceAsStream(fileName)).useDelimiter(",");
        while (scan.hasNextLine()) { //Scan over items in file
            String line = scan.nextLine();
            if (line.contains(x[0])) {
                y = line.split(","); //Split using , in txt file
            }
        }
        return new Items(y[0], y[1], Integer.parseInt(y[2])); //Return Items
    }

    public static void rtransaction() {

        //Increments transactionID
        String fileName = "databases/transactiondetails.txt";
        Scanner scan = new Scanner(BorrowingMgr.class.getResourceAsStream(fileName)).useDelimiter(",");
        while (scan.hasNextLine()) { //Scan over items in file
            String line = scan.nextLine();
            if (!line.equals("")) {
                transactions.add(line); //Add to arraylist
                transactionID = Integer.parseInt(line.substring(2, 6));
                transactionID++; //Increment ID
            }
        }
    }

    public static void borrowItem() {
        getSelectedItem(OnlineForm.borrowlist); //Uses get selectedItem method 

        String item = OnlineForm.borrowlist.getSelectedValue().toString();
        String ss[] = item.split(",");
        if (Integer.parseInt(ss[2]) == 0) {
            JOptionPane.showMessageDialog(null, "There are no more copies of this item avaliable");
        } else {

            rtransaction(); //Calls the read transaction method

            Users.numberBorrowed++; //Increment Number Borrowed

            //To Stop Users Borrowing 5+ items
            if (Users.numberBorrowed == 5) {
                JOptionPane.showMessageDialog(null, "WARNING! You cannot exceed 5 Items");
                OnlineForm.borrow.setEnabled(false);
                OnlineForm.request.setEnabled(false);
            }
            //To get current date
            DateFormat dateFormat = new SimpleDateFormat(",dd/MM/yyyy");
            Date date = new Date();

            //Elements added to array, ordered
            borrowedItems.add("AB" + transactionID + "," + Users.getID() + ","
                    + Items.getitemName() + "," + Items.decrementAvaliablity()
                    + "," + dateFormat.format(date));

            //Clear list model for error handling
            listmodel.clear();

            for (int i = 0; i < borrowedItems.size(); i++) {
                String[] x = borrowedItems.get(i).split(",");
                listmodel.add(i, x[0] + "," + x[2]); //Display on GUI returns 
            }

            OnlineForm.returnlist.setModel(listmodel); //Set return list

            //Refreshes borrow list
            int selectedIndex;
            selectedIndex = OnlineForm.borrowlist.getSelectedIndex();
            ItemsMgr.model.add(selectedIndex, Items.getID() + "," + Items.getitemName() + "," + Items.decrementAvaliablity());
            ItemsMgr.model.remove(selectedIndex + 1);

            //Write borrowed items to transaction txt file
            try (FileWriter fw = new FileWriter("/UWE/Year2/Design and Analysis"
                    + " of Data Structures and "
                    + "algorithms/Coursework/SubscriptionService/src/databases/"
                    + "transactiondetails.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter pw = new PrintWriter(bw)) {

                //Format which will get wrote to the transactiondetails.txt file
                pw.println("AB" + (transactionID) + "," + Items.getID() + "," + Users.getID()
                        + dateFormat.format(date));

            } catch (IOException x) {
            }
        }
    }

    public static void queueItem() {
        getSelectedItem(OnlineForm.borrowlist); //Uses get selectedItem method 

        String item = OnlineForm.borrowlist.getSelectedValue().toString();
        String ss[] = item.split(",");
        if (Integer.parseInt(ss[2]) == 0) {
            JOptionPane.showMessageDialog(null, "There are no more copies of this item avaliable");
        } else {

            rtransaction(); //Calls the read transaction method

            Users.numberBorrowed++; //Increment Number Borrowed

            //To Stop Users Borrowing 5+ items
            if (Users.numberBorrowed == 5) {
                JOptionPane.showMessageDialog(null, "WARNING! You cannot exceed 5 Items");
                OnlineForm.borrow.setEnabled(false);
                OnlineForm.request.setEnabled(false);
            }
            //To get current date
            DateFormat dateFormat = new SimpleDateFormat(",dd/MM/yyyy");
            Date date = new Date();

            //Elements added to array
            queueItems.add("AB" + transactionID + "," + Users.getID() + ","
                    + Items.getitemName() + "," + Items.itemAvailablity()
                    + "," + dateFormat.format(date));

            //Clear list model for error handling
            listmodel2.clear();

            for (int i = 0; i < queueItems.size(); i++) {
                String[] x = queueItems.get(i).split(",");
                listmodel2.add(i, x[0] + "," + x[2]); //Display on GUI returns 
            }

            OnlineForm.queuing.setModel(listmodel2); //Set return list

            //Write borrowed items to transaction txt file
            try (FileWriter fw = new FileWriter("/databases/queuedList.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter pw = new PrintWriter(bw)) {

                //Format which will get wrote to the queuedItems.txt file
                pw.println("AB" + (transactionID) + "," + Items.getID() + "," + Users.getID()
                        + dateFormat.format(date));

            } catch (IOException x) {
            }

        }
    }

    

    public static void autoReturn() throws ParseException, IOException { //AutoReturn method after 7 days
        rtransaction();
        //For each transaction in arraylist transactions
        for (int i = 0; i < transactions.size(); i++) {
            //Get the date of the transaction as a String
            String[] ss = transactions.get(i).split(",");
            String sDate = ss[3];
            //Convert String to Date
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date transDate = format.parse(sDate);

            //Get the date 7 days ago
            ZonedDateTime now = ZonedDateTime.now();
            ZonedDateTime sevenDaysAgo = now.plusDays(-7);

            //if the transaction date is before seven days ago, remove the object from the arraylist
            if (transDate.toInstant().isBefore(sevenDaysAgo.toInstant())) {
                updateLine(transactions.get(i), "");
            }
        }
    }

    public static void updateLine(String toUpdate, String updated) throws IOException {
        BufferedReader file = new BufferedReader(new FileReader("./src/databases/transactiondetails.txt"));
        String line;
        String input = "";

        while ((line = file.readLine()) != null) {
            input += line + "\n";
        }

        input = input.replace(toUpdate, updated);

        FileOutputStream os = new FileOutputStream("./src/databases/transactiondetails.txt");
        os.write(input.getBytes());

        file.close();
        os.close();
    }

}
