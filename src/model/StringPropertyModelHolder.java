package model;

import javafx.beans.property.StringProperty;

/**
 * Created by Donghwan on 5/24/2016.
 */
public interface StringPropertyModelHolder {
    void setContent(String content);
    StringProperty getTextAreaProperty();
}