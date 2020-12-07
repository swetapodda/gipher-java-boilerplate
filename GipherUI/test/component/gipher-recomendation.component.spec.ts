import { HttpClientModule } from '@angular/common/http';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { routesArray } from 'src/app/app-routing/app-routing.module';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ControllerService } from 'src/app/services/controller.service';
import { GipherManagerService } from 'src/app/services/gipher-manager.service';
import { GipherRecomenderSystemService } from 'src/app/services/gipher-recomender-system.service';
import { RouterService } from 'src/app/services/router.service';


import { DashBoardComponent } from 'src/app/component/dash-board/dash-board.component';
import { RecoverPasswordComponent } from 'src/app/component/recover-password/recover-password.component';
import { PageNotFoundComponent } from 'src/app/component/page-not-found/page-not-found.component';
import { ResetPassswordComponent } from 'src/app/component/reset-passsword/reset-passsword.component';
import { MyFavouritesListComponent } from 'src/app/component/my-favourites-list/my-favourites-list.component';
import { GipherRecomendationComponent } from 'src/app/component/gipher-recomendation/gipher-recomendation.component';
import { SignUpComponent } from 'src/app/component/sign-up/sign-up.component';
import { MyProfileComponent } from 'src/app/component/my-profile/my-profile.component';
import { LoginComponent } from 'src/app/component/login/login.component';
import { BreadCrumbComponent } from 'src/app/component/bread-crumb/bread-crumb.component';
import { ToasterService } from 'src/app/services/toaster.service';

describe('GipherRecomendationComponent', () => {
  let component: GipherRecomendationComponent;
  let fixture: ComponentFixture<GipherRecomendationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        GipherRecomendationComponent,
        BreadCrumbComponent,
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
        RouterTestingModule.withRoutes(routesArray),
        HttpClientModule
      ],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [
        RouterService,
        ControllerService,
        AuthenticationService,
        GipherManagerService,
        GipherRecomenderSystemService,
        ToasterService
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GipherRecomendationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
