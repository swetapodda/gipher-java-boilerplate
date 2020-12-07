import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { routesArray } from 'src/app/app-routing/app-routing.module';

import { DashBoardComponent } from 'src/app/component/dash-board/dash-board.component';
import { RecoverPasswordComponent } from 'src/app/component/recover-password/recover-password.component';
import { PageNotFoundComponent } from 'src/app/component/page-not-found/page-not-found.component';
import { ResetPassswordComponent } from 'src/app/component/reset-passsword/reset-passsword.component';
import { MyFavouritesListComponent } from 'src/app/component/my-favourites-list/my-favourites-list.component';
import { GipherRecomendationComponent } from 'src/app/component/gipher-recomendation/gipher-recomendation.component';
import { SignUpComponent } from 'src/app/component/sign-up/sign-up.component';
import { MyProfileComponent } from 'src/app/component/my-profile/my-profile.component';
import { LoginComponent } from 'src/app/component/login/login.component';


import { AuthenticationService } from 'src/app/services/authentication.service';
import { ControllerService } from 'src/app/services/controller.service';
import { GipherManagerService } from 'src/app/services/gipher-manager.service';
import { GipherRecomenderSystemService } from 'src/app/services/gipher-recomender-system.service';
import { RouterService } from 'src/app/services/router.service';
import { HttpClientModule } from '@angular/common/http';
import { ToasterService } from 'src/app/services/toaster.service';

describe('RouterService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    declarations: [
      LoginComponent,
      RecoverPasswordComponent,
      PageNotFoundComponent,
      SignUpComponent,
      DashBoardComponent,
      ResetPassswordComponent,
      MyProfileComponent,
      MyFavouritesListComponent,
      GipherRecomendationComponent,
    ],
    imports: [
      RouterTestingModule.withRoutes(routesArray),
      HttpClientModule
    ],
    schemas: [NO_ERRORS_SCHEMA],
    providers: [
      ControllerService,
      AuthenticationService,
      GipherManagerService,
      GipherRecomenderSystemService,
      ToasterService,
      RouterService
      /*
       Router //Error: Can't resolve all parameters for Router: (?, ?, ?, ?, ?, ?, ?, ?).
       Already we have withRoutes() option, So we dont need this
       */
    ]
  }));

  it('should be created', () => {
    const service: RouterService = TestBed.get(RouterService);
    expect(service).toBeTruthy();
  });
});
