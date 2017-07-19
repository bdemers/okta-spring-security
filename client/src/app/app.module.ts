import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { Okta } from './shared/okta/okta.service';
import { TrooperService } from './shared/trooper/trooper.service';

import { FooterComponent } from './layout/footer/footer.component';
import { NavbarComponent } from './layout/navbar/navbar.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './troopers/home/home.component';
import { TrooperListComponent } from './troopers/list/list.component';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    NavbarComponent,
    LoginComponent,
    HomeComponent,
    TrooperListComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule
  ],
  providers: [Okta, TrooperService],
  bootstrap: [AppComponent]
})
export class AppModule { }
