package com.walkingny.lag_arc_mac2.walkingny;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *   A class that shares the same format of the JSON data retrieved from the HTTP response
 */

public class JSONData {

    private double latitude;
    private double longitude;
    private String photoName;
    private String address;
    private String zipCode;
    private String photoID;
    private String desc; //description
    private String colID; //collection ID
    private String copyright;
    private String photoDate;
    private String source;


//------------------------Constructor


    public JSONData(){
        latitude = 0.0;
        longitude = 0.0;
        photoName = "";
        address = "";
        zipCode = "";
        photoID = "";
        desc = "";
        colID = "0";
        copyright = "";
        photoDate = "";
        source = "";
    }

//------------------------Methods

    public void parseData(JSONObject jsonObject){
        try{
            setPhotoName(jsonObject.getString("PhotoName"));
            setLatitude(jsonObject.getDouble("Latitude"));
            setLongitude(jsonObject.getDouble("Longitude"));
            setAddress(jsonObject.getString("Address"));
            setZipCode(jsonObject.getString("Zip"));
            setPhotoID(jsonObject.getString("PHOTOID"));
            setDesc(jsonObject.getString("Caption"));
            setColID(jsonObject.getString("Collection_ID"));
            setCopyright(jsonObject.getString("Copyright"));
            setPhotoDate(jsonObject.getString("Date"));
            setSource(jsonObject.getString("Sorce_Website"));

        }catch (JSONException e){
            Log.e("JSON","null "+e);
        }

    }



//------------------------Getters and Setters


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getAddress() {

        if (address.equals("null")){
            return "N/A";
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhotoID() {
        return photoID;
    }

    public void setPhotoID(String photoID) {
        this.photoID = photoID;
    }

    public String getDesc() {

        if (desc.equals("null")){
            return "N/A";
        }

        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getColID() {
        return colID;
    }

    public void setColID(String colID) {
        this.colID = colID;
    }

    public String getCopyright() {
        if (copyright.equals("null")){
            return "N/A";
        }
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getPhotoDate() {
        return photoDate;
    }

    public void setPhotoDate(String photoDate) {
        this.photoDate = photoDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
