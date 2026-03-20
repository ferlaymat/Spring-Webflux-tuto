package com.example.webflux.service;

import com.example.webflux.entity.Person;
import com.example.webflux.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProducerServiceImpl implements ProducerService {
    private final PersonRepository personRepository;
    private final List<String> gangs = List.of("Maelstrom", "Valentinos", "6th Street", "The Voodoo Boys", "Animals", "Tyger Claws", "Moxes");
    private final List<String> members = List.of("Brick", "Welles", "Buchanan", "Placide", "Sasquatch", "T-Bug", "Judy");

    @Override
    public Mono<String> getHello() {
        //use JustOrEmty() in case the content is null.
        //Just is no able to manage null value
        return Mono.just("Hellooo NightCity");
    }

    @Override
    public Flux<String> getGangFomIterable() {
        //convert an iterable to a Flux
        return Flux.fromIterable(gangs);
    }

    /**
     * Synchronous treatment - wait 1 sec for each element
     */
    @Override
    public Flux<Person> getMembers() {
        return personRepository.findAll()
                .map(person -> {
                    log.info("MAP - Processing: {} on {}", person.getFirstName(), Thread.currentThread().getName());
                    person.setFirstName(person.getFirstName().toUpperCase());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return person;
                });
    }

    /**
     * Parallelized treatment - wait 1 sec for each element
     */
    @Override
    public Flux<Person> getMembersFlat() {
        return personRepository.findAll()
                .flatMap(person -> {
                    log.info("FLATMAP - Processing: {} on {}", person.getFirstName(), Thread.currentThread().getName());
                    person.setFirstName(person.getFirstName().toUpperCase());
                    return Mono.just(person).delayElement(Duration.ofMillis(1000));
                });
    }

    @Override
    public Flux<String> getFilteredName() {
        return Flux.fromIterable(members).filter(n -> n.length() < 6);
    }

    @Override
    public Flux<String> toUpperCase() {
        return Flux.fromIterable(members).map(it -> it.toUpperCase(Locale.ROOT));
    }

    @Override
    public Flux<String> toUpperCaseFM() {
        return Flux.fromIterable(members).flatMap(it -> Mono.just(it.toLowerCase(Locale.ROOT)));
    }

    @Override
    public Flux<Integer> getFluxConcat() {
        Flux<Integer> f1 = Flux.range(1, 20).delayElements(Duration.ofMillis(500));
        Flux<Integer> f2 = Flux.range(100, 20).delayElements(Duration.ofMillis(500));
        Flux<Integer> f3 = Flux.range(200, 20).delayElements(Duration.ofMillis(500));
        //merge but keep the order. Return the full f1 flux before starting to return the flux2
        return Flux.concat(f1, f2, f3);
    }

    @Override
    public Flux<Integer> getFluxMerge() {
        Flux<Integer> f1 = Flux.range(1, 20).delayElements(Duration.ofMillis(500));
        Flux<Integer> f2 = Flux.range(100, 20).delayElements(Duration.ofMillis(500));
        Flux<Integer> f3 = Flux.range(200, 20).delayElements(Duration.ofMillis(500));
        //merge but do not keep the order.Return an element when is available
        return Flux.merge(f1, f2, f3);
    }

    @Override
    public Flux<Tuple3<Integer, Integer, Integer>> getFluxZip() {
        Flux<Integer> f1 = Flux.range(1, 20).delayElements(Duration.ofMillis(500));
        Flux<Integer> f2 = Flux.range(100, 20).delayElements(Duration.ofMillis(500));
        Flux<Integer> f3 = Flux.range(200, 20).delayElements(Duration.ofMillis(500));
        //return a tuple3 composed with one of each flux. Stop to return if one flux is finished
        return Flux.zip(f1, f2, f3);
    }

    @Override
    public Flux<List<Integer>> getList() {
        Flux<Integer> f1 = Flux.range(1, 20).delayElements(Duration.ofMillis(500));
        Flux<Integer> f2 = Flux.range(100, 20).delayElements(Duration.ofMillis(500));
        Flux<Integer> f3 = Flux.range(200, 20).delayElements(Duration.ofMillis(500));
        //return a sub list of merged elements. The number of elements depends on the delay to feed to buffer
        return Flux.merge(f1, f2, f3).buffer(Duration.ofMillis(1000));

    }

    @Override
    public Mono<Map<Integer, Integer>> getCollectMap() {
        Flux<Integer> f1 = Flux.range(1, 20);
        Flux<Integer> f2 = Flux.range(100, 20);
        Flux<Integer> f3 = Flux.range(200, 20);
        //apply a function on key and value then return a map of the result. here we compute the square of the key
        return Flux.merge(f1, f2, f3).collectMap(key -> key, value -> value * value);

    }

    @Override
    public void doEach() {
        Flux<Integer> f1 = Flux.range(1, 20);
        Flux<Integer> f2 = Flux.range(100, 20);
        Flux<Integer> f3 = Flux.range(200, 20);
        //return sublist of 5 elements on each next and print it in the log
        Flux.merge(f1, f2, f3).buffer(5).doOnEach(signal -> System.out.println(signal)).subscribe();

    }
}
