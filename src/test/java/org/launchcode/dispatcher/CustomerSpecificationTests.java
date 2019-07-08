package org.launchcode.dispatcher;

import org.junit.jupiter.api.Test;
import org.launchcode.dispatcher.models.Business;
import org.launchcode.dispatcher.models.Customer;
import org.launchcode.dispatcher.repositories.BusinessRepository;
import org.launchcode.dispatcher.repositories.CustomerRepository;
import org.launchcode.dispatcher.searchFilters.CustomerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.launchcode.dispatcher.repositories.specifications.CustomerSpecifications.*;

@SpringBootTest
public class CustomerSpecificationTests {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BusinessRepository businessRepository;

    @Test
    void byNameTest() {
        Collection<Customer> customers = customerRepository.findAll(byName("Customer"));
        assertEquals(2, customers.size());
    }

    @Test
    void byAddressTest() {
        Collection<Customer> customers = customerRepository.findAll(byAddress("address"));
        assertEquals(2, customers.size());
    }

    @Test
    void byContactTest() {
        Collection<Customer> customers = customerRepository.findAll(byContact("Mark"));
        assertEquals(70, customers.iterator().next().getId());
    }

    @Test
    void byPhoneNumberTest() {
        Collection<Customer> customers = customerRepository.findAll(byPhoneNumber("555"));
        assertEquals(41, customers.iterator().next().getId());
    }

    @Test
    void byFilterTest() {
        CustomerFilter filter = new CustomerFilter();
        filter.setAddress("address");
        filter.setContactName("oskdof");
        Collection<Customer> customers = customerRepository.findAll(byFilter(filter));
        assertEquals(44, customers.iterator().next().getId());

        filter.setPhoneNumber("2423");
        customers = customerRepository.findAll(byFilter(filter));
        assertEquals(0, customers.size());

        filter.setPhoneNumber("poksdofk");
        customers = customerRepository.findAll(byFilter(filter));
        assertEquals(1, customers.size());

        filter = new CustomerFilter();
        customers = customerRepository.findAll(byFilter(filter));
        assertEquals(7, customers.size());
    }

    @Test
    void byBusinessIdTest() {
        Business business = businessRepository.getOne((long) 36);
        Collection<Customer> customers = customerRepository.findAll(byBusiness(business));

        assertEquals(5, customers.size());
    }

}
