import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IRfbUser } from 'app/shared/model/rfb-user.model';
import { RfbUserService } from './rfb-user.service';
import { IUser, UserService } from 'app/core';
import { IRfbLocation } from 'app/shared/model/rfb-location.model';
import { RfbLocationService } from 'app/entities/rfb-location';

@Component({
    selector: 'jhi-rfb-user-update',
    templateUrl: './rfb-user-update.component.html'
})
export class RfbUserUpdateComponent implements OnInit {
    rfbUser: IRfbUser;
    isSaving: boolean;

    users: IUser[];

    rfblocations: IRfbLocation[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private rfbUserService: RfbUserService,
        private userService: UserService,
        private rfbLocationService: RfbLocationService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rfbUser }) => {
            this.rfbUser = rfbUser;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rfbLocationService.query().subscribe(
            (res: HttpResponse<IRfbLocation[]>) => {
                this.rfblocations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.rfbUser.id !== undefined) {
            this.subscribeToSaveResponse(this.rfbUserService.update(this.rfbUser));
        } else {
            this.subscribeToSaveResponse(this.rfbUserService.create(this.rfbUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRfbUser>>) {
        result.subscribe((res: HttpResponse<IRfbUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackRfbLocationById(index: number, item: IRfbLocation) {
        return item.id;
    }
}
