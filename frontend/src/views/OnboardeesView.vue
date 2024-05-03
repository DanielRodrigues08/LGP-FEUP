<template>
  <div class="onboardees">
    <div class="title-container">
      <h1>Incoming onboardees</h1>
      <router-link to="/add-onboardee">
          <Button class="add-button" label="New onboardee" icon="pi pi-plus" />
      </router-link>
    </div>
    
    <div class="search-container">
      <div>
        <InputText v-model="globalFilter.value" placeholder="Search for name" @input="filterOnboardees" />
      </div>
    </div>
    <DataTable class="custom-table" :value="onboardees" :globalFilter="globalFilter" @filter="filterOnboardees">
      <Column field="id" header="ID"></Column>
      <Column field="name" header="Name"></Column>
      <Column field="email" header="Email"></Column>
      <Column field="phoneNumber" header="Phone"></Column>
    </DataTable>
  </div>
</template>

<script>
import DataTable from 'primevue/datatable';
import Column from 'primevue/column'; 
import axios from 'axios';
import Button from 'primevue/button';
import InputText from 'primevue/inputtext';

export default {
  components: {
    DataTable,
    Column,
    Button,
    InputText
  },
  data() {
    return {
      onboardees: [],
      originalOnboardees: [],
      globalFilter: { value: null, matchMode: 'contains', field: 'name' },
      selectedOnboardee: null
    };
  },
  mounted() {
    this.fetchOnboardees();

  },
  methods: {
    async fetchOnboardees() {
      try {
        const response = await axios.get('http://localhost:8081/onboardees');
        this.onboardees = response.data;
        this.originalOnboardees = response.data;

      } catch (error) {
        console.error('Error fetching onboardees:', error);
      }
    },
    filterOnboardees() {
      this.globalFilter = {
        value: this.globalFilter.value,
        matchMode: 'contains',
        field: 'name'
      };
      if (this.globalFilter.value === '') {
        this.onboardees = this.originalOnboardees;
      } else {
        this.onboardees = this.originalOnboardees.filter(item => item.name.toLowerCase().includes(this.globalFilter.value.toLowerCase()));
      }    }
      }

};
</script>

<style scoped>
  h1 {
    color: #033A65;
    font-weight:600;
    margin-right: 2rem;
  }
  .add-button {
    background-color: #033A65;
    color: white;
    border-color: rgba(0, 0, 0, 0.1); 
  }
  .title-container {
    display: flex;
    align-items: center;
    margin-top: 1.5rem;
    margin-left: 2rem;
  }
  .search-container {
    display: flex;
    margin-top: 1rem;
    margin-left: 2rem;
  }
  .search-container > div {
    margin-right: 1rem;
  }
  .custom-table {
    max-width: 90%;
    margin: 0 auto;
    margin-top: 1em;
  }
</style>
