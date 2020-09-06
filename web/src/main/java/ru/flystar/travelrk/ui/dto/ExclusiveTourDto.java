package ru.flystar.travelrk.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by sergey on 04.11.2017.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExclusiveTourDto {
    private int id;
    private String path;
    private String name;
    private String size;
    private String logo;
    private String latitude;
    private String longitude;
    private String dateOfDownload;
}
