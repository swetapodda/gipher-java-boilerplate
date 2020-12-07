import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';

import { ControllerService } from './controller.service';
import { RouterService } from './router.service';




@Injectable()
export class CanActivateRouteGuard implements CanActivate {

  constructor(private controllerService: ControllerService, private routerService: RouterService) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {



    const authStatus = this.controllerService.isUserAuthenticated()
      .then((data) => {
        return data;
      }, err => {
        console.log(err);

        const aletMessage = {
          message: 'Your Session Expired. Please Loging and Continue.',
          type: 'danger'
        };
        this.controllerService.pushAlertMessage(aletMessage, false);
        this.controllerService.cleanUpSession();
        this.routerService.routeToLogin();
        return false;
      });

    if (authStatus) {
      return true;
    } else {
      const aletMessage = {
        message: 'Your Session Expired. Please Loging and Continue.',
        type: 'danger'
      };
      this.controllerService.pushAlertMessage(aletMessage, false);
      this.controllerService.logOut();
      this.routerService.routeToLogin();
      return false;
    }

  }

}
