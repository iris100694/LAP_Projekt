package com.lap.roomplaningsystem.app;

public class Constants {
    public static final String PATH_TO_HOME_VIEW = "/com/lap/roomplaningsystem/views/home-view.fxml";
    public static final String PATH_TO_EVENT_VIEW = "/com/lap/roomplaningsystem/views/event-view.fxml";
    public static final String PATH_TO_ROOM_VIEW = "/com/lap/roomplaningsystem/views/room-view.fxml";
    public static final String PATH_TO_CREATE_EVENT_VIEW = "/com/lap/roomplaningsystem/views/eventCreate-view.fxml";
    public static final String PATH_TO_STAMMDATA_VIEW = "/com/lap/roomplaningsystem/views/stammdata-view.fxml";
    public static final String PATH_TO_LOGIN_VIEW = "/com/lap/roomplaningsystem/views/login-view.fxml";

    public static final String PATH_TO_EVENT_DETAIL_VIEW = "/com/lap/roomplaningsystem/views/detailViews/eventDetail-view.fxml";
    public static final String PATH_TO_ROOM_DETAIL_VIEW = "/com/lap/roomplaningsystem/views/detailViews/roomDetail-view.fxml";
    public static final String PATH_TO_COURSE_DETAIL_VIEW = "/com/lap/roomplaningsystem/views/detailViews/courseDetail-view.fxml";
    public static final String PATH_TO_PROGRAM_DETAIL_VIEW = "/com/lap/roomplaningsystem/views/detailViews/programDetail-view.fxml";
    public static final String PATH_TO_LOCATION_DETAIL_VIEW = "/com/lap/roomplaningsystem/views/detailViews/locationDetail-view.fxml";
    public static final String PATH_TO_EQUIPMENT_DETAIL_VIEW = "/com/lap/roomplaningsystem/views/detailViews/equipmentDetail-view.fxml";
    public static final String PATH_TO_ROOMEQUIPMENT_DETAIL_VIEW = "/com/lap/roomplaningsystem/views/detailViews/roomEquipmentDetail-view.fxml";
    public static final String PATH_TO_USER_DETAIL_VIEW = "/com/lap/roomplaningsystem/views/detailViews/userDetail-view.fxml";

    public static final String PATH_TO_EVENT_ON_UPDATE_VIEW = "/com/lap/roomplaningsystem/views/detailViewsOnUpdate/eventDetailOnUpdate-view.fxml";
    public static final String PATH_TO_COURSE_UPDATE_VIEW = "/com/lap/roomplaningsystem/views/detailViewsOnUpdate/courseDetailOnUpdate-view.fxml";
    public static final String PATH_TO_ROOM_UPDATE_VIEW = "/com/lap/roomplaningsystem/views/detailViewsOnUpdate/roomDetailOnUpdate-view.fxml";
    public static final String PATH_TO_LOCATION_UPDATE_VIEW = "/com/lap/roomplaningsystem/views/detailViewsOnUpdate/locationDetailOnUpdate-view.fxml";
    public static final String PATH_TO_PROGRAM_UPDATE_VIEW = "/com/lap/roomplaningsystem/views/detailViewsOnUpdate/programDetailOnUpdate-view.fxml";
    public static final String PATH_TO_EQUIPMENT_UPDATE_VIEW = "/com/lap/roomplaningsystem/views/detailViewsOnUpdate/equipmentDetailOnUpdate-view.fxml";
    public static final String PATH_TO_ROOMEQUIPMENT_UPDATE_VIEW = "/com/lap/roomplaningsystem/views/detailViewsOnUpdate/roomEquipmentDetailOnUpdate-view.fxml";
    public static final String PATH_TO_USER_UPDATE_VIEW = "/com/lap/roomplaningsystem/views/detailViewsOnUpdate/userDetailOnUpdate-view.fxml";
    public static final String PATH_TO_SUCCESSFUL_UPDATE = "/com/lap/roomplaningsystem/views/detailViewsOnUpdate/successfulUpdate-view.fxml";

    public static final String PATH_TO_EVENT_ON_DELETE_VIEW = "/com/lap/roomplaningsystem/views/detailViewOnDelete/eventDetailOnDelete-view.fxml";

    public static final String PATH_TO_PROGRAM_ADD_VIEW = "/com/lap/roomplaningsystem/views/detailViewsOnAdd/programDetailOnAdd-view.fxml";
    public static final String PATH_TO_COURSE_ADD_VIEW = "/com/lap/roomplaningsystem/views/detailViewsOnAdd/courseDetailOnAdd-view.fxml";
    public static final String PATH_TO_LOCATION_ADD_VIEW = "/com/lap/roomplaningsystem/views/detailViewsOnAdd/locationDetailOnAdd-view.fxml";
    public static final String PATH_TO_EQUIPMENT_ADD_VIEW = "/com/lap/roomplaningsystem/views/detailViewsOnAdd/equipmentDetailOnAdd-view.fxml";
    public static final String PATH_TO_ROOMEQUIPMENT_ADD_VIEW = "/com/lap/roomplaningsystem/views/detailViewsOnAdd/roomEquipmentDetailOnAdd-view.fxml";
    public static final String PATH_TO_USER_ADD_VIEW = "/com/lap/roomplaningsystem/views/detailViewsOnAdd/userDetailOnAdd-view.fxml";
    public static final String PATH_TO_ROOM_ADD_VIEW = "/com/lap/roomplaningsystem/views/detailViewsOnAdd/roomDetailOnAdd-view.fxml";



