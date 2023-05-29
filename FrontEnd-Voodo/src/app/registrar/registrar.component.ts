import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Route, Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { SnackbarService } from '../services/snackbar.service';
import { MatDialogRef } from '@angular/material/dialog';

import { GlobalConstants } from '../shared/global-constants';
import { NgxUiLoaderService } from 'ngx-ui-loader';

@Component({
  selector: 'app-registrar',
  templateUrl: './registrar.component.html',
  styleUrls: ['./registrar.component.scss']
})
export class RegistrarComponent implements OnInit {
  password=true;
  confirmarPassword=true;
  registrarForm:any =FormGroup;
  responsemessage:any;
  constructor(private formBuilder: FormBuilder,
    private router:Router,
    private userService: UserService,
    private snackbarService:SnackbarService,
    public dialogRef:MatDialogRef<RegistrarComponent>,
    private ngxService:NgxUiLoaderService){}


  ngOnInit(): void {
    this.registrarForm=this.formBuilder.group({
      nombre:[null,[Validators.required,Validators.pattern(GlobalConstants.nameRegex)]],
      email:[null,[Validators.required,Validators.pattern(GlobalConstants.emailRegex)]],
      numero_contacto:[null,[Validators.required,Validators.pattern(GlobalConstants.contactNumberRegex)]],
      password:[null,[Validators.required]],
      confirmarPassword:[null,[Validators.required]]
    })
    
  }

  validateSubmit(){
    if(this.registrarForm.controls['password'].value != this.registrarForm.controls['confirmarPassword'].value){
      return true;
    }
  else{
    return false;

  }
}
handleSubmit(){
    this.ngxService.start();
    var formData = this.registrarForm.value;
    var data = {
      nombre : formData.nombre,
      email : formData.email,
      numero_contacto : formData.numero_contacto,
      password : formData.password
    }

    this.userService.registrar(data).subscribe((response:any)=>{
      this.ngxService.stop();
      this.dialogRef.close();
      this.responsemessage = response?.message;
      this.snackbarService.openSnackBar(this.responsemessage,"");
      this.router.navigate(['/'])
    },(error)=>{
      this.ngxService.stop();
      if(error.error?.message){
        this.responsemessage = error.error?.message;
      }else
      {
        this.responsemessage = GlobalConstants.genericError
      }
      this.snackbarService.openSnackBar(this.responsemessage,GlobalConstants.error)
    }
    )
  }
}