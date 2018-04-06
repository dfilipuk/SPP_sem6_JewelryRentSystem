import { User } from "../../models/user";

export interface IUserService {
    isUser: boolean;
    isAdmin: boolean;
    user: User;
    updateCurrentUser(): void;
}