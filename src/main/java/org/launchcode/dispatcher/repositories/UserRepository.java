package org.launchcode.dispatcher.repositories;

import org.launchcode.dispatcher.models.Business;
import org.launchcode.dispatcher.models.Role;
import org.launchcode.dispatcher.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByUsername(String username);
    List<User> findAllByBusinessIsNull();
    List<User> findAllByUsernameAndBusinessIsNull(String username);
    List<User> findAllByRolesAndBusiness(Role role, Business business);

}
