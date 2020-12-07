import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AppConstants } from '../model/model';

@Injectable()
export class AuthenticationService {




  /*
  These Options are not resolving the : SyntaxError: Unexpected token xxxx in JSON at position 0 at JSON.parse
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
  private httpOptions1 = {

    headers: new HttpHeaders({
      'Accept': 'application/json, *\/*',
      'Content-Type': 'application/json',
      responseType: 'json'
    },

    ) };
  private headers = new Headers({'Content-Type': 'application/json'});
  */


  httpOptions = { headers: new HttpHeaders({ 'Content-Type': 'application/json', }), responseType: 'text' as 'json' };


  constructor(private httpClient: HttpClient) {

  }
  createUser(gipherUsesr: any) {
    console.log('Posting data');
    return this.httpClient.post(`${AppConstants.ACCOUNT_MANAGER_API_URL}/gipherUser`, JSON.stringify(gipherUsesr), this.httpOptions);
  }

  authenticateUser(data: any) {
    return this.httpClient.post(`${AppConstants.ACCOUNT_MANAGER_API_URL}/authenticateGipherUser`, JSON.stringify(data), this.httpOptions);
  }

  recoverPassword(data: any) {
    return this.httpClient.post(`${AppConstants.ACCOUNT_MANAGER_API_URL}/recoverPassword`, JSON.stringify(data), this.httpOptions);
  }

  resetPassword(data: any) {
    return this.httpClient.post(`${AppConstants.ACCOUNT_MANAGER_API_URL}/resetPassword`, JSON.stringify(data), this.httpOptions);
  }

  logOut() {
    return this.httpClient.post(`${AppConstants.ACCOUNT_MANAGER_API_URL}/logOut`, {}, this.httpOptions);
  }

  setBearerToken(token) {
    localStorage.setItem('bearerToken', token);
  }

  getBearerToken() {
    return localStorage.getItem('bearerToken');
  }

  isUserAuthenticated(token) {
    return this.httpClient.post(`${AppConstants.ACCOUNT_MANAGER_API_URL}/isAuthenticated`, {},
      {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': `BEARER_KEY:${token}`
        }), responseType: 'text' as 'json'
      }

    );
  }
}
