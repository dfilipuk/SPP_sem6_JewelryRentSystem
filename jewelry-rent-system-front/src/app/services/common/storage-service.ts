import { IStorageService } from '../interfaces/storage-service-interface';

export class StorageService implements IStorageService {

    public get(key: string) {
        return localStorage.getItem(key);
    }

    public set(key: string, value: string) {
        localStorage.setItem(key, value);
    }

    public remove(key: string) {
        localStorage.removeItem(key);
    }
}