import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../../service/user.service';
import { User } from '../../../model/user';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent {

  user: User;
  errors: string[];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService) {
    this.user = new User();
  }

  onSubmit() {
    this.userService.save(this.user)
        .subscribe(
            result => {
                if (Array.isArray(result)) {
                    this.errors = result;
                } else {
                    this.gotoUserList();
                }
            })
  }

  gotoUserList() {
    this.router.navigate(['/home']);
  }

}
