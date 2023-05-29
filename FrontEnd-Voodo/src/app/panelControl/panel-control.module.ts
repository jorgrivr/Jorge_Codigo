import { NgModule} from '@angular/core'
import { RouterModule } from '@angular/router'
import{CommonModule} from '@angular/common'
import { FlexLayoutModule } from '@angular/flex-layout'
import { PanelControlComponent } from './panel-control.component'
import { PanelControlRoutes } from './panel-control.routing'
import { MaterialModule } from '../shared/material-module'

@NgModule({
    imports:[
        CommonModule,
        FlexLayoutModule,
        MaterialModule,
        RouterModule.forChild(PanelControlRoutes)
    ],
    declarations:[PanelControlComponent]
})
export class PanelControlModule{}