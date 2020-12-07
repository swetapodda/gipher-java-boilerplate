package com.stackroute.accountmanager.service;

import com.stackroute.accountmanager.model.GipherUser;
/**
 * Service Interface to Notify Mail 
 * 
 * @author GANESH
 *
 */
public interface IMailSender {

	void notifyPasswordReset(GipherUser gipherUser);

}
