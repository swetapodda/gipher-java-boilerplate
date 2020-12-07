import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Gipher } from 'src/app/model/model';
import { ControllerService } from 'src/app/services/controller.service';

@Component({
  selector: 'app-dash-board',
  templateUrl: './dash-board.component.html',
  styleUrls: ['./dash-board.component.css']
})
export class DashBoardComponent implements OnInit {
  colOneImageList: Array<Gipher> = [];

  public DASHBOARD_MODE: string;
  public searchKey: string;
  public processingStatus: boolean;
  searchKeyForm: FormGroup;


  constructor(private formBuilder: FormBuilder,
    private controllerService: ControllerService) {
    this.controllerService.subscribeProcessingStatusSubject().subscribe(
      (processingStatus: boolean) => {
        this.processingStatus = processingStatus;
      }
    );
    this.searchKeyForm = this.formBuilder.group({
      searchKey: ['', '']
    });
  }


  getImageList(colNuber) {
    // console.log('Get Image List:'+colNuber);
    if (this.colOneImageList && this.colOneImageList.length > 0) {
      const totalImages = this.colOneImageList.length;
      const totalCols = 5;
      const perColImages = Math.ceil(totalImages / totalCols);
      const from = colNuber * perColImages;
      const upTo = from + perColImages;

      // console.log('Need Copy from:' + from + ' : ' + upTo);
      return this.colOneImageList.slice(from, upTo); // Note slice end is not included.
    } else {
      return [];
    }
  }

  getTrimmedText(gifImage) {
    if (gifImage) {
      if (gifImage.title) {
        const titleText = gifImage.title;
        const maxLength = 20;
        return titleText.substr(0, titleText.length > maxLength ? maxLength : titleText.length);
      }

    }
    return 'N/A';
  }

  addToFavourites(giphy) {
    console.log('Adding to Favourites:' + giphy);
    this.controllerService.setProcessingStatus(true);
    this.controllerService.addToFavourites(giphy)
      .subscribe(
        (responseData: any) => {
          console.log('Adding to Fav:' + responseData);
          this.controllerService.setProcessingStatus(false);
        },
        error => {
          console.log('Error:' + error);
          const defaultErrorMessage = 'Could Not Add to Your Favourites';
          const aletMessage = this.controllerService.populateProperErrorMessage(defaultErrorMessage, error);
          this.controllerService.pushAlertMessage(aletMessage, false);
        });

  }
  removeFromFavourites(giphy) {
    console.log('Removing From Favourites:' + giphy);
    this.controllerService.setProcessingStatus(true);
    this.controllerService.removeFromFavourites(giphy)
      .subscribe(
        (responseData: any) => {
          console.log('Remove From Fav:' + responseData);
          this.controllerService.setProcessingStatus(false);
        },
        error => {
          const defaultErrorMessage = 'Could Not Remove from Your Favourites';
          const aletMessage = this.controllerService.populateProperErrorMessage(defaultErrorMessage, error);

          this.controllerService.pushAlertMessage(aletMessage, false);
        });

  }
  isAddedFavourite(gipher: Gipher) {
    return this.controllerService.isAddedFavourite(gipher);
  }
  isLoggedIn() {
    return this.controllerService.isLoggedIn();
  }
  searchGifImages(event: Event) {
    event.preventDefault();
    this.colOneImageList = [];
    this.DASHBOARD_MODE = 'SEARCH_VIEW';

    this.controllerService.setBreadCrumbTitle('DashBoard (Listing Search GIFs)...');
    const searchKey = this.searchKeyForm.value.searchKey;
    console.log('Searching...:' + searchKey);
    // Set marking process Started
    this.controllerService.setProcessingStatus(true);

    this.controllerService.searchGifImages(searchKey)
      .subscribe(
        (responseJSONObject: any) => {
          const temp = responseJSONObject['data'];

          for (let index = 0; index < temp.length; index++) {
            const localGipher = this.controllerService.copyToGipher(temp[index]);
            // console.log('Copied:' + localGipher);
            this.colOneImageList.push(localGipher);
          }

          this.controllerService.setBreadCrumbTitle('DashBoard (Listing Search GIFs. Total Found: ' +
            responseJSONObject['pagination'].total_count + ')');

          this.controllerService.setProcessingStatus(false);
        },
        error => {
          console.log('Error:' + error);
          const errorMessage = 'Failed to Load Serach GIFs';
          const aletMessage = this.controllerService.populateProperErrorMessage(errorMessage, error);
          this.controllerService.pushAlertMessage(aletMessage, false);
        });
  }
  ngOnInit() {
    this.colOneImageList = [];
    this.DASHBOARD_MODE = 'TREND_VIEW';
    this.controllerService.setBreadCrumbTitle('DashBoard (Listing Current Trend GIFs)...');
    this.controllerService.setProcessingStatus(true);
    console.log('Fetching Trending GIFs');
    this.controllerService.fetchTrendGifs()
      // return this.httpClient.get(this.gifphyTrendingURL)
      .subscribe(
        (responseJSONObject: any) => {
          const temp = responseJSONObject['data'];

          for (let index = 0; index < temp.length; index++) {
            const localGipher = this.controllerService.copyToGipher(temp[index]);
            // console.log('Copied:' + localGipher);
            this.colOneImageList.push(localGipher);
          }

          this.controllerService.setBreadCrumbTitle('DashBoard (Listing Current Trend GIFs. Total Found: '
            + responseJSONObject['pagination'].total_count + ')');

          this.controllerService.setProcessingStatus(false);
        },
        error => {
          console.log('Error:' + error);
          const defaultErrorMessage = 'Failed to Load Trend GIFs';
          const aletMessage = this.controllerService.populateProperErrorMessage(defaultErrorMessage, error);

          this.controllerService.pushAlertMessage(aletMessage, false);
        }

      );

  }
}
