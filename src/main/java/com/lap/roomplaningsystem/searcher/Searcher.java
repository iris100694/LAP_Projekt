package com.lap.roomplaningsystem.searcher;

import com.lap.roomplaningsystem.model.Event;
import javafx.collections.ObservableList;

public interface Searcher<T> {

    ObservableList<T> search(String s, ObservableList<T> list);
}
