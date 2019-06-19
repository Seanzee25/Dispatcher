package org.launchcode.dispatcher.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class WorkOrder {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Business business;

    private String description;

    @ManyToMany()
    @JoinTable(
            name = "work_orders_users",
            joinColumns = @JoinColumn(
                    name = "workOrder_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"
            )
    )
    private Collection<User> technicians;

    public WorkOrder() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<User> getTechnicians() {
        return technicians;
    }

    public void setTechnicians(Collection<User> technicians) {
        this.technicians = technicians;
    }
}
