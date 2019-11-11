export class Util {
  static getPlayer(player: string): HTMLElement {
    return <HTMLElement> document.getElementById('player_' + player);
  }

  public static getApiUrl(): string {
    const url = window.location.origin;
    const localUrl = 'http://localhost:8084';
    return url.indexOf('localhost') !== -1 ? localUrl : url;
  }

}
