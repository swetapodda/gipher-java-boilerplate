import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { AuthenticationService } from './authentication.service';
import { Gipher, User } from '../model/model';
import { Observable, Subject } from 'rxjs';
import { throwError } from 'rxjs/internal/observable/throwError';
import { GipherManagerService } from './gipher-manager.service';
import { GipherRecomenderSystemService } from './gipher-recomender-system.service';
import { RouterService } from './router.service';
import { ToasterService } from './toaster.service';

@Injectable()
export class ControllerService {


  private totalFoundGifs = 0;
  private noOfImagesPerPage = 25;
  private BEARER_KEY: string = undefined;

  constructor(private authenticationService: AuthenticationService,
    private gipherManagerService: GipherManagerService,
    private routerServicee: RouterService,
    private gipherRecomenderSystemService: GipherRecomenderSystemService,
    private toasterService: ToasterService) {

  }


  // Favourites GIFs
  private favouriteSubject = new Subject();
  public myFavouriteGifs: Array<Gipher> = [];

  // Login Stauts.
  private loginStatuSubject = new Subject();
  public loginStatus = false;

  // Login User Info.
  private logedUserSubject = new Subject();
  public logedUser: User;

  private breadCrumbSubject = new Subject();
  public breadCrumbTitle: string;

  private processingStatusSubject = new Subject();
  processingStatus = false;

  subscribeFavouriteSubject() {
    return this.favouriteSubject;
  }
  subscribeLoginStatusSubject() {
    return this.loginStatuSubject;
  }
  subscribeLogedUserSubject() {
    return this.logedUserSubject;
  }
  subscribeBreadCrumbSubject() {
    return this.breadCrumbSubject;
  }
  subscribeProcessingStatusSubject() {
    return this.processingStatusSubject;
  }

  addToFavourites(gipher: Gipher) {

    const existingObject = this.myFavouriteGifs.find(obj => obj.giphyId === gipher.giphyId);
    if (existingObject === undefined) {
      gipher.bookMarkedBy = this.logedUser.userId;
      // Push to API
      return this.gipherManagerService.addToFavourites(gipher)
        .pipe(
          map(
            (data: any) => {
              this.myFavouriteGifs.push(gipher);
              this.favouriteSubject.next(this.myFavouriteGifs);
              return data;
            }),
          catchError(error => {
            console.log('Added Error:' + error);
            return throwError(error);
          })
        );
    } // Fav Exist Check

  }

  removeFromFavourites(gipher: Gipher) {
    const removeIndex = this.myFavouriteGifs.findIndex(obj => obj.giphyId === gipher.giphyId);

    // Call Remove from Fav  API

    return this.gipherManagerService.removeFromFavourites(gipher.giphyId, this.logedUser.userId)// Deleting By Id
      .pipe(
        map((data: any) => {
          this.myFavouriteGifs.splice(removeIndex, 1);
          this.favouriteSubject.next(this.myFavouriteGifs);
          return data;
        }), catchError(error => {
          console.log('Error:' + error);
          return throwError(error);
        })
      );
  }

  fetchFavouriteGiphies() {

    // Call Remove from Fav  API

    return this.gipherManagerService.fetchFavouriteGiphies(this.logedUser && this.logedUser.userId ? this.logedUser.userId : undefined)
      .pipe(
        map((data: any) => {
          console.log('Favourite Giphers');
          const responseObject = JSON.parse(data);
          this.myFavouriteGifs = [];

          // Note: if we use forEach-- We could not access this.myFavouriteGifs variable inside for look

          for (const gipher of responseObject) {

            const localGipher = new Gipher();

            localGipher.id = gipher.id;
            localGipher.giphyId = gipher.giphyId;
            localGipher.giphyUrl = gipher.giphyUrl;
            localGipher.title = gipher.title;
            localGipher.bookMarkedBy = gipher.bookMarkedBy;

            this.myFavouriteGifs.push(localGipher);
            this.favouriteSubject.next(this.myFavouriteGifs);
          }
          return responseObject;
        }), catchError(error => {
          console.log('Error:' + error);
          return throwError(error);
        })
      );

  }

