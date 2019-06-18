package org.launchcode.dispatcher.repositories;

import org.launchcode.dispatcher.models.Business;
import org.launchcode.dispatcher.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Collection<Customer> findAllByBusiness(Business business);
}
