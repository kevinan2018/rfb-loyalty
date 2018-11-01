import { Component, OnInit } from '@angular/core';

import { LoginService, Principal, Account, AccountService, User } from 'app/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IRfbLocation, RfbLocation } from 'app/shared/model/rfb-location.model';
import { RfbUser } from 'app/shared/model/rfb-user.model';
import { RfbEvent } from 'app/shared/model/rfb-event.model';
import { IRfbEventAttendance, RfbEventAttendance } from 'app/shared/model/rfb-event-attendance.model';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { RfbLocationService } from 'app/entities/rfb-location';
import { RfbEventService } from 'app/entities/rfb-event';
import { RfbEventAttendanceService } from 'app/entities/rfb-event-attendance';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { RfbUserService } from 'app/entities/rfb-user';
import moment = require('moment');

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.css']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    isSaving: boolean;
    locations: RfbLocation[];
    todaysEvent: RfbEvent;
    currentUser: RfbUser;
    model: any;
    rfbEventAttendance: RfbEventAttendance;
    errors: any = { invalidEventCode: '' };
    checkedIn = false;

    constructor(
        private principal: Principal,
        private loginService: LoginService,
        private eventManager: JhiEventManager,
        private locationService: RfbLocationService,
        private eventService: RfbEventService,
        private accountService: AccountService,
        private rfbUserService: RfbUserService,
        private rfbEventAttenanceService: RfbEventAttendanceService,
        private jhiAlertService: JhiAlertService
    ) {}

    ngOnInit() {
        this.model = { location: 0, eventCode: '' };
        this.rfbEventAttendance = new RfbEventAttendance(null, moment(), null, null, null, null);

        this.principal.identity().then(account => {
            if (this.isAuthenticated()) {
                this.account = account;
                this.afterUserAccountSetup(account);
                this.loadLocations();
            } else {
                this.registerAuthenticationSuccess();
            }
        });
    }

    afterUserAccountSetup(user: Account) {
        if (user != null) {
            this.rfbUserService
                .findRfdUserByUsername(user.login)
                .pipe(map((rfbUser: HttpResponse<RfbUser>) => rfbUser.body))
                .subscribe(
                    (rfbUser: RfbUser) => {
                        this.currentUser = rfbUser;
                        // event id set in checkIn()
                        this.rfbEventAttendance.rfbUserId = this.currentUser.id;
                        this.rfbEventAttendance.rfbUsername = this.currentUser.username;
                        this.rfbEventAttendance.rfbLocationName = this.currentUser.homeLocationName;

                        if (user.authorities.indexOf('ROLE_ORGANIZER') !== -1) {
                            this.setTodaysEvent(this.currentUser.homeLocationId);
                        }
                        if (user.authorities.indexOf('ROLE_USER') !== -1) {
                            this.model.location = this.currentUser.homeLocationId;
                        }
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } // user not null
    }

    setTodaysEvent(locationId: number) {
        // find an event with today's date and the location id
        this.eventService
            .findByLocation(locationId)
            .pipe(map((rfbEvent: HttpResponse<RfbEvent>) => rfbEvent.body))
            .subscribe((rfbEvent: RfbEvent) => {
                this.todaysEvent = rfbEvent;
            });
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', () => {
            this.principal.identity().then(account => {
                this.account = account;
                this.afterUserAccountSetup(account);
                this.loadLocations();
                console.log('*** authenticationSuccess called ***');
            });
        });
    }

    loadLocations() {
        this.locationService
            .query({
                page: 0,
                size: 100,
                sort: ['locationName', 'ASC']
            })
            .subscribe(
                (res: HttpResponse<IRfbLocation[]>) => {
                    this.locations = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    checkIn() {
        this.eventService
            .findByLocation(this.model.location)
            .pipe(map((rfbEvent: HttpResponse<RfbEvent>) => rfbEvent.body))
            .subscribe((rfbEvent: RfbEvent) => {
                const thisEvent = rfbEvent;
                this.rfbEventAttendance.rfbEventId = rfbEvent.id;
                if (thisEvent.eventCode === this.model.eventCode) {
                    // user is checked in
                    this.rfbEventAttenanceService.create(this.rfbEventAttendance).subscribe(
                        (res: HttpResponse<IRfbEventAttendance>) => {
                            this.checkedIn = true;
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
                } else {
                    this.errors.invalidEventCode =
                        'There is either no run today for this location or you have entered an incorrect event code. Please try again.';
                }
            });
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.loginService.login();
    }
}
