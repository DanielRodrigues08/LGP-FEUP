<template>
    <div class="new-user">
      <div class="form-container">
        <h1>New User</h1>
        <form @submit.prevent="handleSubmit">
          <div class="form-group">
            <label for="name">Name:</label>
            <InputText id="name" v-model="name" required />
          </div>
          <div class="form-group">
            <label for="email">Email:</label>
            <InputText id="email" v-model="email" required />
          </div>
          <div class="form-group">
            <label for="permissionLevel">Permissions:</label>
            <select id="permissionLevel" v-model="permissionLevel" required>
              <option value="ADMIN">Admin</option>
              <option value="HR">HR Member</option>
              <option value="EMPLOYEE">Employee</option>
            </select>
          </div>
          <div class="button-container">
            <div class="go-back" @click="goBack">
              <i class="pi pi-arrow-left"></i>
              <span>Go Back</span>
            </div>
            <Button type="submit" label="Submit" />
          </div>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  import Button from 'primevue/button';
  import InputText from 'primevue/inputtext';  
  import axios from 'axios';
  import { useAuthStore } from '@/stores/auth';
  
  export default {
    data() {
      return {
        name: '',
        email: '',
        permissionLevel: 'HR'
      };
    },
    methods: {
      async handleSubmit() {
        const requestData = {
          name: this.name,
          email: this.email,
          permissionLevel: this.permissionLevel
        };
        // TODO
      },
      goBack() {
        this.$router.go(-1);
      }
    },
    components: {
      InputText,
      Button,
    }
  };
  </script>
  
  <style scoped>
  h1 {
  color: #033A65;
  font-weight: 600;
  margin-bottom: 1.5rem;
  font-size: 1.5rem;
  }

  .new-onboardee {
  background-color: #033A65;
  height: calc(100vh - 3.25rem);
  display: flex;
  justify-content: center;
  align-items: center;
  }

  .form-container {
  background-color: white;
  padding: 1.5rem;
  border-radius: 20px;
  width: 30%;
  display: flex;
  flex-direction: column;
  align-items: center;
  }

  .form-group {
  margin-bottom: 1.4rem;
  }

  label {
  display: block;
  margin-bottom: 0.3rem;
  color: #033A65;
  font-size: 0.9rem;
  }

  select,
  input[type="date"] {
  width: 100%;
  padding: 0.5rem;
  font-size: 0.8rem;
  border-radius: 5px;
  border: 1px solid #ccc;
  }

  .p-inputtext {
  padding: 0.5rem 0.5rem;
  }

  .go-back {
  display: flex;
  align-items: center;
  cursor: pointer;
  font-size: 0.8rem;
  }

  .go-back i {
  margin-right: 0.5rem;
  }

  .button-container {
  width: 100%;
  display: flex;
  justify-content: space-between;
  margin-top: 3rem;
  }

  button {
  background-color: #033A65;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  font-size: 0.9rem;
  border-radius: 5px;
  }
.new-user {
  background-color: #033A65;
  height: calc(100vh - 3.25rem);
  display: flex;
  justify-content: center;
  align-items: center;
}

</style>
  