export class LocalStorageUtil {

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
