package com.lap.roomplaningsystem.converter;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Course;
import com.lap.roomplaningsystem.model.Equipment;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class EquipmentConverter implements ConverterInterface<Equipment> {
    private ObservableList<Equipment> equipmentList;


    public EquipmentConverter() {
        this.equipmentList = BaseController.model.getDataholder().getEquipments();

//        equipmentList.addListener(new ListChangeListener<Equipment>() {
//            @Override
//            public void onChanged(Change<? extends Equipment> change) {
//                updateList(equipmentList);
//            }
//        });
    }

    @Override
    public void updateList(ObservableList<Equipment> list) {
        this.equipmentList = list;
    }

    @Override
    public void setConverter(ComboBox<Equipment> box) {

        box.setConverter(new StringConverter<Equipment>() {
            @Override
            public String toString(Equipment equipment) {
                return equipmentList == null ? "Equipment" : equipment.getDescription();
            }

            @Override
            public Equipment fromString(String s) {
                return matchByString(s);
            }
        });
    }

    @Override
    public Equipment matchByString(String s) {
        for(Equipment e : equipmentList){
            if (e.getDescription().equals(s)){
                return e;
            }
        }
        return null;
    }

}
