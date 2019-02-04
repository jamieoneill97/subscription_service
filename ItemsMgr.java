import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.DefaultListModel;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jamieoneill
 */
public class ItemsMgr {

    public static DefaultListModel model = new DefaultListModel();
    private static ArrayList<Items> Items = new ArrayList<Items>();
    private static ArrayList<String> iteminfo = new ArrayList<String>();

    public static ArrayList<Items> getItems() {

        String fileName = "databases/itemsList.txt"; 
        Scanner scan = new Scanner(ItemsMgr.class.getResourceAsStream(fileName))
                .useDelimiter(",");
        int index = 0;

        while (scan.hasNextLine()) { //Scan over database
            iteminfo.clear();
            String line = scan.nextLine();
            String[] x = line.split(","); //Split String

            for (int i = 0; i < x.length; i++) {
                iteminfo.add(x[i]);
            }

            //Variables getting asigned Items
            String ID = iteminfo.get(0); //Number = Position in array
            String Itemname = iteminfo.get(1);
            int itemAvailablity = Integer.parseInt(iteminfo.get(2));
            
            //Adding elements to JList
            model.add(index, ID + "," + Itemname + "," + itemAvailablity); //Format GUI display borrow
            index++; //increment index
        }
        
        //Set Model
        OnlineForm.borrowlist.setModel(model); //Borrow list getting set

        //Return the items
        return Items;
    }


    public static void searchItems() { //Search method on GUI
        //Clear model
        model.clear();
        String searching = OnlineForm.searchBar.getText(); //Gets text from search bar
        if (searching.equals("")) {
            getItems(); //If users doesnt enter return all items
        } else {
            String fileName = "databases/itemsList.txt";
            Scanner scan = new Scanner(ItemsMgr.class.getResourceAsStream(fileName)).useDelimiter(",");
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (line.contains(searching)) { //
                    model.add(0, line);
                }
            }
             OnlineForm.borrowlist.setModel(model); //Sets the model
        }
    }

}
