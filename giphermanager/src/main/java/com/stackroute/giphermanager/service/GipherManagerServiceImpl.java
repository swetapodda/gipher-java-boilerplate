package com.stackroute.giphermanager.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.giphermanager.exception.GipherNotCreatedException;
import com.stackroute.giphermanager.exception.GipherNotFoundException;
import com.stackroute.giphermanager.model.Gipher;
import com.stackroute.giphermanager.repository.GipherManagerRepository;

/**
 * Gipher Manager Service Implementation
 * 
 * @author L.GANESH
 *
 */

@Service
public class GipherManagerServiceImpl implements IGipherManagerService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private GipherManagerRepository gipherManagerRepository;

	@Override
	public Gipher addToFavourites(Gipher gipher) throws GipherNotCreatedException {
		Gipher gipherSaved = this.gipherManagerRepository.insert(gipher);
		if (gipherSaved != null) {
			return gipherSaved;
		} else {
			throw new GipherNotCreatedException("Gipher Could not be Created.");
		}
	}

	@Override
	public List<Gipher> getAllGipherByGipherUser(String bookMarkedBy) {
		List<Gipher> listOfGiher = this.gipherManagerRepository.getAllBookMarkedBy(Long.parseLong(bookMarkedBy));
		return listOfGiher;
	}

	@Override
	public boolean removeFromFavourites(Long userId,String giphyId) throws GipherNotFoundException {
		try {
			this.gipherManagerRepository.deleteBookMarkedByAndGiphyId(userId,giphyId);
			return true;
		} catch (Exception e) {
			log.error("Gipher Could not be For the User.", e);
			throw new GipherNotFoundException("Gipher Could not be For the User.",e);
		}
	}

}
