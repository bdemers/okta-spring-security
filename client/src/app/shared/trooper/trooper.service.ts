import { Injectable } from '@angular/core';
import { Http, RequestOptions, Response, Headers } from '@angular/http';
import 'rxjs/add/operator/map';
import { Observable } from 'rxjs';
import { Okta } from '../okta/okta.service';

@Injectable()
export class TrooperService {

  constructor(private http: Http, private okta: Okta) {
  }

  getAll(): Observable<any> {
    const headers: Headers = new Headers();
    headers.append('Authorization', 'Bearer ' + this.okta.getWidget().tokenManager.get('my_access_token').accessToken);

    const options = new RequestOptions({ headers: headers });

    return this.http.get('http://localhost:8080/troopers', options)
      .map((response: Response) => response.json());
  }
}
