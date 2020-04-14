package controllers;


import dtos.MovieDto;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.MovieService;

import javax.inject.Inject;
import java.util.List;

public class MovieController extends Controller {

    @Inject
    private FormFactory formFactory;

    @Inject
    private MessagesApi messagesApi;

    @Inject
    private MovieService movieService;

    public Result create(Http.Request request) {
        Form<MovieDto> feedbackForm = formFactory.form(MovieDto.class);
        return ok(views.html.movie.create.render(feedbackForm,request, messagesApi.preferred(request)));
    }

    public Result listAll() {
        List<MovieDto> movies =  this.movieService.getAllMovies();
        return ok(views.html.movie.index.render(movies));
    }

    public Result save(Http.Request request){
        Form<MovieDto> feedbackForm =  this.formFactory.form(MovieDto.class).bindFromRequest(request);
        MovieDto movieDto = feedbackForm.get();
        MovieDto movieDtoResponse = movieService.addMovie(movieDto);
        request.flash().adding("success", "Record Added Successfully");
        return redirect(routes.MovieController.listAll());
    }

    public Result removeMovie(Integer movieId) {
        movieService.removeMovie(movieId);
        return redirect(routes.MovieController.listAll());
    }

    public Result editMovie(Http.Request request, Integer movieId) {
        Form<MovieDto> feedbackForm = formFactory.form(MovieDto.class);
        return ok(views.html.movie.create.render(feedbackForm,request, messagesApi.preferred(request)));
    }
}
