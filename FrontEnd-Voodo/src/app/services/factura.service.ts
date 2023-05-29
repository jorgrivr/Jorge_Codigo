import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FacturaService {
url=environment.apiUrl;
  constructor(private httpClient:HttpClient) { }

  generarReporte(data:any){
    return this.httpClient.post(this.url+
      "/factura/generarReporte",data,{
        headers: new HttpHeaders().set('Content-Type',"application/json")
      })
  }

  getPdf(data:any):Observable<Blob>{
    return this.httpClient.post(this.url+"/factura/getPdf",data,{responseType:'blob'})

  }

  getFactura(){
    return this.httpClient.get(this.url+"/factura/getFactura");
  }

  delete(id:any){
    return this.httpClient.post(this.url+"/factura/delete/"+ id,{
      Headers:new HttpHeaders().set('Content-Type',"application/json")
    })
  }
}


