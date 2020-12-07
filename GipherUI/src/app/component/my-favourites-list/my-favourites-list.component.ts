import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Gipher } from 'src/app/model/model';
import { ControllerService } from 'src/app/services/controller.service';

@Component({
  selector: 'app-my-favourites-list',
  templateUrl: './my-favourites-list.component.html',
  styleUrls: ['./my-favourites-list.component.css']
})
export class MyFavouritesListComponent implements OnInit {

  favouriteSubsciption: Subscription;
  favouriteGifs: Array<Gipher> = [];
  noOfImagesPerSlide = 5;
  gifObject: any;

  constructor(private controllerService: ControllerService) {

    this.controllerService.setBreadCrumbTitle('My Favourite GIFs');

    // this.favouriteGifs = this.controllerService.myFavouriteGifs; // Danger: If we change local Variable Controller Object also reflect

    this.favouriteSubsciption = this.controllerService.subscribeFavouriteSubject()
      .subscribe((favouriteGifsArray: Array<Gipher>) => {
        console.log('Local: Image Size:' + this.favouriteGifs.length);
        this.favouriteGifs = favouriteGifsArray;
      });


  }
  removeFromFavourites(giphy) {

    this.controllerService.setProcessingStatus(true);

    this.controllerService.removeFromFavourites(giphy)
      .subscribe(
        (responseData: any) => {
          console.log('Removed From Fav:' + responseData);
          this.controllerService.setProcessingStatus(false);
        },
        error => {

          const defaultErrorMessage = 'Could Not Remove from Your Favourites';
          const aletMessage = this.controllerService.populateProperErrorMessage(defaultErrorMessage, error);

          this.controllerService.pushAlertMessage(aletMessage, false);
        });
  }

  ngOnInit() {
    this.controllerService.fetchFavouriteGiphies()
      .subscribe(data => {
        console.log('Data Fetched');
      },
        error => {
          console.log('Error:' + error);
        });
  }

}
