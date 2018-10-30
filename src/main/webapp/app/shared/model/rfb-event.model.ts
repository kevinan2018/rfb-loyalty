import { Moment } from 'moment';
import { IRfbEventAttendance } from 'app/shared/model//rfb-event-attendance.model';

export interface IRfbEvent {
    id?: number;
    eventDate?: Moment;
    eventCode?: string;
    rfbLocationId?: number;
    rfbEventAttendances?: IRfbEventAttendance[];
    rfbLocationName?: string;
}

export class RfbEvent implements IRfbEvent {
    constructor(
        public id?: number,
        public eventDate?: Moment,
        public eventCode?: string,
        public rfbLocationId?: number,
        public rfbEventAttendances?: IRfbEventAttendance[],
        public rfbLocationName?: string
    ) {}
}
