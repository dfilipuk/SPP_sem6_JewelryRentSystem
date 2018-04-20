export class Jewelry {
    constructor(
        public id: number,
        public name: string,
        public producer: string,
        public description: string,
        public pictureUrl: string,
        public type: string,
        public weight: number,
        public status: string,
        public costPerDay: number,
        public daysRental: number,
        public branchId: number,
        public materialsIds: number[]
    ) { }
}