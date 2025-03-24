package ru.flystar.travelrk;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class ExclusiveTourModel {

    private final int id;

    private String path;

    private String name;

    private String size;

    private String logo;

    private Date dateOfDownload;

}
