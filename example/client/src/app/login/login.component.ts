import { Component } from '@angular/core';

import { AppComponent } from '../app.component';

@Component({
  selector: 'okta-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {
  appComponent;

  constructor(private app: AppComponent) {
    this.appComponent = app;
  }
}
