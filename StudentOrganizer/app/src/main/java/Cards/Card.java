package Cards;
import java.util.Date;

/*
    Card is just a wrapper class to contain the few pieces of information all cards have

    This class class could eventually help out with SQL or onClickListeners, but for now it's just a wrapper

    If you take a look at res/layout/activity_card.xml this will make more sense
    -Lucas 10/3/17
 */
public class Card {
    //Any of these could potentially be null except for the title
    private Integer iconID; //Store R.drawable.ID value of an image
    private String title; //NOT NULL
    private String description;
    private Date dueDate;

    //No empty constructor b/c title cannot be null

    public Card(String title){
        this.setTitle(title);
    }

    public Card(String title, String description, Date dueDate, Integer iconID){
        this.setTitle(title);
        this.setDescription(description);
        this.setDueDate(dueDate);
        this.setIconID(iconID);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        //Think about how confusing it would be to the user to have a title-less title! Denied!
        if(title == null || title.equals("")){
            throw new IllegalArgumentException("Card title cannot be null or empty");
        }
        this.title = title;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getIconID() {
        return iconID;
    }

    public void setIconID(Integer iconID) {
        this.iconID = iconID;
    }
}
