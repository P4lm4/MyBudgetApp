import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

export interface NavItem {
  title: string;
  url: string;
}

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss',
})
export class NavbarComponent {
  public items: NavItem[] = [
    {
      title: 'Accounts',
      url: '/accounts',
    },
    {
      title: 'Transactions',
      url: '/transactions',
    },
    {
      title: 'Settings',
      url: '/settings',
    },
  ];
}
