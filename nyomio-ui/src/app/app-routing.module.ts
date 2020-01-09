import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OrganizationPageComponent} from 'nyomio-ng-components';
import {UserPageComponent} from 'nyomio-ng-components';
import {AuthGuard, EntityEditorGuard} from 'nyomio-ng-components';
import {DeviceComponent} from "./page/device/device.component";

export const ROUTES = {
  organizations: 'organizations',
  users: 'users',
  devices: 'devices',
};

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: '/' + ROUTES.organizations
  },
  {
    component: OrganizationPageComponent,
    path: ROUTES.organizations,
    canActivate: [AuthGuard, EntityEditorGuard]
  },
  {
    component: UserPageComponent,
    path: ROUTES.users,
    canActivate: [AuthGuard, EntityEditorGuard]
  },
  {
    component: DeviceComponent,
    path: ROUTES.devices,
    canActivate: [AuthGuard, EntityEditorGuard]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
