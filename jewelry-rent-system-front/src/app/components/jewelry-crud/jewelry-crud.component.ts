import { Component, OnInit } from '@angular/core';
import { JewelryService } from '../../services/jewelry-service';

@Component({
  selector: 'jewelry-crud',
  templateUrl: './jewelry-crud.component.html',
  styleUrls: ['./jewelry-crud.component.css'],
  providers: [JewelryService]
})
export class JewelryCrudComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
