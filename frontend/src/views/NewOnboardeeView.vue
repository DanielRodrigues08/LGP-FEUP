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
        <div class="form-group">
          <label for="gender">Gender:</label>
          <select id="gender" v-model="gender">
            <option value="female">Female</option>
            <option value="male">Male</option>
            <option value="other">Other</option>
          </select>
        </div>
        <div class="form-group">
          <label for="nationality">Nationality:</label>
          <select id="nationality" v-model="nationality">
            <option v-for="country in countries" :value="country.name" :key="country.name">{{ country.name }}</option>
          </select>
        </div>
        <div class="form-group">
          <label for="annualSalary">Annual Salary:</label>
          <InputText id="annualSalary" v-model="annualSalary" />
        </div>
        <div class="form-group">
          <label for="payrollNumber">Payroll Number:</label>
          <InputText id="payrollNumber" v-model="payrollNumber" />
        </div>
        <div class="form-group">
          <label for="startDate">Start Date:</label>
          <InputText id="startDate" type="date" v-model="startDate" />
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
      countries: [],
      name: '',
      email: '',
      phone: '',
      gender: '',
      nationality: '',
      annualSalary: '',
      payrollNumber: '',
      startDate: ''
    };
  },
  mounted() {
    axios
      .get('https://api.openaq.org/v1/countries')
      .then(response => (this.countries = response.data.results))
      .catch(console.log);
  },
  methods: {
    async handleSubmit() {
      const requestData = {
        username: this.name, //TODO: ask if make sense to have username or not
        password: "password",
        name: this.name,
        phoneNumber: this.phone,
        email: this.email,
        gender: this.gender,
        nationality: this.nationality,
        annualSalary: this.annualSalary,
        payrollNumber: this.payrollNumber,
        startDate: this.startDate
      };

      await axios.post('http://localhost:8081/onboardees', requestData)
        .then(response => {
          this.$router.push({ name: 'onboardees' });
        })
        .catch(error => {
          console.error('Error adding Onboardee:', error);
        });
    },
    goBack() {
      this.$router.go(-1);
    }
  },
  components: {
    InputText,
    Button,
  };
  </script>
  
  <style scoped>
  h1 {
    color: #033A65;
    font-weight: 600;
    margin-bottom: 1rem;
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
    height: 92%;
    width: 35%;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  
  .form-group {
    margin-bottom: 0.8rem;
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
};
</script>

<style scoped>
h1 {
  color: #033A65;
  font-weight: 600;
  margin-bottom: 0.5rem;
  font-size: 1.4rem;
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
  height: 92%;
  width: 30%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.form-group {
  margin-bottom: 0.3rem;
}

label {
  display: block;
  margin-bottom: 0.2rem;
  color: #033A65;
  font-size: 0.9rem;
}

select,
input[type="date"] {
  width: 100%; /* Adjust the width here */
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
  margin-top: 1rem;
}

button {
  background-color: #033A65;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  font-size: 0.8rem;
  border-radius: 5px;
}

</style>
