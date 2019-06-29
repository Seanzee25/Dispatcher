package org.launchcode.dispatcher.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

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

    private WorkOrderStatus status;

    public WorkOrder() {
        status = WorkOrderStatus.CREATED;
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

    public WorkOrderStatus getStatus() {
        return status;
    }

    public void setStatus(WorkOrderStatus status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
