import { Component, OnInit, ViewChild, TemplateRef, EventEmitter } from '@angular/core';
import { JewelryService } from '../../services/jewelry-service';
import { Jewelry } from '../../models/jewelry';
import { MaterializeAction } from 'angular2-materialize';
import { CrudStatus } from '../../models/enums/crud-status';

@Component({
  selector: 'jewelry-crud',
  templateUrl: './jewelry-crud.component.html',
  styleUrls: ['./jewelry-crud.component.css'],
  providers: [JewelryService]
})
export class JewelryCrudComponent implements OnInit {

  @ViewChild('readOnlyTemplate')
  public readOnlyTemplate: TemplateRef<any>;

  @ViewChild('editTemplate')
  public editTemplate: TemplateRef<any>;

  public deleteModalAction = new EventEmitter<string | MaterializeAction>();
  public type: string;
  public materialsStr: string;
  public materialSpliter = ",";

  public editedItem: Jewelry;
  public items: Array<Jewelry>;
  public isNewRecord: boolean;
  public operationStatus: CrudStatus;
  public sorting: string;

  constructor(private service: JewelryService) {
    this.items = new Array<Jewelry>();
  }

  ngOnInit() {
    this.loadList();
  }

  loadTemplate(item: Jewelry) {
    if (this.editedItem && this.editedItem.id == item.id) {
      return this.editTemplate;
    } else {
      return this.readOnlyTemplate;
    }
  }

  convertMaterialStrToIds() {
    this.editedItem.materialsIds = this.materialsStr.split(this.materialSpliter)
      .map(x => Number(x.trim()))
      .filter(x => !isNaN(x));
  }

  convertMaterialIdsToStr(materialsIds: number[]) {
    return materialsIds === undefined
      ? ""
      : materialsIds.join(this.materialSpliter);
  }

  sort() {
    switch (this.sorting) {
      case "Id":
        this.items.sort((a, b) => this.sortCompare(a.id, b.id));
        return;
      case "Type":
        this.items.sort((a, b) => this.sortCompare(a.type, b.type));
        return;
      case "Name":
        this.items.sort((a, b) => this.sortCompare(a.name, b.name));
        return;
      case "Producer":
        this.items.sort((a, b) => this.sortCompare(a.producer, b.producer));
        return;
      case "Branch ID":
        this.items.sort((a, b) => this.sortCompare(a.branchId, b.branchId));
        return;
      default:
        return;
    }
  }

  addItem() {
    this.editedItem = new Jewelry(0, "", "", "Description", "", "Earring", 0, "", 0, 1, 1, []);
    this.type = this.editedItem.type;
    this.insertItemToList(0, this.editedItem);
    this.isNewRecord = true;
  }

  editItem(item: Jewelry) {
    this.editedItem = new Jewelry(item.id, item.name, item.producer, item.description, item.pictureUrl,
      item.type, item.weight, item.status, item.costPerDay, item.daysRental, item.branchId, item.materialsIds);
    this.type = this.editedItem.type;
  }

  saveItem() {
    this.editedItem.type = this.type;
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
    this.service.getAll().subscribe((items: Jewelry[]) => {
      this.items = items;
    });
  }

  private saveAddedItem(alwaysFunc: () => void) {
    this.service.create(this.editedItem).subscribe((id: number) => {
      let addedItem = new Jewelry(id, this.editedItem.name, this.editedItem.producer, this.editedItem.description,
        this.editedItem.pictureUrl, this.editedItem.type, this.editedItem.weight, this.editedItem.status,
        this.editedItem.costPerDay, this.editedItem.daysRental, this.editedItem.branchId, this.editedItem.materialsIds);
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

  private insertItemToList(index: number, item: Jewelry) {
    this.items.splice(index, 0, item);
  }

  private changeItemInList(itemId: number, changedItem?: Jewelry) {
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
