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
@Table(name = "organization_address_city")
public class AddressCity implements Serializable {

    private String id;
    private String name;
    private AddressProvince addressProvince;

    @Id
    @GenericGenerator(name = "id", strategy = "com.ming800.core.p.model.M8idGenerator")
    @GeneratedValue(generator = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "city_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id")
    @NotFound(action= NotFoundAction.IGNORE)
    public AddressProvince getAddressProvince() {
        return addressProvince;
    }

    public void setAddressProvince(AddressProvince addressProvince) {
        this.addressProvince = addressProvince;
    }

}
