import { Component, OnInit } from '@angular/core';
import { Okta } from './shared/okta/okta.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  user;
  oktaSignIn;

  constructor(private okta: Okta) {
    this.oktaSignIn = okta.getWidget();
  }

  showLogin() {
    this.oktaSignIn.renderEl({el: '#okta-login-container'},
      (response) => {

        // i.e. authParams.responseType = 'id_token':
        console.log("response:");
        console.log(response);

        console.log("response.claims:");
        console.log(response.claims);


        // The user has successfully completed the authentication flow
        if (response.status === 'SUCCESS') {

          // Handle success when the widget is not configured for OIDC

            if (response.type === 'SESSION_STEP_UP') {
              // Session step up response
              // If the widget is not configured for OIDC and the authentication type is SESSION_STEP_UP,
              // the response will contain user metadata and a stepUp object with the url of the resource
              // and a 'finish' function to navigate to that url
              console.log(response.user);
              console.log('Target resource url: ' + response.stepUp.url);
              response.stepUp.finish();
              return;
            } else {
              console.log(response.type)
            }


          // this.oktaSignIn.tokenManager.add('my_id_token', response);
          this.user = response[0].claims.email;
          // console.log("[0]"+ response[0]);
          // console.log("[1]"+ response[1]);
          //
          // If the widget is configured for OIDC with multiple responseTypes, the
          // response will be an array of tokens:
          // i.e. authParams.responseType = ['id_token', 'token']
          this.oktaSignIn.tokenManager.add('my_id_token', response[0]);
          this.oktaSignIn.tokenManager.add('my_access_token', response[1]);

        }



      },
      (error) => {
        console.error(error);
      });
  }

  ngOnInit() {
    this.oktaSignIn.session.get((response) => {
      if (response.status !== 'INACTIVE') {
        this.user = response.login
      } else {
        this.showLogin();
      }
    });
  }

  logout() {
    this.oktaSignIn.signOut(() => {
      location.reload();
    });
  }
}