  fetchRecomendationGiphies() {
    return this.gipherRecomenderSystemService.fetchRecomendationGiphies()
      .pipe(
        map((data: any) => {
          console.log('Recomender System Responsed Giphers');
          const responseObject = JSON.parse(data);
          return responseObject;
        }), catchError(error => {
          console.log('Error:' + error);
          return throwError(error);
        })
      );
  }

  setBreadCrumbTitle(breadCrumbTitle: string) {
    this.breadCrumbTitle = breadCrumbTitle;
    this.breadCrumbSubject.next(this.breadCrumbTitle);
  }

  createUser(user: User) {
    return this.authenticationService.createUser(user);
  }

  authenticateUser(dataArg: any) {

    // Server Returns Map Object which contains JWT_TOKEN and USER_DATA

    return this.authenticationService.authenticateUser(dataArg)
      .pipe(
        map((data: any) => {
          const responseObject = JSON.parse(data);
          this.setBearerKey(responseObject['JWT_TOKEN']);
          localStorage.setItem('BEARER_TOKEN', responseObject['JWT_TOKEN']);

          if (this.getBearerKey() !== undefined) {
            this.logedUser = responseObject['USER_DATA'];
            this.loginStatus = true;
            this.loginStatuSubject.next(this.loginStatus);
            this.logedUserSubject.next(this.logedUser);

            // Need to Fetch Data, So Subscribe it then only request is sending to Server....

            this.fetchFavouriteGiphies().subscribe(data2 => {
              console.log('Data Fetched');
            }, error => {
              console.log('Error:' + error);
            });

          }
          return data;
        }), catchError(error => {
          this.cleanUpSession();
          return throwError(error);
        })
      );
  }

  getBearerToken() {
    return localStorage.getItem('BEARER_TOKEN');
  }

  isUserAuthenticated() {

    const bearerToken = this.getBearerToken();

    if (bearerToken !== undefined) {
      return this.authenticationService.isUserAuthenticated(bearerToken)
        .pipe(
          map((res: any) => {
            const responseObject = JSON.parse(res);
            console.log('canactivate:' + res);
            const validToken = responseObject['isAuthenticated'];
            return validToken;
          }
          )
        ).toPromise();
    } else {
      return new Promise(undefined);
    }

  }

  recoverPassword(data: any) {
    return this.authenticationService.recoverPassword(data);
  }

  resetPassword(data: any) {
    return this.authenticationService.resetPassword(data);
  }

  logOut() {

    return this.authenticationService.logOut()
      .pipe(
        map((data: any) => {
          console.log('Lgout Success');
          this.cleanUpSession();
          return data;
        }), catchError(error => {
          console.log('Lgout Error' + error);
          this.cleanUpSession();
          this.loginStatuSubject.next(false);
          return throwError(error);
        })
      );
  }

  cleanUpSession() {
    this.logedUser = undefined;
    localStorage.removeItem('BEARER_TOKEN');
    this.logedUserSubject.next(this.logedUser);

    this.loginStatuSubject.next(false);
    this.myFavouriteGifs = [];
    this.favouriteSubject.next(this.myFavouriteGifs);
    this.setBearerKey(undefined);
  }

  getAvatarText() {
    if (this.logedUser) {
      return this.logedUser.firstName.charAt(0) + '' + this.logedUser.lastName.charAt(0);
    }
  }

