import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import { ControllerService } from 'src/app/services/controller.service';
import { RouterService } from 'src/app/services/router.service';

@Component({
  selector: 'app-recover-password',
  templateUrl: './recover-password.component.html',
  styleUrls: ['./recover-password.component.css']
})

export class RecoverPasswordComponent implements OnInit {


  recoverPasswordForm: FormGroup;
  submitted = false;


  constructor(private formBuilder: FormBuilder, private controllerService: ControllerService, private routerService: RouterService) {

    this.controllerService.setBreadCrumbTitle(null); // No BreadCrumb
    this.createReactiveForm();
  }


  createReactiveForm() {
    this.recoverPasswordForm = this.formBuilder.group({
      email: ['', Validators.required] /*,
      securityToken: ['', [Validators.required]],
      password: ['', [Validators.required]], // Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]
      ] // Validators.minLength(6)]] */
    });
  }


  ngOnInit(): void {
  }

  recoverPassword() {
    this.submitted = true;

    // Check whether form is Valid
    if (this.recoverPasswordForm.invalid) {
      console.log('Reset Password form is Invalid');
      return;
    }
      // Recover Password Request API Call

      this.controllerService.recoverPassword(this.recoverPasswordForm.value)
      .subscribe((data: any) => {
        const aletMessage = {
          message: 'Passwrod Reset Request Success. Check your Email. And Follow.',
          type: 'success'
        };
        this.controllerService.pushAlertMessage(aletMessage, false);

        this.submitted = false;

        this.routerService.routerToResetPassword();
      },
        error => {
          const defaultErrorMessage = 'Request to Reset Password Failed';
          const aletMessage = this.controllerService.populateProperErrorMessage(defaultErrorMessage, error);

          this.controllerService.pushAlertMessage(aletMessage, false);

          this.submitted = false;
        }); // Subscribe
  }

  get recoverPasswordFormControls() { return this.recoverPasswordForm.controls; }

}
