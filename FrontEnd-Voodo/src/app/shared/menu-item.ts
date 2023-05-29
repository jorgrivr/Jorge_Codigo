import { Injectable } from "@angular/core";

export interface Menu{
    state:string;
    name:string;
    type:string;
    icon:string;
    role:string;

}


const MENUITEMS=[
    {state:'panelControl',name:'Panel Control',type:'link',icon:'dashboard',role:''},
    {state:'pedido',name:'Realizar Pedido',type:'link',icon:'shoppint_cart',role:''},
    { state: 'factura', name: 'Ver Factura', type: 'link', icon: 'backup_table', role: '' },
    {state:'categoria',name:'Gestion Categoria',type:'link',icon:'category',role:'admin'},
    {state:'producto',name:'Gestion Producto',type:'link',icon:'inventory_2',role:'admin'},
    { state: 'user', name: 'Gestion Usuario', type: 'link', icon: 'people', role: 'admin' }
]

@Injectable()
export class MenuItems{
    getMenuitem():Menu[]{
        return MENUITEMS;
    }

}