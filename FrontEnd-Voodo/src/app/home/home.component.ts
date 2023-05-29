import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { RegistrarComponent } from '../registrar/registrar.component';
import { OlvidarPasswordComponent } from '../olvidar-password/olvidar-password.component';
import { IniciarSesionComponent } from '../iniciarSesion/iniciar-sesion.component';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private dialog:MatDialog,
    private userService:UserService,
    private router:Router){}
      
  ngOnInit(): void {
    this.userService.checkToken().subscribe((response:any)=>{
      this.router.navigate(['voodo/panel-control']);
    },(error:any)=>{
      console.log(error);
    })

  }
  handleRegistrarAction(){
    this.dialog.open(RegistrarComponent)
  
  }

  handleOlvidarPasswordAction(){
    this.dialog.open(OlvidarPasswordComponent)
  
  }

  handleIniciarSesionAction(){
    this.dialog.open(IniciarSesionComponent)
  
  }
  }


