package Cards;

import android.widget.LinearLayout;
import javax.sql.DataSource;

/**
 * The page class is meant to be a one-stop-shop for page information.
 *  RULE OF THUMB: Every item contained in the home spinner has a Page instance
 *  + Pull data from database
 *  + Draw nodes on screen with data
 *  + Update database if needed
 */
public class Page {
    DataSource pageData;
    LinearLayout nodeContainer;
    int primaryKey;

    //If the page is not in the database, we will add it later
    //We should keep primary keys in some sort of VALUES.xml file
    public Page(int primaryKey, LinearLayout container){

        //Don't allow someone to give me an empty prmary key
        //(Since int is a primitive that cannot be null n' stuff)
        if(primaryKey == 0){
            throw new IllegalArgumentException("Primary key cannot be zero.");
        }

        this.primaryKey = primaryKey;
        this.nodeContainer = container;

        //GO ahead a pull our nodes from SQL
        //If this affects performance, we could potentially move this call to drawElementsToScreen();
        getDataFromSQL();
    }

    //TODO
    //Use primary key we passed in from the constructor
    private void getDataFromSQL(){
        throw new UnsupportedOperationException("We haven't added the database yet");
    }

    public void drawElementsToScreen(){
        /*
        ForEach node in this.PageData{
            this.nodeContainer.addView(node);
        }
        */
    }
}
