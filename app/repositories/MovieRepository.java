package repositories;

import entities.Movie;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Function;

@Singleton
public class MovieRepository {

    @Inject
    private JPAApi jpaApi;

    private <T> T wrap(Function<EntityManager, T> function){
        return this.jpaApi.withTransaction(function);
    }


    @Transactional
    public Movie insert(Movie movie){
        jpaApi.withTransaction(entityManager -> {
            entityManager.persist(movie);
        });
        return movie;
    }

    public List<Movie> list(){
        return this.wrap(entityManager -> {
            List<Movie> movies =  entityManager.createQuery("select m from Movie m ", Movie.class).getResultList();
            return movies;
        });
    }

    public boolean delete(Integer movieId){
        jpaApi.withTransaction(entityManager -> {
            Movie movie = entityManager.find(Movie.class,movieId);
            entityManager.remove(movie);
            return true;
        });
        return false;
    }

    public Movie findById(Integer movieId){
        return this.wrap(entityManager -> {
            Movie movie =  entityManager.find(Movie.class, movieId);
            return movie;
        });
    }

    public Movie findByName(String movieName){
        return this.wrap(entityManager -> {
            String queryString = "select m from Movie m where m.name = '"+movieName+"'";
            Movie movie =  entityManager.createQuery(queryString, Movie.class).getSingleResult();
            return movie;
        });
    }
}
