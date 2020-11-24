package edu.temple.webbrowserapp;

//Bookmart struct
public class TBookmark {
    private int igID;
    private String sgTitle;
    private String sgURL;

    public TBookmark(){
    }

    public int setVal(int iID,String sTitle, String sURL){
        this.igID=iID;
        this.sgTitle=sTitle;
        this.sgURL=sURL;
        return 0;
    }

    public TBookmark getVal(){
        TBookmark rtn=new TBookmark();
        rtn.setVal(this.igID,this.sgTitle,this.sgURL);
        return rtn;
    }

    public int getID(){
        return this.igID;
    }

    public String getTitle(){
        return  this.sgTitle;
    }

    public String getURL(){
        return this.sgURL;
    }
}
