// src/stores/auth.js
import { defineStore } from 'pinia';
import axios from 'axios';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null,
    token: localStorage.getItem('token') || null,
  }),
  actions: {
    async login(email, password) {
      try {
        const response = await axios.post(`${import.meta.env.VITE_API_URL}/auth/signin`, { email, password });
        this.token = response.data.accessToken;
        this.user = response.data;
        localStorage.setItem('token', this.token);
        localStorage.setItem('user', JSON.stringify(this.user)); // Store user info in local storage
      } catch (error) {
        if (+error.response.status == 401) {
          return "Invalid credentials";
        }
      }
    },
    async loadStoredUser() {
      const storedUser = localStorage.getItem('user');
      if (storedUser !== null && storedUser !== undefined) {
        try {
          this.user = JSON.parse(storedUser);
        } catch (error) {
          console.error('Error parsing stored user data:', error);
          localStorage.removeItem('user'); // Remove corrupted data
        }
      }
    },
    logout() {
      this.token = null;
      this.user = null;
      localStorage.removeItem('token');
      localStorage.removeItem('user');
    },
    authData() {
      return {'Authorization': `Bearer ${localStorage.getItem('token')}`}
    } 
  },
});
