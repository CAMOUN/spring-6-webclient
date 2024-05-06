package guru.springframework.client;

import com.fasterxml.jackson.databind.JsonNode;
import guru.springframework.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.Flow;

public interface BeerClient {

    Flux<String> listBeers();

    Flux<Map> listBeersMap();

    Flux<BeerDTO> listBeersDTO();

    Flux<JsonNode> listBeersJson();

    Mono<BeerDTO> getBeerById(String id);

    Flux<BeerDTO> findBeersByBeerStyle(String beerStyle);

    Mono<BeerDTO> createBeer(BeerDTO beerDTO);

    Mono<BeerDTO> updateBeer(BeerDTO beerDTO);

    Mono<BeerDTO> patchBeer(BeerDTO dto);

    Mono<Void> deleteBeer(BeerDTO dto);
}
