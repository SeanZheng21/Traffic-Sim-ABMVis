package MainGUI;

import UIAbmModel.Drawable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;

/**
 * this class is used to filter the Drawable object
 * it allows to modify the object to change the way the user see it
 */
public class Filter {

    /**
     * the drawable class affected by the modification
     */
    private final Class<? extends Drawable> aClass;

    /**
     * the type for this object affected by the modification
     */
    private final String type;

    /**
     * the value of the type affected
     */
    private final String value;

    /**
     * the new Color of an affected Drawable Object
     */
    private final Color color;

    /**
     * the size of the object not implemented Yet
     */
    private final double size;

    /**
     * boolean value to hide some Object if false not used yet
     */
    private final boolean isDisplayed;

    /**
     * true if the filter has to be applied
     */
    private final BooleanProperty active;

    /**
     * Constructor
     * @param aClass the class to filter
     * @param type the type to filter
     * @param value the value of the type
     * @param color the new color
     * @param size the new Size
     * @param isDisplayed false if the object has to be hided
     */
    public Filter(Class<? extends Drawable> aClass, String type, String value, Color color, double size, boolean isDisplayed) {
        this.aClass = aClass;
        this.type = type;
        this.value = value;
        this.color = color;
        this.size = size;
        this.isDisplayed = isDisplayed;
        this.active = new SimpleBooleanProperty(true);
    }

    /**
     * function to apply a filter and it verifies if it should apply it
     * @param e the Drawables Object to apply the filter
     */
    public void applied(Drawable e){
        if(this.active.get() && aClass.isInstance(e)){
            if(e.getType().isPresent(type, value)){
                e.setColor(color);
                e.setSize(size);
                e.setDisplayed(isDisplayed);
            }
        }
    }

    /**
     * getter for the class object
     * @return the class to filter
     */
    @Deprecated
    public Class<? extends Drawable> getClasse() {
        return aClass;
    }

    /**
     * getter for the class object
     * @return the class to filter
     */
    public Class<? extends Drawable> getAClass() {
        return aClass;
    }

    /**
     * getter for the type
     * @return the type to filter
     */
    public String getType() {
        return type;
    }

    /**
     * getter for the value
     * @return the value to filter
     */
    public String getValue() {
        return value;
    }

    /**
     * getter for the color
     * @return the color to filter
     */
    public Color getColor() {
        return color;
    }

    /**
     * getter for the BooleanProperty
     * @return the activeProperty to filter
     */
    public BooleanProperty activeProperty() {
        return active;
    }

    /**
     * getter for the BooleanProperty
     * @return the activeProperty to filter
     */
    @Deprecated
    public BooleanProperty actifProperty() {
        return active;
    }

    /**
     * getter for the size
     * @return the Size
     */
    public double getSize() {
        return size;
    }
}
