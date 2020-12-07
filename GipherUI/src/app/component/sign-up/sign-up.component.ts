import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { ControllerService } from 'src/app/services/controller.service';
import { RouterService } from 'src/app/services/router.service';

/*
Reactive Forms and Validation
*/

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})

export class SignUpComponent implements OnInit {

  customError: string;
  registrationForm: FormGroup;
  submitted = false;


  constructor(private formBuilder: FormBuilder, private controllerService: ControllerService, private routerService: RouterService) {

    this.controllerService.setBreadCrumbTitle(null); // No BreadCrumb
    this.createReactiveForm();

  }


  createReactiveForm() {
    this.registrationForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', [Validators.required]], // , Validators.minLength(8)]],
      confirmPassword: ['', [Validators.required]] // , Validators.minLength(8)]]
    });

  }

  ngOnInit(): void {

  }
  createUesr() {

    this.controllerService.setProcessingStatus(true);
    this.submitted = true;
    this.customError = null;

    // Check whether form is Valid

    if (this.registrationForm.invalid) {

      this.controllerService.setProcessingStatus(false);
      this.submitted = false;
      return;

    } else {

      if (this.registrationControls['password'].value !== this.registrationControls['confirmPassword'].value) {
        this.controllerService.setProcessingStatus(false);

        // Password Mismatch

        this.submitted = false;
        this.customError = 'Password Mismatch';
        return false;
      }
    }

    // User Creation API Call

    this.controllerService.createUser(this.registrationForm.value)
      .subscribe({
        next: () => {
          const aletMessage = {
            message: 'Registration Success. Redirecting to Login. Login and Contunue.',
            type: 'success'
          };

          this.controllerService.pushAlertMessage(aletMessage, false);
          this.submitted = false;
          this.routerService.routeToLogin();
        },
        error: error => {
          const defaultErrorMessage = 'Failed to Sign Up';
          const aletMessage = this.controllerService.populateProperErrorMessage(defaultErrorMessage, error);

          this.controllerService.pushAlertMessage(aletMessage, false);

          this.submitted = false;
        }
      }); // Subscribe

  }

  get registrationControls() { return this.registrationForm.controls; }
}
