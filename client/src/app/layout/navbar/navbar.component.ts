import { Component } from '@angular/core';

import { AppComponent } from '../../app.component';
import { Okta } from '../../shared/okta/okta.service';

@Component({
  selector: 'layout-navbar',
  templateUrl: './navbar.component.html'
})
export class NavbarComponent {
  title = 'Trooper Portal';
  appComponent;

  constructor(private app: AppComponent) {
    this.appComponent = app;
  }

  showLogin() {
    this.appComponent.ngOnInit();
  }

}
