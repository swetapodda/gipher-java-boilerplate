package com.stackroute.gipherrecommendersystem.service;

import java.util.List;

import com.stackroute.gipherrecommendersystem.model.GipherRecomendation;

public interface IGipherRecomenderSystemService {
	public void addedToFavourite(GipherRecomendation gipherRecomendation);
	public void removedFromFavourite(GipherRecomendation gipherRecomendation);
	public List<GipherRecomendation> getAllFavourite( );
}
