export class Util {
  static getPlayer(player: string): HTMLElement {
    return <HTMLElement> document.getElementById('player_' + player);
  }

  public static getApiUrl(): string {
    const url = window.location.origin;
    console.log(url);
    const localUrl = `http://localhost:8085`;
    return url.indexOf('localhost:4200') !== -1 ? localUrl : url;
  }

}
