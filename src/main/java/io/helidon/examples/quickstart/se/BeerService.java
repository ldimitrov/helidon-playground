package io.helidon.examples.quickstart.se;

import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class BeerService implements Service {

    private BeerManager beerManager = new BeerManager();

    @Override
    public void update(Routing.Rules rules) {
        rules
                .get("/", this::beers)
                .get("/{id}", this::beerById);
    }

    private void beerById(ServerRequest serverRequest, ServerResponse serverResponse) {
        //get the book with the given id
        String id = serverRequest.path().param("id");
        Beer beer = beerManager.get(id);
        JsonObject jsonObject = from(beer);
        serverResponse.send(jsonObject);
    }

    private void beers(ServerRequest serverRequest, ServerResponse serverResponse) {
        //get all beers
        List<Beer> beers = beerManager.getAll();
        JsonArray jsonArray = from(beers);
        serverResponse.send(jsonArray);
    }

    private JsonObject from(Beer beer) {
        return Json.createObjectBuilder()
                .add("id", beer.getId())
                .add("name", beer.getName())
                .add("type", beer.getType())
                .add("strength", beer.getStrength())
                .build();
    }

    private JsonArray from(List<Beer> books) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        books.forEach(book -> {
            jsonArrayBuilder.add(from(book));
        });
        return jsonArrayBuilder.build();
    }
}
