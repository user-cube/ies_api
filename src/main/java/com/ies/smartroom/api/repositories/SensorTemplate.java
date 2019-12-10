package com.ies.smartroom.api.repositories;

import com.ies.smartroom.api.entities.internal.Average;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public abstract class SensorTemplate {

    private final MongoTemplate mongoTemplate;

    public SensorTemplate(MongoTemplate mongoTemplate){
        this.mongoTemplate=mongoTemplate;
    }

    public List<Average> getAverageRange(int home,String datefrom,String dateto){
        String search="temp";
        String collection="temperature";
        if (this.getClass() == Co2Template.class){
            search="co2(ppm)";
            collection="co2";
        }
        if (this.getClass() == HumidityTemplate.class) {
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

}