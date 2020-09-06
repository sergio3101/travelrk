package ru.flystar.travelrk;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Cat implements Comparable<Cat> {
    private int id;
    private String name;
    private Integer age;

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Cat o) {
        return name.compareTo(o.getName());
    }
}
