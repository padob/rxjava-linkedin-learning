package com.linkedin;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import static java.util.Arrays.asList;

public class Main {

    public static void main(String[] names) {
        hello("Iron maiden", "asd", "Osd");

        String[] arr = new String[]{"hi", "hola", "czesc"};
        Observable<String> observable = Observable.fromArray(arr);

        List<Integer> intList = asList(3, 1, 2, 7, 4, 5, 8, 6);
        Observable<Integer> integerObservable = Observable.fromIterable(intList);

        Observable<String> singleValueObservable = Observable.just("Just one string");

        //custom observable
        Observable.create(emitter -> {
            try {
                IntStream.range(0, 10).boxed().forEach(
                        integer -> System.out.print("integer = " + integer)
                );
                emitter.onComplete();
            }
            catch (Exception e) {
                emitter.onError(e);
            }

        });

        Observable interval = Observable.interval(100, TimeUnit.MILLISECONDS);

        //consuming observables
        integerObservable.subscribe(intVal -> System.out.print(intVal + " "));
        System.out.println("\nUsing static method reference");
        integerObservable.subscribe(Main::printObservable);

        //filter out any value that isn't greather than 4
        System.out.println("\nAFTER FILTER");
        integerObservable.filter(n -> n < 4).subscribe(Main::printObservable);

        //map each value to the square of that value
        System.out.println("\nAFTER MAPPING");
        integerObservable.map(n -> Math.multiplyExact(n, n)).subscribe(Main::printObservable);

        //subscribe using ObservablePrintObserver
        System.out.println("\nUsing ObservablePrintObserver");
        integerObservable
                .subscribe(new ConsolePrintObserver());

        System.out.println("\nUsing new thread schedular");
        integerObservable
                .unsubscribeOn(Schedulers.newThread())
                .subscribe(new ConsolePrintObserver());

        //use it all in one statement
        System.out.println("\nFilter, map, schedular and observer");
        integerObservable
                .filter(v -> v > 4)
                .map(v -> Math.multiplyExact(v, v))
                .unsubscribeOn(Schedulers.newThread())
                .subscribe(new ConsolePrintObserver());
    }

    public static <T> void printObservable(Object val) {
        System.out.print(val + " - ");

    }

    private static void hello(String... names) {
        Observable.fromArray(names).subscribe(s -> System.out.println("Hello " + s));
    }
}
