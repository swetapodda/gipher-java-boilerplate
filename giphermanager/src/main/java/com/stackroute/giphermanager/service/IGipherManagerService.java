package com.stackroute.giphermanager.service;

import java.util.List;

import com.stackroute.giphermanager.exception.GipherNotCreatedException;
import com.stackroute.giphermanager.exception.GipherNotFoundException;
import com.stackroute.giphermanager.model.Gipher;
/**
 * Gipher Manager Interface Service 
 * 
 * @author L.GANESH
 *
 */
public interface IGipherManagerService {

	Gipher addToFavourites(Gipher gipher) throws GipherNotCreatedException;

	List<Gipher> getAllGipherByGipherUser(String gipherUserId);

	boolean removeFromFavourites(Long userId, String gipherId) throws GipherNotFoundException;


}
