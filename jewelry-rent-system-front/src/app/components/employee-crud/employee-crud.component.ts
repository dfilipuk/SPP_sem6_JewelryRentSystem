import { Component, OnInit, ViewChild, TemplateRef, EventEmitter } from '@angular/core';
import { MaterializeAction } from 'angular2-materialize';
import { Employee } from '../../models/employee';
import { CrudStatus } from '../../models/enums/crud-status';
import { EmployeeService } from '../../services/employee-service';

@Component({
  selector: ' employee-crud',
  templateUrl: './employee-crud.component.html',
  styleUrls: ['./employee-crud.component.css'],
  providers: [EmployeeService]
})
export class EmployeeCrudComponent implements OnInit {

  @ViewChild('readOnlyTemplate')
  public readOnlyTemplate: TemplateRef<any>;

  @ViewChild('editTemplate')
  public editTemplate: TemplateRef<any>;

  public deleteModalAction = new EventEmitter<string | MaterializeAction>();
  public role: string = "ROLE_ADMIN";

  public editedItem: Employee;
  public items: Array<Employee>;
  public isNewRecord: boolean;
  public operationStatus: CrudStatus;
  public sorting: string;
  public showPassword = true;

  constructor(private service: EmployeeService) {
    this.items = new Array<Employee>();
  }

  ngOnInit() {
    this.loadList();
  }

  loadTemplate(item: Employee) {
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
      case "Login":
        this.items.sort((a, b) => this.sortCompare(a.login, b.login));
        return;
      case "Salary":
        this.items.sort((a, b) => this.sortCompare(a.salary, b.salary));
        return;
      default:
        return;
    }
  }

  addItem() {
    this.editedItem = new Employee(0, "", "", "", 0, "Position", "", "", "", 1);
    this.insertItemToList(0, this.editedItem);
    this.isNewRecord = true;
  }

  editItem(item: Employee) {
    this.editedItem = new Employee(item.id, item.name, item.surname, item.secondName,
      item.salary, item.position, item.login, item.password, item.role, item.branchId);
    this.role = this.editedItem.role;
  }

  saveItem() {
    this.editedItem.role = this.role;
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
    this.service.getAll().subscribe((items: Employee[]) => {
      this.items = items;
    });
  }

  private saveAddedItem(alwaysFunc: () => void) {
    this.service.create(this.editedItem).subscribe((id: number) => {
      let addedItem = new Employee(id, this.editedItem.name, this.editedItem.surname, this.editedItem.secondName,
        this.editedItem.salary, this.editedItem.position, this.editedItem.login, 
        this.editedItem.password, this.editedItem.role, this.editedItem.branchId);
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

  private insertItemToList(index: number, item: Employee) {
    this.items.splice(index, 0, item);
  }

  private changeItemInList(itemId: number, changedItem?: Employee) {
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
