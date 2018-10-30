import { IRfbEventAttendance } from 'app/shared/model//rfb-event-attendance.model';

export interface IRfbUser {
    id?: number;
    username?: string;
    userId?: number;
    rfbeventAttendances?: IRfbEventAttendance[];
    homeLocationId?: number;
    homeLocationName?: string;
}

export class RfbUser implements IRfbUser {
    constructor(
        public id?: number,
        public username?: string,
        public userId?: number,
        public rfbeventAttendances?: IRfbEventAttendance[],
        public homeLocationId?: number,
        public homeLocationName?: string
    ) {}
}
