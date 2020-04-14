package dtos;

import entities.Movie;

public class MultiplexDto {

    public Integer id;

    public String multiplexname;

    public String address;

    public String numberofscreens;

    public String screename;

    public Movie movie;

    public Integer movieid;

    public String moviename;

    public MultiplexDto() {
    }

    public MultiplexDto(Integer id, String multiplexname, String address, String numberofscreens, String screename, Movie movie) {
        this.id = id;
        this.multiplexname = multiplexname;
        this.address = address;
        this.numberofscreens = numberofscreens;
        this.screename = screename;
        this.movie = movie;
    }

    public MultiplexDto(Integer id, String multiplexname, String address, String numberofscreens, String screename, Movie movie, String moviename) {
        this.id = id;
        this.multiplexname = multiplexname;
        this.address = address;
        this.numberofscreens = numberofscreens;
        this.screename = screename;
        this.movie = movie;
        this.moviename = moviename;
    }

    public MultiplexDto(Integer id, String multiplexname, String address, String numberofscreens, String screename, Movie movie, Integer movieid, String moviename) {
        this.id = id;
        this.multiplexname = multiplexname;
        this.address = address;
        this.numberofscreens = numberofscreens;
        this.screename = screename;
        this.movie = movie;
        this.movieid = movieid;
        this.moviename = moviename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMultiplexname() {
        return multiplexname;
    }

    public void setMultiplexname(String multiplexname) {
        this.multiplexname = multiplexname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumberofscreens() {
        return numberofscreens;
    }

    public void setNumberofscreens(String numberofscreens) {
        this.numberofscreens = numberofscreens;
    }

    public String getScreename() {
        return screename;
    }

    public void setScreename(String screename) {
        this.screename = screename;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Integer getMovieid() {
        return movieid;
    }

    public void setMovieid(Integer movieid) {
        this.movieid = movieid;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    @Override
    public String toString() {
        return "MultiplexDto{" +
                "id=" + id +
                ", multiplexname='" + multiplexname + '\'' +
                ", address='" + address + '\'' +
                ", numberofscreens='" + numberofscreens + '\'' +
                ", screename='" + screename + '\'' +
                ", movie=" + movie +
                ", movieid=" + movieid +
                ", moviename='" + moviename + '\'' +
                '}';
    }
}
