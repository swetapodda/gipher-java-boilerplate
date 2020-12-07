import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ToasterService } from 'src/app/services/toaster.service';

@Component({
  selector: 'app-toaster-message',
  templateUrl: './toaster-message.component.html',
  styleUrls: ['./toaster-message.component.css']
})

export class ToasterMessageComponent implements OnInit, OnDestroy {

  alertSubscription: Subscription;
  alertMessages: any[] = [];

  constructor(private toasterService: ToasterService) {

    this.toasterService.notifyMessages()
      .subscribe((alert: any) => {

        // Add alert to array and set Time out for auto Close

        if (alert.hasOwnProperty('type')) {
          const currentDate = new Date();
          alert.dateTimeId = currentDate.getTime();
          alert.dateTime = currentDate.toLocaleDateString() + ' ' + currentDate.toLocaleTimeString();
          this.alertMessages.push(alert);
          setTimeout(() => this.removeAlert(alert), 10 * 1000); // After N Seconds automatically allert Message Disappears...
        }

      });
  }

  removeAlert(alert: any): void {
    const removeIndex = this.alertMessages.findIndex(obj => obj.dateTimeId === alert.dateTimeId);
    if (removeIndex >= 0) {
      this.alertMessages.splice(removeIndex, 1);
    }

  }

  ngOnInit() {
  }

  ngOnDestroy(): void {
  }


}

