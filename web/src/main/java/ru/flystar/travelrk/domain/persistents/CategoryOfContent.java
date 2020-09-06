package ru.flystar.travelrk.domain.persistents;

import lombok.*;

import javax.persistence.*;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 30.10.2017.
 */
@Entity
@Table(name = "categoryOfContent")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryOfContent extends BaseId {
    @Column(name = "name", nullable = true, length = 45)
    private String name;

    @Column(name = "viewName", nullable = true, length = 45)
    private String viewName;

    @Column(name = "description", nullable = true, length = -1)
    private String description;

    @Column(name = "icoPath", nullable = true, length = 45)
    private String icoPath;

    public CategoryOfContent(String name) {
        this.name = name;
    }

    public CategoryOfContent(String name, String viewName, String icoPath) {
        this.name = name;
        this.viewName = viewName;
        this.icoPath = icoPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryOfContent that = (CategoryOfContent) o;
        if (getId() != that.getId()) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
