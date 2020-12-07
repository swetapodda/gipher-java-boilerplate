import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ToasterService } from 'src/app/services/toaster.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit, OnDestroy {


  processing = false;

  constructor(private toasterService: ToasterService) {

    this.toasterService.processingStatus()
      .subscribe(processingStatus => {
        console.log('Footer Alert:' + processingStatus);
        if (processingStatus) {
          this.processing = true;
        } else {
          this.processing = false;
        }
      });
  }

  ngOnDestroy(): void {
    throw new Error('Method not implemented.');
  }

  ngOnInit() {
  }

}
