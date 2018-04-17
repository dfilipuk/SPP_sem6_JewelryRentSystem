import { Component, OnInit } from '@angular/core';
import { BranchService } from '../../services/branch-service';
import { Branch } from '../../models/branch';
import { TemplateRef, ViewChild } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';
import { CrudStatus } from '../../models/enums/crud-status';

@Component({
  selector: 'branch-crud',
  templateUrl: './branch-crud.component.html',
  styleUrls: ['./branch-crud.component.css'],
  providers: [BranchService]
})
export class BranchCrudComponent implements OnInit {

  @ViewChild('readOnlyTemplate')
  public readOnlyTemplate: TemplateRef<any>;

  @ViewChild('editTemplate')
  public editTemplate: TemplateRef<any>;

  public editedItem: Branch;
  public items: Array<Branch>;
  public isNewRecord: boolean;
  public operationStatus: CrudStatus;

  constructor(private service: BranchService) {
    this.items = new Array<Branch>();
  }

  ngOnInit() {
    this.loadList();
  }

  loadTemplate(item: Branch) {
    if (this.editedItem && this.editedItem.id == item.id) {
      return this.editTemplate;
    } else {
      return this.readOnlyTemplate;
    }
  }

  addItem() {
    this.editedItem = new Branch(0, "", "");
    this.insertItemToList(0, this.editedItem);
    this.isNewRecord = true;
  }

  editItem(item: Branch) {
    this.editedItem = new Branch(item.id, item.address, item.telephone);
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

  deleteItem(item: Branch) {
    this.service.delete(item.id).subscribe(() => {
      this.changeItemInList(item.id);
      this.operationStatus = CrudStatus.Deleted;
    }, error => {
      this.operationStatus = CrudStatus.NotDeleted;
    });
  }

  private loadList() {
    this.service.getAll().subscribe((items: Branch[]) => {
      this.items = items;
    });
  }

  private saveAddedItem(alwaysFunc: () => void) {
    this.service.create(this.editedItem).subscribe((id: number) => {
      let addedItem = new Branch(id, this.editedItem.address, this.editedItem.telephone);
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

  private insertItemToList(index: number, item: Branch) {
    this.items.splice(index, 0, item);
  }

  private changeItemInList(itemId: number, changedItem?: Branch) {
    let i = this.items.findIndex(x => x.id == itemId);
    changedItem == null
      ? this.items.splice(i, 1)
      : this.items.splice(i, 1, changedItem);
  }
}