  fetchTrendGifs() {
    const pageNumber = 1; // this.randomInteger();
    /*
    if(this.totalTrendGifs > 0){
      pageNumber=this.totalTrendGifs / this.noOfImagesPerPage;
    }
    */
    return this.gipherManagerService.fetchTrendGifs(pageNumber)
      .pipe(
        map((data: any) => {
          console.log('Treand GIF Fetched');
          const responseObject = JSON.parse(data);
          this.totalFoundGifs = responseObject['pagination']['total_count'];
          /*var temp = responseObject['data'];
          var gipherArray: Array<Gipher> = [];
          for (var index = 0; index < temp.length; index++) {
            var localGipher = this.copyToGipher(temp[index]);

            console.log('Copied:' + localGipher);
            gipherArray.push(localGipher);
          }*/
          return responseObject;
        }), catchError(error => {
          console.log('Fetch Trendh GIF Failed:' + error);
          // this.loginStatuSubject.next(false);
          return throwError(error);
        })
      );
  }

  copyToGipher(giphy: any) {
    const localGipher = new Gipher();

    localGipher.giphyId = giphy.id;
    localGipher.giphyUrl = giphy.images.original.url;
    localGipher.giphyMp4Url = giphy.images.original.mp4;
    localGipher.title = giphy.title;
    localGipher.bookMarkedBy = this.isAddedFavourite(giphy) ? this.logedUser.userId : undefined;
    return localGipher;
  }

  searchGifImages(searchKey: any) {
    const pageNumber = 1;
    // pagination: {total_count: 86465, count: 10, offset: 40}
    return this.gipherManagerService.searchGifImages(searchKey, pageNumber)
      .pipe(
        map((data: any) => {
          console.log('Search GIF Result Got');
          const responseObject = JSON.parse(data);
          this.totalFoundGifs = responseObject['pagination']['total_count'];
          /* var temp = responseObject['data'];
          var gipherArray: Array<Gipher> = [];
          for (var index = 0; index < temp.length; index++) {
            var localGipher = this.copyToGipher(temp[index]);
            //console.log('Copied:' + localGipher);
            gipherArray.push(localGipher);
          }*/
          return responseObject;
        }), catchError(error => {
          console.log('Search GIF Failed : ' + error);
          return throwError(error);
        })
      );

  }
  populateProperErrorMessage(defaultErrorMessage: string, error: any) {
    let errorMessage = defaultErrorMessage;
    if (error.status === 0 || error.status >= 500) {
      errorMessage = defaultErrorMessage;
    } else if (error.status === 403) {
      errorMessage = 'Your Session Expired. Please Loging and Continue.';
      this.logOut().subscribe(data => {
        console.log('Data Fetched');
      }, error2 => {
        console.log('Error:' + error2);
      });
      this.routerServicee.routeToLogin();
    } else if (error.status === 409) {
      errorMessage = error.error; // Server Response Error Message
    } else {
      errorMessage = error.message;
    }
    const aletMessage = {
      message: errorMessage, // error.message ==> Provides Server Communication Failures. error.error ==>Server Response with Error Code
      type: 'danger'
    };
    return aletMessage;
  }



  isAddedFavourite(gipher: Gipher) {
    const existingObject = this.myFavouriteGifs.find(obj => obj.giphyId === gipher.giphyId);
    if (existingObject !== undefined) {
      return true;
    }
    return false;
  }

  isLoggedIn() {
    if (this.logedUser) {
      return true;
    } else {
      return false;
    }
  }

  randomInteger() {
    const min = 1, max = 10;
    return Math.floor(Math.random() * (max - min + 1)) + min;
  }

  getBearerKey(): string {
    return this.BEARER_KEY;
  }

  setBearerKey(newKey: string) {
    this.BEARER_KEY = newKey;
  }

  pushAlertMessage(message, processingStatus: any) { // Note: message is Array and First Element [{message,false}] in format
    this.processingStatus = processingStatus;
    this.processingStatusSubject.next(this.processingStatus);
    this.toasterService.pushAlertMessage(message, processingStatus);
  }

  setProcessingStatus(processingStatus: any) {
    this.processingStatus = processingStatus;
    this.processingStatusSubject.next(this.processingStatus);
    this.toasterService.setProcessingStatus(this.processingStatus);
  }
}
