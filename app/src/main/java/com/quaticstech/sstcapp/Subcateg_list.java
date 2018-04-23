package com.quaticstech.sstcapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Subcateg_list implements Parcelable {
    private String rid;
    private String serv_uid;
    private String serv_selection_uid;
    private String serv_selection_name;
    private String serv_amt;
private String service_categ_name;

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

    public String getServ_selection_uid() {
        return serv_selection_uid;
    }

    public void setServ_selection_uid(String serv_selection_uid) {
        this.serv_selection_uid = serv_selection_uid;
    }

    public String getServ_selection_name() {
        return serv_selection_name;
    }

    public void setServ_selection_name(String serv_selection_name) {
        this.serv_selection_name = serv_selection_name;
    }

    public String getServ_amt() {
        return serv_amt;
    }

    public void setServ_amt(String serv_amt) {
        this.serv_amt = serv_amt;
    }

    public String getService_categ_name() {
        return service_categ_name;
    }

    public void setService_categ_name(String service_categ_name) {
        this.service_categ_name = service_categ_name;
    }

    public static Creator<Subcateg_list> getCREATOR() {
        return CREATOR;
    }

    private Subcateg_list(Parcel in) {
        rid = in.readString();
        serv_uid = in.readString();
        serv_selection_uid = in.readString();
        serv_selection_name = in.readString();
        serv_amt = in.readString();
        service_categ_name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rid);
        dest.writeString(serv_uid);
        dest.writeString(serv_selection_uid);
        dest.writeString(serv_selection_name);
        dest.writeString(serv_amt);
        dest.writeString(service_categ_name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Subcateg_list> CREATOR = new Creator<Subcateg_list>() {
        @Override
        public Subcateg_list createFromParcel(Parcel in) {
            return new Subcateg_list(in);
        }

        @Override
        public Subcateg_list[] newArray(int size) {
            return new Subcateg_list[size];
        }
    };
}
