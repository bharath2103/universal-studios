package controllers;

import dtos.MovieDto;
import dtos.MultiplexDto;
import entities.Movie;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.MovieService;
import services.MultiplexService;

import javax.inject.Inject;
import java.util.List;

public class MultiplexController extends Controller {

    @Inject
    private FormFactory formFactory;

    @Inject
    private MessagesApi messagesApi;

    @Inject
    private MultiplexService multiplexService;

    @Inject
    private MovieService movieService;

    public Result addMultiplex(Http.Request request) {
        Form<MultiplexDto> feedbackForm = formFactory.form(MultiplexDto.class);
        return ok(views.html.multiplex.create.render(feedbackForm,request, messagesApi.preferred(request)));
    }

    public Result listAll() {
        List<MultiplexDto> multiplexes =  this.multiplexService.getAllMultiplexes();
        for (MultiplexDto multiplex : multiplexes) {
            if(multiplex.getMovie() == null) {
                multiplex.setMoviename("No Movies on screen");
            }
            else
            {
                multiplex.setMoviename(multiplex.getMovie().getName());
            }
        }
        return ok(views.html.multiplex.index.render(multiplexes));
    }

    public Result save(Http.Request request){
        Form<MultiplexDto> feedbackForm =  this.formFactory.form(MultiplexDto.class).bindFromRequest(request);
        MultiplexDto multiplexDto = feedbackForm.get();
        MultiplexDto multiplexDtoResponse = multiplexService.addMultiplex(multiplexDto);
        request.flash().adding("success", "Record Added Successfully");
        return redirect(routes.MultiplexController.listAll());
    }

    public Result removeMultiplex(Integer multiplexId) {
        multiplexService.removeMultiplex(multiplexId);
        return redirect(routes.MultiplexController.listAll());
    }

    public Result addmovietomultiplex(Http.Request request) {
        Form<MultiplexDto> feedbackForm = formFactory.form(MultiplexDto.class);
        return ok(views.html.multiplex.addmovie.render(feedbackForm,request, messagesApi.preferred(request)));
    }

    public Result saveMovieToMultiplex(Http.Request request) {
        Form<MultiplexDto> feedbackForm = this.formFactory.form(MultiplexDto.class).bindFromRequest(request);
        MultiplexDto response = feedbackForm.get();
        // Find the Multiplex
        MultiplexDto multiplexDto = multiplexService.findByMultiplexIdAndScreenName(response.getMultiplexname()
                                                                                    , response.getScreename());
        MovieDto movieDto = movieService.findMovieByName(response.getMoviename());
        Movie movie = movieService.convertToMovie(movieDto);
        multiplexDto.setMovie(movie);
        multiplexDto.setMoviename(movie.getName());
        multiplexService.updateMultiplex(multiplexDto);
        request.flash().adding("success", "Record Added Successfully");
        return redirect(routes.MultiplexController.listAll());
    }

    public Result removemoviefrommultiplex(Http.Request request) {
        Form<MultiplexDto> feedbackForm = formFactory.form(MultiplexDto.class);
        return ok(views.html.multiplex.removemovie.render(feedbackForm,request, messagesApi.preferred(request)));
    }

    public Result clearMovieToMultiplex(Http.Request request) {
        Form<MultiplexDto> feedbackForm = this.formFactory.form(MultiplexDto.class).bindFromRequest(request);
        MultiplexDto response = feedbackForm.get();
        // Find the Multiplex
        MultiplexDto multiplexDto = multiplexService.findByMultiplexIdAndScreenName(response.getMultiplexname()
                , response.getScreename());
        multiplexDto.setMovie(null);
        multiplexDto.setMoviename("No Movies on screen");
        multiplexService.updateMultiplex(multiplexDto);
        request.flash().adding("success", "Record Added Successfully");
        return redirect(routes.MultiplexController.listAll());
    }

}
