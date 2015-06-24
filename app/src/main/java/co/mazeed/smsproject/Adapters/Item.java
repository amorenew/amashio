package co.mazeed.smsproject.Adapters;

/**
 * Created by amorenew on 6/24/2015.
 */
public class Item {
    public static final int ITEM = 0;
    public static final int SECTION = 1;

    public final int type;
    public final String text;

    public int sectionPosition;
    public int listPosition;

    public Item(int type, String text) {
        this.type = type;
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
