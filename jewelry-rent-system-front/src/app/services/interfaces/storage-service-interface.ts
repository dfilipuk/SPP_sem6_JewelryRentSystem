export interface IStorageService {
    get(key: string);
    set(key: string, value: string);
    remove(key: string);
}