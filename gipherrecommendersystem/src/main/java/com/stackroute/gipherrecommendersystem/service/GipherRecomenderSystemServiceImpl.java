package com.stackroute.gipherrecommendersystem.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.stackroute.gipherrecommendersystem.model.GipherRecomendation;
import com.stackroute.gipherrecommendersystem.repository.GipherRecomenderSystemRepository;
@Service
public class GipherRecomenderSystemServiceImpl implements IGipherRecomenderSystemService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private GipherRecomenderSystemRepository gipherRecomenderSystemRepository;
	
	@Override
	public void addedToFavourite(GipherRecomendation gipherRecomendation) {
		// Increase Count if Exist
		log.debug("Inside Increment Service..");
		List<GipherRecomendation> existingRecomendationList=gipherRecomenderSystemRepository.getGipherRecomendationByGiphyId(gipherRecomendation.getGiphyId());
		if(existingRecomendationList != null && existingRecomendationList.size() >0) {
			log.debug("Found...Save");
			GipherRecomendation gipherRecomendationFound=existingRecomendationList.get(0);
			gipherRecomendationFound.setCount(new Long(gipherRecomendationFound.getCount()+1));
			gipherRecomenderSystemRepository.save(gipherRecomendationFound);
			
		}else {
			log.debug("Not Found...Save");
			gipherRecomenderSystemRepository.save(gipherRecomendation);
		}
	}

	@Override
	public void removedFromFavourite(GipherRecomendation gipherRecomendation) {
		List<GipherRecomendation> existingRecomendationList=gipherRecomenderSystemRepository.getGipherRecomendationByGiphyId(gipherRecomendation.getGiphyId());
		if(existingRecomendationList != null && existingRecomendationList.size() >0) {
			GipherRecomendation gipherRecomendationFound=existingRecomendationList.get(0);
			gipherRecomendationFound.setCount(new Long(gipherRecomendationFound.getCount()-1));
			if(gipherRecomendationFound.getCount() <=0) { //Count Zero, So we can delete from DB
				gipherRecomenderSystemRepository.deleteById(gipherRecomendationFound.getId());
			}else {
				gipherRecomenderSystemRepository.save(gipherRecomendationFound);
			}
			
			
		}else {
			// If Not found.. Just ignore...
		}
		
	}

	@Override
	public List<GipherRecomendation> getAllFavourite() {
		// List All Favourites
		Sort sort = new Sort(Sort.Direction.DESC, "count");
		return gipherRecomenderSystemRepository.findAll(sort);
	}

}
