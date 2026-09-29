package ue.edu.co.pokenavigation.data.model;

public class Pokemon {
    private String name;
    private String url;

    public Pokemon() {
    }

    public Pokemon(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}