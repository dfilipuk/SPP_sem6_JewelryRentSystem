import { Component, OnInit, ViewChild, TemplateRef, EventEmitter } from '@angular/core';
import { MaterializeAction } from 'angular2-materialize';
import { Material } from '../../models/material';
import { CrudStatus } from '../../models/enums/crud-status';
import { MaterialService } from '../../services/material-service';

@Component({
  selector: 'material-crud',
  templateUrl: './material-crud.component.html',
  styleUrls: ['./material-crud.component.css'],
  providers: [MaterialService]
})
export class MaterialCrudComponent implements OnInit {

  @ViewChild('readOnlyTemplate')
  public readOnlyTemplate: TemplateRef<any>;

  @ViewChild('editTemplate')
  public editTemplate: TemplateRef<any>;

  public deleteModalAction = new EventEmitter<string | MaterializeAction>();

  public editedItem: Material;
  public items: Array<Material>;
  public isNewRecord: boolean;
  public operationStatus: CrudStatus;
  public sorting: string;

  constructor(private service: MaterialService) {
    this.items = new Array<Material>();
  }

  ngOnInit() {
    this.loadList();
  }

  loadTemplate(item: Material) {
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
      default:
        return;
    }
  }

  addItem() {
    this.editedItem = new Material(0, "", "", 0);
    this.insertItemToList(0, this.editedItem);
    this.isNewRecord = true;
  }

  editItem(item: Material) {
    this.editedItem = new Material(item.id, item.name, item.description, item.parentMaterialId);
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
    this.service.getAll().subscribe((items: Material[]) => {
      this.items = items;
    });
  }

  private saveAddedItem(alwaysFunc: () => void) {
    this.service.create(this.editedItem).subscribe((id: number) => {
      let addedItem = new Material(id, this.editedItem.name, this.editedItem.description, this.editedItem.parentMaterialId);
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

  private insertItemToList(index: number, item: Material) {
    this.items.splice(index, 0, item);
  }

  private changeItemInList(itemId: number, changedItem?: Material) {
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
