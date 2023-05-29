import { Component, EventEmitter, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CategoriaService } from 'src/app/services/categoria.service';
import { ProductoService } from 'src/app/services/producto.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-producto',
  templateUrl: './producto.component.html',
  styleUrls: ['./producto.component.scss']
})
export class ProductoComponent implements OnInit {

  onAddProducto = new EventEmitter();

  onEditarProducto = new EventEmitter();

  productoForm: any = FormGroup;

  dialogAction: any = "Añadir";

  action: any = "Añadir";

  responseMessage: any;

  categoria: any = [];

  constructor(
    @Inject(MAT_DIALOG_DATA) public dialogData: any,
    private formBuilder: FormBuilder,
    private productoService: ProductoService,
    public dialogRef: MatDialogRef<ProductoComponent>,
    private categoriaService: CategoriaService,
    private snackbarService: SnackbarService
  ) { }

  ngOnInit(): void {
    this.productoForm = this.formBuilder.group({
      nombre: [null, [Validators.required, Validators.pattern(GlobalConstants.nameRegex)]],
      categoriaId: [null, [Validators.required]],
      precio: [null, [Validators.required]],
      descripcion: [null, [Validators.required]]
    });
    if (this.dialogData.action === "Editar") {
      this.dialogAction = "Editar";
      this.action = "Actualizar";
      this.productoForm.patchValue(this.dialogData.data);
    }
    this.getCategoria();
  }

  getCategoria() {
    this.categoriaService.getCategoria().subscribe((response: any) => {
      this.categoria = response;
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

  handleSubmit() {
    if (this.dialogAction === "Editar") {
      this.editar();
    } else {
      this.add();
    }
  }

  add() {
    var formData = this.productoForm.value;
    var data = {
      nombre: formData.nombre,
      categoriaId: formData.categoriaId,
      precio: formData.precio,
      descripcion: formData.descripcion
    }
    this.productoService.add(data).subscribe((response: any) => {
      this.dialogRef.close();
      this.onAddProducto.emit();
      this.responseMessage = response.message;
      this.snackbarService.openSnackBar(this.responseMessage, "exitoso");
    }, (error) => {
      console.log(error);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }

  editar() {
    var formData = this.productoForm.value;
    var data = {
      id: this.dialogData.data.id,
      nombre: formData.nombre,
      categoriaId: formData.categoryId,
      precio: formData.precio,
      descripcion: formData.descripcion
    }
    this.productoService.update(data).subscribe((response: any) => {
      this.dialogRef.close();
      this.onEditarProducto.emit();
      this.responseMessage = response.message;
      this.snackbarService.openSnackBar(this.responseMessage, "exitoso");
    }, (error) => {
      console.log(error);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }

}
