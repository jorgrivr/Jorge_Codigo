import { Component, EventEmitter, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CategoriaService } from 'src/app/services/categoria.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-categoria',
  templateUrl: './categoria.component.html',
  styleUrls: ['./categoria.component.scss']
})
export class CategoriaComponent implements OnInit {

  onAddCategoria = new EventEmitter();
  onEditarCategoria = new EventEmitter();
  categoriaForm: any = FormGroup;
  dialogAction: any = "Añadir";
  action: any = "Añadir";
  responseMessage: any;

  constructor(
    @Inject(MAT_DIALOG_DATA) public dialogData: any,
    private formBuilder: FormBuilder,
    private categoriaService: CategoriaService,
    public dialogRef: MatDialogRef<CategoriaComponent>,
    private snackbarService: SnackbarService
  ) { }

  ngOnInit(): void {
    this.categoriaForm = this.formBuilder.group({
      nombre: [null, [Validators.required]]
    });
    if (this.dialogData.action === 'Editar') {
      this.dialogAction = "Editar";
      this.action = "Actualizar";
      this.categoriaForm.patchValue(this.dialogData.data);
    }
  }

  handleSubmit() {
    if (this.dialogAction === "Editar") {
      this.editar();
    } else {
      this.add();
    }
  }

  add() {
    var formData = this.categoriaForm.value;
    var data = {
      nombre: formData.nombre
    }
    this.categoriaService.add(data).subscribe((response: any) => {
      this.dialogRef.close();
      this.onAddCategoria.emit();
      this.responseMessage = response.message;
      this.snackbarService.openSnackBar(this.responseMessage, "exitoso");
    }, (error) => {
      this.dialogRef.close();
      console.error(error);
      if(error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    });
  }

  editar() {
    var formData = this.categoriaForm.value;
    var data = {
      id: this.dialogData.data.id,
      nombre: formData.nombre
    }
    this.categoriaService.update(data).subscribe((response: any) => {
      this.dialogRef.close();
      this.onEditarCategoria.emit();
      this.responseMessage = response.message;
      this.snackbarService.openSnackBar(this.responseMessage, "exitoso");
    }, (error) => {
      this.dialogRef.close();
      console.error(error);
      if(error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    });
  }

}
