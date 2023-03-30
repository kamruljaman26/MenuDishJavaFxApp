package practicumopdracht.models;

import java.time.LocalDate;
import java.util.Objects;

public class Menu {

    private String menuName;
    private LocalDate releaseDate;

    public Menu() {
    }

    public Menu(String menuName, LocalDate releaseDate) {
        this.menuName = menuName;
        this.releaseDate = releaseDate;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(menuName, menu.menuName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuName);
    }

    @Override
    public String toString() {
        return "Menu {" +
                "menuName='" + menuName + '\'' +
                ", releaseDate=" + releaseDate +
                " } ";
    }
}
