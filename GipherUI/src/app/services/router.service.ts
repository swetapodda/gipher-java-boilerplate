import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { ControllerService } from './controller.service';

@Injectable()
export class RouterService {


  constructor(private router: Router) { }
  routeToDashbBard() {
    this.router.navigate(['dashBoard']);
  }
  routeToLogin() {
    this.router.navigate(['login']);
  }

  routeToDashBoard() {
    this.router.navigate(['dashBoard']);
  }

  routerToResetPassword() {
    this.router.navigate(['resetPassword']);
  }
  routeToLogout() {
    this.router.navigate(['logOut']);
  }
}
