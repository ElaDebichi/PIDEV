/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netdev.mindspace.entites;

import java.util.Date;

//import com.codename1.util.DateUtil;

//import java.sql.Date;



/**
 *
 * @author Ela
 */
public class Post {
    private int id;
    private String content;
        private String date;

    private int nblikes=0;
    private int nbreports = 0;
    private int bookmarked = 0;

    public Post(int id, String content, String date) {
        this.id = id;
        this.content = content;
        this.date = date;
    }

    public Post(String content, String date) {
        this.content = content;
        this.date = date;
    }

    public Post(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public Post(String content) {
        this.content = content;
    }

    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNblikes() {
        return nblikes;
    }

    public void setNblikes(int nblikes) {
        this.nblikes = nblikes;
    }

    public int getNbreports() {
        return nbreports;
    }

    public void setNbreports(int nbreports) {
        this.nbreports = nbreports;
    }

    public int getBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(int bookmarked) {
        this.bookmarked = bookmarked;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    

    @Override
    public String toString() {
        return "Post{" + "id=" + id + ", content=" + content + ", nblikes=" + nblikes + ", nbreports=" + nbreports + ", bookmarked=" + bookmarked + '}';
    }
    
    
    
    
    
}
