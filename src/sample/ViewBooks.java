package sample;

/**
 * Created by Bhavesh on 7/16/2017.
 */
public class ViewBooks {
    String Bname;
    String Bauthor;
    String Bpublisher;
    String Bquant;

    public ViewBooks(String name, String author, String publisher, String quant) {
        Bname = name;
        Bauthor = author;
        Bpublisher = publisher;
        Bquant = quant;
    }

    public String getBname(){
        return Bname;
    }

    public String getBauthor(){
        return Bauthor;
    }

    public String getBpublisher(){
        return Bpublisher;
    }

    public String getBquant(){
        return Bquant;
    }



}
