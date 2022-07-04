package com.lap.roomplaningsystem.converter;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Location;
import com.lap.roomplaningsystem.model.Program;
import com.lap.roomplaningsystem.model.Room;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class RoomConverter implements ConverterInterface<Room>{
    private ObservableList<Room> roomList;
    private ComboBox<Location> box;

    public RoomConverter(ComboBox<Location> box) {
        this.roomList = BaseController.model.getDataholder().getRooms();
        this.box = box;


//        roomList.addListener(new ListChangeListener<Room>() {
//            @Override
//            public void onChanged(Change<? extends Room> change) {
//                updateList(roomList);
//            }
//        });
    }

    public void updateList(ObservableList<Room> list) {
        this.roomList = list;
    }


    @Override
    public void setConverter(ComboBox<Room> box) {

        box.setConverter(new StringConverter<Room>() {
            @Override
            public String toString(Room room) {
                return room == null ? "Raum" : room.getDescription();
            }

            @Override
            public Room fromString(String s) {
                return matchByString(s);
            }
        });
    }

    @Override
    public Room matchByString(String s) {
        ObservableList<Room> rooms = listOfLocation(roomList);
        for(Room r : rooms){
            if(r.getDescription().equals(s)){
                return r;
            }
        }
        return null;
    }

    private ObservableList<Room> listOfLocation(ObservableList<Room> list) {
        list.removeIf(r -> r.getLocation() != box.getValue());
        return list;
    }
}
