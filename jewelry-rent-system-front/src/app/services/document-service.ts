import { IDocumentService } from "./interfaces/document-service-interface";
import { DocumentExtension } from "../models/enums/document-extension";
import { DocumentType } from "../models/enums/document-type";
import { Injectable } from "@angular/core";
import { EnvironmentService } from "./common/enviroment-service";
import { StorageService } from "./common/storage-service";
import { NetworkService } from "./base/network-service-base";
import { Http } from "@angular/http";

@Injectable()
export class DocumentService extends NetworkService implements IDocumentService {

    constructor(http: Http) {
        super(EnvironmentService.ServerUrl, EnvironmentService.AuthTokenName, http);
    }

    openDocument(docType: DocumentType, docExtension: DocumentExtension, params?: any) {
        let requestArg = this.getDocumentParams(docType, params);
        let methodUrl = `/document/${DocumentType[docType]}-${DocumentExtension[docExtension]}`;
        let requestRelativeUrl = `${methodUrl}?${requestArg}`.toLowerCase();
        this.openWindow(requestRelativeUrl);
    }

    private getDocumentParams(docType: DocumentType, params: any): string {
        switch (docType) {
            case DocumentType.Order:
            case DocumentType.Profit:
                return `start-date=${params.startDate}&end-date=${params.endDate}`;
            case DocumentType.Employee:
                return 'branch-id=' + params.branchId;
            case DocumentType.Jewelry:
                return 'jewelry-id=' + params.jewelryId;
            case DocumentType.Client:
            default:
                return "";
        }
    }
}