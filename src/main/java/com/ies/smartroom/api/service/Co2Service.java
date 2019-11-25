package com.ies.smartroom.api.service;

import com.ies.smartroom.api.entities.Co2;
import com.ies.smartroom.api.repositories.Co2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class Co2Service {

    @Autowired
    private Co2Repository co2Repository;

    public List<Co2> findAll(long home) {
        List<Co2> co2 = co2Repository.findByHome(home);
        return co2;
    }

    public List<Co2> getByDate(String date, long home) {

        try {
            Timestamp localdate = StringToStamp(date);
            List<Co2> co2s = new ArrayList<>();
            Iterator<Co2> co2Iterator = co2Repository.findByHome(home).iterator();

            while (co2Iterator.hasNext()) {
                Co2 Co2Next = co2Iterator.next();
                Timestamp dateNext = Timestamp.valueOf(Co2Next.getTimestamp());
                if (!dateNext.before(localdate)) {         // if is equal or higher than the selected date
                    co2s.add(Co2Next);
                }
            }
            return co2s;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Co2> getNow(long home) {
        return getByDate(LocalDate.now().toString(), home);
    }

    public List<Co2> getByDateRange(String from, String to, long home) {
        Timestamp dateTo = null;
        try{
            dateTo = StringToStamp(to);
        }
        catch (Exception ex){
            dateTo = StringToStamp(LocalDate.now().toString());
        }
        try{
            Timestamp dateFrom = StringToStamp(from);
            List<Co2> co2s = new ArrayList<>();
            Iterator<Co2> co2Iterator = co2Repository.findByHome(home).iterator();

            while (co2Iterator.hasNext()) {
                Co2 Co2Next = co2Iterator.next();
                Timestamp dateNext = Timestamp.valueOf(Co2Next.getTimestamp());
                if (!(dateNext.before(dateFrom) && dateNext.after(dateTo))) {         // if is equal or higher than the selected date
                    co2s.add(Co2Next);
                }
            }
            return co2s;
        }
        catch (Exception ex){
            return null;
        }
    }
    public List<Co2> getLastWeek(long home) {
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
