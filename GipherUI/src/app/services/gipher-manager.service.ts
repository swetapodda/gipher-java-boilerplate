import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AppConstants, Gipher } from '../model/model';

@Injectable()
export class GipherManagerService {

  httpOptions = { headers: new HttpHeaders({ 'Content-Type': 'application/json', }), responseType: 'text' as 'json' };

  constructor(private httpClient: HttpClient) { }


  searchGifImages(searchKey: any, pageNumber: number) {
    return this.httpClient.get(`${AppConstants.GIPHER_MANAGER_API_URL}/searchGiphies?pageNo=${pageNumber}&searchKey=${searchKey}`,
      this.httpOptions);
  }

  fetchTrendGifs(pageNumber: number) {
    console.log('Fetching Trendinging no httpOptions..' + `${AppConstants.GIPHER_MANAGER_API_URL}/trendGiphies?pageNo=${pageNumber}`);
    return this.httpClient.get(`${AppConstants.GIPHER_MANAGER_API_URL}/trendGiphies?pageNo=${pageNumber}`, this.httpOptions);
  }

  addToFavourites(gipherObject: Gipher) {
    console.log('Adding to Favourites' + `${AppConstants.GIPHER_MANAGER_API_URL}/gipher`);
    return this.httpClient.post(`${AppConstants.GIPHER_MANAGER_API_URL}/gipher`, JSON.stringify(gipherObject),
      {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'AUTHORIZATION': `BEARER_KEY:` + localStorage.getItem('BEARER_TOKEN')
        }), responseType: 'text' as 'json'
      });
  }

  removeFromFavourites(giphyId: any, userId: any) {
    console.log('Adding to Favourites');
    return this.httpClient.delete(`${AppConstants.GIPHER_MANAGER_API_URL}/${userId}/${giphyId}`,
      {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'AUTHORIZATION': `BEARER_KEY:` + localStorage.getItem('BEARER_TOKEN')
        }), responseType: 'text' as 'json'
      });
  }

  fetchFavouriteGiphies(userId: number) {
    console.log('Getting All Favourites GIPHYs ');
    return this.httpClient.get(`${AppConstants.GIPHER_MANAGER_API_URL}/gipher?gipherUserId=${userId}`,
      {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'AUTHORIZATION': `BEARER_KEY:` + localStorage.getItem('BEARER_TOKEN')
        }), responseType: 'text' as 'json'
      });
  }
}
