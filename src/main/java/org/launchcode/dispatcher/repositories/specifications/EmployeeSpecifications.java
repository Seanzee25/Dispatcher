package org.launchcode.dispatcher.repositories.specifications;

import org.launchcode.dispatcher.models.Business;
import org.launchcode.dispatcher.models.User;
import org.launchcode.dispatcher.searchFilters.EmployeeFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class EmployeeSpecifications {

    public static Specification<User> byBusiness(Business business) {
        return (root, query, cb) -> cb.equal(root.get("business"), business);
    }

    public static Specification<User> byFilter(EmployeeFilter filter) {
        Collection<Specification<User>> specifications = getSpecificationsFromFilter(filter);
        Iterator<Specification<User>> it = specifications.iterator();

        if(!it.hasNext()) {
            return (root, query, cb) -> cb.and();
        }

        Specification<User> result = it.next();
        while(it.hasNext()) {
            result = result.and(it.next());
        }

        return result;
    }

    private static Collection<Specification<User>> getSpecificationsFromFilter(EmployeeFilter filter) {
        Collection<Specification<User>> specifications = new ArrayList<>();

        String username = filter.getUsername();
        if(username != null && !username.equals("")) {
            specifications.add(byUsername(username));
        }

        return specifications;
    }

    public static Specification<User> byUsername(String username) {
        return (root, query, cb) -> cb.like(root.get("username"), "%"+username+"%");
    }
}
