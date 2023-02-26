import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component'
import { UserListComponent } from './user-list/user-list.component';
import { UserFormComponent } from './signup/user-form.component';
import { LoginComponent } from './login/login.component';
import { UserPageComponent} from './user-page/user-page.component';
import { UserDeleteComponent } from './user-delete/user-delete.component';
import { AddressFormComponent } from './address-form/address-form.component';
import { ProductListComponent } from './product-list/product-list.component';


const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'users', component: UserListComponent },
  { path: 'registration', component: UserFormComponent },
  { path: 'login', component: LoginComponent },
  { path: 'users/:id', component: UserPageComponent },
  { path: 'users/:id/delete', component: UserDeleteComponent },
  { path: 'addresses/new', component: AddressFormComponent },
  { path: 'products', component: ProductListComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