    public static final String PATH_TO_EVENT_TABLE_VIEW = "/com/lap/roomplaningsystem/views/tableViews/eventTable.fxml";
    public static final String PATH_TO_ROOM_TABLE_VIEW = "/com/lap/roomplaningsystem/views/tableViews/roomTable.fxml";
    public static final String PATH_TO_EQUIPMENT_TABLE_VIEW = "/com/lap/roomplaningsystem/views/tableViews/equipmentTable.fxml";
    public static final String PATH_TO_PROGRAM_TABLE_VIEW = "/com/lap/roomplaningsystem/views/tableViews/programTable.fxml";
    public static final String PATH_TO_COURSE_TABLE_VIEW = "/com/lap/roomplaningsystem/views/tableViews/courseTable.fxml";
    public static final String PATH_TO_LOCATION_TABLE_VIEW = "/com/lap/roomplaningsystem/views/tableViews/locationTable.fxml";
    public static final String PATH_TO_ROOMEQUIPMENT_TABLE_VIEW = "/com/lap/roomplaningsystem/views/tableViews/roomEquipmentTable.fxml";
    public static final String PATH_TO_USER_TABLE_VIEW = "/com/lap/roomplaningsystem/views/tableViews/userTable.fxml";

    public static final String PATH_TO_REQUEST_VIEW = "/com/lap/roomplaningsystem/views/request-view.fxml";
    public static final String PATH_TO_ROOM_REQUEST_RESULT_VIEW = "/com/lap/roomplaningsystem/views/requestResult-view.fxml";


    public static final String CALL_LISTS_FOR_CHOICEBOX_ROOM_FILTER = "{CALL ListsForChoiceBoxRoomFilter()}";
    public static final String CALL_LISTS_FOR_CHOICEBOX_EVENT_FILTER = "{CALL ListsForChoiceBoxEventFilter()}";

    public static final String ROOM_BASE_FILTER = "SELECT rooms.ROOMID, rooms.DESCRIPTION, rooms.MAXPERSONS, rooms.PHOTO, locations.LOCATIONID, locations.DESCRIPTION AS \"LDESCRIPTION\", locations.ADRESS, locations.POSTCODE, locations.CITY FROM rooms LEFT JOIN locations ON rooms.LOCATIONID = locations.LOCATIONID ";
    public  static final String ROOM_EQUIPMENT_FILTER = "SELECT rooms.ROOMID, rooms.DESCRIPTION, rooms.MAXPERSONS, rooms.PHOTO, locations.LOCATIONID, locations.DESCRIPTION AS \"LDESCRIPTION\", locations.ADRESS, locations.POSTCODE, locations.CITY FROM rooms LEFT JOIN locations ON rooms.LOCATIONID = locations.LOCATIONID INNER JOIN roomequipment ON rooms.ROOMID = roomequipment.ROOMID INNER JOIN equipment ON roomequipment.EQUIPMENTID = equipment.EQUIPMENTID";

    public  static final String EVENTS_BASE_FILTER = "SELECT events.EVENTID, events.CREATORID, rooms.ROOMID, rooms.DESCRIPTION AS \"ROOMDESCRIPTION\", locations.LOCATIONID, locations.DESCRIPTION AS \"LOCATIONDESCRIPTION\", locations.ADRESS, locations.POSTCODE, locations.CITY, rooms.MAXPERSONS, rooms.PHOTO, course.COURSEID, program.PROGRAMID, program.DESCRIPTION AS \"PROGRAMDESCRIPTION\", course.TITLE, course.MEMBERS, course.START AS \"COURSESTART\", course.END AS \"COURSEEND\", events.COACHID, events.START, events.END FROM events LEFT JOIN rooms ON events.ROOMID = rooms.ROOMID INNER JOIN locations ON rooms.LOCATIONID = locations.LOCATIONID LEFT JOIN course ON events.COURSEID = course.COURSEID INNER JOIN program ON course.PROGRAMID = program.PROGRAMID ";


    public static final String CALL_LISTS_FOR_REQUEST_COMBOBOXES = "{CALL listsForRequest()}";
   public static final String CALL_LISTS_FOR_REQUEST_ROOMS = "{CALL listsForRequestRooms(?)}";



}
