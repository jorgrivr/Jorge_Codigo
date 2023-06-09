import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { CategoriaService } from 'src/app/services/categoria.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { CategoriaComponent } from '../dialog/categoria/categoria.component';

@Component({
  selector: 'app-gestion-categoria',
  templateUrl: './gestion-categoria.component.html',
  styleUrls: ['./gestion-categoria.component.scss']
})
export class GestionCategoriaComponent implements OnInit {

  displayedColumns: string[] = ['nombre', 'editar'];
  dataSource: any;
  responseMessage: any;

  constructor(private categoriaService: CategoriaService,
    private ngxService: NgxUiLoaderService,
    private dialog: MatDialog,
    private snackbarService: SnackbarService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.ngxService.start();
    this.tableData();
  }

  tableData() {
    this.categoriaService.getCategoria().subscribe((response:any) => {
      this.ngxService.stop();
      this.dataSource = new MatTableDataSource(response);
    },(error:any) => {
      this.ngxService.stop();
      console.log(error.error?.message);
      if(error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }

  applyFiltro(event:Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  handleAddAccion() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      action: 'Añadir'
    }

    const dialogRef = this.dialog.open(CategoriaComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });
    const sub = dialogRef.componentInstance.onAddCategoria.subscribe((response) => {
      this.tableData();
    })
  }

  handleEditarAccion(values:any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      action: 'Editar',
      data: values
    }

    const dialogRef = this.dialog.open(CategoriaComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });
    const sub = dialogRef.componentInstance.onAddCategoria.subscribe((response) => {
      this.tableData();
    })
  }

}
