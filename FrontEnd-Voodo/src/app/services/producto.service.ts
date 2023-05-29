import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {
url=environment.apiUrl;

  constructor(private httpClient:HttpClient) { }


  add(data:any){
    return this.httpClient.post(this.url +"/producto/add",data,{
      headers: new HttpHeaders().set('Content-Type',"application/json")
    })
  }
  update(data:any) {
    return this.httpClient.post(this.url + "/producto/update", data, {
      headers: new HttpHeaders().set('Content-Type', "application/json")
    })
  }

  getProducto(){
    return this.httpClient.get(this.url + "/producto/get");
  }

  updateEstado(data:any){
    return this.httpClient.post(this.url +"/producto/updateEstado",data,{
      headers: new HttpHeaders().set('Content-Type',"application/json")
    })

  }

  delete(id:any){
    return this.httpClient.post(this.url +"/producto/delete/" +id,{
      headers: new HttpHeaders().set('Content-Type',"application/json")
    })
  }
  getProductoByCategoria(id: any) {
    return this.httpClient.get(this.url + "/producto/getByCategoria/" + id);
  }

  getById(id: any) {
    return this.httpClient.get(this.url + "/producto/getById/" + id);
  }

}




