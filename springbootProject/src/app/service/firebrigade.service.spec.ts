import { TestBed } from '@angular/core/testing';

import { FirebrigadeService } from './firebrigade.service';

describe('FirebrigadeService', () => {
  let service: FirebrigadeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FirebrigadeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
