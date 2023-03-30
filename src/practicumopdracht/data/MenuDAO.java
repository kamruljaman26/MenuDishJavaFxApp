package practicumopdracht.data;

import practicumopdracht.models.Menu;

import java.util.ArrayList;
import java.util.List;

public abstract class MenuDAO implements DAO<Menu> {

    protected List<Menu> menus;

    public MenuDAO() {
        this.menus = new ArrayList<>();
    }

    @Override
    public Menu getById(int id) {
        return menus.get(id);
    }

    @Override
    public List<Menu> getAll() {
        return menus;
    }

    @Override
    public void addOrUpdate(Menu object) {
        if (!menus.contains(object)) {
            menus.add(object);
        }
    }

    // todo test
    public int getIdFor(Menu menu){
        return menus.indexOf(menu);
    }

    @Override
    public void remove(Menu object) {
        menus.remove(object);
    }

    @Override
    public abstract boolean save();

    @Override
    public abstract boolean load();
}
