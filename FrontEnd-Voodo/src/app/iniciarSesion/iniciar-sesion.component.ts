import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { MatDialogRef } from '@angular/material/dialog';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SnackbarService } from '../services/snackbar.service';
import { GlobalConstants } from '../shared/global-constants';

@Component({
  selector: 'app-iniciar-sesion',
  templateUrl: './iniciar-sesion.component.html',
  styleUrls: ['./iniciar-sesion.component.scss']
})
export class IniciarSesionComponent implements OnInit {
  hide=true;
  iniciarSesionForm:any =FormGroup;
  responseMessage:any;

  constructor(private formBuilder:FormBuilder,
    private router:Router,
    private userService:UserService,
    public dialogRef:MatDialogRef<IniciarSesionComponent>,
    private ngxService:NgxUiLoaderService,
    private snackbarService:SnackbarService){}

  ngOnInit(): void {
    this.iniciarSesionForm=this.formBuilder.group({
      email:[null,[Validators.required,Validators.pattern(GlobalConstants.emailRegex)]],
      password:[null,[Validators.required]],

    })
   
  }

  
handleSubmit(){
  this.ngxService.start();
  var formData=this.iniciarSesionForm.value;
  var data={
    email:formData.email,
    password:formData.password
  }

  this.userService.iniciarSesion(data).subscribe((response:any) =>{
    this.ngxService.stop();
    this.dialogRef.close();
    localStorage.setItem('token',response.token);
    this.router.navigate(['/voodo/panel-control']);

  },(error)=>{
    this.ngxService.stop();
    if(error.error?.message){
      this.responseMessage=error.error?.message;
    }else{
      this.responseMessage=GlobalConstants.genericError;
    }
    this.snackbarService.openSnackBar(this.responseMessage,GlobalConstants.error);
  })
}

}
