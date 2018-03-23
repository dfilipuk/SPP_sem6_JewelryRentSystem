import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CrudMessageComponent } from './crud-message.component';

describe('CrudMessageComponent', () => {
  let component: CrudMessageComponent;
  let fixture: ComponentFixture<CrudMessageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CrudMessageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CrudMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
