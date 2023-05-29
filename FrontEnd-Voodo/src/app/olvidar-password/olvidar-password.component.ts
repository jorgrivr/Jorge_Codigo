import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../services/user.service';
import { MatDialogRef } from '@angular/material/dialog';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SnackbarService } from '../services/snackbar.service';
import { GlobalConstants } from '../shared/global-constants';

@Component({
  selector: 'app-olvidar-password',
  templateUrl: './olvidar-password.component.html',
  styleUrls: ['./olvidar-password.component.scss']
})
export class OlvidarPasswordComponent implements OnInit {
  olvidarPasswordForm: any =FormGroup;
  responseMessage:any;

  constructor(private formBuilder: FormBuilder,
  private userService:UserService,
  public dialogRef:MatDialogRef<OlvidarPasswordComponent>,
  private ngxService:NgxUiLoaderService,
  private snackbarService:SnackbarService){ }


  ngOnInit(): void {
    this.olvidarPasswordForm = this.formBuilder.group({
      email:[null,[Validators.required,Validators.pattern(GlobalConstants.emailRegex)]]

    })
  }


  handleSubmit(){
    this.ngxService.start();
    var formData=this.olvidarPasswordForm.value;
    var data={
      email:formData.email,
    }
  
    this.userService.olvidarPassword(data).subscribe((response:any) =>{
      this.ngxService.stop();
      this.dialogRef.close();
      this.responseMessage=response?.message;
      this.snackbarService.openSnackBar(this.responseMessage,"");
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
