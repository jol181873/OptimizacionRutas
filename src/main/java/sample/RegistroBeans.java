package sample;

/**
 * Created by jol on 18/12/16.
 */
public class RegistroBeans {
    private static RegistroBeans instance;
    private BeanMenu beanMenu;

    private RegistroBeans() {
        beanMenu=new BeanMenu();
    }

    public static RegistroBeans getInstance() {
        if (instance==null) {
            instance=new RegistroBeans();
        }

        return instance;
    }

    public BeanMenu getBeanMenu() {
        return beanMenu;
    }

    public void setBeanMenu(BeanMenu beanMenu) {
        this.beanMenu = beanMenu;
    }
}
