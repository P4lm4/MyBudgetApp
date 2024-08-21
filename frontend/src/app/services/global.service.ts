import { Injectable } from '@angular/core';
import { Currency } from '../models/Currency.interface';

@Injectable({
  providedIn: 'root',
})
export class GlobalService {
  API_URL = 'http://localhost:8080/api/';

  constructor() {}

  async getAllCurrencies(): Promise<Currency[]> {
    const response = await this.apiFetch<Object>('currency/all');
    return Object.entries(response).map(([key, value]) => {
      return { currencyKey: key, fullName: value as string };
    });
  }

  async apiFetch<T>(Url: string, method = 'GET', data: any = null): Promise<T> {
    try {
      const response = await fetch(this.API_URL + Url, {
        method,
        headers: {
          'Content-Type': 'application/json',
        },
        body: data != null ? JSON.stringify(data) : null,
      });

      // Check if the response is in JSON format
      const contentType = response.headers.get('content-type');
      if (response.ok) {
        if (contentType && contentType.includes('application/json')) {
          return response.json() as Promise<T>;
        } else {
          return response.text() as unknown as T; // cast to T if necessary
        }
      } else {
        // Handle HTTP errors
        const errorText = await response.text();
        throw new Error(errorText);
      }
    } catch (err) {
      console.error('API fetch error:', err);
      throw err;
    }
  }
}
