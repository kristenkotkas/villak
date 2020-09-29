import {Category} from './category';

export class Round {
  id: number;
  categories: Category[];
  active: boolean = false;

  constructor(id: number, categories: Category[]) {
    this.id = id;
    this.categories = categories;
    if (id === 0) {
      this.active = true;
    }
  }
}
