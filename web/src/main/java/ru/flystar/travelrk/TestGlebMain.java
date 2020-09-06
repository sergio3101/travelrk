package ru.flystar.travelrk;

import java.util.Comparator;
import java.util.TreeSet;

public class TestGlebMain {
    public static void main(String[] args) {
        Cat cat1 = new Cat(1,"Барсик",5);
        Cat cat2 = new Cat(2,"Васька",3);
        Cat cat3 = new Cat(3, "Жорик", 4);
        Comparator<Cat> catCompareAge = new Comparator<Cat>() {
            @Override
            public int compare(Cat o1, Cat o2) {
                return o1.getAge().compareTo(o2.getAge());
            }
        };

        Comparator<Cat> catCompareId = new Comparator<Cat>() {
            @Override
            public int compare(Cat o1, Cat o2) {
                return Integer.compare(o1.getId(),o2.getId());
            }
        };

        Comparator<Cat> catCompareNames = new Comparator<Cat>() {
            @Override
            public int compare(Cat o1, Cat o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };

        if (catCompareNames.compare(cat1,cat2) > 0) {
            System.out.println(cat1 + " старше " + cat2);
        } else if (catCompareAge.compare(cat1,cat2) == 0) {
            System.out.println(cat1 + " такого же возраста что и " + cat2);
        } else {
            System.out.println(cat1 + " младше " + cat2);
        }
        TreeSet<Cat> cats = new TreeSet<>(catCompareAge);
        cats.add(cat1);
        cats.add(cat2);
        cats.add(cat3);
        System.out.println(cats);

    }
}
