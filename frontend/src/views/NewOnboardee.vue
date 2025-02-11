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
        <div class="form-group">
          <label for="process">Process:</label>
          <select id="process" v-model="processId">
            <option v-for="proc in processes" :value="proc.id" :key="proc.id">{{ proc.title }}</option>
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
      countries: [],
      processes: [],
      name: '',
      email: '',
      phone: '',
      gender: '',
      nationality: '',
      processId: '',
      annualSalary: '',
      payrollNumber: '',
      startDate: ''
    };
  },
  setup() {
    const authStore = useAuthStore();
    return { authStore };
  },
  mounted() {
    axios
      .get('https://api.openaq.org/v1/countries')
      .then(response => (this.countries = response.data.results))
      .catch(console.log);
    
    axios
      .get(`${import.meta.env.VITE_API_URL}/processes`, { headers: this.authStore.authData() })
      .then(response => (this.processes = response.data))
      .catch(console.log);
  },
  methods: {
    async handleSubmit() {
      const requestData = {
        name: this.name,
        phoneNumber: this.phone,
        email: this.email,
        gender: this.gender,
        nationality: this.nationality,
        annualSalary: this.annualSalary,
        payrollNumber: this.payrollNumber,
        startDate: this.startDate,
        processId: this.processId,
      };

      await axios.post(`${import.meta.env.VITE_API_URL}/onboardees`, requestData, {headers: this.authStore.authData()})
        .then(response => {
          this.$router.push(`/onboardees/${response.data.id}`);
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
    Button
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
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.form-container {
  background-color: white;
  padding: 1.5rem;
  border-radius: 20px;
  margin-top: 3em;
  margin-bottom: 3em;
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
