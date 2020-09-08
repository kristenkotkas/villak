export class Util {
  static getPlayer(player: string): HTMLElement {
    return <HTMLElement>document.getElementById('player_' + player);
  }

  public static getApiUrl(): string {
    const url = window.location.origin;
    const localUrl = `http://localhost:8085`;
    const privateUrl = `http://192.168.1.126:8085`;
    return url.indexOf('localhost:4200') !== -1 ? localUrl :
      url.indexOf('192.168.1') !== -1 ? privateUrl :
        url;
  }

  public static getDeviceId(): number {
    return Math.floor(Math.random() * 99999) + 1;
  }

}
