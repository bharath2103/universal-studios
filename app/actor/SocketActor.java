package actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.http.javadsl.Http;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpEntity;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.Materializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.MovieDto;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class SocketActor extends AbstractActor {

    private ActorRef guardian;

    public SocketActor(ActorRef guardian){
        this.guardian = guardian;
    }

    // not going to return actorRef
    // special object : Props
    public static Props init(ActorRef guardian){
        // this will initiate the set of prop for current actor
        return Props.create(SocketActor.class, ()-> new SocketActor(guardian));
    }

    // message processing method
    private void processMessage(JsonNode jsonNode){
        // if required convert into request model
        // fetch random message from Rest API
        System.out.println("Processing message");
        String message = jsonNode.get("message").textValue();
        System.out.println("Message : "+message);
        CompletionStage<HttpResponse> responseFuture= this.callRestApi(message);
        System.out.println("FutureResponse : "+responseFuture.toString());
        // 1. consume and convert into my model format
        // 2. convert into JsonNode and send it to client
        responseFuture.thenCompose(this::consumeHttpResponse)
                .thenAccept(MovieDto -> {
                    System.out.println("DATA : " + MovieDto);
                    // convert to JsonNode : design util classes
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode json = mapper.convertValue(MovieDto, JsonNode.class);
                    // sent to guardian actor
                    this.guardian.tell(json, getSelf());
                });
    }

    // method to generate a http call to an REST API
    private CompletionStage<HttpResponse> callRestApi(String message){
        // generate a random number
        System.out.println("Calling Rest API");
        int id = Integer.valueOf(message);
       // int id = ThreadLocalRandom.current().nextInt(0,100);
        //System.out.println("https://jsonplaceholder.typicode.com/posts/" + id);
        System.out.println("http://localhost:9000/api/movie/findbyid/" + id);
        //return Http.get(getContext().getSystem()).singleRequest(HttpRequest.create("https://jsonplaceholder.typicode.com/posts/" + id));
        CompletionStage<HttpResponse> response = Http.get(getContext().getSystem()).singleRequest(HttpRequest.create("http://localhost:9000/api/movie/findbyid/7"));
        System.out.println(response.toString());
        return response;
    }

    // method to consume httpResponse
    private CompletionStage<MovieDto> consumeHttpResponse(HttpResponse httpResponse){
        // get mat from actorSystem
        System.out.println("Consuming");
        System.out.println("Response Entity is "+httpResponse.entity());
        Materializer materializer = Materializer.matFromSystem(getContext().getSystem());
        System.out.println("materializer "+materializer);
        HttpEntity.Strict strict = null;
        try {
            strict = httpResponse.entity().toStrict(1000, materializer).toCompletableFuture().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String body = strict.getData().utf8String();
        System.out.println("Body"+body);
        return Jackson.unmarshaller(MovieDto.class)
                .unmarshal(httpResponse.entity(), materializer)
                .thenApply(movieDto -> {
                    this.discardEntity(httpResponse, materializer);
                    return movieDto;
                });
    }

    private void discardEntity(HttpResponse httpResponse, Materializer materializer) {
        httpResponse.discardEntityBytes(materializer)
                .completionStage()
                .whenComplete((done, ex) -> System.out.println("Discarded"));
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                // we recieve message of type JsonNode
                .match(JsonNode.class, this::processMessage)
                .build();
    }
}
