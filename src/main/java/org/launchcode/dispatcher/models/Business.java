package org.launchcode.dispatcher.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Business {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String address;

    @OneToOne
    private User owner;

    @OneToMany
    @JoinColumn(name = "business_id")
    private Collection<User> employees;

    @OneToMany
    @JoinColumn(name="business_id")
    private Collection<Invite> invites;

    @OneToMany
    @JoinColumn(name="business_id")
    private Collection<Customer> customers;

    @OneToMany
    @JoinColumn(name="business_id")
    private Collection<WorkOrder> workOrders;

    public Business() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Collection<User> getEmployees() {
        return employees;
    }

    public void setEmployees(Collection<User> employees) {
        this.employees = employees;
    }

    public Collection<Invite> getInvites() {
        return invites;
    }

    public void setInvites(Collection<Invite> invites) {
        this.invites = invites;
    }

    public Collection<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Collection<Customer> customers) {
        this.customers = customers;
    }

    public Collection<WorkOrder> getWorkOrders() {
        return workOrders;
    }

    public void setWorkOrders(Collection<WorkOrder> workOrders) {
        this.workOrders = workOrders;
    }
}
