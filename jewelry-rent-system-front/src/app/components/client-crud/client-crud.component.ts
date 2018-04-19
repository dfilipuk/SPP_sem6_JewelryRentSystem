import { Component, OnInit, ViewChild, TemplateRef, EventEmitter } from '@angular/core';
import { MaterializeAction } from 'angular2-materialize';
import { Client } from '../../models/client';
import { CrudStatus } from '../../models/enums/crud-status';
import { ClientService } from '../../services/client-service';

@Component({
  selector: 'client-crud',
  templateUrl: './client-crud.component.html',
  styleUrls: ['./client-crud.component.css'],
  providers: [ClientService]
})
export class ClientCrudComponent implements OnInit {
  @ViewChild('readOnlyTemplate')
  public readOnlyTemplate: TemplateRef<any>;

  @ViewChild('editTemplate')
  public editTemplate: TemplateRef<any>;

  public deleteModalAction = new EventEmitter<string | MaterializeAction>();

  public editedItem: Client;
  public items: Array<Client>;
  public isNewRecord: boolean;
  public operationStatus: CrudStatus;
  public sorting: string;

  constructor(private service: ClientService) {
    this.items = new Array<Client>();
  }

  ngOnInit() {
    this.loadList();
  }

  loadTemplate(item: Client) {
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
      case "Name":
        this.items.sort((a, b) => this.sortCompare(a.name, b.name));
        return;
      case "Second name":
        this.items.sort((a, b) => this.sortCompare(a.secondName, b.secondName));
        return;
      case "Address":
        this.items.sort((a, b) => this.sortCompare(a.address, b.address));
        return;
      case "Telephone":
        this.items.sort((a, b) => this.sortCompare(a.telephone, b.telephone));
        return;
      default:
        return;
    }
  }

  addItem() {
    this.editedItem = new Client(0, "", "", "", "", "", "");
    this.insertItemToList(0, this.editedItem);
    this.isNewRecord = true;
  }

  editItem(item: Client) {
    this.editedItem = new Client(item.id, item.name, item.surname, item.secondName,
      item.passportNumber, item.address, item.telephone);
  }

  saveItem() {
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
    this.service.getAll().subscribe((items: Client[]) => {
      this.items = items;
    });
  }

  private saveAddedItem(alwaysFunc: () => void) {
    this.service.create(this.editedItem).subscribe((id: number) => {
      let addedItem = new Client(id, this.editedItem.name, this.editedItem.surname,
        this.editedItem.secondName, this.editedItem.passportNumber, this.editedItem.address, this.editedItem.telephone);
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

  private insertItemToList(index: number, item: Client) {
    this.items.splice(index, 0, item);
  }

  private changeItemInList(itemId: number, changedItem?: Client) {
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
