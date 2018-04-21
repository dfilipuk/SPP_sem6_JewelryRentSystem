import { DocumentExtension } from "../../models/enums/document-extension";
import { DocumentType } from "../../models/enums/document-type";

export interface IDocumentService {
    openDocument(docType: DocumentType, docExtension: DocumentExtension, params?: any);
}