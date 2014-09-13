package com.mcnallydevelopers.android.apps.encartelera;

/**
 * Created by paulomcnally on 9/13/14.
 */
public class MoviesList {
    private String id;
    private String name;
    private String front;
    private String synopsis;
    private String genres;
    private String clasification;
    private String duration;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFront() {
        return this.front;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getSynopsis() {
        return this.synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getGenres() {
        return this.genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getClasification() {
        return this.clasification;
    }

    public void setClasification(String clasification) {
        this.clasification = clasification;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
