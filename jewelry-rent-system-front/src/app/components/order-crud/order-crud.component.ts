import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../services/order-service';

@Component({
  selector: 'order-crud',
  templateUrl: './order-crud.component.html',
  styleUrls: ['./order-crud.component.css'],
  providers: [OrderService]
})
export class OrderCrudComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
