package ru.flystar.travelrk.domain.persistents;

import lombok.*;
import ru.flystar.travelrk.domain.nopersist.*;

import javax.persistence.*;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 21.11.2017.
 */
@Entity
@Table(name = "krpanoConfig")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KrpanoConfig extends BaseId {
    private final static String DSTDIR = "/var/www/travelrk";
    /**
     * Krpano name settings
     */
    @Column(name = "path", length = 1024)
    private String path = "defaultkrpano.config";
    @Column(name = "name", length = 1024)
    private String name = "Конфигурация по умолчанию";
    /**
     * Input Image Settings
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "panotype")
    private PanoType panotype = PanoType.AUTODETECT;
    @Column(name = "hfov")
    private int hfov = 360;
    @Column(name = "vfov")
    private int vfov;
    @Column(name = "voffset")
    private int voffset;
    /**
     * Stereo Settings
     */
    @Column(name = "stereosupport")
    private boolean stereosupport = true;
    /**
     * Format Conversion Settings (Sphere/Cylinder to Cube)
     */
    @Column(name = "converttocube")
    private boolean converttocube = true;
    @Column(name = "converttocubelimit360x")
    private int converttocubelimit360x=120;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "converttocubeformat")
    private CubeFormat converttocubeformat = CubeFormat.KRO;
    /**
     * Multi-Resolution Image Settings
     */
    @Column(name = "multires")
    private boolean multires = true;
    @Column(name = "tilesize")
    private int tilesize;
    @Column(name = "levels")
    private int levels;
    @Column(name = "levelstep")
    private double levelstep = 2.0;
    @Column(name = "minsize")
    private int minsize;
    @Column(name = "maxsize")
    private int maxsize;
    @Column(name = "maxcubesize")
    private int maxcubesize;
    /**
     * Output Images / Tile-Paths Setings
     */
    @Column(name = "tilepath", length = 1024)
    private String tilepath = "[c/]l%Al/%Av/l%Al[_c]_%Av_%Ah.jpg";
    /**
     * Preview Pano Setings
     */
    @Column(name = "preview")
    private boolean preview = true;
    @Column(name = "graypreview")
    private boolean graypreview;
    @Column(name = "cspreview")
    private boolean cspreview = true;
    @Column(name = "previewsmooth")
    private int previewsmooth = 25;
    @Column(name = "previewspsize")
    private int previewspsize = 1024;
    @Column(name = "previewcssize")
    private int previewcssize = 256;
    @Column(name = "previewpath", length = 1024)
    private String previewpath = "preview.jpg";
    /**
     * Mobile version settings
     */
    @Column(name = "mobileVersion")
    private boolean mobileVersion = true;
    /**
     * Thumbnail Image Settings
     */
    @Column(name = "makethumb")
    private boolean makethumb;
    @Column(name = "thumbsize")
    private int thumbsize=80;
    /**
     * Image Filtering / Compression Settings
     */
    @Column(name = "jpegquality")
    private int jpegquality=85;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "jpegsubsamp")
    private JpegSubSamp jpegsubsamp = JpegSubSamp.HOR12;
    @Column(name = "jpegoptimize")
    private boolean jpegoptimize = true;
    @Column(name = "jpegprogressive")
    private boolean jpegprogressive;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "filter")
    private ImageFilter filter = ImageFilter.LANCZOS;
    /**
     * Basic / Setup Settings
     */
    @Column(name = "parsegps")
    private boolean parsegps = true;
    @Column(name = "flash")
    private boolean flash = true;
    @Column(name = "html5")
    private boolean html5 = true;
    @Column(name = "ignorelayers")
    private boolean ignorelayers;
    @Column(name = "sortinput")
    private boolean sortinput = true;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "autolevel")
    private AutoLevel autolevel = AutoLevel.REMAP;
    /**
     * Protection Settings
     */
    @Column(name = "protect")
    private boolean protect;
    @Column(name = "noep")
    private boolean noep;
    @Column(name = "nojs")
    private boolean nojs;
    @Column(name = "nolu")
    private boolean nolu;
    @Column(name = "noex")
    private boolean noex;
    @Column(name = "pxml")
    private boolean pxml;
    @Column(name = "domain", length = 1024)
    private String domain = "";
    @Column(name = "expire", length = 1024)
    private String expire = "";

    public KrpanoConfig(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KrpanoConfig krpanoConfig = (KrpanoConfig) o;

        if (getId() != krpanoConfig.getId()) return false;
        if (path != null ? !path.equals(krpanoConfig.path) : krpanoConfig.path != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }
}
