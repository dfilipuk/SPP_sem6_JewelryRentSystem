import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'error-page',
  templateUrl: './error-page.component.html',
  styleUrls: ['./error-page.component.css']
})
export class ErrorPageComponent implements OnInit {

  @Input() code: number;

  constructor() { }

  ngOnInit() {
  }

}
