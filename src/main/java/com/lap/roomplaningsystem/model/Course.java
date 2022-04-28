package com.lap.roomplaningsystem.model;

import java.sql.Date;

public record Course (int courseID, Program program, String title, int members, Date start, Date end){

    @Override
    public int courseID() {
        return courseID;
    }

    @Override
    public Program program() {
        return program;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public int members() {
        return members;
    }

    @Override
    public Date start() {
        return start;
    }

    @Override
    public Date end() {
        return end;
    }
}
