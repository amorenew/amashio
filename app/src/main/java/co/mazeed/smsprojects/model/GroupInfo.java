package co.mazeed.smsprojects.model;

/**
 * Created by neolptp on 7/14/2015.
 */
import java.util.Comparator;

public class GroupInfo  {
   public  String id;
    public String title;

    @Override
    public String toString() {
        return title+ " ("+id+")";
    }

    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public static Comparator<GroupInfo> GroupNameComparator = new Comparator<GroupInfo>() {

        public int compare(GroupInfo s1, GroupInfo s2) {
            String groupName1 = s1.title.toUpperCase();
            String groupName2 = s2.title.toUpperCase();

            //ascending order
            return groupName1.compareTo(groupName2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};

}