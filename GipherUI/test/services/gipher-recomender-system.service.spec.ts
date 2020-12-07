import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';

import { GipherManagerService } from 'src/app/services/gipher-manager.service';

import { GipherRecomenderSystemService } from '../../src/app/services/gipher-recomender-system.service';
import { ControllerService } from 'src/app/services/controller.service';
import { ToasterService } from 'src/app/services/toaster.service';

describe('GipherRecomenderSystemService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    declarations: [],
    imports: [HttpClientModule],
    schemas: [NO_ERRORS_SCHEMA],
    providers: [
      ControllerService,
      AuthenticationService,
      GipherManagerService,
      GipherRecomenderSystemService,
      ToasterService
    ]
  }));

  it('should be created', () => {
    const service: GipherRecomenderSystemService = TestBed.get(GipherRecomenderSystemService);
    expect(service).toBeTruthy();
  });
});