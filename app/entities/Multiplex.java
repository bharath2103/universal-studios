package entities;

import javax.persistence.*;

@Entity
@Table(name = "multiplex")
public class Multiplex {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public String multiplexname;

    public String address;

    public String numberofscreens;

    public String screename;

    @OneToOne
    public Movie movie;

    public Multiplex() {
    }


/*    public Multiplex(Integer id, String multiplexname, String address, String numberofscreens, String screename) {
        this.id = id;
        this.multiplexname = multiplexname;
        this.address = address;
        this.numberofscreens = numberofscreens;
        this.screename = screename;
    }*/

    public Multiplex(Integer id, String multiplexname, String address, String numberofscreens, String screename, Movie movie) {
        this.id = id;
        this.multiplexname = multiplexname;
        this.address = address;
        this.numberofscreens = numberofscreens;
        this.screename = screename;
        this.movie = movie;
    }


    public Multiplex(String multiplexname, String address, String numberofscreens, String screename, Movie movie) {
        this.multiplexname = multiplexname;
        this.address = address;
        this.numberofscreens = numberofscreens;
        this.screename = screename;
        this.movie = movie;
    }

    /* Use this constructor when an Multiplex entity is saved without Movie entity */
    public Multiplex(String multiplexname, String address, String numberofscreens, String screename) {
        this.multiplexname = multiplexname;
        this.address = address;
        this.numberofscreens = numberofscreens;
        this.screename = screename;
    }

/*    public Multiplex(String multiplexname, String address, String numberofscreens, String screename) {
        this.multiplexname = multiplexname;
        this.address = address;
        this.numberofscreens = numberofscreens;
        this.screename = screename;
    }*/

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

    @Override
    public String toString() {
        return "Multiplex{" +
                "id=" + id +
                ", multiplexname='" + multiplexname + '\'' +
                ", address='" + address + '\'' +
                ", numberofscreens='" + numberofscreens + '\'' +
                ", screename='" + screename + '\'' +
                ", movie=" + movie +
                '}';
    }
}


