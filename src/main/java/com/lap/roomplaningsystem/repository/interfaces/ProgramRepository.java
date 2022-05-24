package com.lap.roomplaningsystem.repository.interfaces;

import com.lap.roomplaningsystem.model.Program;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Optional;

public interface ProgramRepository extends BaseRepository{

    Optional<ObservableList<Program>> readAll() throws Exception;

    Program add(String description) throws Exception;

    boolean update(Program program) throws SQLException;

    boolean delete(Program program) throws SQLException;

}
