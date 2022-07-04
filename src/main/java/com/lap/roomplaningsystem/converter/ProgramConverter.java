package com.lap.roomplaningsystem.converter;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Program;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class ProgramConverter implements ConverterInterface<Program>{

    private ObservableList<Program> programList;


    public ProgramConverter() {
        this.programList = BaseController.model.getDataholder().getPrograms();

//        programList.addListener(new ListChangeListener<Program>() {
//            @Override
//            public void onChanged(Change<? extends Program> change) {
//                updateList(programList);
//            }
//        });
    }

    @Override
    public void updateList(ObservableList<Program> list) {
        this.programList = list;
    }


    @Override
    public void setConverter(ComboBox<Program> box) {

        box.setConverter(new StringConverter<Program>() {
            @Override
            public String toString(Program program) {
                return program == null ? "Programm" : program.getDescription();
            }

            @Override
            public Program fromString(String s) {
                return matchByString(s);
            }
        });
    }

    @Override
    public Program matchByString(String s) {
        for(Program p : programList){
            if (p.getDescription().equals(s)){
                return  p;
            }
        }
        return null;
    }


}
