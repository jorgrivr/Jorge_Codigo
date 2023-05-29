import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { UserService } from 'src/app/services/user.service';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-cambiar-password',
  templateUrl: './cambiar-password.component.html',
  styleUrls: ['./cambiar-password.component.scss']
})
export class CambiarPasswordComponent implements OnInit {

  viejaPassword=true;
  nuevaPassword=true;
  confirmarPassword=true;
  cambiarPasswordForm:any= FormGroup;
  responsemessage:any;
  constructor(private formBuilder:FormBuilder,
    private userService:UserService,
    public dialogRef:MatDialogRef<CambiarPasswordComponent>,
    private ngxService:NgxUiLoaderService,
    private snackbarService:SnackbarService){}


  ngOnInit(): void {
    this.cambiarPasswordForm=this.formBuilder.group({
      viejaPassword:[null,Validators.required],
      nuevaPassword:[null,Validators.required],
      confirmarPassword:[null,Validators.required],
    })
  }

  validateSubmit(){
    if(this.cambiarPasswordForm.controls['nuevaPassword'].value != this.cambiarPasswordForm.controls[''].value){
      return true;
    }else{
      return false;
    }
  }

  handlePasswordChangeSubmit(){
    this.ngxService.start();
    var formData=this.cambiarPasswordForm.value;
    var data={
      viejaPassword: formData.viejaPassword,
      nuevaPassword: formData.nuevaPassword,
      confirmarPassword: formData.confirmarPassword
    }

    this.userService.cambiarPassword(data).subscribe((response:any)=>{
      this.ngxService.stop();
      this.responsemessage=response?.message;
      this.dialogRef.close();
      this.snackbarService.openSnackBar(this.responsemessage,"exitoso");
    },(error)=>{
      console.log(error);
      this.ngxService.stop();
      if(error.error?.message){
        this.responsemessage=error.error?.message;
      }else{
        this.responsemessage=GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responsemessage,GlobalConstants.error);
    })
  }

}
