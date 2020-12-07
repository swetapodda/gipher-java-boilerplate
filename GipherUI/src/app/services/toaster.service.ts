import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable()
export class ToasterService {



  private subject = new Subject();
  private processingSubject = new Subject();


  constructor() { }

  notifyMessages() {
    return this.subject;
  }
  processingStatus() {
    return this.processingSubject;
  }
  /*
  pushAlertMessage(message: any) { // Note: message is Array and First Element [{message,false}] in format
    console.log("Message:"+message);
    /*this.subject.next(message[0][0]);
    if (message.length > 0) {
      this.setProcessingStatus(message[0][1]);
    }* /

  }
  */
  pushAlertMessage(aletMessage: { message: string; type: string; }, processingStatus: boolean) {
    // Notify with Message
    this.subject.next(aletMessage);
    // Notify whether Processing Running or Stopped
    this.setProcessingStatus(processingStatus);
  }
  setProcessingStatus(processingStatus: any) {
    this.processingSubject.next(processingStatus);
  }
}
