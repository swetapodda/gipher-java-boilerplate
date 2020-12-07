import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from '../component/login/login.component';

import { RecoverPasswordComponent } from '../component/recover-password/recover-password.component';
import { PageNotFoundComponent } from '../component/page-not-found/page-not-found.component';
import { SignUpComponent } from '../component/sign-up/sign-up.component';
import { DashBoardComponent } from '../component/dash-board/dash-board.component';
import { ResetPassswordComponent } from '../component/reset-passsword/reset-passsword.component';
import { MyProfileComponent } from '../component/my-profile/my-profile.component';
import { MyFavouritesListComponent } from '../component/my-favourites-list/my-favourites-list.component';
import { GipherRecomendationComponent } from '../component/gipher-recomendation/gipher-recomendation.component';
import { CanActivateRouteGuard } from '../services/can-activate-route.guard';

/*
Gipher UI Routing Mapping Details
*/
// Exporting So that Testing will be Easy.

export const routesArray: Routes = [
  { path: '', redirectTo: 'dashBoard', pathMatch: 'full' },
  { path: 'dashBoard', component: DashBoardComponent },
  { path: 'myFavourites', component: MyFavouritesListComponent, canActivate: [CanActivateRouteGuard] },
  { path: 'gipherRecomendation', component: GipherRecomendationComponent, canActivate: [CanActivateRouteGuard] },
  { path: 'logOut', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'signUp', component: SignUpComponent },
  { path: 'recoverPassword', component: RecoverPasswordComponent },
  { path: 'resetPassword', component: ResetPassswordComponent },
  { path: 'myProfile', component: MyProfileComponent, canActivate: [CanActivateRouteGuard] },
  { path: '**', component: PageNotFoundComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routesArray, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
