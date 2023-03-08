import { Component } from '@angular/core';
import { Category } from '../model/category';
import { CategoryService } from '../service/category.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-category-create',
  templateUrl: './category-create.component.html',
  styleUrls: ['./category-create.component.css']
})
export class CategoryCreateComponent {

    category: Category;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private categoryService: CategoryService) {
            this.category = new Category();
        }

    onSubmit() {
        this.categoryService.save(this.category)
            .subscribe(result => this.gotoProductList());
    }

    gotoProductList() {
        this.router.navigate(['/products']);
    }
}
