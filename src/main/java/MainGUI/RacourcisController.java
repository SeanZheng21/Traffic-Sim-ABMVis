package MainGUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * the short cut Controller
 */
public class RacourcisController {

    /**
     * the pane
     */
    @FXML public Pane pane;

    //=====================================================
    //initialisation
    //=====================================================

    //=====================================================
    //setters
    //=====================================================

    /**
     * key bindings
     * @param keyBinding the bindings
     */
    public void setRaccourcis(HashMap<KeyCode, Pair<String, Consumer<KeyCode>>> keyBinding) {
        for(KeyCode code: keyBinding.keySet()){
            this.addRaccourcis(code, keyBinding.get(code).getKey());
        }
    }

    /**
     * wrote on the pane the information for one key binding
     * @param code KeyCode
     * @param key string description
     */
    private void addRaccourcis(KeyCode code, String key) {
        Label label = new Label(code.getChar() + " " + key);
        this.pane.getChildren().add(label);
    }

    //=====================================================
    //functions
    //=====================================================


    //=====================================================
    //FXML functions
    //=====================================================

}
