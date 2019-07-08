package org.launchcode.dispatcher;

import org.junit.jupiter.api.Test;
import org.launchcode.dispatcher.models.Business;
import org.launchcode.dispatcher.models.User;
import org.launchcode.dispatcher.repositories.BusinessRepository;
import org.launchcode.dispatcher.repositories.UserRepository;
import org.launchcode.dispatcher.searchFilters.EmployeeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.launchcode.dispatcher.repositories.specifications.EmployeeSpecifications.*;

@SpringBootTest
public class EmployeeSpecificationTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BusinessRepository businessRepository;

    @Test
    void byUsernameTest() {
        Collection<User> users = userRepository.findAll(byUsername("tech"));
        assertEquals(38, users.iterator().next().getId());
    }

    @Test
    void byBusinessTest() {
        Business business = businessRepository.getOne((long) 36);
        Collection<User> users = userRepository.findAll(byBusiness(business));
        assertEquals(3, users.size());
    }

    @Test
    void byFilterTest() {
        EmployeeFilter filter = new EmployeeFilter();
        filter.setUsername("owner2");
        Collection<User> users = userRepository.findAll(byFilter(filter));
        assertEquals(1, users.size());
        assertEquals(42, users.iterator().next().getId());
    }

}
