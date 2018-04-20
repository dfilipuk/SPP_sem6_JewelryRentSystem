import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JewelryCrudComponent } from './jewelry-crud.component';

describe('JewelryCrudComponent', () => {
  let component: JewelryCrudComponent;
  let fixture: ComponentFixture<JewelryCrudComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JewelryCrudComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JewelryCrudComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
