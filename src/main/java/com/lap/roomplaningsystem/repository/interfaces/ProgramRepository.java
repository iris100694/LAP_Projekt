package com.lap.roomplaningsystem.repository.interfaces;

import com.lap.roomplaningsystem.model.Event;
import com.lap.roomplaningsystem.model.Program;
import com.lap.roomplaningsystem.model.User;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Optional;

public interface ProgramRepository extends BaseRepository{

    Optional<ObservableList<Program>> readAll() throws Exception;

    Program add(String description) throws Exception;

    void update(Program program) throws SQLException;

    void delete(Program program) throws SQLException;

}
