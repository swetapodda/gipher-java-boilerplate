import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ControllerService } from 'src/app/services/controller.service';
import { RouterService } from 'src/app/services/router.service';

@Component({
  selector: 'app-reset-passsword',
  templateUrl: './reset-passsword.component.html',
  styleUrls: ['./reset-passsword.component.css']
})

export class ResetPassswordComponent implements OnInit {

  resetPasswordForm: FormGroup;
  submitted = false;
  customError: string;

  constructor(private formBuilder: FormBuilder, private controllerService: ControllerService, private routerService: RouterService) {

    this.controllerService.setBreadCrumbTitle(null); // No BreadCrumb
    this.createReactiveForm();

  }


  createReactiveForm() {
    this.resetPasswordForm = this.formBuilder.group({
      email: ['', Validators.required],
      passwordResetToken: ['', [Validators.required]],
      password: ['', [Validators.required]], // Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]] // Validators.minLength(6)]]
    });
  }


  ngOnInit(): void {

  }

  resetPassword() {

    this.controllerService.setProcessingStatus(true);
    this.submitted = true;
    this.customError = null;

    // Check whether form is Valid

    if (this.resetPasswordForm.invalid) {
      console.log('Reset Password form is Invalid');
      this.controllerService.setProcessingStatus(false);
      this.submitted = false;
      return;
    } else {

      if (this.resetPasswordFormControls['password'].value !== this.resetPasswordFormControls['confirmPassword'].value) {
        this.controllerService.setProcessingStatus(false);

        // Password Mismatch

        this.submitted = false;
        this.customError = 'Password Mismatch';
        return false;
      }
    }

    console.log('Resetting Password');

    // API Call to Reset Password

    this.controllerService.resetPassword(this.resetPasswordForm.value)
      .subscribe({
        next: data => {
          const aletMessage = {
            message: 'Password Reset Success. Redirecting to Login. Login and Contunue.',
            type: 'success'
          };

          this.controllerService.pushAlertMessage(aletMessage, false);

          this.submitted = false;

          this.routerService.routeToLogin();
        },
        error: error => {

          const defaultErrorMessage = 'Failed to Reset Password';
          const aletMessage = this.controllerService.populateProperErrorMessage(defaultErrorMessage, error);

          this.controllerService.pushAlertMessage(aletMessage, false);

          this.submitted = false;
        }
      }); // Subscribe
  }

  get resetPasswordFormControls() { return this.resetPasswordForm.controls; }

}
