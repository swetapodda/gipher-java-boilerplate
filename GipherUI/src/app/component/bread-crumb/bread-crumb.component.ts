import { Component, OnInit } from '@angular/core';
import { ControllerService } from 'src/app/services/controller.service';

@Component({
  selector: 'app-bread-crumb',
  templateUrl: './bread-crumb.component.html',
  styleUrls: ['./bread-crumb.component.css']
})

export class BreadCrumbComponent implements OnInit {

  breadCrumbTitle: string;

  constructor(private controllerService: ControllerService) { }

  ngOnInit() {
    this.breadCrumbTitle = this.controllerService.breadCrumbTitle;
    this.controllerService.subscribeBreadCrumbSubject().subscribe(
      (breadCrumbTitle: string) => {
        this.breadCrumbTitle = breadCrumbTitle;
      }
    );
  }

}
