package edu.temple.webbrowserapp;


import android.os.Parcel;
import android.os.Parcelable;



public class TWebPage implements Parcelable {

    private String sgTitle;
    private PageViewerFragment pvfgWeb;

    public TWebPage(Parcel in){
        super();
//        this.sgTitle = in.readString();
//        this.pvfgWeb = in。readTypedObject(PageViewerFragment);
    }

    public static final Parcelable.Creator<TWebPage> CREATOR = new Parcelable.Creator<TWebPage>() {
        public TWebPage createFromParcel(Parcel in) {
            return new TWebPage(in);
        }

        public TWebPage[] newArray(int size) {
            return new TWebPage[size];
        }

    };

    public TWebPage() {

    }

    public String getTitle(){
        return sgTitle;
    }

    public PageViewerFragment getPageViewerFragment(){
        return pvfgWeb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
