package com.quaticstech.sstcapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Categ_list implements Parcelable{
    private String rid;
    private String serv_uid;
    private String serv_image;
    private String serv_name;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getServ_uid() {
        return serv_uid;
    }

    public void setServ_uid(String serv_uid) {
        this.serv_uid = serv_uid;
    }

    public String getServ_image() {
        return serv_image;
    }

    public void setServ_image(String serv_image) {
        this.serv_image = serv_image;
    }

    public String getServ_name() {
        return serv_name;
    }

    public void setServ_name(String serv_name) {
        this.serv_name = serv_name;
    }

    public static Creator<Categ_list> getCREATOR() {
        return CREATOR;
    }

    protected Categ_list(Parcel in) {
        rid = in.readString();
        serv_uid = in.readString();
        serv_image = in.readString();
        serv_name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rid);
        dest.writeString(serv_uid);
        dest.writeString(serv_image);
        dest.writeString(serv_name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Categ_list> CREATOR = new Creator<Categ_list>() {
        @Override
        public Categ_list createFromParcel(Parcel in) {
            return new Categ_list(in);
        }

        @Override
        public Categ_list[] newArray(int size) {
            return new Categ_list[size];
        }
    };
}
