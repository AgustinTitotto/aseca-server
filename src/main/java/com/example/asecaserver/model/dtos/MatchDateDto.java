package com.example.asecaserver.model.dtos;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class MatchDateDto {

    int gameDay;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    Date date;
    long homeTeamId;
    long awayTeamId;

    public MatchDateDto(int gameDay, Date date, long homeTeamId, long awayTeamId) {
        this.gameDay = gameDay;
        this.date = date;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
    }

    public MatchDateDto() {

    }

    public int getGameDay() {
        return gameDay;
    }

    public Date getDate() {
        return date;
    }

    public long getHomeTeamId() {
        return homeTeamId;
    }

    public long getAwayTeamId() {
        return awayTeamId;
    }

    // Custom deserializer for the Date field
    public static class CustomDateDeserializer extends JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode node = p.getCodec().readTree(p);
            int year = node.get("year").asInt();
            int month = node.get("month").asInt();
            int day = node.get("day").asInt();
            int hrs = node.get("hrs").asInt();
            int min = node.get("min").asInt();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, day, hrs, min);

            return calendar.getTime();
        }

    }
}



