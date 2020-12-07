import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing/app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './component/header/header.component';
import { FooterComponent } from './component/footer/footer.component';

import { LoginComponent } from './component/login/login.component';
import { RecoverPasswordComponent } from './component/recover-password/recover-password.component';
import { PageNotFoundComponent } from './component/page-not-found/page-not-found.component';
import { SignUpComponent } from './component/sign-up/sign-up.component';
import { DashBoardComponent } from './component/dash-board/dash-board.component';
import { ControllerService } from './services/controller.service';
import { AuthenticationService } from './services/authentication.service';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ToasterMessageComponent } from './component/toaster-message/toaster-message.component';
import { ResetPassswordComponent } from './component/reset-passsword/reset-passsword.component';
import { MyProfileComponent } from './component/my-profile/my-profile.component';
import { RouterService } from './services/router.service';
import { MyFavouritesListComponent } from './component/my-favourites-list/my-favourites-list.component';
import { BreadCrumbComponent } from './component/bread-crumb/bread-crumb.component';
import { GipherManagerService } from './services/gipher-manager.service';
import { GipherRecomendationComponent } from './component/gipher-recomendation/gipher-recomendation.component';
import { GipherRecomenderSystemService } from './services/gipher-recomender-system.service';
import { CanActivateRouteGuard } from './services/can-activate-route.guard';
import { ToasterService } from './services/toaster.service';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,

    LoginComponent,
    RecoverPasswordComponent,
    PageNotFoundComponent,
    SignUpComponent,
    DashBoardComponent,
    ToasterMessageComponent,
    ResetPassswordComponent,
    MyProfileComponent,
    MyFavouritesListComponent,
    BreadCrumbComponent,
    GipherRecomendationComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule, /* Reactive Form Related */
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [
    RouterService,
    ControllerService,
    AuthenticationService,
    GipherManagerService,
    GipherRecomenderSystemService,
    ToasterService,
    CanActivateRouteGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
