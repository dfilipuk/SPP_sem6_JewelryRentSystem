import { Credential } from '../../models/credential';

export interface IAuthorizationService {
    login(credential: Credential): Promise<void>;
    logout(): Promise<void>;
}