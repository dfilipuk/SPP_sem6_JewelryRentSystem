export class Material {
    constructor(
        public id: number,
        public name: string,
        public description: string,
        public parentMaterialId: number) { }
}