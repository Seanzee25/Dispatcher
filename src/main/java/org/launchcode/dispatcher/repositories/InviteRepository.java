package org.launchcode.dispatcher.repositories;

import org.launchcode.dispatcher.models.Invite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {

}
