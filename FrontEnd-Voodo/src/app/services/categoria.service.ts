import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {
  url=environment.apiUrl;

  constructor(private httpClient:HttpClient) { }

  add(data:any){
    return this.httpClient.post(this.url + "/categoria/add",data,{
      headers: new HttpHeaders().set('Content-Type',"application/json")
    })

  }

  update(data:any){
    return this.httpClient.post(this.url + "/categoria/update",data,{
      headers: new HttpHeaders().set('Content-Type',"application/json")
    })

  }

  getCategoria(){
    return this.httpClient.get(this.url+ "/categoria/get");
  }

  getProductoByCategoria(id:any){
    return this.httpClient.get(this.url+"/producto/getByCategoria" + id);
  }

  getById(id:any){
    return this.httpClient.get(this.url+"/producto/getById" +id);

  }

  getFilteredCategoria(){
    return this.httpClient.get(this.url+"/categoria/get?filterValue=true");
  }
}
