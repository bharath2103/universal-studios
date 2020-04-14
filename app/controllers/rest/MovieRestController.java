package controllers.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.MovieDto;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import services.MovieService;
import utils.ResponseDesigner;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class MovieRestController extends Controller {
    // dependency
    @Inject
    private MovieService movieService;

    // Http Execution Context : to maintain an http context for async
    @Inject
    private HttpExecutionContext httpExecutionContext;


/*    public CompletionStage<Result> create(Http.Request request){
        JsonNode json = request.body().asJson();

        // wrap whole code to return an async response
        // supplyAsync(<lambda>,httpExecutionContext)
        return supplyAsync(()->{

            if(json == null){
                // respond bad request
                // return badRequest(<error json data back to client>);
                return badRequest(ResponseDesigner.design("Expecting Valid Json Data!", false));
            }
            // convert JsonNode -> ProductModel
            ProductModel productModel = Json.fromJson(json, ProductModel.class);
            // add record to database
            ProductModel productModel_added = this.productService.addProduct(productModel);
            // convert into Optional ~ to be in sync with Java 8
            Optional<ProductModel> optionalProduct = Optional.ofNullable(productModel_added);
            // convert object into JSON and send response back to client
            return optionalProduct.map(product->{
                // convert into JsonNode
                JsonNode responseObject = Json.toJson(product);
                // return success
                // return created(<send the response object>);
                return created(ResponseDesigner.design(responseObject, true));
            })
                    // if object is null : insertion not successfull
                    .orElse(
                            //internalServerError(<error response>)
                            internalServerError(ResponseDesigner.design("Could not add record!", false))
                    );
        }, httpExecutionContext.current());


    }*/

    public CompletionStage<Result> list(){
        // wrap whole code to return an async response
        return supplyAsync(()->{
            // fetch records from DB
            List<MovieDto> movies = this.movieService.getAllMovies();
            // Mapper object to convert Java List into JSON array
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseObject = mapper.convertValue(movies, JsonNode.class);
            return ok(ResponseDesigner.design(responseObject, true));
        }, httpExecutionContext.current());

    }

    public CompletionStage<Result> findMovieById(Integer movieId){
        // wrap whole code to return an async response
        return supplyAsync(()->{
            // fetch records from DB
            System.out.println("The MovieId is"+movieId);
            MovieDto movieDto = this.movieService.findMovieById(movieId);
            // Mapper object to convert Java List into JSON array
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseObject = mapper.convertValue(movieDto, JsonNode.class);
            return ok(responseObject);
        }, httpExecutionContext.current());

    }





}
