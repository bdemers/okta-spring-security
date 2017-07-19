import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../../app.component';

@Component({
  selector: 'home',
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {
  appComponent;

  constructor(private app: AppComponent) {
    this.appComponent = app;
  }

  ngOnInit() {
    console.log("init this stuff")
  }

}
