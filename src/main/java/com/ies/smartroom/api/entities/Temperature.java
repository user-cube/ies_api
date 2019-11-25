package com.ies.smartroom.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data @ToString @AllArgsConstructor
@Document(collection="temperature")
public class Temperature {

    @Id @JsonIgnore
    private String id;
    @Field("home") @Getter @Setter
    private long home;
    @Field("room") @Getter @Setter
    private String room;
    @Field("timestamp") @Getter @Setter
    private String timestamp;
    @Field("temp") @Getter @Setter
    private double temp;

    public long getHome() {
        return home;
    }

    public void setHome(long home) {
        this.home = home;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
    @JsonIgnore
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDate(){
        return this.timestamp.split(" ")[0];
    }
    public String getTime(){
        return this.timestamp.split(" ")[1];
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }


}