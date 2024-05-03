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
            <label for="role">Role:</label>
            <select id="role" v-model="role" required>
              <option value="read">Read</option>
              <option value="hr_member">HR Member</option>
              <option value="admin">Admin</option>
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
  
  export default {
    data() {
      return {
        name: '',
        email: '',
        role: 'read'
      };
    },
    methods: {
      async handleSubmit() {
        const requestData = {
          name: this.name,
          email: this.email,
          role: this.role
        };
  
        // // Assuming you have an endpoint for handling login
        // await axios.post('http://localhost:8081/login', requestData)
        //   .then(response => {
        //     // Handle successful login
        //     console.log('Login successful');
        //   })
        //   .catch(error => {
        //     // Handle login error
        //     console.error('Error during login:', error);
        //   });
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
  height: 70%;
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
  