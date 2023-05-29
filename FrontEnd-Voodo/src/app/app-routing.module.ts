import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { FullComponent } from './layouts/full/full.component';
import { RouteGuardService } from './services/route-guard.service';




const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'voodo',
    component: FullComponent,
    children: [
      {
        path: '',
        redirectTo: '/voodo/panel-control',
        pathMatch: 'full',
      },
      {
        path: '',
        loadChildren:
          () => import('./material-component/material.module').then(m => m.MaterialComponentsModule),
          canActivate: [RouteGuardService],
          data: {
            expectedRole: ['admin', 'user']
          }
        
      },
      {
        path: 'panel-control',
        loadChildren: () => import('./panelControl/panel-control.module').then(m => m.PanelControlModule),
        canActivate: [RouteGuardService],
        data: {
          expectedRole: ['admin', 'user']
        }
      }
    ]
  },
  { path: '**', component: HomeComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }