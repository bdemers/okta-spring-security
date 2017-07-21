import { Component, OnInit } from '@angular/core';
import { TrooperService } from '../../shared/trooper/trooper.service';

@Component({
  selector: 'trooper-list',
  templateUrl: './list.component.html'
})
export class TrooperListComponent implements OnInit {

  troopers: Array<any>;
  trooperService;

  constructor(private trooperServ: TrooperService) {
    this.trooperService = trooperServ;
  }

  ngOnInit() {
    this.trooperService.getAll().subscribe(
      data => {
        this.troopers = data;
      },
      error => console.log(error)
    );
  }

}
