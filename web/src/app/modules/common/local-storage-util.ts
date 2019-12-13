export class LocalStorageUtil {

  public static KEY_DEVICE_ID: string = '671b750dad5f30d6eaf736b4cb910d35';

  static write(key: string, value: string): void {
    localStorage.setItem(key, value);
  }

  static read(key: string): string {
    return localStorage.getItem(key);
  }

  static remove(key: string): void {
    localStorage.removeItem(key);
  }

}
