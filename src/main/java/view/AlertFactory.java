package view;

import javafx.scene.control.Alert;

/**
 * Created by Donghwan on 2016-11-23.
 */
public interface AlertFactory {
    Alert newErrorAlert(String type);
    Alert newConfirmationAlert(String type);
}
