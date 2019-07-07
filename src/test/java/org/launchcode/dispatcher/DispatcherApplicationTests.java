package org.launchcode.dispatcher;

import org.junit.jupiter.api.Test;
import org.launchcode.dispatcher.models.WorkOrder;
import org.launchcode.dispatcher.models.WorkOrderStatus;
import org.launchcode.dispatcher.repositories.WorkOrderRepository;
import org.launchcode.dispatcher.searchFilters.WorkOrderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.launchcode.dispatcher.repositories.specifications.WorkOrderSpecifications.*;

@SpringBootTest
class DispatcherApplicationTests {

	@Autowired
	private WorkOrderRepository workOrderRepository;

	@Test
    void hasCustomerIdTest() {
	    Collection<WorkOrder> workOrders = workOrderRepository.findAll(hasCustomerName("Customer"));
        assertEquals(4, workOrders.size());
    }

    @Test
    void hasDateBetweenTest() {
        LocalDate from = LocalDate.of(2019, 1, 1);
        LocalDate to = LocalDate.of(2020, 1, 1);

	    Collection<WorkOrder> workOrders = workOrderRepository.findAll(hasDateBetween(from, to));
	    assertEquals(8, workOrders.size());

	    from = LocalDate.of(1991, 1, 1);
	    to = LocalDate.of(1992, 1, 1);

	    workOrders = workOrderRepository.findAll(hasDateBetween(from, to));
	    assertEquals(1, workOrders.size());
    }

    @Test
    void hasAssignedTechnicianTest() {

	    Collection<WorkOrder> workOrders = workOrderRepository.findAll(hasAssignedTechnicianName("tech"));

        assertEquals(1, workOrders.size());
	    assertEquals(83, ((List<WorkOrder>) workOrders).get(0).getId());

	    workOrders = workOrderRepository.findAll(hasAssignedTechnicianName("tech4"));
	    assertEquals(0, workOrders.size());
    }

    @Test
    void hasStatusTest() {
	    Collection<WorkOrder> workOrders = workOrderRepository.findAll(hasStatus(WorkOrderStatus.CREATED));
	    assertEquals(10, workOrders.size());

	    workOrders = workOrderRepository.findAll(hasStatus(WorkOrderStatus.FINISHED));
	    assertEquals(0, workOrders.size());
    }

    @Test
    void hasAddressTest() {
        Collection<WorkOrder> workOrders = workOrderRepository.findAll(hasAddress("dress"));
        assertEquals(4, workOrders.size());

        workOrders = workOrderRepository.findAll(hasAddress("Research Blvd"));
        assertEquals(2, workOrders.size());

        workOrders = workOrderRepository.findAll(hasAddress("950"));
        assertEquals(4, workOrders.size());
    }

    @Test
    void hasPhoneNumberTest() {
        Collection<WorkOrder> workOrders = workOrderRepository.findAll(hasPhoneNumber("555-555-5555"));
        assertEquals(4, workOrders.size());

        workOrders = workOrderRepository.findAll(hasPhoneNumber(""));
        assertEquals(10, workOrders.size());

        workOrders = workOrderRepository.findAll(hasPhoneNumber("314"));
        assertEquals(2, workOrders.size());
    }

    @Test
    void hasContactTest() {
        Collection<WorkOrder> workOrders = workOrderRepository.findAll(hasContact("Mark"));
        assertEquals(2, workOrders.size());

        workOrders = workOrderRepository.findAll(hasContact("John"));
        assertEquals(4, workOrders.size());
    }

    @Test
    void hasTimeBetweenTest() {
        LocalTime from = LocalTime.of(5, 0);
        LocalTime to = LocalTime.of(10, 0);

        Collection<WorkOrder> workOrders = workOrderRepository.findAll(hasTimeBetween(from, to));
        assertEquals(3, workOrders.size());

        from = LocalTime.of(0, 0);
        to = LocalTime.of(0, 0);

        workOrders = workOrderRepository.findAll(hasTimeBetween(from, to));
        assertEquals(1, workOrders.size());
    }

    @Test
    void compoundQueryTest() {
	    long expectedId = 83;
        String customerName = "Customer";
        String technicianName = "tech";
        LocalDate fromDate = LocalDate.of(1991, 6, 1);
        LocalDate toDate = LocalDate.of(1991, 6, 11);
        LocalTime fromTime = LocalTime.of(6, 0);
        LocalTime toTime = LocalTime.of(8, 0);
        WorkOrderStatus status = WorkOrderStatus.CREATED;
        String address = "Address";
        String contact = "John Smith";
        String phoneNumber = "555-555-5555";

        Collection<WorkOrder> workOrders = workOrderRepository.findAll(
                hasCustomerName(customerName)
                .and(hasDateBetween(fromDate, toDate))
                .and(hasTimeBetween(fromTime, toTime))
                .and(hasStatus(status))
                .and(hasAddress(address))
                .and(hasContact(contact))
                .and(hasPhoneNumber(phoneNumber))
                .and(hasAssignedTechnicianName(technicianName))
        );

        assertEquals(1, workOrders.size());
        assertEquals(expectedId, ((List<WorkOrder>) workOrders).get(0).getId());

    }

    @Test
    void byExampleCompoundTest() {
        WorkOrderFilter filter = new WorkOrderFilter();
        filter.setAddress("address");
        filter.setContact("John Smith");
        filter.setFromDate(LocalDate.of(2019, 6, 7));
        filter.setToDate(LocalDate.of(2019, 6, 29));

        Collection<WorkOrder> workOrders = workOrderRepository.findAll(byExample(filter));

        assertEquals(2, workOrders.size());
    }

    // TODO: Add customer name? Technician name? etc? description?
}
