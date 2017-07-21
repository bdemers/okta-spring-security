import { TestBed, async } from '@angular/core/testing';

import { AppComponent } from './app.component';
import { FooterComponent } from './layout/footer/footer.component';
import { NavbarComponent } from './layout/navbar/navbar.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './troopers/home/home.component';
import { TrooperListComponent } from './troopers/list/list.component';

import { Okta } from './shared/okta/okta.service';

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        NavbarComponent,
        FooterComponent,
        LoginComponent,
        HomeComponent,
        TrooperListComponent
      ],
      providers: [Okta]
    }).compileComponents();
  }));

  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
