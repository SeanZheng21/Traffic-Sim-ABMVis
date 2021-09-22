package MainGUI;

import UIAbmModel.Drawable;
import javafx.scene.paint.Color;

/**
 * this is another definition for the filters to create default Filter to apply
 * on all the types and all possible values
 */
public class FilterDefault extends Filter {

    /**
     * New constructor with default values
     * @param aClass the class to filter
     * @param color the default Color
     */
    public FilterDefault(Class<? extends Drawable> aClass, Color color) {
        super(aClass, "*", "*", color, 1, true);
    }

    /**
     * new applied function to only check the class
     * @param e the Drawable Object on which the filter is applied
     */
    @Override
    public void applied(Drawable e) {
        if(this.getAClass().isInstance(e)){
                e.setColor(this.getColor());
                e.setSize(this.getSize());
                e.setDisplayed(true);
        }
    }
}
