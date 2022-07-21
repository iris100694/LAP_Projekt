package com.lap.roomplanningsystem.converter;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public interface ConverterInterface<T>  {

    void setConverter(ComboBox<T> box);
    void updateList(ObservableList<T> list);
    T matchByString(String s);
}
