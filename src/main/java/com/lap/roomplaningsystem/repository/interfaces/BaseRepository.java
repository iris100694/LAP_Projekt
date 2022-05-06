package com.lap.roomplaningsystem.repository.interfaces;

import com.lap.roomplaningsystem.model.Course;
import javafx.collections.ObservableList;

import java.util.Optional;

public interface BaseRepository<T> {
    Optional<ObservableList<T>> readAll() throws Exception;


}
