import { Component, OnInit } from '@angular/core';
import { DocumentExtension } from '../../models/enums/document-extension';
import { DocumentType } from '../../models/enums/document-type';
import { DocumentService } from '../../services/document-service';

@Component({
  selector: 'documents-page',
  templateUrl: './documents-page.component.html',
  styleUrls: ['./documents-page.component.css'],
  providers: [DocumentService]
})
export class DocumentsPageComponent implements OnInit {

  public docExtension: DocumentExtension = 0;
  public docType: DocumentType = 0;
  public docParams: any;

  public startDateValue: string;
  public endDateValue: string;

  constructor(private service: DocumentService) { 
    this.docParams = {
      branchId: 1,
      materialId: 1,
      startDate: "",
      endDate: ""
    }
  }

  ngOnInit() {
  }

  generateDocument() {
    this.docParams.startDate = this.startDateValue;
    this.docParams.endDate = this.endDateValue;
    this.service.openDocument(this.docType, this.docExtension, this.docParams);
  }

}
