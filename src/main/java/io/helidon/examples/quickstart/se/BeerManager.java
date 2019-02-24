package io.helidon.examples.quickstart.se;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BeerManager {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
    private AtomicInteger beerIdGenerator = new AtomicInteger(0);

    private ConcurrentMap<String, Beer> inMemoryStore = new ConcurrentHashMap<>();

    public BeerManager() {
        Beer beer = Beer.builder()
                .id(getNextId())
                .name("Augustiner")
                .type("Heles")
                .strength(5)
                .build();
        inMemoryStore.put(beer.getId(), beer);
    }

    private String getNextId() {
        String date = LocalDate.now().format(formatter);
        return String.format("%04d-%s", beerIdGenerator.incrementAndGet(), date);
    }

    public String add(Beer beer) {
        String id = getNextId();
        beer.setId(id);
        inMemoryStore.put(id, beer);
        return id;
    }

    public Beer get(String id) {
        return inMemoryStore.get(id);
    }

    public List<Beer> getAll() {
        return new ArrayList<>(inMemoryStore.values());
    }
}
