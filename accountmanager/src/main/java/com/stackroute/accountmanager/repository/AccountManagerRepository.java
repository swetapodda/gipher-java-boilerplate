package com.stackroute.accountmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.accountmanager.model.GipherUser;


/**
 * 
 * JPA Repository for CRUD Operation of Gipher User
 * 
 * @author L.GANESH
 *
 */
@Repository
public interface AccountManagerRepository extends JpaRepository<GipherUser, String> {

	GipherUser findByEmailAndPassword(String mailId, String password);
	GipherUser findByEmail(String mailId);
	GipherUser findByEmailAndPasswordResetToken(String email, String passwordResetToken);
}
