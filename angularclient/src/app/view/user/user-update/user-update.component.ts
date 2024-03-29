import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../../../model/user';
import { UserService } from '../../../service/user.service';

@Component({
  selector: 'app-user-update',
  templateUrl: './user-update.component.html',
  styleUrls: ['./user-update.component.css']
})
export class UserUpdateComponent implements OnInit {

    userId: number;
    user: User;
    errors: string[];

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private userService: UserService) {
            this.user = new User();
        }

    ngOnInit() {
        this.userId = this.route.snapshot.params['id'];

        this.userService.findOneUser(this.userId)
            .subscribe(data => {this.user = data;});
    }

    onSubmit() {
        this.userService.update(this.userId, this.user)
            .subscribe(
                result => {
                    if (Array.isArray(result)) {
                        this.errors = result;
                    } else {
                        this.gotoUserDetails();
                    }
                })
    }

    gotoUserDetails() {
        const usersUrl = '/users';
        this.router.navigate([`${usersUrl}/${this.userId}`]);
    }
}
