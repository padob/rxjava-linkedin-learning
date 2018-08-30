package com.linkedin;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class Main {

    public static void main(String[] names) {
        Flowable.just("Hello world").subscribe(System.out::println);

        hello("Iron maiden", "asd", "Osd");
    }

    private static void hello(String... names) {
        Observable.fromArray(names).subscribe(s -> System.out.println("Hello " + s));
    }
}
