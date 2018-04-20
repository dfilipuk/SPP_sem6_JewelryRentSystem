import { Component, OnInit, ViewChild, TemplateRef, EventEmitter } from '@angular/core';
import { OrderService } from '../../services/order-service';
import { MaterializeAction } from 'angular2-materialize';
import { CrudStatus } from '../../models/enums/crud-status';
import { Order } from '../../models/order';

@Component({
  selector: 'order-crud',
  templateUrl: './order-crud.component.html',
  styleUrls: ['./order-crud.component.css'],
  providers: [OrderService]
})
export class OrderCrudComponent implements OnInit {

  @ViewChild('readOnlyTemplate')
  public readOnlyTemplate: TemplateRef<any>;

  @ViewChild('editTemplate')
  public editTemplate: TemplateRef<any>;

  public deleteModalAction = new EventEmitter<string | MaterializeAction>();
  public rentDateAction = new EventEmitter<string | MaterializeAction>();
  public rentDateValue: string;

  public editedItem: Order;
  public items: Array<Order>;
  public isNewRecord: boolean;
  public operationStatus: CrudStatus;
  public sorting: string;

  constructor(private service: OrderService) {
    this.items = new Array<Order>();
  }

  ngOnInit() {
    this.loadList();
  }

  loadTemplate(item: Order) {
    if (this.editedItem && this.editedItem.id == item.id) {
      return this.editTemplate;
    } else {
      return this.readOnlyTemplate;
    }
  }

  sort() {
    switch (this.sorting) {
      case "Id":
        this.items.sort((a, b) => this.sortCompare(a.id, b.id));
        return;
      case "Rent date":
        this.items.sort((a, b) => this.sortCompare(a.rentDate, b.rentDate));
        return;
      case "Cost":
        this.items.sort((a, b) => this.sortCompare(a.cost, b.cost));
        return;
      case "Client ID":
        this.items.sort((a, b) => this.sortCompare(a.clientId, b.clientId));
        return;
      case "Employee ID":
        this.items.sort((a, b) => this.sortCompare(a.employeeId, b.employeeId));
        return;
      case "Jewelry ID":
        this.items.sort((a, b) => this.sortCompare(a.jewelryId, b.jewelryId));
        return;
      default:
        return;
    }
  }

  addItem() {
    this.editedItem = new Order(0, "", "", 1, 0, 1, 1, 1);
    this.rentDateValue = this.editedItem.rentDate;
    this.insertItemToList(0, this.editedItem);
    this.isNewRecord = true;
  }

  editItem(item: Order) {
    this.editedItem = new Order(item.id, item.status, item.rentDate, item.daysRent, item.cost,
      item.clientId, item.employeeId, item.jewelryId);
    this.rentDateValue = this.editedItem.rentDate;
  }

  saveItem() {
    this.editedItem.rentDate = this.rentDateValue + " 00:00";
    let always = () => {
      this.editedItem = null;
      this.isNewRecord = false;
    }
    this.isNewRecord
      ? this.saveAddedItem(always)
      : this.saveEditedItem(always);
  }

  cancel() {
    if (this.isNewRecord) {
      this.changeItemInList(this.editedItem.id);
      this.isNewRecord = false;
    }
    this.editedItem = null;
  }

  openDeleteModal(item) {
    this.editedItem = item;
    this.deleteModalAction.emit({ action: "modal", params: ['open'] });
  }

  closeDeleteModal() {
    this.editedItem = null;
    this.deleteModalAction.emit({ action: "modal", params: ['close'] });
  }

  private deleteItem() {
    this.service.delete(this.editedItem.id).subscribe(() => {
      this.changeItemInList(this.editedItem.id);
      this.operationStatus = CrudStatus.Deleted;
    }, error => {
      this.operationStatus = CrudStatus.NotDeleted;
    }, () => this.editedItem = null);
  }

  private loadList() {
    this.service.getAll().subscribe((items: Order[]) => {
      this.items = items;
    });
  }

  private saveAddedItem(alwaysFunc: () => void) {
    this.service.create(this.editedItem).subscribe((id: number) => {
      let addedItem = new Order(id, this.editedItem.status, this.editedItem.rentDate,
        this.editedItem.daysRent, this.editedItem.cost,
        this.editedItem.clientId, this.editedItem.employeeId, this.editedItem.jewelryId);
      this.changeItemInList(this.editedItem.id, addedItem);
      this.operationStatus = CrudStatus.Added;
    }, error => {
      this.operationStatus = CrudStatus.NotAdded;
    }, alwaysFunc);
  }

  private saveEditedItem(alwaysFunc: () => void) {
    this.service.update(this.editedItem).subscribe(() => {
      this.changeItemInList(this.editedItem.id, this.editedItem);
      this.operationStatus = CrudStatus.Updated;
    }, error => {
      this.operationStatus = CrudStatus.NotUpdated;
    }, alwaysFunc);
  }

  private insertItemToList(index: number, item: Order) {
    this.items.splice(index, 0, item);
  }

  private changeItemInList(itemId: number, changedItem?: Order) {
    let i = this.items.findIndex(x => x.id == itemId);
    if (changedItem == null) {
      this.items.splice(i, 1);
      return;
    }
    this.items.splice(i, 1, changedItem);
    this.sort();
  }

  private sortCompare(a: any, b: any) {
    return a > b
      ? 1
      : a < b
        ? -1
        : 0;
  }

}
