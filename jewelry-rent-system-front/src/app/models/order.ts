export class Order {
    constructor(
        public id: number,
        public status: string,
        public rentDate: string,
        public daysRent: number,
        public cost: number,
        public clientId: number,
        public employeeId: number,
        public jewelryId: number
    ) { }
}