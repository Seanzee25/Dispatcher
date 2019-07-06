package org.launchcode.dispatcher.searchFilters;

import org.launchcode.dispatcher.models.WorkOrderStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collection;

public class WorkOrderFilter {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate toDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate fromDate;

    private String customerName;
    private String technicianName;
    private WorkOrderStatus status;
    private String address;
    private String phoneNumber;
    private String contact;

    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TemporalType.TIME)
    private LocalTime fromTime;

    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TemporalType.TIME)
    private LocalTime toTime;

    private Collection<WorkOrderStatus> statuses;

    public WorkOrderFilter() {
        statuses = Arrays.asList(WorkOrderStatus.values());
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }

    public WorkOrderStatus getStatus() {
        return status;
    }

    public void setStatus(WorkOrderStatus status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public LocalTime getFromTime() {
        return fromTime;
    }

    public void setFromTime(LocalTime fromTime) {
        this.fromTime = fromTime;
    }

    public LocalTime getToTime() {
        return toTime;
    }

    public void setToTime(LocalTime toTime) {
        this.toTime = toTime;
    }

    public Collection<WorkOrderStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(Collection<WorkOrderStatus> statuses) {
        this.statuses = statuses;
    }
}