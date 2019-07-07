package org.launchcode.dispatcher.repositories;

import org.launchcode.dispatcher.models.Business;
import org.launchcode.dispatcher.models.WorkOrder;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long>, JpaSpecificationExecutor<WorkOrder> {
    Collection<WorkOrder> findAllByBusiness(Business business);
    Collection<WorkOrder> findAllByBusiness(Business business, Sort sort);
}
