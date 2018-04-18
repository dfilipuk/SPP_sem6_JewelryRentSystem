import { NetworkService } from "./base/network-service-base";
import { IUserService } from "./interfaces/user-service-interface";
import { Injectable } from "@angular/core";
import { EnvironmentService } from "./common/enviroment-service";
import { Http } from "@angular/http";
import { isNullOrUndefined } from "util";
import { User } from "../models/user";

@Injectable()
export class UserService extends NetworkService implements IUserService {

    isUser: boolean;
    isAdmin: boolean;
    isSeller: boolean;
    isManager: boolean;
    user: User = undefined;

    constructor(http: Http) {
        super(EnvironmentService.ServerUrl, EnvironmentService.AuthTokenName, http);
        this.updateCurrentUser();
    }

    updateCurrentUser(): void {
        if (isNullOrUndefined(this.storage.get(this.authTokenName))) {
            this.setUserValue();
            return;
        }
        this.getCurrentUser().subscribe(
            data => this.setUserValue(data),
            error => this.setUserValue()
        );
    }

    private getCurrentUser() {
        return this.get("/auth/user");
    }

    private setUserValue(user?: User) {
        this.user = user == null ? new User() : user;
        this.isUser = !isNullOrUndefined(user);
        this.isAdmin = this.isUser && user.role === "ROLE_ADMIN";
        this.isSeller = this.isUser && user.role === "ROLE_SELLER";
        this.isManager = this.isUser && user.role === "ROLE_MANAGER";
    }
}