package sample;

/**
 * Created by Bhavesh on 7/16/2017.
 */
public class ViewRecords {
    public String Lname;
    public String Lpass;

    public ViewRecords(String name, String pass){
        Lname = name;
        Lpass = pass;
    }
    public String getLname(){
        return Lname;
    }

    public void setLname(String name){
        Lname = name;
    }
    public String getLpass(){
        return Lpass;
    }

    public void setLpass(String pass){
        Lpass = pass;
    }
}
