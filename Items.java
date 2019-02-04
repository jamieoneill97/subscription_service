/**
 *
 * @author jamieoneill
 */
public class Items {

    //Variables declared for Items
    public static String ID;
    private static String itemName;
    private static int itemAvailablity;
    private static int numberAvaliable;
    
  
    public Items(String ID, String itemName, int itemAvailablity) {
        this.ID = ID;
        this.itemName = itemName;
        this.itemAvailablity = itemAvailablity;
    }

    public static Integer getID() { //Make static to get borrowing method working
        return Integer.parseInt(ID);
    }

    public void setID() {
        this.ID = ID;
    }

    public static String getitemName() {
        return itemName;
    }

    public void setitemName() {
        this.itemName = itemName;
    }

    public static int itemAvailablity() {
        return itemAvailablity;
    }
    

    public static int decrementAvaliablity() {
        //Get the availablity
        String selected;
        selected = OnlineForm.borrowlist.getSelectedValue().toString();
        String[] x = selected.split(",");
        numberAvaliable = Integer.parseInt(x[2]);
        numberAvaliable--;
        return numberAvaliable;
    }

    @Override
    public String toString() {
        return "\nItems: " + "\nID: " + ID + "\nName: " + itemName + 
                "\nAvailblity: " + itemAvailablity;
    }

}
