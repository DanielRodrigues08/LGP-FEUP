<template>
    <div class="users">
      <div class="title-container">
        <h1>Job Scheduling</h1>
      </div>

      <div class="search-container">
        <div>
          <InputText v-model="globalFilter.value" placeholder="Search for name" @input="filterProcesses" />
        </div>
      </div>

      <DataTable class="custom-table" removableSort :value="processes" paginator :rows="10" :rowsPerPageOptions="[5,10,20,50]" :globalFilter="globalFilter" @filter="filterUsers">
        <Column field="step" sortable header="Title" class="custom-header" :headerStyle="{ backgroundColor: '#033A65', color:'white', width:'20%' }" ></Column>
        <Column field="onboardee" header="Onboardee" class="custom-header" :headerStyle="{ backgroundColor: '#033A65', color:'white', width:'20%'}"></Column>
        <Column field="due_date" header="Due date" class="custom-header " :headerStyle="{ backgroundColor: '#033A65', color:'white', width:'20%'}"></Column>
        <Column field="owner" header="Owner" class="custom-header " :headerStyle="{ backgroundColor: '#033A65', color:'white', width:'20%'}"></Column>
        <Column field="backup" header="Backup" class="custom-header " :headerStyle="{ backgroundColor: '#033A65', color:'white', width:'20%'}"></Column>
      </DataTable>

    </div>
  </template>
  
  <script>
  import DataTable from 'primevue/datatable';
  import Column from 'primevue/column'; 
  import axios from 'axios';
  import Button from 'primevue/button';
  import InputText from 'primevue/inputtext';
  import { useAuthStore } from '@/stores/auth';

  export default {
    components: {
      DataTable,
      Column,
      Button,
      InputText
    },
    data() {
      return {
        processes: [],
        originalProcesses: [],
        globalFilter: { value: null, matchMode: 'contains', field: 'title' }
      };
    },
    setup() {
      const authStore = useAuthStore();
      return { authStore };
    },
    mounted() {
      this.fetchProcesses();
    },
    methods: {
      async fetchProcesses() {
        try {
          const response = await axios.get(`${import.meta.env.VITE_API_URL}/processes`, {headers: this.authStore.authData()});
          this.processes = response.data.map(process =>({
            ...process,
            stepsCount: process.stepsInProcess.length,
          }));
          console.log(this.processes)
          this.originalProcesses = response.data.map(process =>({
            ...process,
            stepsCount: process.stepsInProcess.length,
          }));
          } catch (error) {
          console.error('Error fetching processes:', error);
        }
      },
      filterProcesses() {
        this.globalFilter = {
          value: this.globalFilter.value,
          matchMode: 'contains',
          field: 'title'
        };
        if (this.globalFilter.value === '') {
          this.processes = this.originalProcesses;
        } else {
          this.processes = this.originalProcesses.filter(item => item.title.toLowerCase().includes(this.globalFilter.value.toLowerCase()));
        }
      }
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
      margin-bottom: 1em;

    }

    .center-text {
      text-align: center;
    }





  </style>
  