<template>
    <div class="new-onboardee">
      <div class="form-container">
        <h1>New Onboardee</h1>
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
            <label for="phone">Phone Number:</label>
            <InputText id="phone" v-model="phone" required />
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
  
  export default {
    data() {
      return {
        name: '',
        email: '',
        phone: ''
      };
    },
    methods: {
      handleSubmit() {
        // Handle form submission here
      },
      goBack() {
        this.$router.go(-1);
      }
    },
    components: {
      InputText,
      Button
    }
  };
  </script>
  
  <style scoped>
  h1 {
    color: #033A65;
    font-weight: 600;
    margin-bottom: 2rem;
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
    padding: 2rem;
    border-radius: 20px;
    height: 85%;
    width: 30%;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  
  .form-group {
    margin-bottom: 1.5rem;
  }
  
  label {
    display: block;
    margin-bottom: 0.5rem;
    color: #033A65;
  }
  
  .go-back {
    display: flex;
    align-items: center;
    cursor: pointer;
  }
  
  .go-back i {
    margin-right: 0.5rem;
  }
  
  .button-container {
    width: 100%;
    display: flex;
    justify-content: space-between;
    margin-top: 1rem;
  }
  
  button {
    background-color: #033A65;
    color: white;
    border: none;
  }
  </style>
  