package com.stackroute.accountmanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.stackroute.accountmanager.model.GipherUser;

/**
 * Service for Sending Mail notification to User. (This will print mail content info in Console).
 * 
 * @author GANESH
 *
 */

@Service
public class MailSenderImpl implements IMailSender {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	public void notifyPasswordReset(GipherUser gipherUser) {
		StringBuffer buffer=new StringBuffer();
		buffer.append("=============================================================================================").append("\n").append("\n");
		buffer.append("TO:"+gipherUser.getEmail()).append("\n");
		buffer.append("From:giphersupport@gipher.com").append("\n");
		buffer.append("Subject:Gipher Account- Password Reset Request").append("\n");
		
		buffer.append("=============================================================================================").append("\n").append("\n");
		buffer.append("Hi "+gipherUser.getFirstName().toUpperCase()+",").append("\n");
		buffer.append("Your account is ready to Reset password. Use the below link to to reset").append("\n").append("\n");
		
		//String UI_HOST_NAME="192.168.1.11";
		/*
		try {
			UI_HOST_NAME=InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.error("Could not Find Host IP Address. This might lead to localhost",e);
		} 
		buffer.append("http://"+UI_HOST_NAME+":4200/resetPassword").append("\n");
		*/
		buffer.append("http://192.168.1.11/#/resetPassword").append("\n");
		buffer.append("Your Security Token:"+gipherUser.getPasswordResetToken()).append("\n").append("\n");
		buffer.append("Note: Once used this token and cannot be reuse.").append("\n").append("\n");
		buffer.append("Thank you,").append("\n");
		buffer.append("Gipher Team").append("\n").append("\n");
		buffer.append("=============================================================================================");
		
		// Connect SMTP Server and Post Data.
		log.debug(buffer.toString());
		log.debug(buffer.toString().replaceAll(gipherUser.getPasswordResetToken(), "{XXXXX-XXXXXXX-XXXXX}"));
	}

}
