package com.lap.roomplaningsystem.matcher;

import com.lap.roomplaningsystem.model.Location;
import javafx.collections.ObservableList;

import java.util.Optional;

public interface Matcher<T> {
    T matchByString(String s, ObservableList<T> list);

}
