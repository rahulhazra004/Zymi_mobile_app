package com.zymiapp.apps.Model;


import java.util.ArrayList;
import java.util.List;

public class Home_1 {

    private String cat_name;
    private String cat_no;
    private String cat_desc;
    private String tag_name;
    private String tag_name_2;
    private String tag_color;
    private String tag_color_2;
    private String cat_count;
    private String img_url;
    private String ava_rating_cat;
    private String fsatisfy;
    private String imga;
    private String imgb;
    private String imgc;
    private String startingprice;
    private String shipment;
    private ArrayList<String> imageList;

    public Home_1(String cat_name, String cat_no,String cat_count,String tag_name,String tag_name_2,String tag_color,String tag_color_2, String cat_desc,String img_url) {
        this.cat_name = cat_name;
        this.cat_no = cat_no;
        this.cat_count = cat_count;
        this.tag_name = tag_name;
        this.tag_name_2 = tag_name_2;
        this.tag_color = tag_color;
        this.tag_color_2 = tag_color_2;
        this.cat_desc = cat_desc;
        this.img_url=img_url;
    }


    public Home_1(String cat_name, String cat_no,String cat_count,String tag_name,String tag_name_2,String tag_color,String tag_color_2, String cat_desc,String img_url,String fsatisfy) {
        this.cat_name = cat_name;
        this.cat_no = cat_no;
        this.cat_count = cat_count;
        this.tag_name = tag_name;
        this.tag_name_2 = tag_name_2;
        this.tag_color = tag_color;
        this.tag_color_2 = tag_color_2;
        this.cat_desc = cat_desc;
        this.img_url=img_url;
        this.fsatisfy = fsatisfy;
    }
    public Home_1(String cat_name, String cat_no,String cat_count,String tag_name,String tag_name_2,String tag_color,String tag_color_2, String cat_desc,String img_url,String fsatisfy,String imga
            ,String imgb,String imgc,String starting) {
        this.cat_name = cat_name;
        this.cat_no = cat_no;
        this.cat_count = cat_count;
        this.tag_name = tag_name;
        this.tag_name_2 = tag_name_2;
        this.tag_color = tag_color;
        this.tag_color_2 = tag_color_2;
        this.cat_desc = cat_desc;
        this.img_url=img_url;
        this.fsatisfy = fsatisfy;
        this.imga=imga;
        this.imgb=imgb;
        this.imgc=imgc;
        this.startingprice=starting;

    }

    public Home_1(String cat_name, String cat_no,String cat_count,String tag_name,String tag_name_2,String tag_color,String tag_color_2, String cat_desc,String img_url,String fsatisfy,String imga
    ,String imgb,String imgc,String starting,ArrayList<String> images) {
        this.cat_name = cat_name;
        this.cat_no = cat_no;
        this.cat_count = cat_count;
        this.tag_name = tag_name;
        this.tag_name_2 = tag_name_2;
        this.tag_color = tag_color;
        this.tag_color_2 = tag_color_2;
        this.cat_desc = cat_desc;
        this.img_url=img_url;
        this.fsatisfy = fsatisfy;
        this.imga=imga;
        this.imgb=imgb;
        this.imgc=imgc;
        this.startingprice=starting;
        this.imageList=images;
    }


    public Home_1(String cat_name, String cat_no,String cat_count,String tag_name,String tag_name_2,String tag_color,String tag_color_2, String cat_desc,String img_url,String fsatisfy,String imga
            ,String imgb,String imgc,String starting,String Shipment) {
        this.cat_name = cat_name;
        this.cat_no = cat_no;
        this.cat_count = cat_count;
        this.tag_name = tag_name;
        this.tag_name_2 = tag_name_2;
        this.tag_color = tag_color;
        this.tag_color_2 = tag_color_2;
        this.cat_desc = cat_desc;
        this.img_url=img_url;
        this.fsatisfy = fsatisfy;
        this.imga=imga;
        this.imgb=imgb;
        this.imgc=imgc;
        this.startingprice=starting;
        this.shipment=Shipment;
    }


    public Home_1(String cat_name, String cat_no,String cat_count,String tag_name,String tag_name_2,String tag_color,String tag_color_2, String cat_desc,String img_url,String ava_rating_cat,String fsatisfy) {
        this.cat_name = cat_name;
        this.cat_no = cat_no;
        this.cat_count = cat_count;
        this.tag_name = tag_name;
        this.tag_name_2 = tag_name_2;
        this.tag_color = tag_color;
        this.tag_color_2 = tag_color_2;
        this.cat_desc = cat_desc;
        this.img_url=img_url;
        this.ava_rating_cat = ava_rating_cat;
        this.fsatisfy = fsatisfy;
    }


    public String getCat_name() {
        return cat_name;
    }

    public String getCat_no() {
        return cat_no;
    }

    public String getCat_desc() {
        return cat_desc;
    }

    public String getTag_name() {
        return tag_name;
    }

    public String getTag_color() {
        return tag_color;
    }

    public String getCat_count() {
        return cat_count;
    }

    public String getTag_name_2() {
        return tag_name_2;
    }

    public String getTag_color_2() {
        return tag_color_2;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getAva_rating_cat() {
        return ava_rating_cat;
    }

    public String getFsatisfy() {
        return fsatisfy;
    }

    public String getImga() {
        return imga;
    }

    public void setImga(String imga) {
        this.imga = imga;
    }

    public String getImgb() {
        return imgb;
    }

    public void setImgb(String imgb) {
        this.imgb = imgb;
    }

    public String getImgc() {
        return imgc;
    }

    public void setImgc(String imgc) {
        this.imgc = imgc;
    }

    public String getStartingprice() {
        return startingprice;
    }

    public void setStartingprice(String startingprice) {
        this.startingprice = startingprice;
    }

    public String getShipment() {
        return shipment;
    }

    public void setShipment(String shipment) {
        this.shipment = shipment;
    }

    public ArrayList<String> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }
}

