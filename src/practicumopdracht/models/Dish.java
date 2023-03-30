package practicumopdracht.models;

import practicumopdracht.IO.Writeable;
import practicumopdracht.exceptions.InvalidLineFormatException;

import java.util.Objects;

public class Dish implements Writeable<Dish> {

    private String dishName;
    private double price;
    private int averageCookingTimeInMinutes;
    private boolean isVegan;
    private Menu belongsTo;

    public Dish() {
    }

    public Dish(String dishName, double price, int averageCookingTimeInMinutes, boolean isVegan, Menu belongsTo) {
        this.dishName = dishName;
        this.price = price;
        this.averageCookingTimeInMinutes = averageCookingTimeInMinutes;
        this.isVegan = isVegan;
        this.belongsTo = belongsTo;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAverageCookingTimeInMinutes() {
        return averageCookingTimeInMinutes;
    }

    public void setAverageCookingTimeInMinutes(int averageCookingTimeInMinutes) {
        this.averageCookingTimeInMinutes = averageCookingTimeInMinutes;
    }

    public boolean isVegan() {
        return isVegan;
    }

    public void setVegan(boolean vegan) {
        isVegan = vegan;
    }

    public Menu getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(Menu belongsTo) {
        this.belongsTo = belongsTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return Objects.equals(dishName, dish.dishName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dishName);
    }

    @Override
    public String toString() {
        return "Dish{" +
                "Name='" + dishName + '\'' +
                ", price=" + price +
                ", CookingTimeInMinutes=" + averageCookingTimeInMinutes +
                ", isVegan=" + isVegan +
                '}';
    }

    @Override
    public String toWrite() {
        return String.format("%s|%s|%s|%s|%s", dishName, price, averageCookingTimeInMinutes, isVegan, belongsTo.getMenuName());
    }

    @Override
    public Dish toRead(String toRead) throws InvalidLineFormatException {
        try {
            String[] parts = toRead.split("\\|");

            Dish dish = new Dish();
            dish.setDishName(parts[0]);
            dish.setPrice(Double.parseDouble(parts[1]));
            dish.setAverageCookingTimeInMinutes(Integer.parseInt(parts[2]));
            dish.setVegan(Boolean.parseBoolean(parts[3]));

            Menu menu = new Menu();
            menu.setMenuName(parts[4]);
            dish.setBelongsTo(menu);

            return dish;
        } catch (Exception e) {
            throw new InvalidLineFormatException();
        }
    }
}
