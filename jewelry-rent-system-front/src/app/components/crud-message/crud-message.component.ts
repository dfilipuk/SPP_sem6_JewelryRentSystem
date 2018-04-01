import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { CrudStatus } from '../../models/enums/crud-status';
import { toast } from 'angular2-materialize';

@Component({
  selector: 'crud-message',
  templateUrl: './crud-message.component.html',
  styleUrls: ['./crud-message.component.css']
})
export class CrudMessageComponent implements OnInit, OnChanges {

  @Input()
  public crudStatus: CrudStatus;

  constructor() {}

  ngOnInit() {
  }
  
  ngOnChanges(changes: SimpleChanges): void {
    if (this.crudStatus === undefined) return;
    let message = this.getStatusMessage(this.crudStatus);
    toast(message, 4000);
  }

  private getStatusMessage(status: any) {
    switch (status) {
      case CrudStatus.Deleted:
        return 'The data was successfully deleted.';
      case CrudStatus.NotDeleted:
        return 'The data was not deleted.';
      case CrudStatus.Added:
        return 'The data was successfully added.';
      case CrudStatus.NotAdded:
        return 'The data was not added.';
      case CrudStatus.Updated:
        return 'The data was successfully updated.';
      case CrudStatus.NotUpdated:
        return 'The data was not updated.';
      default:
        return "";
    }
  }
}
