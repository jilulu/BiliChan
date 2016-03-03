package xyz.jilulu.bilifun.helpers;

/**
 * Created by jamesji on 23/2/2016.
 */
public class MuseMember {
    private String museMemberName;
    private int museMemberProfileID;

    public MuseMember(String museMemberName, int museMemberProfileID) {
        this.museMemberName = museMemberName;
        this.museMemberProfileID = museMemberProfileID;
    }

    public String getMuseMemberName() {
        return museMemberName;
    }

    public int getMuseMemberProfileID() {
        return museMemberProfileID;
    }
}
