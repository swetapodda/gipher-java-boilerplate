import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Gipher } from 'src/app/model/model';
import { ControllerService } from 'src/app/services/controller.service';
import { RouterService } from 'src/app/services/router.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})

export class HeaderComponent implements OnInit {

  favouriteSubsciption: Subscription;
  loginStatusSubsciption: Subscription;
  loginUserSubsciption: Subscription;
  favouriteGifs: Array<Gipher> = [];
  loginStatus  = false;
  loginUser: any;

  constructor(private controllerService: ControllerService, private routerService: RouterService) {

    this.favouriteSubsciption = this.controllerService.subscribeFavouriteSubject()
      .subscribe((favouriteGifsArray: Array<Gipher>) => {

        this.favouriteGifs = favouriteGifsArray;
      });

    this.loginStatusSubsciption = this.controllerService.subscribeLoginStatusSubject()
      .subscribe((loginStatus: boolean) => {
        this.loginStatus = loginStatus;
      });

    this.loginUserSubsciption = this.controllerService.subscribeLogedUserSubject()
      .subscribe((loginUser: any) => {
        this.loginUser = loginUser;
      });
  }

  getAvatarText() {
    return this.controllerService.getAvatarText();
  }

  logOut(event: Event) {
    event.preventDefault(); // Prevents browser following the link
    this.controllerService.logOut().subscribe(
      data => {
        const aletMessage = {
          message: 'Logout Success. Missing You. Login and Contunue.',
          type: 'success'
        };

        this.controllerService.cleanUpSession();
        this.controllerService.pushAlertMessage(aletMessage, false);
        this.routerService.routeToLogout();
      },
      error => {
        console.log('Logout:' + error);
      }
    );
  }

  ngOnInit() {
  }

}
