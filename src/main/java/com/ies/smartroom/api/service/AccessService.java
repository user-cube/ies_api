package com.ies.smartroom.api.service;

import com.ies.smartroom.api.entities.Access;
import com.ies.smartroom.api.entities.Credential;
import com.ies.smartroom.api.repositories.AccessRepository;
import com.ies.smartroom.api.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.HTMLDocument;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.ListIterator;

@Service
public class AccessService {

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private CredentialRepository credentialRepository;


    public List<Access> findAll(long home) {
        return accessRepository.findByHome(home);
    }

    public List<Access> getByDate(String date_start,String date_end, long home) {

        try {
            String aux_start = StringToStamp(date_start).toString();
            String aux_end = StringToStamp(date_end).toString();
            System.out.println(aux_start+" "+aux_end);
            return accessRepository.findByDate(home,aux_start,aux_end);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Access> today(long home) {
        return getByDate(LocalDate.now().toString(),LocalDate.now().plusDays(1).toString(), home);
    }

    public List<Access> getByDateRange(String from, String to, long home) {
        List<Access> acs;
        try{
            acs=getByDate(from,to,home);
            return acs;
        } catch (Exception ex){
            return null;
        }
    }

    public List<Access> getLastWeek(long home) {
        LocalDate today = LocalDate.now();
        String to = today.toString();
        String from = today.minusDays(7).toString();
        return getByDate(from,to,home);
    }

    private Timestamp StringToStamp(String date){
        String tDate = date +" 00:00:00";
        return Timestamp.valueOf(tDate);
    }
    public List<Access> getUnauthorizedAcess(long home){
        return accessRepository.findLastUnauthorized(home);
    }

    public Credential checkCredentials(long home, String cart_id){
         ListIterator<Credential> credentialListIterator = credentialRepository.findByCredetialAndHome(home, cart_id).listIterator();
        if (credentialListIterator.hasNext())return credentialListIterator.next()
                ;
        return null;
    }

    public Credential SaveCredential(Credential credential){
        Credential credentialSave = credentialRepository.save(credential);
        return credentialSave;
    }

}
