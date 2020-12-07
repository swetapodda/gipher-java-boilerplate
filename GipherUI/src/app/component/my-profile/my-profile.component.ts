import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ControllerService } from 'src/app/services/controller.service';

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent implements OnInit {

  profileForm: FormGroup;

  submitted: false = false;

  constructor(private formBuilder: FormBuilder, private controllerService: ControllerService) {
    this.createReactiveForm();
  }


  createReactiveForm() {

    this.profileForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', [Validators.required]], // , Validators.minLength(8)]],
      confirmPassword: ['', [Validators.required]] // , Validators.minLength(8)]]
    });

    if (this.controllerService.logedUser) {
      this.profileFormControls['firstName'].setValue(this.controllerService.logedUser.firstName);
      this.profileFormControls['lastName'].setValue(this.controllerService.logedUser.lastName);
      this.profileFormControls['email'].setValue(this.controllerService.logedUser.email);
    }
  }

  getAvatarText() {
    return this.controllerService.getAvatarText();
  }

  ngOnInit() {
    this.controllerService.setBreadCrumbTitle('My Profile');
  }

  updateProfile() {
    console.log('Profile Update is not yet Available');
  }

  get profileFormControls() {
    return this.profileForm.controls;
  }
}
