package org.launchcode.dispatcher.searchFilters;

import org.launchcode.dispatcher.models.WorkOrderStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

public class WorkOrderFilter {

    private LocalDate toDate;
    private LocalDate fromDate;
    private long customerId;
    private long technicianId;
    private WorkOrderStatus status;
    private String address;
    private String phoneNumber;
    private String contact;
    private LocalTime fromTime;
    private LocalTime toTime;

    private Collection<WorkOrderStatus> statuses;

    public WorkOrderFilter() {
        statuses = new ArrayList<>();
        statuses.add(WorkOrderStatus.CREATED);
        statuses.add(WorkOrderStatus.STARTED);
        statuses.add(WorkOrderStatus.FINISHED);
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

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(long technicianId) {
        this.technicianId = technicianId;
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
