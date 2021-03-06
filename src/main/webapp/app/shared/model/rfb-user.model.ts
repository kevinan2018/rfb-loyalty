import { IRfbEventAttendance } from 'app/shared/model//rfb-event-attendance.model';

export interface IRfbUser {
    id?: number;
    username?: string;
    userId?: number;
    rfbeventAttendances?: IRfbEventAttendance[];
    homeLocationId?: number;
    homeLocationName?: string;
    jhiUser?: JhiUser;
}

export class RfbUser implements IRfbUser {
    constructor(
        public id?: number,
        public username?: string,
        public userId?: number,
        public rfbeventAttendances?: IRfbEventAttendance[],
        public homeLocationId?: number,
        public homeLocationName?: string,
        public jhiUser?: JhiUser
    ) {}
}

export interface IJhiUser {
    id?: number;
    login?: string;
    email?: number;
    firstName?: string;
    lastName?: string;
    activated?: boolean;
    langKey?: string;
    imageUrl?: string;
}

export class JhiUser implements IJhiUser {
    constructor(
        public id?: number,
        public login?: string,
        public email?: number,
        public firstName?: string,
        public lastName?: string,
        public activated?: boolean,
        public langKey?: string,
        public imageUrl?: string
    ) {}
}
