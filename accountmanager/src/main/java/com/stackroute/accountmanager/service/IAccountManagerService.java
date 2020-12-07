package com.stackroute.accountmanager.service;

import com.stackroute.accountmanager.exception.GipherUserAlreadyExistsException;
import com.stackroute.accountmanager.exception.GipherUserNotFoundException;
import com.stackroute.accountmanager.exception.InvalidTokenException;
import com.stackroute.accountmanager.model.GipherUser;

import io.jsonwebtoken.Claims;

/*
 * Service Interface to Create/Update Gipher User Account
 */
public interface IAccountManagerService {

    public String authenticateUser(GipherUser gipherUser) throws GipherUserNotFoundException;
    public GipherUser findByEmailId(String emailId);
	public GipherUser registerUser(GipherUser gipherUser) throws GipherUserAlreadyExistsException;
	public void recoverPassword(String email) throws GipherUserNotFoundException;
	public void resetPassword(GipherUser gipherUser) throws InvalidTokenException;
	public Claims isValidJwt(String jwtToken);

}
