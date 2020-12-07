import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HttpClientModule } from '@angular/common/http';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';

import { GipherManagerService } from 'src/app/services/gipher-manager.service';
import { GipherRecomenderSystemService } from 'src/app/services/gipher-recomender-system.service';


import { ControllerService } from '../../src/app/services/controller.service';

import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from 'src/app/app-routing/app-routing.module';
import { ReactiveFormsModule } from '@angular/forms';


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
import { ToasterService } from 'src/app/services/toaster.service';

describe('ResetPassswordComponent', () => {
  let component: ResetPassswordComponent;
  let fixture: ComponentFixture<ResetPassswordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
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
      imports: [
        BrowserModule,
        ReactiveFormsModule, /* Reactive Form Related */
        HttpClientModule,
        AppRoutingModule],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [
        ControllerService,
        AuthenticationService,
        GipherManagerService,
        GipherRecomenderSystemService,
        ToasterService,
        RouterService
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResetPassswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
