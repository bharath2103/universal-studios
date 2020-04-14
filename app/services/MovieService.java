package services;

import dtos.MovieDto;
import entities.Movie;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import repositories.MovieRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class MovieService {

    @Inject
    private MovieRepository movieRepository;

    public Movie convertToMovie(MovieDto movieDto){
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(MovieDto.class, Movie.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();
        return mapper.map(movieDto, Movie.class);
    }

    public MovieDto convertToMovieDto(Movie movie){
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Movie.class, MovieDto.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();
        return mapper.map(movie, MovieDto.class);
    }

    public MovieDto addMovie(MovieDto movieDto){
        Movie movie = this.movieRepository.insert(this.convertToMovie(movieDto));
        return this.convertToMovieDto(movie);
    }

    public List<MovieDto> getAllMovies(){
        List<MovieDto> movies = new ArrayList<>();
        List<Movie> movieList = this.movieRepository.list();
        movies = movieList.stream()
                .map(movie-> new MovieDto(movie.getId(), movie.getName(), movie.getCategory(), movie.getProducer(), movie.getDirector(), movie.getReleasedate()))
                .collect(Collectors.toList());
        return movies;
    }

    public boolean removeMovie(Integer movieId){

        return this.movieRepository.delete(movieId);
    }

    public MovieDto findMovieById(Integer movieId){
        Movie movie = movieRepository.findById(movieId);
        return this.convertToMovieDto(movie);
    }

    public MovieDto findMovieByName(String movieName){
        Movie movie = movieRepository.findByName(movieName);
        return this.convertToMovieDto(movie);
    }
}
