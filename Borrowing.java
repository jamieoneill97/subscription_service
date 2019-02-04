/**
 *
 * @author jamieoneill
 */
public class Borrowing {

    //Variables for booking class
    private String borrowingID;
    private String date;
    private String userID;
    private String itemID;

    public Borrowing(String borrowingID, String date, String userID, String itemID) {
        this.borrowingID = borrowingID;
        this.date = date;
        this.userID = userID;
        this.itemID = itemID;

    }

    public String getBorrowingID() {
        return this.borrowingID;
    }

    public void setBookingID(String result) {
        this.borrowingID = result;
    }
    
    public String getdate(){
        return this.getdate();
    }
    
    public String getuserID(){
        return this.getuserID();
    }
    
    public String itemID(){
        return this.itemID;
    }
    
    @Override
    public String toString() {
        return "\nBorrowing No: "+ borrowingID + "\nDate: " + date +
                "\nUserID: " + userID + "\nItemID: " + itemID;
    }

}


