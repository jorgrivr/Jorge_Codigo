import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { ProductoService } from 'src/app/services/producto.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { ProductoComponent } from '../dialog/producto/producto.component';
import { ConfirmationComponent } from '../dialog/confirmation/confirmation.component';

@Component({
  selector: 'app-gestion-producto',
  templateUrl: './gestion-producto.component.html',
  styleUrls: ['./gestion-producto.component.scss']
})
export class GestionProductoComponent implements OnInit {

displayedColumns:string[]=['nombre','nombreCategoria','descripcion','precio','editar'];
dataSource:any;
length1:any;
responseMessage:any;

  constructor(private productoService:ProductoService,
    private ngxService:NgxUiLoaderService,
    private dialog:MatDialog,
    private snackbarService:SnackbarService,
    private router:Router){}


  ngOnInit(): void {
    this.ngxService.start();
    this.tableData();

  }

  tableData(){
    this.productoService.getProducto().subscribe((response:any)=>{
      this.ngxService.stop();
      this.dataSource=new MatTableDataSource(response);
    },(error:any)=>{
      this.ngxService.stop();
      console.log(error.error?.message);
      if(error.error?.message){
        this.responseMessage=error.error?.message;
      }else{
        this.responseMessage=GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage,GlobalConstants.error);
    })
  }

  applyFiltro(event:Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  handleAddAccion(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      action: 'Añadir'
  }
  const dialogRef=this.dialog.open(ProductoComponent,dialogConfig);
  this.router.events.subscribe(()=>{
    dialogRef.close();
  });
  const sub=dialogRef.componentInstance.onAddProducto.subscribe((respone)=>{
    this.tableData();
  })
}
  handleEditarAccion(value:any){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      action: 'Editar',
      data:value
  }
  const dialogRef=this.dialog.open(ProductoComponent,dialogConfig);
  this.router.events.subscribe(()=>{
    dialogRef.close();
  });
  const sub=dialogRef.componentInstance.onEditarProducto.subscribe((respone)=>{
    this.tableData();
  })
    
  }
  handleDeleteAccion(value:any){
    const dialogConfig=new MatDialogConfig();
    dialogConfig.data={
      message:'Borrar ' + value.name+ ' producto',
      confirmation:true
    }
    const dialogRef= this.dialog.open(ConfirmationComponent,dialogConfig);
    const sub=dialogRef.componentInstance.onEmitStatusChange.subscribe((respone)=>{
      this.ngxService.start();
      this.deleteProduct(value.id);
      dialogRef.close();
    })
  }

  deleteProduct(id:any){
    this.productoService.delete(id).subscribe((response:any)=>{
      this.ngxService.start();
      this.tableData();
      this.responseMessage=response?.message;
      this.snackbarService.openSnackBar(this.responseMessage,"exitoso");
    },(error:any)=>{
      this.ngxService.stop();
      console.log(error);
      if(error.error?.message){
        this.responseMessage=error.error?.message;
      }else{
        this.responseMessage=GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage,GlobalConstants.error);
    })
  }
  onChange(status:any,id:any){
    this.ngxService.start();
    var data={
      status: status.toString(),
      id:id
    }

    this.productoService.updateEstado(data).subscribe((response:any)=>{
      this.ngxService.stop();
      this.responseMessage=response?.message;
      this.snackbarService.openSnackBar(this.responseMessage,"exitoso");
    },(error:any)=>{
      this.ngxService.stop();
      console.log(error);
      if(error.error?.message){
        this.responseMessage=error.error?.message;
      }else{
        this.responseMessage=GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage,GlobalConstants.error);
    })

  }

}
