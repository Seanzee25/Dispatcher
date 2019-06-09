package org.launchcode.dispatcher.repositories;

import org.launchcode.dispatcher.models.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    Business findByName(String name);
}
