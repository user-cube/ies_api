package com.ies.smartroom.api.service;

import com.ies.smartroom.api.entities.Co2;
import com.ies.smartroom.api.entities.Temperature;
import com.ies.smartroom.api.entities.internal.Average;
import com.ies.smartroom.api.entities.internal.Sensor;
import com.ies.smartroom.api.repositories.SensorRepository;
import com.ies.smartroom.api.repositories.SensorTemplate;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDate;
import java.util.*;


public abstract class SensorService {

    private SensorRepository sensorRepository;

    @Autowired
    private SensorTemplate sensorTemplate;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<?> findAll(long home) {
        return sensorRepository.findByHome(home);
    }

    public List<?> getByDay(String date, long home) {
        try {
            LocalDate localdate = LocalDate.parse(date);
            List<Sensor> sensors = new ArrayList<>();

            for (Object o : sensorRepository.findByHome(home)) {
                Sensor TNext = (Sensor) o;
                LocalDate dateNext = LocalDate.parse(TNext.getDate());
                if (dateNext.isEqual(localdate)) {         // if is equal or higher than the selected date
                    sensors.add(TNext);
                }
            }
            return sensors;
        } catch (Exception e) {
            return null;
        }
    }

    public List<?> getNow(long home) {
        return getByDay(LocalDate.now().toString(), home);
    }

    public List<?> getByDateRange(String from, String to, long home) {
        LocalDate dateTo;
        try {
            dateTo = LocalDate.parse(to);
        } catch (Exception ex) {
            dateTo = LocalDate.now();
        }
        try {
            LocalDate dateFrom = LocalDate.parse(from);
            List<Sensor> sensors = new ArrayList<>();

            for (Object o : sensorRepository.findByHome(home)) {
                Sensor sensorNext = (Sensor) o;
                LocalDate dateNext = LocalDate.parse(sensorNext.getDate());
                if (!(dateNext.isBefore(dateFrom) || dateNext.isAfter(dateTo))) {         // if is equal or higher than the selected date
                    sensors.add(sensorNext);
                }
            }
            return sensors;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<?> getLastWeek(long home) {
        LocalDate today = LocalDate.now();
        String to = today.toString();
        String from = today.minusDays(7).toString();
        return getByDateRange(from, to, home);
    }

    public List<Average> getAverageDate(String from, String to, long home, Class type) {
        try {
            LocalDate datefrom=LocalDate.parse(from);
            LocalDate dateto=LocalDate.parse(to);
            List<Average> results=new ArrayList<>();
            if (dateto.isBefore(datefrom))
                return null;

            while (datefrom.isBefore(dateto)){
                Average daily = this.getAverageDay(datefrom.toString(),home,type);
                if (daily != null)
                    results.add(daily);
                datefrom=datefrom.plusDays(1);
            }
            return results;
        } catch (
                Exception ex) {
            return null;
        }
    }

    public List<Average> getAverageWeek(long home,Class type) {
        LocalDate today = LocalDate.now();
        String to = today.toString();
        String from = today.minusDays(7).toString();
        return getAverageDate(from, to, home,type);
    }

    public Average getAverageDay(String day, long home,Class type) {
        try {
            LocalDate localdate = LocalDate.parse(day);
            LocalDate next = localdate.plusDays(1);
            Average result = null;
            if (type == Co2.class){
                result=sensorTemplate.getAverageCo2((int)home,day+" 00:00:00",next.toString()+" 00:00:00").get(0);
            }
            else if (type == Temperature.class)
                result=sensorTemplate.getAverageTemp((int)home,day+" 00:00:00",next.toString()+" 00:00:00").get(0);
            result.setPeriod(day);
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    public Average getAverageToday(long home,Class type) {
        return getAverageDay(LocalDate.now().toString(), home,type);
    }

    private Map<String, double[]> HashCount(LocalDate from, LocalDate to) {
        Map<String, double[]> hashMap = new HashMap<>();
        while (!from.isEqual(to.plusDays(1))) {
            double[] newDouble = {0, 0};
            hashMap.put(from.toString(), newDouble);
            from = from.plusDays(1);
        }
        return hashMap;
    }
}