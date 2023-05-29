import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  url = environment.apiUrl;

  constructor(private httpClient: HttpClient) {}

  registrar(data: any) {
    const user = localStorage.getItem('token');
    console.log(user);


    return this.httpClient.post(this.url + '/user/registrar', data, {
      headers: new HttpHeaders()
        .set('Content-type', 'application/json')
        .set('Authorization', 'Bearer ' + user),
    });
  }

  olvidarPassword(data: any) {
    return this.httpClient.post(this.url + '/user/olvidarPassword', data, {
      headers: new HttpHeaders().set('Content-type', 'application/json'),
    });
  }

  iniciarSesion(data: any) {
    return this.httpClient.post(this.url + '/user/iniciarSesion', data, {
      headers: new HttpHeaders().set('Content-type', 'application/json'),
    });
  }

  checkToken() {
    const user = window.sessionStorage.getItem('token');
    console.log(user);
    if (user) {
      const userToken = JSON.parse(user);
      return this.httpClient.get(this.url + '/user/checkToken', {
        headers: new HttpHeaders()
          .set('Content-type', 'application/json')
          .set('Authorization', 'Bearer ' + userToken),
      });
    }
    return this.httpClient.get(this.url + '/user/checkToken', {
      headers: new HttpHeaders()
        .set('Content-type', 'application/json')
        .set('Authorization', 'Bearer ' + user),
    });
  }

  cambiarPassword(data: any) {
    return this.httpClient.post(this.url + '/user/cambiarPassword', data, {
      headers: new HttpHeaders().set('Content-type', 'application/json'),
    });
  }

  getUsers(){
    return this.httpClient.get(this.url+"/user/get")
  }

  update(data:any){
    return this.httpClient.post(this.url +"/user/update",data,{
      headers:new HttpHeaders().set('Content-type', 'application/json')
    })
  }
}
