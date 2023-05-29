import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { saveAs } from 'file-saver';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { FacturaService } from 'src/app/services/factura.service';
import { CategoriaService } from 'src/app/services/categoria.service';
import { ProductoService } from 'src/app/services/producto.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-gestion-pedido',
  templateUrl: './gestion-pedido.component.html',
  styleUrls: ['./gestion-pedido.component.scss']
})
export class GestionPedidoComponent implements OnInit {

  displayedColumns: string[] = ['nombre', 'categoria', 'precio', 'cantidad', 'total', 'editar'];

  dataSource: any = [];

  gestionPedidoForm: any = FormGroup;

  categoria: any = [];

  producto: any = [];

  precio: any;

  total: number = 0;

  responseMessage: any;

  constructor(
    private formBuilder: FormBuilder,
    private categoriaService: CategoriaService,
    private productoService: ProductoService,
    private facturaService: FacturaService,
    private snackbarService: SnackbarService,
    private ngxService: NgxUiLoaderService
  ) { }

  ngOnInit(): void {
    this.ngxService.start();
    this.getCategoria();
    this.gestionPedidoForm = this.formBuilder.group({
      nombre: [null, [Validators.required, Validators.pattern(GlobalConstants.nameRegex)]],
      email: [null, [Validators.required, Validators.pattern(GlobalConstants.emailRegex)]],
      numero_contacto: [null, [Validators.required, Validators.pattern(GlobalConstants.contactNumberRegex)]],
      metodoPago: [null, [Validators.required]],
      producto: [null, [Validators.required]],
      categoria: [null, [Validators.required]],
      cantidad: [null, [Validators.required]],
      precio: [null, [Validators.required]],
      total: [0, [Validators.required]],
    })
  }

  getCategoria() {
    this.categoriaService.getFilteredCategoria().subscribe((response: any) => {
      this.ngxService.stop();
      this.categoria = response;
    }, (error: any) => {
      this.ngxService.stop();
      console.log(error);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }

  getProductoByCategoria(value: any) {
    this.productoService.getProductoByCategoria(value.id).subscribe((response: any) => {
      this.producto = response;
      this.gestionPedidoForm.controls['precio'].setValue('');
      this.gestionPedidoForm.controls['cantidad'].setValue('');
      this.gestionPedidoForm.controls['total'].setValue(0);
    }, (error: any) => {
      console.log(error);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }

  getDetallesProducto(value: any) {
    this.productoService.getById(value.id).subscribe((response: any) => {
      this.precio = response.precio;
      this.gestionPedidoForm.controls['precio'].setValue(response.precio);
      this.gestionPedidoForm.controls['cantidad'].setValue('1');
      this.gestionPedidoForm.controls['total'].setValue(this.precio * 1);
    }, (error: any) => {
      console.log(error);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }

  setCantidad(value: any) {
    var temp = this.gestionPedidoForm.controls['cantidad'].value;
    if (temp > 0) {
      this.gestionPedidoForm.controls['total'].setValue(this.gestionPedidoForm.controls['cantidad'].value * this.gestionPedidoForm.controls['precio'].value);
    } else if(temp != '') {
      this.gestionPedidoForm.controls['cantidad'].setValue('1');
      this.gestionPedidoForm.controls['total'].setValue(this.gestionPedidoForm.controls['cantidad'].value * this.gestionPedidoForm.controls['precio'].value);
    }
  }

  validateProductoAdd() {
    if(this.gestionPedidoForm.controls['total'].value === 0 || this.gestionPedidoForm.controls['total'].value === null || this.gestionPedidoForm.controls['cantidad'].value <= 0) {
      return true;
    } else return false;
  }

  validateSubmit() {
    if(this.total === 0 || this.gestionPedidoForm.controls['nombre'].value === null || this.gestionPedidoForm.controls['email'].value === null || 
    this.gestionPedidoForm.controls['numero_contacto'].value === null || this.gestionPedidoForm.controls['metodoPago'].value === null){
      return true;
    } else return false;
  }

  add() {
    var formData = this.gestionPedidoForm.value;
    var productoNombre = this.dataSource.find((e: { id: number }) => e.id === formData.producto.id);
    if(productoNombre === undefined) {
      this.total = this.total + formData.total;
      this.dataSource.push({
        id:formData.producto.id,
        nombre: formData.producto.nombre,
        categoria: formData.categoria.nombre,
        cantidad: formData.cantidad,
        precio: formData.precio,
        total: formData.total
      });
      this.dataSource = [...this.dataSource];
      this.snackbarService.openSnackBar(GlobalConstants.productAdded, "exitoso");
    } else {
      this.snackbarService.openSnackBar(GlobalConstants.productExistError, GlobalConstants.error);
    }
  }

  handleDeleteAccion(value: any, element: any) {
    this.total = this.total - element.total;
    this.dataSource.splice(value, 1);
    this.dataSource = [...this.dataSource];
  }

  submitAction() {
    var formData = this.gestionPedidoForm.value;
    var data = {
      nombre: formData.nombre,
      email: formData.email,
      numero_contacto: formData.numero_contacto,
      metodoPago: formData.metodoPago,
      total: this.total.toString(),
      detallesProducto: JSON.stringify(this.dataSource)
    }
    this.ngxService.start();
    this.facturaService.generarReporte(data).subscribe((response: any) => {
      this.descargarFile(response?.uuid);
      this.gestionPedidoForm.reset();
      this.dataSource = [];
      this.total = 0;
    }, (error: any) => {
      console.log(error);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }

  descargarFile(fileName: string) {
    var data = {
      uuid: fileName
    }

    this.facturaService.getPdf(data).subscribe((response: any) => {
      saveAs(response, fileName + '.pdf');
      this.ngxService.stop();
    })
  }

}
