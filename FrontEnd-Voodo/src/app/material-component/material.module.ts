import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { CdkTableModule } from '@angular/cdk/table';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';

import { MaterialRoutes } from './material.routing';
import { MaterialModule } from '../shared/material-module';
import { VerPedidoProductoComponent } from './dialog/ver-pedido-producto/ver-pedido-producto.component';
import { ConfirmationComponent } from './dialog/confirmation/confirmation.component';
import { CambiarPasswordComponent } from './dialog/cambiar-password/cambiar-password.component';
import { GestionCategoriaComponent } from './gestion-categoria/gestion-categoria.component';

import { CategoriaComponent } from './dialog/categoria/categoria.component';
import { GestionProductoComponent } from './gestion-producto/gestion-producto.component';
import { ProductoComponent } from './dialog/producto/producto.component';

import { VerFacturaComponent } from './ver-factura/ver-factura.component';
import { GestionUserComponent } from './gestion-user/gestion-user.component';
import { GestionPedidoComponent } from './gestion-pedido/gestion-pedido.component';



@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(MaterialRoutes),
    MaterialModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    CdkTableModule,
   
  ],
  providers: [],
  declarations: [
    VerPedidoProductoComponent,
    ConfirmationComponent,
    CambiarPasswordComponent,
    GestionCategoriaComponent,
    CategoriaComponent,
    GestionProductoComponent,
    ProductoComponent,
    VerFacturaComponent,
    GestionUserComponent,
    GestionPedidoComponent
  ]
})
export class MaterialComponentsModule {}
