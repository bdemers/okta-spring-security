import { Injectable } from '@angular/core';
declare let OktaSignIn: any;

@Injectable()
export class Okta {
  widget;

  constructor() {
    this.widget = new OktaSignIn({
      baseUrl: 'https://dev-259824.oktapreview.com',
      clientId: '0oab95unvqHUouItw0h7',
      redirectUri: 'http://localhost:4200',
      authParams: {
        issuer: 'https://dev-259824.oktapreview.com/oauth2/ausar5cbq5TRRsbcJ0h7',
        responseType: ['id_token', 'token']
      }
    });
  }

  getWidget() {
    return this.widget;
  }
}
