package org.launchcode.dispatcher.repositories.specifications;

import org.launchcode.dispatcher.models.Business;
import org.launchcode.dispatcher.models.Customer;
import org.launchcode.dispatcher.searchFilters.CustomerFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CustomerSpecifications {

    public static Specification<Customer> byBusiness(Business business) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<Customer> customer = root;
            Root<Business> rootBusiness = query.from(Business.class);
            Expression<Collection<Customer>> businessCustomers = rootBusiness.get("customers");
            return cb.and(cb.equal(rootBusiness.get("id"), business.getId()), cb.isMember(customer, businessCustomers));
        };
    }

    public static Specification<Customer> byFilter(CustomerFilter filter) {
        Collection<Specification<Customer>> specifications = getSpecificationsFromFilter(filter);
        Iterator<Specification<Customer>> it = specifications.iterator();

        if(!it.hasNext()) {
            return (root, query, cb) -> cb.and();
        }

        Specification<Customer> result = it.next();
        while(it.hasNext()) {
            result = result.and(it.next());
        }

        return result;
    }

    private static Collection<Specification<Customer>> getSpecificationsFromFilter(CustomerFilter filter) {
        Collection<Specification<Customer>> specifications = new ArrayList<>();

        String name = filter.getName();
        if(name != null && !name.equals("")) {
            specifications.add(byName(name));
        }
        String address = filter.getAddress();
        if(address != null && !address.equals("")) {
            specifications.add(byAddress(address));
        }
        String contact = filter.getContactName();
        if(contact != null && !contact.equals("")) {
            specifications.add(byContact(contact));
        }
        String phoneNumber = filter.getPhoneNumber();
        if(phoneNumber != null && !phoneNumber.equals("")) {
            specifications.add(byPhoneNumber(phoneNumber));
        }

        return specifications;
    }

    public static Specification<Customer> byName(String name) {
        return (root, query, cb) -> cb.like(root.get("name"), "%"+name+"%");
    }

    public static Specification<Customer> byAddress(String address) {
        return (root, query, cb) -> cb.like(root.get("address"), "%"+address+"%");
    }

    public static Specification<Customer> byContact(String contact) {
        return (root, query, cb) -> cb.like(root.get("contact"), "%"+contact+"%");
    }

    public static Specification<Customer> byPhoneNumber(String phoneNumber) {
        return (root, query, cb) -> cb.like(root.get("phoneNumber"), "%"+phoneNumber+"%");
    }

}
