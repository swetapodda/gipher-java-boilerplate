import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ControllerService } from 'src/app/services/controller.service';
import { RouterService } from 'src/app/services/router.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  submitted = false;
  loginForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
    private controllerService: ControllerService,
    private routerService: RouterService) {

    this.controllerService.setBreadCrumbTitle(null); // No BreadCrumb

    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', [Validators.required]]
    });

  }

  ngOnInit() {

  }

  loginUser() {

    this.controllerService.setProcessingStatus(true);
    this.submitted = true;

    // Check whether form is Valid

    if (this.loginForm.invalid) {
      this.controllerService.setProcessingStatus(false);
      this.submitted = false;
      return;
    }

    this.controllerService.authenticateUser(this.loginForm.value)
      .subscribe((data: any) => {
        const aletMessage = {
          message: 'Login Success. Forwarding to DashBoard',
          type: 'success'
        };

        this.controllerService.pushAlertMessage(aletMessage, false);
        this.submitted = false;

        this.routerService.routeToDashBoard();
      },
        error => {
          const defaultErrorMessage = 'Could not Login';
          const aletMessage = this.controllerService.populateProperErrorMessage(defaultErrorMessage, error);

          this.controllerService.pushAlertMessage(aletMessage, false);
          this.submitted = false;
        }); // Subscribe
  }

  get loginFormControls() { return this.loginForm.controls; }

}
