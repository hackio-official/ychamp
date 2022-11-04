package com.hackio.ychamp;

public class historylist {
    private final String url;
    private final int id;
    private final String url_title;

    public historylist(String url_title,String url, int id) {
        this.url = url;
        this.id = id;
        this.url_title=url_title;
    }
    public String getUrl() {
        return url;
    }
    public int getId() {
        return id;
    }
    public String getUrl_title(){return url_title;}


}
