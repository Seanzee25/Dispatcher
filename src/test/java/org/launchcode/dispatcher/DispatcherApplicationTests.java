package org.launchcode.dispatcher;

import org.junit.jupiter.api.Test;
import org.launchcode.dispatcher.models.WorkOrder;
import org.launchcode.dispatcher.models.WorkOrderStatus;
import org.launchcode.dispatcher.repositories.WorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.Calendar;
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
	    Collection<WorkOrder> workOrders = workOrderRepository.findAll(hasCustomerId(41));
        assertEquals(4, workOrders.size());
    }

    @Test
    void hasDateBetweenTest() {
        Calendar from = new Calendar.Builder()
                .setDate(2019, 0, 1)
                .build();
        Calendar to = new Calendar.Builder()
                .setDate(2020, 0, 1)
                .build();

	    Collection<WorkOrder> workOrders = workOrderRepository.findAll(hasDateBetween(from, to));
	    assertEquals(8, workOrders.size());

	    from.set(1991, 0, 1);
	    to.set(1992, 0, 1);
	    workOrders = workOrderRepository.findAll(hasDateBetween(from, to));
	    assertEquals(1, workOrders.size());
    }

    @Test
    void hasAssignedTechnicianTest() {

	    Collection<WorkOrder> workOrders = workOrderRepository.findAll(hasAssignedTechnician(38));

        assertEquals(1, workOrders.size());
	    assertEquals(83, ((List<WorkOrder>) workOrders).get(0).getId());

	    workOrders = workOrderRepository.findAll(hasAssignedTechnician(53));
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

}
