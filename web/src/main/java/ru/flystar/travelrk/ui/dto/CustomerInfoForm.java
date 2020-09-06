package ru.flystar.travelrk.ui.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import ru.flystar.travelrk.domain.persistents.Region;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 01.12.2017.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerInfoForm {
    private int id;
    private String companyName = "";
    private Region region;
    private String phone = "";
    private String address = "";
    private String site = "";
    private String excltour = "";
    private String email = "";
    private MultipartFile logoPath;
    private String logo = "";
    private String officeLat = "";
    private String officeLng = "";
    private String height = "";
    private String description = "";
}
