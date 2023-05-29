import { Routes } from '@angular/router';
import { PanelControlComponent } from '../panelControl/panel-control.component';
import { GestionCategoriaComponent } from './gestion-categoria/gestion-categoria.component';
import { RouteGuardService } from '../services/route-guard.service';
import { GestionProductoComponent } from './gestion-producto/gestion-producto.component';
import { GestionPedidoComponent } from './gestion-pedido/gestion-pedido.component';
import { VerFacturaComponent } from './ver-factura/ver-factura.component';
import { GestionUserComponent } from './gestion-user/gestion-user.component';


export const MaterialRoutes: Routes = [
    {
        path:'categoria',
        component:GestionCategoriaComponent,
        canActivate:[RouteGuardService],
        data:{
            expectedRole:['admin']
        }
    },

    {
        path:'producto',
        component:GestionProductoComponent,
        canActivate:[RouteGuardService],
        data:{
            expectedRole:['admin']
        }
    },
    {
        path:'pedido',
        component:GestionPedidoComponent,
        canActivate:[RouteGuardService],
        data:{
            expectedRole:['admin','user']
        }
    },
    {
        path: 'factura', component: VerFacturaComponent,
        canActivate: [RouteGuardService], 
        data: { expectedRole: ['admin', 'user'] }
    },
    {
        path: 'user', component: GestionUserComponent,
        canActivate: [RouteGuardService], 
        data: { expectedRole: ['admin'] }
    }
];
