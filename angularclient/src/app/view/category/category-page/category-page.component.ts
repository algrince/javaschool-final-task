import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Product } from '../../../model/product';
import { Category } from '../../../model/category';
import { PropertyValue } from '../../../model/property-value';
import { ProductService } from '../../../service/product.service';
import { CategoryService } from '../../../service/category.service';
import { ImageService } from '../../../service/image.service';
import { PropertyValueService } from '../../../service/property-value.service';
import { CookieService } from 'ngx-cookie-service';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-category-page',
  templateUrl: './category-page.component.html',
  styleUrls: ['./category-page.component.css']
})
export class CategoryPageComponent implements OnInit{

    products: Product[];
    categories: Category[];
    propertyValues: PropertyValue[];
    selectedValues: number[] = [];
    category: Category;
    categoryId: number;
    totalElements = 0;
    page = 0;
    size = 9;
    sortField = "id";
    sortDir = "ASC";
    imageSrc: any;
    minPrice: any;
    maxPrice: any;
    role: string;
    roleExists: boolean;
    productImages: { [productId: number]: string } = {};

    constructor(
        private productService: ProductService,
        private categoryService: CategoryService,
        private imageService: ImageService,
        private cookieService: CookieService,
        private sanitizer: DomSanitizer,
        private router: Router,
        private route: ActivatedRoute,
        private propertyValueService: PropertyValueService) {
            this.category = new Category();
    }

    ngOnInit() {

        this.route.paramMap.subscribe(
            (routeParam) => {
                const id = parseInt(routeParam.get("id"));
                this.categoryId = id;
                this.categoryService.findOneCategory(this.categoryId)
                            .subscribe(
                            (data) => {
                            this.category = data;
                             this.getProducts({category: this.categoryId, page: this.page, size: this.size});
                            });

                this.categoryService.findAll()
                    .subscribe(data => {this.categories = data})

                this.propertyValueService.findAll()
                    .subscribe(data => {this.propertyValues = data});
            }
        )

        this.roleExists = this.cookieService.check("userRole");
        this.role = this.cookieService.get("userRole");
    }

    changeCategory(id: number) {
        this.router.navigate(['/home']);
        this.router.navigate(['/categories/{id}']);
        this.page = 0;
        this.categoryId = id;
        this.categoryService.findOneCategory(id)
            .subscribe((data) => {
                this.category = data;
                this.getProducts({category: id, page: 0, size: this.size});
                });

        this.categoryService.findAll()
            .subscribe(data => {this.categories = data})

        this.propertyValueService.findAll()
            .subscribe(data => {this.propertyValues = data});
    }

    private getProducts(request) {

        this.productService.findAllByCategory(request)
            .subscribe(data => {
                this.products = data['content'];
                this.totalElements = data['totalElements'];
                this.getImages();
            });
    }

    getImage(id: number) {
        this.imageService.getImage({id: id})
            .subscribe(data =>
                {this.imageSrc = this.sanitizer
                    .bypassSecurityTrustResourceUrl(`data:image/png;base64, ${data}`);
                this.productImages[id] = this.imageSrc;
                });
    }

    getImages() {
        for (var product of this.products) {
            let id = product.id;
            this.getImage(id);
        }
    }



    onSubmit() {
        this.page = 0;

        this.getProducts
            ({category: this.categoryId,
            page: this.page, size: this.size,
            sortField: this.sortField, sortDir: this.sortDir,
            minPrice: this.minPrice, maxPrice: this.maxPrice,
            prValues: this.selectedValues});
    }

    public onPageChange(pageNum: number): void {
        this.getProducts(
        {category: this.categoryId,
        page: (pageNum - 1), size: this.size,
        sortField: this.sortField, sortDir: this.sortDir,
        minPrice: this.minPrice, maxPrice: this.maxPrice,
        prValues: this.selectedValues})
    }

        updateSelectedValues(selectedValueId: number) {
          if (selectedValueId) {
            const index = this.selectedValues.findIndex(pv => pv === selectedValueId);
            if (index > -1) {
              // If the property value is already in the array, remove it
              this.selectedValues.splice(index, 1);
            } else {
              // If the property value is not in the array, add it
              this.selectedValues.push(selectedValueId);
            }
          }
        }
}
