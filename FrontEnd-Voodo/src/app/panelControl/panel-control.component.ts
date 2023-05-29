import { Component } from '@angular/core';
import { AfterViewInit} from '@angular/core'
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SnackbarService } from '../services/snackbar.service';
import { GlobalConstants } from '../shared/global-constants';
import { PanelControlService } from '../services/panelcontrol.service';

@Component({
  selector: 'app-panel-control',
  templateUrl: './panel-control.component.html',
  styleUrls: ['./panel-control.component.scss']
})
export class PanelControlComponent implements AfterViewInit {
  constructor(private panelControlService:PanelControlService,
    private ngxService:NgxUiLoaderService,
    private snackbarService:SnackbarService,
    ){
      this.ngxService.start();
      this.panelControlData();

    }

  responseMessage:any;
  data:any;

  panelControlData(){
    this.panelControlService.getDetalles().subscribe((response:any)=>{
      this.ngxService.stop();
      this.data=response;
    },(error:any)=>{
      this.ngxService.stop();
      console.log(error);
      if(error.error?.message){
        this.responseMessage = error.error?.message
      }else{
        this.responseMessage=GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage,GlobalConstants.error);
    })
  }
  
  ngAfterViewInit(): void {
  }
}

