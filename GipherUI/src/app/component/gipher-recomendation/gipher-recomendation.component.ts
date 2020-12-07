import { Component, OnInit } from '@angular/core';
import { ControllerService } from 'src/app/services/controller.service';

@Component({
  selector: 'app-gipher-recomendation',
  templateUrl: './gipher-recomendation.component.html',
  styleUrls: ['./gipher-recomendation.component.css']
})

export class GipherRecomendationComponent implements OnInit {

  recomendationGifs: Array<any> = [];

  constructor(private controllerService: ControllerService) {
    this.controllerService.setBreadCrumbTitle('Gipher Recomendations');
  }

  ngOnInit() {

    this.controllerService.setProcessingStatus(true);
    this.controllerService.fetchRecomendationGiphies()
      .subscribe(
        (responseData: any) => {

          this.recomendationGifs = [];

          for (const gipher of responseData) {
            this.recomendationGifs.push(gipher);
          }
          this.controllerService.setProcessingStatus(false);

        },
        error => {

          const defaultErrorMessage = 'Could Not Fetch Recomendations';
          const aletMessage = this.controllerService.populateProperErrorMessage(defaultErrorMessage, error);

          this.controllerService.pushAlertMessage(aletMessage, false);

        });
  }
}
