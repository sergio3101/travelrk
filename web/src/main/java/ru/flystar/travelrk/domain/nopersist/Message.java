package ru.flystar.travelrk.domain.nopersist;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 25.09.2017.
 */
@Setter
@Getter
@NoArgsConstructor
public class Message {
    private Status status;
    private String statusMsg = "";
    private ArrayList<Integer> errorKys;
    private String error = "";
    private Object msgObj;

}
