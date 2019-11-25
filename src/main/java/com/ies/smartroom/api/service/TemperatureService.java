package com.ies.smartroom.api.service;

import com.ies.smartroom.api.entities.Temperature;
import com.ies.smartroom.api.repositories.TemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class TemperatureService {

    @Autowired
    private TemperatureRepository temperatureRepository;

    public List<Temperature> findAll(long home) {
        List<Temperature> temp = temperatureRepository.findByHome(home);
        return temp;
    }

    public List<Temperature> getByDate(String date, long home) {

        try {
            Timestamp localdate = StringToStamp(date);
            List<Temperature> temps = new ArrayList<>();
            Iterator<Temperature> tempIterator = temperatureRepository.findByHome(home).iterator();

            while (tempIterator.hasNext()) {
                Temperature temperatureNext = tempIterator.next();
                Timestamp dateNext = Timestamp.valueOf(temperatureNext.getTimestamp());
                if (!dateNext.before(localdate)) {         // if is equal or higher than the selected date
                    temps.add(temperatureNext);
                }
            }
            return temps;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Temperature> getNow(long home) {
        return getByDate(LocalDate.now().toString(), home);
    }

    public List<Temperature> getByDateRange(String from, String to, long home) {
        Timestamp dateTo = null;
        try{
            dateTo = StringToStamp(to);
        }
        catch (Exception ex){
            dateTo = StringToStamp(LocalDate.now().toString());
        }
        try{
            Timestamp dateFrom = StringToStamp(from);
            List<Temperature> temps = new ArrayList<>();
            Iterator<Temperature> tempIterator = temperatureRepository.findByHome(home).iterator();

            while (tempIterator.hasNext()) {
                Temperature temperatureNext = tempIterator.next();
                Timestamp dateNext = Timestamp.valueOf(temperatureNext.getTimestamp());
                if (!(dateNext.before(dateFrom) && dateNext.after(dateTo))) {         // if is equal or higher than the selected date
                    temps.add(temperatureNext);
                }
            }
            return temps;
        }
        catch (Exception ex){
            return null;
        }
    }
    public List<Temperature> getLastWeek(long home) {
        LocalDate today = LocalDate.now();
        String to = today.toString();
        String from = today.minusDays(7).toString();
        return getByDateRange(from,to,home);
    }


    private Timestamp StringToStamp(String date){
        String tDate = date +" 00:00:00";
        return Timestamp.valueOf(tDate);
    }
}
