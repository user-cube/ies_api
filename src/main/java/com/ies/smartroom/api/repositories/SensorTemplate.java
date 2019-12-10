package com.ies.smartroom.api.repositories;

import com.ies.smartroom.api.entities.Co2;
import com.ies.smartroom.api.entities.Humidity;
import com.ies.smartroom.api.entities.Temperature;
import com.ies.smartroom.api.entities.internal.Average;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class SensorTemplate {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public SensorTemplate(MongoTemplate mongoTemplate){
        this.mongoTemplate=mongoTemplate;
    }

    public List<Average> getAverageRange(int home,String datefrom,String dateto,Class type){
        String search="temp";
        String collection="temperature";
        if (type == Co2.class){
            search="co2(ppm)";
            collection="co2";
        }
        if (type == Humidity.class) {
            search = "humidity";
            collection = "humidity";
        }
        MatchOperation matchOp = Aggregation.match(new Criteria().andOperator(
                Criteria.where("home").is(home),
                Criteria.where("timestamp").gt(datefrom),
                Criteria.where("timestamp").lt(dateto)
        ));
        GroupOperation groupOp = Aggregation.group("room","home").avg(search).as("average");

        Aggregation agg = Aggregation.newAggregation(matchOp,groupOp);
        AggregationResults<Average> res = mongoTemplate.aggregate(agg,collection, Average.class);

        Iterator it = res.iterator();
        List<Average> ret = new ArrayList<>();
        while (it.hasNext()){
            ret.add((Average) it.next());
        }
        return ret;
    }

    public List<Average> getAverageCo2(int home,String datefrom,String dateto){
        return this.getAverageRange(home,datefrom,dateto, Co2.class);
    }

}