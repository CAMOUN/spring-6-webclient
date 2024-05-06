package guru.springframework.client;

import guru.springframework.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClient beerClient;

    @Test
    void listBeers() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        beerClient.listBeers().subscribe(
                response -> {
                    System.out.println(response);
                    atomicBoolean.set(true);
                }
        );
        await().untilTrue(atomicBoolean);
    }

    @Test
    void listBeersMap() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        beerClient.listBeersMap().subscribe(
                response -> {
                    System.out.println(response);
                    atomicBoolean.set(true);
                }
        );
        await().untilTrue(atomicBoolean);
    }

    @Test
    void listBeersJson() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        beerClient.listBeersJson().subscribe(
                jsonNode -> {
                    System.out.println(jsonNode.toPrettyString());
                    atomicBoolean.set(true);
                }
        );
        await().untilTrue(atomicBoolean);
    }

    @Test
    void listBeersDTO() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        beerClient.listBeersDTO().subscribe(
                beerDTO -> {
                    System.out.println(beerDTO.getBeerName());
                    atomicBoolean.set(true);
                }
        );
        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetBeerById() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        beerClient.listBeersDTO()
                .flatMap(beerDTO -> beerClient.getBeerById(beerDTO.getId()))
                .subscribe(
                beerDTO -> {
                    System.out.println(beerDTO.getBeerName());
                    atomicBoolean.set(true);
                }
        );
        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetBeerByBeerStyle() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        beerClient.findBeersByBeerStyle("Pale Ale")
                .subscribe(
                    beerDTO -> {
                        System.out.println(beerDTO.getBeerName());
                        atomicBoolean.set(true);
                    }
                );
        await().untilTrue(atomicBoolean);
    }

    @Test
    void testCreateBeer() {

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        BeerDTO newDto = BeerDTO.builder()
                .price(new BigDecimal("10.99"))
                .beerName("Mango Bobs")
                .beerStyle("IPA")
                .quantityOnHand(500)
                .upc("123245")
                .build();

        beerClient.createBeer(newDto)
                .subscribe(dto -> {
                    System.out.println(dto.toString());
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testPatch() {
        final String NAME = "New Name";

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.listBeersDTO()
                .next()
                .map(beerDTO ->  BeerDTO.builder().beerName(NAME).id(beerDTO.getId()).build())
                .flatMap(dto -> beerClient.patchBeer(dto))
                .subscribe(byIdDto -> {
                    System.out.println(byIdDto.toString());
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testUpdate() {

        final String NAME = "New Name";

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.listBeersDTO()
                .next()
                .doOnNext(beerDTO -> beerDTO.setBeerName(NAME))
                .flatMap(dto -> beerClient.updateBeer(dto))
                .subscribe(byIdDto -> {
                    System.out.println(byIdDto.toString());
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testDelete() {

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.listBeersDTO()
                .next()
                .flatMap(dto -> beerClient.deleteBeer(dto))
                .doOnSuccess(mt -> atomicBoolean.set(true))
                .subscribe();

        await().untilTrue(atomicBoolean);
    }



}