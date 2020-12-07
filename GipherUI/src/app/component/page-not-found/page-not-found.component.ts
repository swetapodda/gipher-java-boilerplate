import { Component, OnInit } from '@angular/core';
import { ControllerService } from 'src/app/services/controller.service';

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.css']
})

export class PageNotFoundComponent implements OnInit {

  constructor(private controllerService: ControllerService) {
    this.controllerService.setBreadCrumbTitle(null); // No BreadCrumb
  }

  ngOnInit() {
  }

}
