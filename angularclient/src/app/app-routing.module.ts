import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminGuard } from './util/admin.guard';
import { AuthGuard } from './util/auth.guard';

import { HomeComponent } from './view/home/home.component'

import { UserListComponent } from './view/user/user-list/user-list.component';
import { UserFormComponent } from './view/user/signup/user-form.component';
import { UserPageComponent} from './view/user/user-page/user-page.component';
import { UserUpdateComponent } from './view/user/user-update/user-update.component';
import { UserDeleteComponent } from './view/user/user-delete/user-delete.component';
import { LoginComponent } from './view/user/login/login.component';
import { UserChangePasswordComponent } from './view/user/user-change-password/user-change-password.component';
import { UserChangeRoleComponent } from './view/user/user-change-role/user-change-role.component';

import { AddressFormComponent } from './view/address/address-form/address-form.component';
import { AddressUpdateComponent } from './view/address/address-update/address-update.component';
import { AddressDeleteComponent } from './view/address/address-delete/address-delete.component';
import { AddressAdminListComponent } from './view/address/address-admin-list/address-admin-list.component';

import { ProductListComponent } from './view/product/product-list/product-list.component';
import { ProductCreateComponent } from './view/product/product-create/product-create.component';
import { ProductDetailComponent } from './view/product/product-detail/product-detail.component';
import { ProductUpdateComponent } from './view/product/product-update/product-update.component';
import { ProductDeleteComponent } from './view/product/product-delete/product-delete.component';

import { CategoryCreateComponent } from './view/category/category-create/category-create.component';
import { CategoryPageComponent } from './view/category/category-page/category-page.component';
import { CategoryUpdateComponent } from './view/category/category-update/category-update.component';
import { CategoryDeleteComponent } from './view/category/category-delete/category-delete.component';
import { CategoryAdminListComponent } from './view/category/category-admin-list/category-admin-list.component';

import { PropertyListComponent } from './view/property/property-list/property-list.component';
import { PropertyCreateComponent } from './view/property/property-create/property-create.component';
import { PropertyUpdateComponent } from './view/property/property-update/property-update.component';
import { PropertyDeleteComponent } from './view/property/property-delete/property-delete.component';

import { PropertyValueListComponent } from './view/property-value/property-value-list/property-value-list.component';
import { PropertyValueCreateComponent } from './view/property-value/property-value-create/property-value-create.component';
import { PropertyValueUpdateComponent } from './view/property-value/property-value-update/property-value-update.component';
import { PropertyValueDeleteComponent } from './view/property-value/property-value-delete/property-value-delete.component';

import { OrderFormComponent } from './view/order/order-form/order-form.component';
import { OrderUserListComponent } from './view/order/order-user-list/order-user-list.component'
import { OrderUpdateComponent } from './view/order/order-update/order-update.component';
import { OrderAdminListComponent } from './view/order/order-admin-list/order-admin-list.component';

import { UnauthorizedComponent } from './util/unauthorized/unauthorized.component';
import { NotFoundComponent } from './util/not-found/not-found.component';

import { AdminPageComponent } from './view/admin-page/admin-page.component';
import { RevenueComponent } from './view/statistics/revenue/revenue.component';
import { TopProductsComponent } from './view/statistics/top-products/top-products.component';
import { TopBuyersComponent } from './view/statistics/top-buyers/top-buyers.component';



const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'registration', component: UserFormComponent },
  { path: 'login', component: LoginComponent },
  { path: 'users/:id', component: UserPageComponent },
  { path: 'users/:id/update', component: UserUpdateComponent},
  { path: 'users/:id/delete', component: UserDeleteComponent},
  { path: 'users/:id/changePassword', component: UserChangePasswordComponent},
  { path: 'admin/users/:id/changeRole', component: UserChangeRoleComponent, canActivate: [AdminGuard] },
  { path: 'addresses/new', component: AddressFormComponent },
  { path: 'addresses/:id/update', component: AddressUpdateComponent },
  { path: 'addresses/:id/delete', component: AddressDeleteComponent },
  { path: 'products', component: ProductListComponent },
  { path: 'admin/products/new', component: ProductCreateComponent, canActivate: [AdminGuard] },
  { path: 'products/:id', component: ProductDetailComponent },
  { path: 'admin/products/:id/update', component: ProductUpdateComponent, canActivate: [AdminGuard] },
  { path: 'admin/products/:id/delete', component: ProductDeleteComponent, canActivate: [AdminGuard] },
  { path: 'admin/categories/new', component: CategoryCreateComponent, canActivate: [AdminGuard] },
  { path: 'categories/:id', component: CategoryPageComponent },
  { path: 'admin/categories/:id/update', component: CategoryUpdateComponent, canActivate: [AdminGuard] },
  { path: 'admin/categories/:id/delete', component: CategoryDeleteComponent, canActivate: [AdminGuard] },
  { path: 'admin/properties', component: PropertyListComponent, canActivate: [AdminGuard] },
  { path: 'admin/properties/new', component: PropertyCreateComponent, canActivate: [AdminGuard] },
  { path: 'admin/properties/:id/update', component: PropertyUpdateComponent, canActivate: [AdminGuard] },
  { path: 'admin/properties/:id/delete', component: PropertyDeleteComponent, canActivate: [AdminGuard] },
  { path: 'admin/propertyValues', component: PropertyValueListComponent, canActivate: [AdminGuard] },
  { path: 'admin/propertyValues/new', component: PropertyValueCreateComponent, canActivate: [AdminGuard] },
  { path: 'admin/propertyValues/:id/update', component: PropertyValueUpdateComponent, canActivate: [AdminGuard] },
  { path: 'admin/propertyValues/:id/delete', component: PropertyValueDeleteComponent, canActivate: [AdminGuard] },
  { path: 'orders/new', component: OrderFormComponent },
  { path: 'users/:id/orders', component: OrderUserListComponent },
  { path: 'orders/:id/update', component: OrderUpdateComponent },
  { path: 'unauthorized', component: UnauthorizedComponent },
  { path: 'admin/users', component: UserListComponent, canActivate: [AdminGuard] },
  { path: 'admin/addresses', component: AddressAdminListComponent, canActivate: [AdminGuard] },
  { path: 'admin/orders', component: OrderAdminListComponent, canActivate: [AdminGuard] },
  { path: 'admin/categories', component: CategoryAdminListComponent, canActivate: [AdminGuard] },
  { path: 'admin', component: AdminPageComponent, canActivate: [AdminGuard]},
  { path: 'admin/statistics/revenue', component: RevenueComponent, canActivate: [AdminGuard] },
  { path: 'admin/statistics/top-10-products', component: TopProductsComponent, canActivate: [AdminGuard] },
  { path: 'admin/statistics/top-10-buyers', component: TopBuyersComponent, canActivate: [AdminGuard] },
  { path: 'not-found', component: NotFoundComponent},
  { path: '**', pathMatch: 'full', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
