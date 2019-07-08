package org.launchcode.dispatcher.repositories;

import org.launchcode.dispatcher.models.Business;
import org.launchcode.dispatcher.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    Collection<Customer> findAllByBusiness(Business business);
}
