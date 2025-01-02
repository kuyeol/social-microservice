package fluent;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.core.Response;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class TextHelper {

    @Inject
    EntityManager em;

    public TextHelper() {

    }


    public Multi<Multi<Object>> multiStream() {


        RateProduct rr = new RateProduct();
        rr.setProductName("rr1");

        RateProduct rr2 = new RateProduct();
        rr2.setProductName("rr2");

        rr.setId(String.valueOf(UUID.randomUUID()));
        Product pp = new Product();

        pp.setId("pp");


        Multi<Command> prod = Multi.createFrom()
                                   .items(rr, pp, rr2);


        Multi.createBy()
             .concatenating()
             .streams(prod)
             .subscribe()
             .with(this::accept);

        Multi<String> multi = Multi.createFrom()
                                   .items("DFS", "ASDF");
        Uni<String> uni = Uni.createFrom()
                             .item("SDF");

        Uni<String> u = uni.onItem()
                           .invoke(i -> System.out.println("Received item: " + i));

        Multi<String> m = multi.onItem()
                               .invoke(i -> System.out.println("Received item: " + i));

        Multi<String> mm1 = multi.onSubscription()
                                 .invoke(() -> System.out.println("⬇️ Subscribed"))
                                 .onItem()
                                 .invoke(i -> System.out.println("⬇️ Received item: " + i))
                                 .onFailure()
                                 .invoke(f -> System.out.println("⬇️ Failed with " + f))
                                 .onCompletion()
                                 .invoke(() -> System.out.println("⬇️ Completed"))
                                 .onCancellation()
                                 .invoke(() -> System.out.println("⬆️ Cancelled"))
                                 .onRequest()
                                 .invoke(l -> System.out.println("⬆️ Requested: " + l));


        Multi<String> mm11 = multi.onItem()
                                  .call(i -> Uni.createFrom()
                                                .voidItem()
                                                .onItem()
                                                .delayIt()
                                                .by(Duration.ofSeconds(1)));


        //Multi.createBy()
        //     .concatenating()
        //     .streams(prod)
        //     .subscribe()
        //     .with(System.out::println);



        //Uni<Tuple2<Response, Response>> responses = Uni.combine()
        //                                               .all()
        //                                               .unis(uniA, uniB)
        //                                               .asTuple();
        ////
        //Uni.combine()
        //   .all()
        //   .unis(uniA, uniB)
        //   .asTuple()
        //   .subscribe()
        //   .with(tuple -> {
        //      // invokeHttpServiceA();
        //      // System.out.println("Response from A: " + tuple.getItem1());
        //      // System.out.println("Response from B: " + tuple.getItem2());
        //     //  System.out.println("Response from 3: " + tuple.getItem3());
        //   });
        //Uni.combine()
        //   .all()
        //   .unis(responses)
        //   .with(tuple -> {
        //     return   invokeHttpServiceA();
        //
        //   });
        //
        //Multi<Response> multi1A = invokeHttpServiceMA();
        //multi1A.onCompletion();
        //Multi<Response> multi1B = invokeHttpServiceMB();
        //multi1B.onCompletion();
        //Multi<Tuple2<Response, Response>> combined = Multi.createBy()
        //                                                  .combining()
        //                                                  .streams(multi1A, multi1B)
        //                                                  .asTuple();
        //Multi.createBy()
        //     .combining()
        //     .streams(multi1A, multi1B)
        //     .using(list -> combineItems(list))
        //     .subscribe()
        //     .with(x -> {
        //
        //         System.out.println(combined);
        //         // do something with the combined items
        //     });


        AtomicInteger atomicInteger = new AtomicInteger();

        Random random = new Random();
        Multi.createBy().repeating().supplier(random::nextInt).atMost(2111)
             .onItem().transformToUniAndMerge(n -> {
                 atomicInteger.getAndIncrement();
                 System.out.println("----");
                 if (n < 0) {
                     return drop(atomicInteger.get());
                 } else if (n % 2 == 0) {
                     return evenOperation(n);
                 } else {
                     return oddOperation(n);
                 }
             })
             .subscribe().with(str -> System.out.println(atomicInteger +" = > " + str));

        return null;

    }
    Uni<String> evenOperation(int n) {
        return Uni.createFrom().item("Even number: " + n)
                  .onItem().invoke(() -> System.out.println("(even branch)"));
    }

    Uni<String> oddOperation(int n) {
        return Uni.createFrom().item("Odd number: " + n)
                  .onItem().invoke(() -> System.out.println("(odd branch)"));
    }
    Uni<String> drop(int n) {
        return Uni.createFrom().<String>nullItem()
                  .onItem().invoke(() -> System.out.println(n+"(dropping negative value)"));
    }


    Uni<String> invoke1() {
        return Uni.createFrom().<String>nullItem()
                  .onItem().invoke(() -> System.out.println("invoke1"));
    }


    Uni<String> invoke2() {
        return Uni.createFrom().<String>nullItem()
                  .onItem().invoke(() -> System.out.println("invoke2"));
    }

    private Object combineItems(List<?> list) {

        for (Object o : list) {
            System.out.println(o);
        }
        return list.stream()
                   .toList();
    }

    private Multi<String> invokeHttpService(String s) {
        return Multi.createFrom()
                    .item("");
    }


    private Multi<Response> invokeHttpServiceMA() {
        for (int i = 0; i < 11; i++) {
            System.out.println("multi A::" + Thread.currentThread()
                                                   .getName() + ": " + i);
        }
        return Multi.createFrom()
                    .item(Response.ok()
                                  .build());
    }

    private Multi<Response> invokeHttpServiceMB() {


        for (int i = 0; i < 11; i++) {
            System.out.println("multi B::" + Thread.currentThread()
                                                   .getName() + ": " + i);
        }
        return Multi.createFrom()
                    .item(Response.ok()
                                  .build());
    }

    private Uni<Response> invokeHttpServiceA() {
        for (int i = 0; i < 11; i++) {
            System.out.println("A::" + Thread.currentThread()
                                             .getName() + ": " + i);
        }
        return Uni.createFrom()
                  .item(Response.ok()
                                .build());
    }

    private Uni<Response> invokeHttpServiceB() {


        for (int i = 0; i < 11; i++) {
            System.out.println("B::" + Thread.currentThread()
                                             .getName() + ": " + i);
        }
        return Uni.createFrom()
                  .item(Response.ok()
                                .build());
    }

    public Multi<String> mm() {
        Multi<String> multi = Multi.createFrom()
                                   .items("");
        Uni<String> uni = Uni.createFrom()
                             .item("");

        Uni<String> u = uni.onItem()
                           .invoke(i -> System.out.println("Received item: " + i));

        Multi<String> m = multi.onItem()
                               .invoke(i -> System.out.println("Received item: " + i));

        Multi<String> mm = multi.onSubscription()
                                .invoke(() -> System.out.println("⬇️ Subscribed"))
                                .onItem()
                                .invoke(i -> System.out.println("⬇️ Received item: " + i))
                                .onFailure()
                                .invoke(f -> System.out.println("⬇️ Failed with " + f))
                                .onCompletion()
                                .invoke(() -> System.out.println("⬇️ Completed"))
                                .onCancellation()
                                .invoke(() -> System.out.println("⬆️ Cancelled"))
                                .onRequest()
                                .invoke(l -> System.out.println("⬆️ Requested: " + l));

        return mm;
    }

    private void accept(Command command) {

        System.out.print(command.getClass()
                                .getSimpleName() + " \t : ");
        System.out.println(command);

    }

    //private void accept(Command command) {
    //
    //    System.out.print(command.getClass()
    //                            .getSimpleName() + " \t : ");
    //    System.out.println(command.getId());
    //}


    public static String MeowPrepend(String text) {

        if (text.length() > 0) {
            text = text.toUpperCase();
        }

        return WoofPrepend(text);
    }


    //private Multi<Product> accept(Product P) {
    //    // em.persist(r);
    //
    //    P.setId(P.getId());
    //
    //    System.out.println(P);
    //    return Multi.createFrom()
    //                .items(P);
    //}


    private static String WoofPrepend(String text) {

        if (text.trim()
                .length() > 0) {
            text = text.toUpperCase();
            return WoohPrepend(text);
        }

        text = "{" + "Empty" + "}";
        return WoohPrepend(text);
    }

    private static String WoohPrepend(String text) {
        text = text + " finished ";
        return text;
    }


}
