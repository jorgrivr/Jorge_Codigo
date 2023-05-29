import { Component } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CambiarPasswordComponent } from 'src/app/material-component/dialog/cambiar-password/cambiar-password.component';
import { ConfirmationComponent } from 'src/app/material-component/dialog/confirmation/confirmation.component';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: []
})
export class AppHeaderComponent {
  role:any;
  constructor(private router:Router,
    private dialog:MatDialog){
    
  }

  cerrarSesion(){
    const dialogConfig=new MatDialogConfig();
    dialogConfig.data={
      message:'Cerrar Sesion',
      confirmation:true
    };
    const dialogRef =this.dialog.open(ConfirmationComponent,dialogConfig);
    const sub=dialogRef.componentInstance.onEmitStatusChange.subscribe((response)=>{
      dialogRef.close();
      localStorage.clear();
      this.router.navigate(['/']);
    })
  }

  cambiarPassword(){
    const dialogConfig=new MatDialogConfig();
    this.dialog.open(CambiarPasswordComponent,dialogConfig);
  }
}
