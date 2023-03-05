import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Product } from '../model/product';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

   private productsUrl: string;

   constructor(private http: HttpClient) {
        this.productsUrl = 'http://localhost:8080/products'
    }


   public findAll(): Observable<Product[]> {
        return this.http.get<Product[]>(this.productsUrl);
   }

   public findOneProduct(id: number): Observable<Product> {
        const detailUrl = `${this.productsUrl}/${id}`;
        return this.http.get<Product>(detailUrl);
   }
}
