package practicumopdracht.data;

import practicumopdracht.models.Dish;
import practicumopdracht.models.Menu;

import java.util.ArrayList;
import java.util.List;


public abstract class DishDAO implements DAO<Dish> {

    protected List<Dish> dishes;

    public DishDAO() {
        this.dishes = new ArrayList<>();
    }

    @Override
    public Dish getById(int id) {
        //The getById method searches for an object in the objects-List based on the given id and returns it. The id in
        // this case is the same as an index in the objects-List. If someone wants the object for id 0, you thus return
        // the first object in the list. Return a null if the id is invalid
        return dishes.get(id);
    }

    @Override
    public List<Dish> getAll() {
        //The getAll method returns all objects from objects-List.
        return dishes;
    }

    public List<Dish> getAllFor(Menu menu) {
        // method returns all objects from the objects-List, but only if they belong to the given Master model.
        List<Dish> newDishes = new ArrayList<>();
        for (Dish dish : dishes) {
            // add in new list that matched
            if (dish.getBelongsTo().equals(menu)) newDishes.add(dish);
        }
        return newDishes;
    }

    @Override
    public void addOrUpdate(Dish object) {
        // The addOrUpdate method checks if:
        // => The incoming object is already present in the objects-List, if it is is the case, nothing happens.
        // => the incoming object is not yet present in the objects-List, then the object is added to the objects-List.
        if (!dishes.contains(object)) {
            dishes.add(object);
        } else {
            int idFor = getIdFor(object);
            dishes.get(idFor).setDishName(object.getDishName());
            dishes.get(idFor).setPrice(object.getPrice());
            dishes.get(idFor).setAverageCookingTimeInMinutes(object.getAverageCookingTimeInMinutes());
            dishes.get(idFor).setVegan(object.isVegan());
            dishes.get(idFor).setBelongsTo(object.getBelongsTo());
        }
    }

    @Override
    public int getIdFor(Dish dish) {
        return dishes.indexOf(dish);
    }


    @Override
    public void remove(Dish object) {
        // The remove method removes the incoming object from the objects-List.
        dishes.remove(object);
    }

    @Override
    public abstract boolean save();

    @Override
    public abstract boolean load();
}
