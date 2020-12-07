package com.stackroute.accountmanager.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.IConstants;
import com.stackroute.accountmanager.exception.GipherUserAlreadyExistsException;
import com.stackroute.accountmanager.exception.GipherUserNotFoundException;
import com.stackroute.accountmanager.exception.InvalidTokenException;
import com.stackroute.accountmanager.model.GipherUser;
import com.stackroute.accountmanager.repository.AccountManagerRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * Gipher Account Manager (Gipher User Authentication and Registration) related Service Implementation
 * 
 * @author GANESH
 *
 */

@Service
public class AccountManagerServiceImpl implements IAccountManagerService {

	@Autowired
	private AccountManagerRepository accountManagerRepository;
	@Autowired
	private IMailSender mailSender;
	@Override
	public String authenticateUser(GipherUser gipherUser) throws GipherUserNotFoundException {
		GipherUser foundGipherUser = this.accountManagerRepository.findByEmailAndPassword(gipherUser.getEmail(), gipherUser.getPassword());
		if (foundGipherUser == null) {
			throw new GipherUserNotFoundException("UserId and Password mismatch");
		} else {
			// Token Generation....
			return generateToken(gipherUser.getEmail(), gipherUser.getPassword());
		}
	}

	@Override
	public GipherUser findByEmailId(String mailId) {
		GipherUser gipherUser = this.accountManagerRepository.findByEmail(mailId);
		//TODO: Cleanup Password
		return gipherUser;
	}
	
	

	@Override
	public GipherUser registerUser(GipherUser gipherUser) throws GipherUserAlreadyExistsException {
		GipherUser foundUser = this.accountManagerRepository.findByEmail(gipherUser.getEmail());
		if (foundUser != null) {
			throw new GipherUserAlreadyExistsException("User Already Exists");
		}
		gipherUser.setUserAddedDate(new Date());
		GipherUser registerUser = this.accountManagerRepository.save(gipherUser);
		return registerUser;
	}

	@Override
	public void recoverPassword( String email) throws GipherUserNotFoundException {
		GipherUser gipherUser = this.accountManagerRepository.findByEmail(email);
		if (gipherUser == null) {
			throw new GipherUserNotFoundException("Associated Email Id User Not found");
		}
		String token = UUID.randomUUID().toString();
		gipherUser.setPasswordResetToken(token);
		this.accountManagerRepository.save(gipherUser);
		//Notify through E-Mail
		this.mailSender.notifyPasswordReset(gipherUser);
		
		

	}

	@Override
	public void resetPassword(GipherUser gipherUser) throws InvalidTokenException {
		GipherUser foundGipherUser = this.accountManagerRepository.findByEmailAndPasswordResetToken(gipherUser.getEmail(), gipherUser.getPasswordResetToken());
		if (foundGipherUser == null) {
			throw new InvalidTokenException("Associated Email User and Tokeen Not found");
		}
		foundGipherUser.setPassword(gipherUser.getPassword());
		foundGipherUser.setPasswordResetToken(null);
		this.accountManagerRepository.save(foundGipherUser);
	}
	
	private String generateToken(String emailId, String password) {

		long EXPIRATION_TIME = 1 * (24 * 60 * 60 * 1000); // 1 Day
		Claims claims = Jwts.claims().setSubject(emailId);
		claims.put("UserId", emailId);
		claims.put("password", password);

		return Jwts.builder().setClaims(claims).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, IConstants.HEADER.SECRET_KEY).compact();
	}

	@Override
	public Claims isValidJwt(String jwtToken) {
		Claims claims = Jwts.parser().setSigningKey(IConstants.HEADER.SECRET_KEY).parseClaimsJws(jwtToken).getBody();
		System.out.println("Claims:"+claims);
		return claims;
	}

	

}
