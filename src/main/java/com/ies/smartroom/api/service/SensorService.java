package com.ies.smartroom.api.service;

import com.ies.smartroom.api.entities.internal.Average;
import com.ies.smartroom.api.entities.internal.Sensor;
import com.ies.smartroom.api.repositories.SensorRepository;

import java.time.LocalDate;
import java.util.*;


public abstract class SensorService {

    private SensorRepository sensorRepository;

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

    public List<Average> getAverageRange(String from, String to, long home) {
        LocalDate dateTo;
        try {
            dateTo = LocalDate.parse(to);
        } catch (Exception ex) {
            dateTo = LocalDate.now();
        }
        try {
            LocalDate dateFrom = LocalDate.parse(from);
            Iterator sensorIterator = sensorRepository.findByHome(home).iterator();

            Map<String, double[]> avg = HashCount(dateFrom, dateTo);

            Sensor sensorNext = null;
            while (sensorIterator.hasNext()) {
                sensorNext = (Sensor) sensorIterator.next();
                LocalDate dateNext = LocalDate.parse(sensorNext.getDate());
                if (!(dateNext.isBefore(dateFrom) || dateNext.isAfter(dateTo))) {         // if is equal or higher than the selected date

                    double[] avgHash = avg.get(sensorNext.getDate());
                    double[] putHash = {avgHash[0] + sensorNext.getValue(), avgHash[1] + 1.0};
                    avg.put(sensorNext.getDate(), putHash);
                }
            }
            if (sensorNext == null) {
                return null;
            }
            List<Average> listVariable = new ArrayList<>();
            while (!dateFrom.isEqual(dateTo.plusDays(1))) {
                double[] returnHash = avg.get(dateFrom.toString());
                Double avgDay;
                if (returnHash[0] == 0.0) {
                    avgDay = null;
                } else {
                    avgDay = returnHash[0] / returnHash[1];
                }
                listVariable.add(new Average(sensorNext.getHome(), sensorNext.getRoom(), dateFrom.toString(), avgDay));
                dateFrom = dateFrom.plusDays(1);
            }
            return listVariable;
        } catch (
                Exception ex) {
            return null;
        }
    }

    public List<Average> getAverageWeek(long home) {
        LocalDate today = LocalDate.now();
        String to = today.toString();
        String from = today.minusDays(7).toString();
        return getAverageRange(from, to, home);
    }

    public Average getAverageDay(String day, long home) {
        try {
            LocalDate localdate = LocalDate.parse(day);
            Iterator<Sensor> TIterator = sensorRepository.findByHome(home).iterator();
            double avg = 0;
            int count = 0;
            Sensor sensorNext = null;
            while (TIterator.hasNext()) {
                sensorNext = TIterator.next();
                LocalDate dateNext = LocalDate.parse(sensorNext.getDate());
                if (dateNext.isEqual(localdate)) {         // if is equal or higher than the selected date
                    avg += sensorNext.getValue();
                    count++;
                }
            }
            if (sensorNext == null || avg == 0) {
                return null;
            }
            return new Average(sensorNext.getHome(), sensorNext.getRoom(), day, avg / count);
        } catch (Exception ex) {
            return null;
        }
    }

    public Average getAverageToday(long home) {
        return getAverageDay(LocalDate.now().toString(), home);
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