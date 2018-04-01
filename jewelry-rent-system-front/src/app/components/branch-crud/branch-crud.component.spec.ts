import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BranchCrudComponent } from './branch-crud.component';

describe('BranchCrudComponent', () => {
  let component: BranchCrudComponent;
  let fixture: ComponentFixture<BranchCrudComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BranchCrudComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BranchCrudComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
