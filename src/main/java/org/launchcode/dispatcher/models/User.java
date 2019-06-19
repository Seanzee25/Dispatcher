package org.launchcode.dispatcher.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;

    private String username;

    private String password;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"
            )
    )
    private Collection<Role> roles;

    @ManyToOne
    private Business business;

    @OneToMany
    @JoinColumn(name = "user_id")
    private Collection<Invite> invites;

    @ManyToMany(mappedBy = "technicians")
    private Collection<WorkOrder> workOrders;

    public User() {}

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public Collection<Invite> getInvites() {
        return invites;
    }

    public void setInvites(Collection<Invite> invites) {
        this.invites = invites;
    }

    public Collection<WorkOrder> getWorkOrders() {
        return workOrders;
    }

    public void setWorkOrders(Collection<WorkOrder> workOrders) {
        this.workOrders = workOrders;
    }
}
