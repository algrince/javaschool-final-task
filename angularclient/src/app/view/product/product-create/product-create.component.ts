import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../../../model/product';
import { Category } from '../../../model/category';
import { Property } from '../../../model/property';
import { PropertyValue } from '../../../model/property-value';
import { ProductService } from '../../../service/product.service';
import { CategoryService } from '../../../service/category.service';
import { PropertyService } from '../../../service/property.service';
import { PropertyValueService } from '../../../service/property-value.service';
import { FormGroup, FormControl, FormArray, FormBuilder } from '@angular/forms';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-product-create',
  templateUrl: './product-create.component.html',
  styleUrls: ['./product-create.component.css']
})
export class ProductCreateComponent implements OnInit {

    product: Product;
    categories: Category[];
    property: Property;
    properties: Property[];
    propertyValue: PropertyValue;
    propertyValues: PropertyValue[];
    selectedPropertyValues: any[];
    propertiesForm: FormGroup;
    subscription: Subscription;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private productService: ProductService,
        private categoryService: CategoryService,
        private propertyService: PropertyService,
        private propertyValueService: PropertyValueService,
        private formBuilder: FormBuilder) {
            this.product = new Product();
             }

    ngOnInit() {
        this.categoryService.findAll()
            .subscribe(data => {this.categories = data});

        this.propertyService.findAll()
            .subscribe(data => {this.properties = data});

        this.propertyValueService.findAll()
            .subscribe(data => {this.propertyValues = data});

        this.selectedPropertyValues = Array.from(this.properties, () => []);

    }

    onSubmit() {

        this.productService.save(this.product)
            .subscribe(result => this.gotoProductList());
    }

    gotoProductList() {
        this.router.navigate(['/products']);
    }

    onCheckboxChange(event, property, propertyValue) {
    const propertyIndex = this.properties.findIndex(p => p.id === property.id);
  if (event.target.checked) {
    this.selectedPropertyValues[propertyIndex].push(propertyValue);
  } else {
    const valueIndex = this.selectedPropertyValues[propertyIndex].indexOf(propertyValue);
    if (valueIndex >= 0) {
      this.selectedPropertyValues[propertyIndex].splice(valueIndex, 1);
    }
  }
  this.product.propertyValues = this.selectedPropertyValues.flat();
}


}