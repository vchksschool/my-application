package com.mtc.top5;

public class CategoryModel {
    private String docID;
    private String name;
    private int noOfTests;
    private int catNumDB;

    public int getCatNumDB() {
        return catNumDB;
    }

    public void setCatNumDB(int catNumDB) {
        this.catNumDB = catNumDB;
    }

    public CategoryModel(String docID, String name, int noOfTests, int catNumDB) {
        this.docID = docID;
        this.name = name;
        this.noOfTests = noOfTests;
        this.catNumDB =catNumDB;
    }


    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoOfTests() {
        return noOfTests;
    }

    public void setNoOfTests(int noOfTests) {
        this.noOfTests = noOfTests;
    }
}
