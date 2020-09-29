export class Team {
  id: number;
  name: string;
  score: number;
  havePressed: boolean;
  timePressed: number;
  timeSynced: number;
  deviceId: number;
  quickest: boolean;
  winner: boolean;

  constructor(id: number, name: string, score: number) {
    this.id = id;
    this.name = name;
    this.score = score;
  }
}
