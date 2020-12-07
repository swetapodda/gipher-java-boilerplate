import { NO_ERRORS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ToasterService } from 'src/app/services/toaster.service';

import { ToasterMessageComponent } from '../../src/app/component/toaster-message/toaster-message.component';

describe('AlertMessageComponent', () => {
  let component: ToasterMessageComponent;
  let fixture: ComponentFixture<ToasterMessageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ToasterMessageComponent],
      imports: [],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [
        ToasterService
      ]
    })
      .compileComponents();
  }));


  beforeEach(() => {

    fixture = TestBed.createComponent(ToasterMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
