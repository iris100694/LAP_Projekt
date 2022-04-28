package com.lap.roomplaningsystem.app;

public class Constants {
    public static final String PATH_TO_HOME_VIEW = "/com/lap/roomplaningsystem/views/home-view.fxml";
    public static final String PATH_TO_EVENT_VIEW = "/com/lap/roomplaningsystem/views/event-view.fxml";
    public static final String PATH_TO_ROOM_VIEW = "/com/lap/roomplaningsystem/views/room-view.fxml";
    public static final String PATH_TO_CREATE_EVENT_VIEW = "/com/lap/roomplaningsystem/views/eventCreate-view.fxml";
    public static final String PATH_TO_STAMMDATA_VIEW = "/com/lap/roomplaningsystem/views/stammdata-view.fxml";
    public static final String PATH_TO_LOGIN_VIEW = "/com/lap/roomplaningsystem/views/login-view.fxml";

    public static final String PATH_TO_EVENT_DETAIL_VIEW = "/com/lap/roomplaningsystem/views/eventDetail-view.fxml";
    public static final String PATH_TO_ROOM_DETAIL_VIEW = "/com/lap/roomplaningsystem/views/roomDetail-view.fxml";

    public static final String CALL_LISTS_FOR_CHOICEBOX_ROOM_FILTER = "{CALL ListsForChoiceBoxRoomFilter()}";
    public static final String CALL_LISTS_FOR_CHOICEBOX_EVENT_FILTER = "{CALL ListsForChoiceBoxEventFilter()}";

    public static final String ROOM_BASE_FILTER = "SELECT rooms.ROOMID, rooms.DESCRIPTION, rooms.MAXPERSONS, rooms.PHOTO, locations.LOCATIONID, locations.DESCRIPTION AS \"LDESCRIPTION\", locations.ADRESS, locations.POSTCODE, locations.CITY FROM rooms LEFT JOIN locations ON rooms.LOCATIONID = locations.LOCATIONID ";

    public  static final String ROOM_EQUIPMENT_FILTER = "SELECT rooms.ROOMID, rooms.DESCRIPTION, rooms.MAXPERSONS, rooms.PHOTO, locations.LOCATIONID, locations.DESCRIPTION AS \"LDESCRIPTION\", locations.ADRESS, locations.POSTCODE, locations.CITY FROM rooms LEFT JOIN locations ON rooms.LOCATIONID = locations.LOCATIONID INNER JOIN roomequipment ON rooms.ROOMID = roomequipment.ROOMID INNER JOIN equipment ON roomequipment.EQUIPMENTID = equipment.EQUIPMENTID";

    public  static final String EVENTS_BASE_FILTER = "SELECT events.EVENTID, events.CREATORID, rooms.ROOMID, rooms.DESCRIPTION AS \"ROOMDESCRIPTION\", locations.LOCATIONID, locations.DESCRIPTION AS \"LOCATIONDESCRIPTION\", locations.ADRESS, locations.POSTCODE, locations.CITY, rooms.MAXPERSONS, rooms.PHOTO, course.COURSEID, program.PROGRAMID, program.DESCRIPTION AS \"PROGRAMDESCRIPTION\", course.TITLE, course.MEMBERS, course.START AS \"COURSESTART\", course.END AS \"COURSEEND\", events.COACHID, events.START, events.END FROM events LEFT JOIN rooms ON events.ROOMID = rooms.ROOMID INNER JOIN locations ON rooms.LOCATIONID = locations.LOCATIONID LEFT JOIN course ON events.COURSEID = course.COURSEID INNER JOIN program ON course.PROGRAMID = program.PROGRAMID ";
}
