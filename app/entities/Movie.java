package entities;

import javax.persistence.*;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public String name;

    public String category;

    public String producer;

    public String director;

    public String releasedate;

/*    @OneToOne
    public Multiplex multiplex;*/

    public Movie() {
    }

    public Movie(Integer id, String name, String category, String producer, String director, String releasedate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.producer = producer;
        this.director = director;
        this.releasedate = releasedate;
    }

/*    public Movie(Integer id, String name, String category, String producer, String director, String releasedate, Multiplex multiplex) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.producer = producer;
        this.director = director;
        this.releasedate = releasedate;
        this.multiplex = multiplex;
    }*/

    public Movie(String name, String category, String producer, String director, String releasedate) {
        this.name = name;
        this.category = category;
        this.producer = producer;
        this.director = director;
        this.releasedate = releasedate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

/*
    public Multiplex getMultiplex() {
        return multiplex;
    }

    public void setMultiplex(Multiplex multiplex) {
        this.multiplex = multiplex;
    }
*/

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", producer='" + producer + '\'' +
                ", director='" + director + '\'' +
                ", releasedate='" + releasedate + '\'' +
                '}';
    }
}
