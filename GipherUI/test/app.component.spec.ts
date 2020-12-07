import { TestBed, async } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from '../src/app/app.component';


import { NO_ERRORS_SCHEMA } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';

import { GipherManagerService } from 'src/app/services/gipher-manager.service';
import { GipherRecomenderSystemService } from 'src/app/services/gipher-recomender-system.service';




import { DashBoardComponent } from 'src/app/component/dash-board/dash-board.component';
import { RecoverPasswordComponent } from 'src/app/component/recover-password/recover-password.component';
import { PageNotFoundComponent } from 'src/app/component/page-not-found/page-not-found.component';
import { ResetPassswordComponent } from 'src/app/component/reset-passsword/reset-passsword.component';
import { MyFavouritesListComponent } from 'src/app/component/my-favourites-list/my-favourites-list.component';
import { GipherRecomendationComponent } from 'src/app/component/gipher-recomendation/gipher-recomendation.component';
import { SignUpComponent } from 'src/app/component/sign-up/sign-up.component';
import { MyProfileComponent } from 'src/app/component/my-profile/my-profile.component';
import { LoginComponent } from 'src/app/component/login/login.component';
import { RouterService } from 'src/app/services/router.service';
import { ControllerService } from 'src/app/services/controller.service';


import { routesArray } from 'src/app/app-routing/app-routing.module';
import { ToasterService } from 'src/app/services/toaster.service';

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes(routesArray),
      ],
      declarations: [
        AppComponent,
        LoginComponent,
        RecoverPasswordComponent,
        PageNotFoundComponent,
        SignUpComponent,
        DashBoardComponent,
        ResetPassswordComponent,
        MyProfileComponent,
        MyFavouritesListComponent,
        GipherRecomendationComponent
      ],

      schemas: [NO_ERRORS_SCHEMA],
      providers: [
        ControllerService,
        AuthenticationService,
        GipherManagerService,
        GipherRecomenderSystemService,
        ToasterService,
        RouterService
      ]
    }).compileComponents();
  }));

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'GipherUI'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('GipherUI');
  });

  // This title included in Default Landing Page (DashBoard). If we include this Welcome message then UI L&F is not good
  /*
  xit('should render title in a h1 tag', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h1').textContent).toContain('Welcome to GipherUI!');
  });
  */
});
