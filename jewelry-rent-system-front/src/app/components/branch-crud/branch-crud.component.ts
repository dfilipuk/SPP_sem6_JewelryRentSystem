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

  public editedBranch: Branch;
  public branches: Array<Branch>;
  public isNewRecord: boolean;
  public operationStatus: CrudStatus;

  constructor(private service: BranchService) {
    this.branches = new Array<Branch>();
  }

  ngOnInit() {
    this.loadBranches();
  }

  private loadBranches() {
    this.service.getAll().subscribe((data: Branch[]) => {
      this.branches = data;
    });
  }

  addBranch() {
    this.editedBranch = new Branch(0, "", "");
    this.branches = [this.editedBranch].concat(this.branches);// .push(this.editedBranch);
    this.isNewRecord = true;
  }

  editBranch(branch: Branch) {
    this.editedBranch = new Branch(branch.id, branch.address, branch.telephone);
  }

  loadTemplate(branch: Branch) {
    if (this.editedBranch && this.editedBranch.id == branch.id) {
      return this.editTemplate;
    } else {
      return this.readOnlyTemplate;
    }
  }

  saveAddedBranch() {
    this.service.create(this.editedBranch).subscribe(data => {
      this.operationStatus = CrudStatus.Added;
      this.loadBranches();
    }, error => {
      this.operationStatus = CrudStatus.NotAdded;
    });
  }

  saveEditedBranch() {
    this.service.update(this.editedBranch).subscribe(data => {
      this.operationStatus = CrudStatus.Updated;
      this.loadBranches();      
    }, error => { 
      this.operationStatus = CrudStatus.NotUpdated;
    });
  }

  saveBranch() {
    if (this.isNewRecord) {
      this.saveAddedBranch();
    } else {
      this.saveEditedBranch();
    }
    this.isNewRecord = false;
    this.editedBranch = null;
  }

  cancel() {
    if (this.isNewRecord) {
      this.branches.pop();
      this.isNewRecord = false;
    }
    this.editedBranch = null;
  }

  deleteBranch(branch: Branch) {
    this.service.delete(branch.id).subscribe(data => {
      this.branches = this.branches.filter(x => x.id != branch.id);
      this.operationStatus = CrudStatus.Deleted;
    }, error => {
      this.operationStatus = CrudStatus.NotDeleted;
    });
  }
}
