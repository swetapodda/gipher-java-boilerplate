/*
Model Class for : User.
Using this we can create Account/Authenticate
*/

export class User {
  public userId: number;

  public firstName: string;
  public lastName: string;
  public email: string;
  public username: string;
  public password: string;
}

/*
Model Class for : Gipher.
Using this we can create Giphy Info Details
*/
export class Gipher {
  public id: string;
  public giphyId: string;
  public title: string;
  public giphyUrl: string;
  public giphyMp4Url: string;
  public recomendationSystemId: string;
  public bookMarkedBy: number;
  public giphyObject: string;
}

/*
Few of Constants
Note: Currently Client is Accessing Services trhough Zuul API Gateway
*/
export class AppConstants {
  /*
  static GIPHER_RECOMENDATION_SYSTEM_API_URL: any='http://192.168.1.10:9300/api/v1/gipherrecomendersystem';
  static GIPHER_MANAGER_API_URL='http://192.168.1.10:9200/api/v1/giphermanager';
  static ACCOUNT_MANAGER_API_URL = 'http://192.168.1.10:9100/api/v1/accountmanager';
  */

  static ACCOUNT_MANAGER_API_URL = 'http://192.168.1.5:8765/account-manager/api/v1/accountmanager';
  static GIPHER_MANAGER_API_URL = 'http://192.168.1.5:8765/gipher-manager/api/v1/giphermanager';
  static GIPHER_RECOMENDATION_SYSTEM_API_URL: any = 'http://192.168.1.5:8765/gipher-recomender-system/api/v1/gipherrecomendersystem';

}
