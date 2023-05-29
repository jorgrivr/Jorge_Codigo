import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PanelControlService {

  url = environment.apiUrl;

  constructor(private httpClient : HttpClient) { }

  getDetalles()
  {
    return this.httpClient.get(this.url +"/panelControl/detalles/")
  }
}
