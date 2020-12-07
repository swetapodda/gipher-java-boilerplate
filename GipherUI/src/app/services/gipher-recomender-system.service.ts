import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AppConstants } from '../model/model';

@Injectable()
export class GipherRecomenderSystemService {


  httpOptions = { headers: new HttpHeaders({ 'Content-Type': 'application/json', }), responseType: 'text' as 'json' };

  constructor(private httpClient: HttpClient) { }
  fetchRecomendationGiphies() {
    return this.httpClient.get(`${AppConstants.GIPHER_RECOMENDATION_SYSTEM_API_URL}/listAllRecomendations`,
      {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'AUTHORIZATION': `BEARER_KEY:` + localStorage.getItem('BEARER_TOKEN')
        }), responseType: 'text' as 'json'
      });
  }
}
