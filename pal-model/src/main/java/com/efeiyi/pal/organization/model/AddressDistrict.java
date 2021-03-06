package com.efeiyi.pal.organization.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2015/7/16.
 */

@Entity
@Table(name = "organization_address_district")
public class AddressDistrict implements Serializable {

    private String id;
    private String name;
    private AddressCity addressCity;

    @Id
    @GenericGenerator(name = "id", strategy = "com.ming800.core.p.model.M8idGenerator")
    @GeneratedValue(generator = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    @NotFound(action= NotFoundAction.IGNORE)
    public AddressCity getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(AddressCity addressCity) {
        this.addressCity = addressCity;
    }

}
