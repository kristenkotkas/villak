import {Action} from './action';
import {Question} from './question';

export class Category {
  id: number;
  name: string;
  questions: Question[];
  nameState: Action.CATEGORY_SHOW | Action.CATEGORY_HIDE = Action.CATEGORY_HIDE;

  constructor(id: number, name: string, questions: Question[]) {
    this.id = id;
    this.name = name;
    this.questions = questions;
  }
}
