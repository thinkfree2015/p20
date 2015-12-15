package com.efeiyi.ec.courier.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/10.
 *
 */
@Entity
@Table(name = "ef_company_freight")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class CompanyFreight implements Serializable {
    private String id;
    private String price;
    private String weight;
    private String times;
    private String from;
    private String to;
    private String name;

    @Id
    @GenericGenerator(name = "id", strategy = "com.ming800.core.p.model.M8idGenerator")
    @GeneratedValue(generator = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Column(name = "price")
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    @Column(name = "weight")
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
    @Column(name = "times")
    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
    @Column(name = "from_city")
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
    @Column(name = "to_city")
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CompanyFreight{" +
                "id='" + id + '\'' +
                ", price='" + price + '\'' +
                ", weight='" + weight + '\'' +
                ", times='" + times + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
