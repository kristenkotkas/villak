export class Team {
  id: number;
  name: string;
  score: number;
  havePressed: boolean = true;
  timePressed: number = 0;
  quickest: boolean = false;
  winner: boolean = false;

  constructor(id: number, name: string, score: number) {
    this.id = id;
    this.name = name;
    this.score = score;
  }
}
