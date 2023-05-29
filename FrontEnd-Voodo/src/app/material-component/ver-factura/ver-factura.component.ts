import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { saveAs } from 'file-saver';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import {  FacturaService } from 'src/app/services/factura.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { ConfirmationComponent } from '../dialog/confirmation/confirmation.component';
import { VerPedidoProductoComponent } from '../dialog/ver-pedido-producto/ver-pedido-producto.component';

@Component({
  selector: 'app-ver-factura',
  templateUrl: './ver-factura.component.html',
  styleUrls: ['./ver-factura.component.scss']
})
export class VerFacturaComponent implements OnInit {

  displayedColumns: string[] = ['nombre', 'email', 'numero_contacto', 'metodoPago', 'total', 'vista'];
  dataSource: any;
  responseMessage: any;

  constructor(
    private facturaService: FacturaService,
    private ngxService: NgxUiLoaderService,
    private dialog: MatDialog,
    private snackbarService: SnackbarService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.ngxService.start();
    this.tableData();
  }

  tableData() {
    this.facturaService.getFactura().subscribe((response: any) => {
      this.ngxService.stop();
      this.dataSource = new MatTableDataSource(response);
    }, (error: any) => {
      this.ngxService.stop();
      console.log(error);
      if(error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }

  applyFiltro(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  handleVistaAccion(values: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      data: values
    }
    dialogConfig.width= "100%";
    dialogConfig.height="70%";
    
    const dialogRef = this.dialog.open(VerPedidoProductoComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();
    })
   
  }

  handleDeleteAccion(values: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message: 'borrar ' + values.nombre + ' factura',
      confirmation: true
    };
    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);
    const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((response) => {
      this.ngxService.start();
      this.deleteFactura(values.id);
      dialogRef.close();
    })
  }

  deleteFactura(id: any) {
    this.facturaService.delete(id).subscribe((response: any) => {
      this.ngxService.stop();
      this.tableData();
      this.responseMessage = response?.message;
      this.snackbarService.openSnackBar(this.responseMessage, "exitoso");
    }, (error: any) => {
      this.ngxService.stop();
      console.log(error);
      if(error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }

  descargarReporteAccion(values: any) {
    this.ngxService.start();
    var data = {
      nombre: values.nombre,
      email: values.email,
      uuid: values.uuid,
      numero_contacto: values.numero_contacto,
      metodoPago: values.metodoPago,
      total: values.total.toString(),
      detallesProducto: values.detallesProducto
    }
    this.descargarArchivo(values.uuid, data);
  }

  descargarArchivo(fileName: string, data: any) {
    this.facturaService.getPdf(data).subscribe((response) => {
      saveAs(response, fileName + '.pdf');
      this.ngxService.stop();
    })
  }

}
