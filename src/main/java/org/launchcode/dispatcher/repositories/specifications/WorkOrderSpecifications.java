package org.launchcode.dispatcher.repositories.specifications;

import org.launchcode.dispatcher.models.User;
import org.launchcode.dispatcher.models.WorkOrder;
import org.launchcode.dispatcher.models.WorkOrderStatus;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public class WorkOrderSpecifications {

    public static Specification<WorkOrder> hasCustomerName(String name) {
        return (root, query, cb) -> cb.like(root.get("customer").get("name"), "%"+name+"%");
    }

    public static Specification<WorkOrder> hasDateBetween(LocalDate from, LocalDate to) {
        if(from == null && to == null) {
            return (root, query, cb) -> cb.and();
        } else if(from == null) {
            return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("date").as(LocalDate.class), to);
        } else if(to == null) {
            return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("date").as(LocalDate.class), from);
        } else {
            return (root, query, cb) -> cb.between(root.get("date").as(LocalDate.class), from, to);
        }
    }

    public static Specification<WorkOrder> hasAssignedTechnicianName(String name) {
        if(name.equals("")) {
            // Searching empty string returns only WorkOrders that have assigned technicians
            // This skips the query in that case.
            return (root, query, criteriaBuilder) -> criteriaBuilder.and();
        }

        return (root, query, cb) -> {
            query.distinct(true);
            Root<WorkOrder> workOrder = root;
            Root<User> technician = query.from(User.class);
            Expression<Collection<WorkOrder>> technicianWorkOrders = technician.get("workOrders");
            return cb.and(cb.like(technician.get("username"), "%"+name+"%"), cb.isMember(workOrder, technicianWorkOrders));
        };
    }

    public static Specification<WorkOrder> hasStatus(WorkOrderStatus status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public static Specification<WorkOrder> hasAddress(String address) {
        return (root, query, cb) -> cb.like(root.get("customer").get("address"), "%"+address+"%");
    }

    public static Specification<WorkOrder> hasPhoneNumber(String phoneNumber) {
        return (root, query, cb) -> cb.like(root.get("customer").get("phoneNumber"), "%"+phoneNumber+"%");
    }

    public static  Specification<WorkOrder> hasContact(String contact) {
        return (root, query, cb) -> cb.like(root.get("customer").get("contact"), "%"+contact+"%");
    }

    public static Specification<WorkOrder> hasTimeBetween(LocalTime from, LocalTime to) {
        if(from == null && to == null) {
            return (root, query, cb) -> cb.and();
        } else if(from == null) {
            return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("startTime").as(LocalTime.class), to);
        } else if(to == null) {
            return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("startTime").as(LocalTime.class), from);
        } else {
            return (root, query, cb) -> cb.between(root.get("startTime").as(LocalTime.class), from, to);
        }
    }
}
